package com.puzek.platform.inspection.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonUtil {
    private static Gson gson = (new GsonBuilder()).disableHtmlEscaping().create();

    public JsonUtil() {
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String str, Class<T> clz) {
        return gson.fromJson(str, clz);
    }

    public static <T> T fromJson(String str, Type type) {
        return gson.fromJson(str, type);
    }

    public static <T> T fromJson(Reader r, Class<T> clz) {
        return gson.fromJson(r, clz);
    }

}
