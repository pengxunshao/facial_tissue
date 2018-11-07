package com.dida.facialtissue.WeChatEntity;

public class WeChatContant {
    //公司服务器地址
    public static final String DIDA_URL = "www.kunshandida.com";
     //APPID
    public static final String appID = "wx1cf3b2c518a43718";
    //appsecret
    public static final String appsecret = "7d223e8ea5c3b8dae1c799ed7eab2df8";
    // Token
    public static final String TOKEN = "dida";
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    public static final Object REQ_MESSAGE_TYPE_TEXT = "text";
    public static final Object REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final Object REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final Object REQ_MESSAGE_TYPE_VIDEO = "video";
    public static final Object REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final Object REQ_MESSAGE_TYPE_LINK = "link";
    public static final Object REQ_MESSAGE_TYPE_EVENT = "event";
    public static final Object EVENT_TYPE_SUBSCRIBE = "SUBSCRIBE";
    public static final Object EVENT_TYPE_UNSUBSCRIBE = "UNSUBSCRIBE";
    public static final Object EVENT_TYPE_SCAN = "SCAN";
    public static final Object EVENT_TYPE_LOCATION = "LOCATION";
    public static final Object EVENT_TYPE_CLICK = "CLICK";
    
    public static final String FromUserName = "FromUserName";
    public static final String ToUserName = "ToUserName";
    public static final String MsgType = "MsgType";
    public static final String Content = "Content";
    public static final String Event = "Event";

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 微信地址（GET）
    public final static String authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //通过OpenID来获取用户基本信息。
    public final static String userinfoByopenID_url ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

}