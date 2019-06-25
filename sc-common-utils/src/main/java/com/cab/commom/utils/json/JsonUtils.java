package com.cab.commom.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtils {

    private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * 获取gson解析器
     * @param
     * @return
     */
    public static Gson getGson(){
        return gson;
    }

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String bean2Json(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象(普通类型)
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T json2Bean(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * JSON转换为对象-针对泛型的类型
     * @param json
     * @param type
     * @return
     */
    public static <T> T json2Bean(String json, Type type){
        return gson.fromJson(json, type);
    }

    /**
     * 转成list
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        List<T> list = gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
        return list;
    }

    /**
     * 转成set
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> Set<T> jsonToSet(String json, Class<T> clazz) {
        Set<T> set = gson.fromJson(json, new TypeToken<Set<T>>() {}.getType());
        return set;
    }

    /**
     * 转成map的
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> jsonToMap(String json, Class<T> clazz) {
        Map<String, T> map = gson.fromJson(json, new TypeToken<Map<String, T>>() {}.getType());
        return map;
    }


//    public static void main(String[] args) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("aaa","==");
//        System.err.println(jsonObject.toJSONString());
//    }


}
