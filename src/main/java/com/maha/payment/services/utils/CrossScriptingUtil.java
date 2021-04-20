package com.maha.payment.services.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Qais Azzeh
 *
 */
public class CrossScriptingUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrossScriptingUtil.class);

	/**
	 * Clean request from XSS
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object cleanXSS(Object value) {
		LOGGER.info("Clean Xss Request Before Start Processing....");
		if (value == null) {
			return null;
		}
		if (value instanceof Map) {
			for (String key : ((Map<String, Object>) value).keySet()) {
				((Map<String, Object>) value).put(cleanXSS(key).toString(),
						cleanXSS(((Map<String, Object>) value).get(key)));
			}
		} else if (value instanceof List) {
			List cleanedValues = new LinkedList();
			for (Object vlu : ((List<?>) value)) {
				cleanedValues.add(cleanXSS(vlu));
			}
			((List) value).clear();
			((List) value).addAll(cleanedValues);
		}
		return value;
	}

}
