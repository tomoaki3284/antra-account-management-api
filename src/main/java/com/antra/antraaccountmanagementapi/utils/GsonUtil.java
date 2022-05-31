package com.antra.antraaccountmanagementapi.utils;

import com.google.gson.Gson;

public class GsonUtil {

    private static GsonUtil obj;

    private final Gson gson = new Gson();

    private GsonUtil() {

    }

    public static GsonUtil getObj() {
        if(obj == null) {
            synchronized (GsonUtil.class) {
                if(obj == null) {
                    obj = new GsonUtil();
                }
            }
        }
        return obj;
    }

    public Object convertJsonToObject(String json, Class clazz) {
        if(json == null || clazz == null || json.equals("")) {
            throw new IllegalArgumentException("json or clazz input must not be null or empty");
        }
        return gson.fromJson(json, clazz);
    }

    public Gson getGson() {
        return this.gson;
    }
}
