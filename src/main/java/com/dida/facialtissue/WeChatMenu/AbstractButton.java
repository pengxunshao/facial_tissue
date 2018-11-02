package com.dida.facialtissue.WeChatMenu;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 9:02
 **/
public abstract class AbstractButton {
    private String name;

    public AbstractButton(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
