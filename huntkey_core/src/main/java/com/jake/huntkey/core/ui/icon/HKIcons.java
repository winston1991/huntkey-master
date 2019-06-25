package com.jake.huntkey.core.ui.icon;

import com.joanzapata.iconify.Icon;

import java.util.ArrayList;


public enum HKIcons implements Icon {

    icon_shuzikanban('\ue632'),
    icon_RectangleCopy('\ue6e0'),
    icon_tubiao4('\ue60b'),
    icon_tubiao3('\ue612'),
    icon_liucheng('\ue772');

    private char character;

    HKIcons(char character) {
        this.character = character;

    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
