package com.maha.payment.services.mongo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author qazzeh
 *
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "WatchCatalog")
public class WatchCatalog {

	@Id
	private String id;
	private String name;
	private String price;
	private String discountThreshold;
	private String discountPrice;

}
