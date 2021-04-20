package com.maha.payment.services.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.maha.payment.services.mongo.entities.WatchCatalog;

/**
 * 
 * @author Qais Azzeh
 *
 */
public interface WatchCatalogRepo extends MongoRepository<WatchCatalog, String> {

}