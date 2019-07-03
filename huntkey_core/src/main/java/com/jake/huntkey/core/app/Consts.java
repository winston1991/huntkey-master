package com.jake.huntkey.core.app;

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
}
