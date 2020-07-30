package by.start.shirostudy.utils;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @ClassName: GsonUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ljt
 * @date 2018-11-5 上午8:26:17 * *
 */
public class GsonUtil {

	// 不用创建对象,直接使用Gson.就可以调用方法
	private static Gson gson = null;
	// 判断gson对象是否存在了,不存在则创建对象
	static {
		if (gson == null) {
			// gson = new Gson();
			// 当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create();
		}
	}

	// 无参的私有构造方法
	private GsonUtil() {
	}

	/**
	 * 将对象转成json格式
	 * 
	 * @param object
	 * @return String
	 */
	public static String objectToGson(Object object) {
		String gsonString = null;
		if (gson != null) {
			gsonString = gson.toJson(object);
		}
		return gsonString;
	}

	/**
	 * 将json转成特定的cls的对象
	 * 
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> T gsonToBean(String gsonString, Class<T> cls) {
		T t = null;
		if (gson != null) {
			// 传入json对象和对象类型,将json转成对象
			t = gson.fromJson(gsonString, cls);
		}
		return t;
	}

	/**
	 * json字符串转成list
	 * 
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		if (gson != null) {

			JsonParser parser = new JsonParser();
			JsonArray jsonarray = parser.parse(gsonString).getAsJsonArray();
			for (JsonElement element : jsonarray) {
				list.add(gson.fromJson(element, cls));
			}

			// 根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
			/*
			 * list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
			 * }.getType());
			 */
		}
		return list;
	}

	/**
	 * list转为对象
	 *  @param List<T> ts
	 *  @return jsons
	 */
	public static <T> String listToGson(List<T> ts) {
		String jsons = gson.toJson(ts);
		return jsons;
	}

	
	 /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
 
}

