package com.wellsoft.pt.utils.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
	/**
	 * 
	* @Title: toCollection
	* @Description: 代码有问题不使用
	* @param @param JSONStr
	* @param @return    设定文件
	* @return Collection    返回类型
	* @throws
	 */
	public static Collection toCollection(String JSONStr) {
		Collection rtn = new ArrayList();

		//		JSONArray array = JSONArray.fromObject(JSONStr);
		//		for (int i = 0; i < array.length(); i++) {
		//			Object obj = array.get(i);
		//			if (obj instanceof JSONObject) {
		//				rtn.add(toMap((JSONObject) obj));
		//			} else {
		//				rtn.add(obj);
		//			}
		//		}

		return rtn;
	}

	public static Collection toCollection(String JSONStr, Class objClass) {
		JSONArray array = JSONArray.fromObject(JSONStr);
		List list = JSONArray.toList(array, objClass);
		return list;
	}

	public static Map toMap(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return toMap(jsonObject);
	}

	private static Map toMap(JSONObject jsonObject) {
		Map rtn = new HashMap();

		if (jsonObject.isNullObject()) {
			return null;
		}
		for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object obj = jsonObject.get(key);

			if (obj instanceof JSONArray) {
				rtn.put(key, toArray((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				rtn.put(key, toMap((JSONObject) obj));
			} else {
				rtn.put(key, obj);
			}
		}

		return rtn;
	}

	private static Object[] toArray(JSONArray jsonArray) {
		//		Object[] rtn = new Object[jsonArray.length()];
		//
		//		for (int i = 0; i < jsonArray.length(); i++) {
		//			Object obj = jsonArray.get(i);
		//			if (obj instanceof JSONObject) {
		//				rtn[i] = toMap((JSONObject) obj);
		//			} else {
		//				rtn[i] = obj;
		//			}
		//		}
		Object[] rtn = null;
		return rtn;
	}

	public static Object toBean(String jsonStr, Class objClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Object obj = JSONObject.toBean(jsonObject, objClass);
		return obj;
	}

	public static String collection2Json(Collection collection) {
		return collection2Json(collection, new String[] {});
	}

	public static String collection2Json(Collection collection, String[] excludes) {
		//		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray = JSONArray.fromObject(collection);
		return jsonArray.toString();
	}

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String object2Json(Object object) {
		StringWriter writer = new StringWriter();
		try {
			objectMapper.writeValue(writer, object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	public static <T extends Object> T json2Object(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
