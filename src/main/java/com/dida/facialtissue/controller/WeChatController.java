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
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");
        //拉取用户信息
        String userinfo_url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + token +
                "&openid=" + openid +
                "&lang=zh_CN";
        JSONObject userinfo = WeChatHttpUtil.httpRequest(userinfo_url, RequestMethodEnum.GET.getName(), null);
        System.out.println(userinfo);
    }
}