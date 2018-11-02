package com.dida.facialtissue.controller;

import com.alibaba.fastjson.JSONObject;
import com.dida.facialtissue.WeChatEntity.WeChatContant;
import com.dida.facialtissue.enums.RequestMethodEnum;
import com.dida.facialtissue.service.ISubscribeService;
import com.dida.facialtissue.util.WeChatHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class WeChatController {
    @Autowired
    ISubscribeService subscribeService;

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     * <p>
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping(value = "/wechat")
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) {
        boolean checkResult = WeChatHttpUtil.checkSignature(signature, timestamp, nonce);
        if (checkResult) {
            System.out.println("微信服务验证成功！");
            return echostr;
        }
        return null;
    }

    @PostMapping(value = "/wechat")
    public void validate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        subscribeService.Subscribe(request, response);
    }

    @GetMapping(value = "/wxLogin")
    public void wxLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //回调地址，必须要在公网上能进行访问
        String backUrl = "http://aamqei.natappfree.cc/callBack";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + WeChatContant.appID +
                "&redirect_uri=" + backUrl +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect ";
        resp.sendRedirect(url);
    }

    @GetMapping(value = "/callBack")
    public void callBack(@RequestParam(value = "code") String code, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //String code = req.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + WeChatContant.appID +
                "&secret=SECRET" + WeChatContant.appsecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = WeChatHttpUtil.httpRequest(url, RequestMethodEnum.GET.getName(), null);
        System.out.println(jsonObject);
        /* 正确时返回的JSON数据包如下：
       {
        "access_token":"ACCESS_TOKEN",  -- 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        "expires_in":7200,  -- access_token接口调用凭证超时时间，单位（秒）
        "refresh_token":"REFRESH_TOKEN",    -- 用户刷新access_token
        "openid":"OPENID",  -- 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
        "scope":"SCOPE"     --用户授权的作用域，使用逗号（,）分隔
        }
       * */
        /*
        错误时微信会返回JSON数据包如下（示例为Code无效错误）:
        {"
        errcode":40029,"errmsg":"invalid code"
        }
        */
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");

        //拉取用户信息
        String userinfo_url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + token +
                "&openid=" + openid +
                "&lang=zh_CN";
        JSONObject userinfo = WeChatHttpUtil.httpRequest(userinfo_url, RequestMethodEnum.GET.getName(), null);
        System.out.println(userinfo);
        /*
        正确时返回的JSON数据包如下：
        {
        "openid":" OPENID",    -- 用户的唯一标识
        " nickname": NICKNAME, -- 用户昵称
        "sex":"1",             -- 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        "province":"PROVINCE"  -- 用户个人资料填写的省份
        "city":"CITY",         -- 普通用户个人资料填写的城市
        "country":"COUNTRY",   -- 国家，如中国为CN
        "headimgurl":    "http://thirdwx.qlogo.cn/mmopen/46",   -- 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像）
                                                                ，用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
        "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],  -- 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
        "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"     -- 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
        }
        */
    }
}