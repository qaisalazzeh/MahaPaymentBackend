package com.maha.payment.services.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author Qais Azzeh
 *
 * @param <T>
 */
public class JsonGsonParser<T> {

	private static final Logger log = LoggerFactory.getLogger(JsonGsonParser.class);
	private Class<T> type;

	public JsonGsonParser(Class<T> type) {
		this.type = type;
	}

	public String parseObjectToJSON(T entity) throws IOException {
		log.trace("parse entity Object to json object");
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(entity);
		return json;
	}

	public T parseJSONToObject(String json) throws IOException {
		log.trace("parse json object to entity Object");
		Gson gson = new Gson();
		T entity = gson.fromJson(json, type);
		return entity;
	}

}
