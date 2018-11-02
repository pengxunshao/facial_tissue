package com.dida.facialtissue.WeChatMenu;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 9:09
 **/
public class Viewbutton extends AbstractButton {
    private String type = "view";
    private String url ;


    public Viewbutton(String name, String key) {
        super(name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
