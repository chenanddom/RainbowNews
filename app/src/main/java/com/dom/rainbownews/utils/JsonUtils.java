package com.dom.rainbownews.utils;

/**
 * Created by Administrator on 2016/8/26 0026.
 */


import com.dom.rainbownews.domain.City;
import com.dom.rainbownews.domain.MyCity;
import com.dom.rainbownews.domain.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<Province> getProvincesJsonData(String provinces) {
        List<Province> list = new ArrayList<Province>();
        try {
            JSONArray array = new JSONArray(provinces);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Province province = new Province();
                province.setProID(obj.getString("ProID"));
                province.setName(obj.getString("name"));
                province.setProSort(obj.getString("ProSort"));
                province.setProRemark(obj.getString("ProRemark"));
                list.add(province);
            }
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static List<City> getCitysJsonData(String citys) {
        List<City> list = new ArrayList<City>();
        try {
            JSONArray array = new JSONArray(citys);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                City city = new City();
                city.setCityID(obj.getString("CityID"));
                city.setName(obj.getString("name"));
                city.setProID(obj.getString("ProID"));
                city.setCitySort(obj.getString("CitySort"));
                list.add(city);
            }
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static List<MyCity> getCityP(String content){
        try {
            List<MyCity> list = new ArrayList<MyCity>();
            JSONArray array = new JSONArray(content);
            for(int i=0;i<array.length();i++){
                MyCity myCity = new MyCity();
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");
                String namep = obj.getString("pinyin");
                myCity.setName(name);
                myCity.setNamep(namep);
                list.add(myCity);
            }
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
}