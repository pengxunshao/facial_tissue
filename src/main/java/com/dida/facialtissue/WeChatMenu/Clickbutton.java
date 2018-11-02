package com.dida.facialtissue.WeChatMenu;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 9:09
 **/
public class Clickbutton extends AbstractButton {
    private String type = "click";
    private String key ;

    public Clickbutton(String name, String key) {
        super(name);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
