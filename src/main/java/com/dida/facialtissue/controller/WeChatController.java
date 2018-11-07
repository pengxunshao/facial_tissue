package com.dida.facialtissue.controller;

import com.alibaba.fastjson.JSONObject;
import com.dida.facialtissue.WeChatEntity.WeChatContant;
import com.dida.facialtissue.WeChatEntity.WeChatUserInfo;
import com.dida.facialtissue.commons.RedisTemplateHelper;
import com.dida.facialtissue.enums.RequestMethodEnum;
import com.dida.facialtissue.service.ISubscribeService;
import com.dida.facialtissue.service.IWeChatService;
import com.dida.facialtissue.util.WeChatHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/wechat")
public class WeChatController {
    @Value("${server.port}")
    private String port;

    @Autowired
    ISubscribeService subscribeService;

    @Autowired
    IWeChatService iWeChatService;

    @Autowired
    RedisTemplateHelper redisTemplateHelper;

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     * <p>
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping(value = "/")
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

    @PostMapping(value = "/")
    public void validate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        subscribeService.Subscribe(request, response);
    }

    @GetMapping(value = "/wxLogin")
    public void wxLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //回调地址，必须要在公网上能进行访问
        // String backUrl = WeChatContant.DIDA_URL+":"+port+"/callBack";
        String backUrl = WeChatContant.DIDA_URL + "/wechat/callBack";
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
                "&secret=" + WeChatContant.appsecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = WeChatHttpUtil.httpRequest(url, RequestMethodEnum.GET.getName(), null);
        System.out.println(jsonObject);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");
        //拉取用户信息
        String userInfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid + "&lang=zh_CN";
        JSONObject jsonObject1 = WeChatHttpUtil.httpRequest(userInfo_url, RequestMethodEnum.GET.getName(), null);
        WeChatUserInfo weixinUserInfo = null;
        if (null != jsonObject1) {
            weixinUserInfo = new WeChatUserInfo();
            // 用户的标识
            weixinUserInfo.setOpenId(jsonObject1.getString("openid"));
            // 昵称
            weixinUserInfo.setNickname(jsonObject1.getString("nickname"));
            // 用户的性别（1是男性，2是女性，0是未知）
            weixinUserInfo.setSex(jsonObject1.getInteger("sex"));
            // 用户所在省份
            weixinUserInfo.setProvince(jsonObject1.getString("province"));
            // 用户所在城市
            weixinUserInfo.setCity(jsonObject1.getString("city"));
            // 用户所在国家
            weixinUserInfo.setCountry(jsonObject1.getString("country"));
            // 用户头像
            weixinUserInfo.setHeadImgUrl(jsonObject1.getString("headimgurl"));
        }

        System.out.println(weixinUserInfo);
    }
}