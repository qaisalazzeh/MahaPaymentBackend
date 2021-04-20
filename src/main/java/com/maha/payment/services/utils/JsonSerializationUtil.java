package com.maha.payment.services.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Qais Azzeh
 *
 */
public class JsonSerializationUtil {

	static Logger logger = LoggerFactory.getLogger(JsonSerializationUtil.class);

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String serialize(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("error in serlizing object {} ", obj, e);
			return "";

		}
	}

	/**
	 * 
	 * @param <T>
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T deserialize(String json, Class<T> type) {
		try {
			return (T) mapper.readValue(json, type);
		} catch (IOException e) {
			logger.error("error in deserlizing object {} ", json, e);
			return null;
		}
	}
}
