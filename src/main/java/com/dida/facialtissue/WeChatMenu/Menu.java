package com.dida.facialtissue.WeChatMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 按钮的基类
 */
public class Menu {
    private List<AbstractButton> button = new ArrayList<>();

    public List<AbstractButton> getButton() {
        return button;
    }

    public void setButton(List<AbstractButton> button) {
        this.button = button;
    }

    /*public static void main(String[] args) {
        Menu menu = new Menu();
        menu.getButton().add(new Clickbutton("一级点击", "1"));
        menu.getButton().add(new Viewbutton("一级跳转", "http://www.baudu.com"));
        Subbutton subbutton = new Subbutton("菜单");
        subbutton.getSub_button().add(new Clickbutton("菜单点击", "2"));
        subbutton.getSub_button().add(new Viewbutton("菜单跳转", "http://www.souhu.com"));
        menu.getButton().add(subbutton);
        String s = JSONObject.toJSONString(menu);
        System.out.println(s);

    }*/
}