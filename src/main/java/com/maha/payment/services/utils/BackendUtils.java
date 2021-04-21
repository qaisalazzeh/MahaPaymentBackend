package com.maha.payment.services.utils;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Qais Azzeh
 *
 */
public class BackendUtils {

	/**
	 * 
	 * @param apiResponse
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getObjectAsMap(Object apiResponse) {
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> map = oMapper.convertValue(apiResponse, Map.class);
		return map;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isObjectEmpty(Object value) {
		if (Objects.isNull(value) || value.equals("") || value.equals(" ")) {
			return true;
		}
		return false;
	}

}