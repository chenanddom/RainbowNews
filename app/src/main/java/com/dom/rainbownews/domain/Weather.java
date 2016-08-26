package com.dom.rainbownews.domain;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class Weather {
    //日期
    private String date;
    //风的变化
    private String wind;
    //气温情况
    private String tmp;
    //云的变化
    private String cond;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getWind() {
        return wind;
    }
    public void setWind(String wind) {
        this.wind = wind;
    }
    public String getTmp() {
        return tmp;
    }
    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
    public String getCond() {
        return cond;
    }
    public void setCond(String cond) {
        this.cond = cond;
    }
    @Override
    public String toString() {
        return "Weather [date=" + date + ", wind=" + wind + ", tmp=" + tmp
                + ", cond=" + cond + "]";
    }
}

