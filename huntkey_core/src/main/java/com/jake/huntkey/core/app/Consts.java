package com.jake.huntkey.core.app;

import android.content.SharedPreferences;

import com.jake.huntkey.core.ui.icon.HKIcons;

import java.util.ArrayList;

public class Consts {
    public static String[] items = {"深圳消费", "深圳SMT", "河源消费", "深圳电源"};

    public static ArrayList<String> homeItems = new ArrayList<>();

    static {
        homeItems.add("{" + HKIcons.icon_factory.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_factory.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_factory.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_factory.name().replace('_', '-') + "}");


    }


    public static  String SP_INSTANT_NAME = "loginToken";
    public static  String SP_ITEM_TOKEN_NAME = "Authorization";
    public static  String SP_ITEM_DEPTCODE_NAME = "deptCode";
}
