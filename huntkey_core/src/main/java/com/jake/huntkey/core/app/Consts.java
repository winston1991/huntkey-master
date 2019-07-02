package com.jake.huntkey.core.app;

import com.jake.huntkey.core.ui.icon.HKIcons;

import java.util.ArrayList;

public class Consts {
    public static String[] items = {"看板", "图标", "我的", "预警", "流程"};

    public static ArrayList<String> homeItems = new ArrayList<>();

    static {
        homeItems.add("{" + HKIcons.icon_board.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_chart.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_mine.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_prewarning.name().replace('_', '-') + "}");
        homeItems.add("{" + HKIcons.icon_process.name().replace('_', '-') + "}");
    }
}
