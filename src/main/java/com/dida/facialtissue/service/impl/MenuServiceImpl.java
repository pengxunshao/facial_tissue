package com.dida.facialtissue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dida.facialtissue.WeChatMenu.Menu;
import com.dida.facialtissue.entity.WeChatContant;
import com.dida.facialtissue.service.IMenuService;
import com.dida.facialtissue.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Administrator
 * @createTime:2018/11/1 15:44
 **/
public class MenuServiceImpl implements IMenuService {
    private static Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Override
    public int createMenu(Menu menu, String accessToken) {
        String menu_create_url = WeChatContant.menu_create_url;
        int result = 0;

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.toJSONString(menu);
        // 调用接口创建菜单
        JSONObject jsonObject = WeChatUtil.httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                result = jsonObject.getInteger("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
}
