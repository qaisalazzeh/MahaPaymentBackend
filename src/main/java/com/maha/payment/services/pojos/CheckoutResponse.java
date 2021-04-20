package com.maha.payment.services.pojos;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Qais Azzeh
 *
 */
@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse {

	private String responseCode;
	private String responseMessage;
	private Integer price;
	private Map<String, Map<String, Object>> invoiceResponseMap;

}
