package com.maha.payment.services.pojos;

import java.util.List;

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
public class CheckoutRequest {

	private String mahaCode;
	private String accessCode;
	private String signature;
	private String identifier;
	private List<String> itemsBasket;
}
