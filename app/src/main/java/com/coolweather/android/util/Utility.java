package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();

                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    /**
     * 处理市级信息
     */
    public static boolean handleCityResponse(String response,int provinceId)
    {

        if(!TextUtils.isEmpty(response))
        {
            try{
                JSONArray cityObjects=new JSONArray(response);
                for(int i=0;i<cityObjects.length();i++)
                {
                    JSONObject cityObject=cityObjects.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();

                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    /**
     * 处理县级信息
     */
    public static boolean handleCountyResponse(String response,int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try{
                JSONArray countyObjects=new JSONArray(response);
                for(int i=0;i<countyObjects.length();i++)
                {
                    JSONObject countyObject=countyObjects.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
