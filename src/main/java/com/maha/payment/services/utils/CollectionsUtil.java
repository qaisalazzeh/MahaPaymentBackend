package com.maha.payment.services.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author qazzeh
 *
 */
public class CollectionsUtil {

	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	@SuppressWarnings("rawtypes")
	public static String getMapAsJson(Map<String, ?> map) throws IOException {
		String json = new JsonJacksonParser<HashMap>(HashMap.class).parseObjectToJSON((HashMap) map);
		return json;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Map> String getAbstractMapAsJson(T map) throws IOException {
		String json = new JsonJacksonParser<T>((Class<T>) map.getClass()).parseObjectToJSON((T) map);
		return json;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getJsonAsMap(String json) throws IOException {
		return new JsonJacksonParser<Map>(Map.class).parseJSONToObject(json);
	}

	public static Object getValueFromMap(Map<String, Object> map, String key, Class<?> clazz) {
		if (map.get(key) != null) {
			if (clazz.equals(Long.class)) {
				return Long.valueOf(map.get(key).toString());
			}
			if (clazz.equals(String.class)) {
				return map.get(key).toString();
			}
			if (clazz.equals(Integer.class)) {
				return Integer.valueOf(map.get(key).toString());
			}
			if (clazz.equals(Date.class)) {
				return map.get(key);
			}
		}
		return null;

	}

	public static String convertParamsToJson(String paramIn) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<NameValuePair> results = URLEncodedUtils.parse(paramIn, Charset.defaultCharset());
		Map<String, String> map = new HashMap<String, String>();
		for (NameValuePair valuePair : results) {
			map.put(valuePair.getName(), valuePair.getValue());
		}
		String result = mapper.writeValueAsString(map);

		return result;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> fromObjectToMap(Object object) {

		if (object == null) {
			return Collections.EMPTY_MAP;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = objectMapper.convertValue(object, Map.class);
		return map;
	}

	public static Object fromMapToObject(Map<?, ?> map, Class<?> objectType) {

		if (map == null) {
			return null;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Object object = objectMapper.convertValue(map, objectType);
		return object;
	}

	public static Object fromJsonStringToObject(String json, Class<?> objectType)
			throws JsonParseException, JsonMappingException, IOException {

		if (json == null || json.isEmpty() || objectType == null) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(json, objectType);
		return object;
	}

	/**
	 * 
	 * This to avoid extrem length data that will make oracle error
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> removeExtremLength(Map<String, Object> map) {
		if (map == null || map.toString().length() < 3750) {
			return null;
		}
		Map<String, Object> removedData = new HashMap<>();
		Map<String, Object> copyData = new HashMap<>(map);
		for (String key : copyData.keySet()) {
			removedData.put(key, map.get(key));
			map.remove(key);
			if (map.toString().length() < 3750) {
				break;
			}
		}
		return removedData;
	}

	public static boolean isNotEmptyList(List<String> list) {
		return !isEmptyList(list);
	}

	public static boolean isEmptyList(List<String> list) {
		return list == null || list.isEmpty();
	}

	public static String getStringValue(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			return (String) object;
		}
		return object.toString();
	}

	/**
	 * to JSON
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static String resolveJsonContentType(Map<String, Object> params) throws IOException {
		return new JsonJacksonParser<Map>(Map.class).parseObjectToJSON(params);
	}

	/**
	 * get headers
	 * 
	 * @param headerValue
	 * @return
	 */
	public static HttpHeaders getHeader(String headerValue) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, headerValue + ";charset=UTF-8");
		return headers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void convertToOneLevelWithQualifiedNameFull(Map<String, Object> props, Map<String, Object> finalMap,
			String parent) {
		for (Map.Entry<String, Object> entry : props.entrySet()) {
			if (entry.getValue() instanceof Map) {
				convertToOneLevelWithQualifiedNameFull((Map<String, Object>) entry.getValue(), finalMap,
						addMeToParentName(parent, entry));
			} else if (entry.getValue() instanceof List) {
				List<Map<String, Object>> list = (List) entry.getValue();
				for (Map<String, Object> subMap : list) {
					if (subMap != null) {
						convertToOneLevelWithQualifiedNameFull(subMap, finalMap, addMeToParentName(parent, entry));
					}
				}
			} else
				finalMap.put(addMeToParentName(parent, entry), entry.getValue().toString());

		}

	}

	private static String addMeToParentName(String parent, Map.Entry<String, Object> entry) {
		if (parent == null) {
			return entry.getKey();
		}
		return parent + "." + entry.getKey();
	}

}
