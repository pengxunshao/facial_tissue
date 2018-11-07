package com.dida.facialtissue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dida.facialtissue.WeChatEntity.WeChatContant;
import com.dida.facialtissue.WeChatEntity.WeChatUserInfo;
import com.dida.facialtissue.service.IWeChatService;
import com.dida.facialtissue.util.WeChatHttpUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("weChatService")
@Scope("prototype")
public class WeChatServiceImpl implements IWeChatService<WeChatUserInfo> {

    @Override
    public WeChatUserInfo getUserInfoService(String token, String openId) {

        WeChatUserInfo weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = WeChatContant.userinfoByopenID_url;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = WeChatHttpUtil.httpRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeChatUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInteger("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    //log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
                    System.err.printf("用户{}已取消关注", weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getInteger("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    // log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                    System.err.printf("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }

}
