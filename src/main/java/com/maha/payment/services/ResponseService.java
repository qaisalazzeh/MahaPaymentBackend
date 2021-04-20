package com.maha.payment.services;

import java.util.Map;

import com.maha.payment.services.pojos.CheckoutResponse;

/**
 * 
 * @author qazzeh
 *
 */
public interface ResponseService {

	/**
	 * 
	 * @return
	 */
	Map<String, Object> generateTechnicalProblemResponse();

	/**
	 * 
	 * @param responseCode
	 * @param responseMessege
	 * @param responseBody
	 * @param weatherInformation
	 * @return
	 */
	CheckoutResponse generateApiResponse(String responseCode, String responseMessege, Integer price,
			Map<String, Map<String, Object>> invoiceResponseMap);
}
