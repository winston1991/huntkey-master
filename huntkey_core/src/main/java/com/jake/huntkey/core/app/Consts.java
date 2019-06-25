package com.jake.huntkey.core.app;

import com.jake.huntkey.core.ui.icon.HKIcons;

import java.util.ArrayList;

public class Consts {
    public static String[] items = {"图表", "看板", "流程", "发现", "预警"};

    public static ArrayList<String> homeItems = new ArrayList<>();

    static {
        homeItems.add("{" + HKIcons.icon_liucheng.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_RectangleCopy.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_shuzikanban.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_tubiao3.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_tubiao4.name().replace('_', '-') + "}");
    }
}
