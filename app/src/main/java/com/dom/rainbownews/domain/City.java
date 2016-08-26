package com.dom.rainbownews.domain;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class City {
    //城市的ID号
    private String CityID;
    //城市的名字
    private String name;
    //城市所属省的分的ID号
    private String ProID;
    //城市的序号
    private String CitySort;

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProID() {
        return ProID;
    }

    public void setProID(String proID) {
        ProID = proID;
    }

    public String getCitySort() {
        return CitySort;
    }

    public void setCitySort(String citySort) {
        CitySort = citySort;
    }

    @Override
    public String toString() {
        return "City [CityID=" + CityID + ", name=" + name + ", ProID=" + ProID
                + ", CitySort=" + CitySort + "]";
    }
}

