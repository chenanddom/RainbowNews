package com.dom.rainbownews.domain;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class MyCity {
    // 城市的名字
    private String name;
    // 城市名字对应的拼音
    private String namep;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamep() {
        return namep;
    }

    public void setNamep(String namep) {
        this.namep = namep;
    }

    @Override
    public String toString() {
        return "MyCity [name=" + name + ", namep=" + namep + "]";
    }
}
