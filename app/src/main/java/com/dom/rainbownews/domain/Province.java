package com.dom.rainbownews.domain;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class Province {
    //省的ID号
    private String ProID;
    //省的名字
    private String name;
    //省的序号
    private String ProSort;
    //省的的标志(是直属省会还是自治区或者是直辖市)
    private String ProRemark;
    public String getProID() {
        return ProID;
    }
    public void setProID(String proID) {
        ProID = proID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProSort() {
        return ProSort;
    }
    public void setProSort(String proSort) {
        ProSort = proSort;
    }
    public String getProRemark() {
        return ProRemark;
    }
    public void setProRemark(String proRemark) {
        ProRemark = proRemark;
    }
    @Override
    public String toString() {
        return "Province [ProID=" + ProID + ", name=" + name + ", ProSort="
                + ProSort + ", ProRemark=" + ProRemark + "]";
    }
}
