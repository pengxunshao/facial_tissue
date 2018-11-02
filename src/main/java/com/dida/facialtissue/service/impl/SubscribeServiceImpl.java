package com.dida.facialtissue.service.impl;/**
 * @description:
 * @author: Administrator
 * @createTime:2018/11/1 11:56
 **/

import com.alibaba.fastjson.JSONObject;
import com.dida.facialtissue.WeChatEntity.AccessToken;
import com.dida.facialtissue.commons.RedisTemplateHelper;
import com.dida.facialtissue.WeChatEntity.WeChatContant;
import com.dida.facialtissue.enums.RequestMethodEnum;
import com.dida.facialtissue.service.ISubscribeService;
import com.dida.facialtissue.util.MessageUtil;
import com.dida.facialtissue.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/1 11:56
 **/
@Service
public class SubscribeServiceImpl implements ISubscribeService {
    @Autowired
    RedisTemplateHelper redisTemplateHelper;

    //关注,订阅
    @Override
    public String Subscribe(HttpServletRequest request, HttpServletResponse response) {
        // xml请求解析
        Map<String, String> requestMap = null;
        try {
            requestMap = MessageUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            System.out.println(fromUserName);
            String token = redisTemplateHelper.getValue("accessToken");
            if (null == token) {
                AccessToken accessToken = WeChatUtil.getAccessToken();
                token = accessToken.getToken();
                redisTemplateHelper.setValue("accessToken", accessToken.getToken(), accessToken.getExpiresIn());
            }
            //消息类型是事件Event
            if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
                // 接收用户发送的事件请求内容
                String Event = requestMap.get("Event");
                System.out.println("Event:" + Event);
                //如果是关注,订阅
                if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(Event)) {
                    String userinfoByopenID_url = WeChatContant.userinfoByopenID_url;
                    String url = userinfoByopenID_url.replace("ACCESS_TOKEN", token).replace("OPENID", fromUserName);
                    JSONObject jsonObject = WeChatUtil.httpRequest(url, RequestMethodEnum.GET.getName(), null);
                    System.out.println(jsonObject);
                }
            }
        } catch (Exception e) {
            System.out.println("parseXml异常!");
            e.printStackTrace();
        }
        return null;
    }
}
