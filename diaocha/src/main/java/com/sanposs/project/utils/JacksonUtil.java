package com.sanposs.project.utils;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 项目名称：test 类名称：JacksonUtil 类描述： jackson2 创建人：yahui 创建时间：2015年8月31日 下午8:20:20
 * 修改人：yahui 修改时间：2015年8月31日 下午8:20:20 修改备注：
 * 
 * @version
 * 
 */
public class JacksonUtil { 
	private static ObjectMapper mapper = new ObjectMapper();

	public static String bean2Json(Object obj) {
		// 美化成多行
		// String aa =
		// mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		// System.out.println(aa);
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"message\":\"系统转换异常\",\"code\":\"77889900\",\"data\":\"\"}";
		}
	}

	public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
		try {
			return mapper.readValue(jsonStr, objClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String json) {
		Map<String, Object> maps = null;
		try {
			maps = mapper.readValue(json, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return maps;
	}
	
	@SuppressWarnings("unchecked")
	public static String map2json(Map map) {
		String jsonfromMap = null;
		try {
			jsonfromMap = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return jsonfromMap;
	}
	
	
	/**
	 * json分析
	 * @param rescontent
	 * @param key
	 * @return
	 */
	public static String getJsonValue(String rescontent, String key) {
		JSONObject jsonObject;
		String v = null;
		try {
			jsonObject = new JSONObject(rescontent);
			v = jsonObject.getString(key);
		} catch (Exception e) {
			return "-100";
		}
		return v;
	}
}