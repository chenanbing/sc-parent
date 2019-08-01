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
     * RestResponse<Base64Utils> r = JsonUtils.json2Bean(str4,new TypeToken<RestResponse<Base64Utils>>(){}.getType());
     * @param json
     * @param type
     * @return
     */
    public static <T> T json2Bean(String json, Type type){
        return gson.fromJson(json, type);
    }

    /**
     * 转成list List<Base64Utils>  l = JsonUtils.jsonToList(str2,new TypeToken<List<Base64Utils>>(){});
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> List<T> jsonToList(String json, TypeToken<List<T>> typeToken) {
        List<T> list = gson.fromJson(json, typeToken.getType());
        return list;
    }

    /**
     * 转成set Set<Base64Utils>  s = JsonUtils.jsonToSet(str2,new TypeToken<Set<Base64Utils>>(){});
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> Set<T> jsonToSet(String json,TypeToken<Set<T>> typeToken) {
        Set<T> set = gson.fromJson(json, typeToken.getType());
        return set;
    }

    /**
     * 转成map的 Map<String, Base64Utils> m = JsonUtils.jsonToMap(str,new TypeToken<Map<String,Base64Utils>>(){});
     * @param json
     * @param typeToken
     * @return
     */
    public static <K,T> Map<K, T> jsonToMap(String json, TypeToken<Map<K,T>> typeToken) {
        Map<K, T> map = gson.fromJson(json,typeToken.getType() );
        return map;
    }


    public static void main(String[] args) {
//        Map<String, Base64Utils> map = new HashMap<>();
//        map.put("1",new Base64Utils());
//        String str = JsonUtils.bean2Json(map);
//        System.out.println(str);
//
//        Map<String, Base64Utils> m = JsonUtils.jsonToMap(str,new TypeToken<Map<String,Base64Utils>>(){
//        });
//        System.out.println(1);
//
//        Set<Base64Utils> set = new HashSet<>();
//        set.add(new Base64Utils());
//        String str2 = JsonUtils.bean2Json(set);
//        System.out.println(str2);
//        Set<Base64Utils>  s = JsonUtils.jsonToSet(str2,new TypeToken<Set<Base64Utils>>(){});
//        System.out.println(2);
//
//
//        List<Base64Utils> list = new ArrayList<>();
//        list.add(new Base64Utils());
//        String str3 = JsonUtils.bean2Json(list);
//        System.out.println(str3);
//        List<Base64Utils>  l = JsonUtils.jsonToList(str2,new TypeToken<List<Base64Utils>>(){});
//        System.out.println(3);
//
//        RestResponse<Base64Utils> restResponse = new RestResponse<>();
//        restResponse.setCode(0);
//        Base64Utils  base64Utils = new Base64Utils();
//        restResponse.setData(base64Utils);
//
//        String str4 = JsonUtils.bean2Json(restResponse);
//        RestResponse<Base64Utils> r = JsonUtils.json2Bean(str4,new TypeToken<RestResponse<Base64Utils>>(){}.getType());
//        System.out.println(4);


    }


}
