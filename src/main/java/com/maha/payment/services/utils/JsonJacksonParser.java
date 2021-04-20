package com.maha.payment.services.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Qais Azzeh
 *
 * @param <T>
 */
public class JsonJacksonParser<T> {

	private static final Logger log = LoggerFactory.getLogger(JsonJacksonParser.class);
	private Class<T> type;

	public JsonJacksonParser(Class<T> type) {
		this.type = type;
	}

	public String parseObjectToJSON(T entity) throws IOException {
		log.trace("parse entity Object to json object");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(entity);
		return json;
	}

	public T parseJSONToObject(String json) throws IOException {
		log.trace("parse json object to entity Object");
		ObjectMapper mapper = new ObjectMapper();
		T entity = mapper.readValue(json, type);
		return entity;
	}

}
