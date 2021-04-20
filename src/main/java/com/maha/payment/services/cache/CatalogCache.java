package com.maha.payment.services.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.maha.payment.services.mongo.entities.WatchCatalog;

/**
 * 
 * @author qazzeh
 *
 */
public class CatalogCache {

	public static Map<String, WatchCatalog> CATALOG = new ConcurrentHashMap<String, WatchCatalog>();

	public static WatchCatalog getWatchCatalogById(String keyId) {
		if (CATALOG.containsKey(keyId)) {
			return CATALOG.get(keyId);

		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @param watchCatalog
	 */
	public static void put(String key, WatchCatalog watchCatalog) {
		CATALOG.put(key, watchCatalog);
	}

	/**
	 * 
	 */
	public static void clearCache() {
		CATALOG.clear();
	}

}
