package com.jake.huntkey.core.entity;

public class HomePageItemEntity {
    //item字体图标的代码
    public String icon = "";
    //item的名字
    public String name = "";

    //服务器id
    public  String sid = "";
    public  String accid = "";

    @Override
    public String toString() {
        return "HomePageItemEntity{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", sid='" + sid + '\'' +
                ", accid='" + accid + '\'' +
                ", extra=" + extra +
                '}';
    }

    //扩展字段
    public Object extra;
}
