package com.dida.facialtissue.WeChatMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 9:18
 **/
public class Subbutton extends AbstractButton{
    private List<AbstractButton> sub_button = new ArrayList<>();

    public Subbutton(String name) {
        super(name);
    }

    public List<AbstractButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<AbstractButton> sub_button) {
        this.sub_button = sub_button;
    }
}
