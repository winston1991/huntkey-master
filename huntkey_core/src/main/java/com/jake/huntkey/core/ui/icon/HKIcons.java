package com.jake.huntkey.core.ui.icon;

import com.joanzapata.iconify.Icon;

import java.util.ArrayList;


public enum HKIcons implements Icon {

    icon_board('\ue604'),
    icon_mine('\ue608'),
    icon_prewarning('\ue72a'),
    icon_process('\ue64c'),
    icon_chart('\ue61e'),
    icon_factory('\ue610');

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
