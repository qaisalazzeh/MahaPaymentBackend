package com.maha.payment.services.Impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.maha.payment.services.ResponseService;
import com.maha.payment.services.constants.BackendParams;
import com.maha.payment.services.constants.BackendParams.ResponseCodes;
import com.maha.payment.services.constants.BackendParams.ResponseMessages;
import com.maha.payment.services.pojos.CheckoutResponse;

/**
 * 
 * @author qazzeh
 *
 */
@Service
public class ResponseServiceImpl implements ResponseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseServiceImpl.class);

	/**
	 * 
	 */
	@Override
	public Map<String, Object> generateTechnicalProblemResponse() {
		LOGGER.info("inside generateTechnicalProblemResponse()");

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put(BackendParams.RESPONSE_CODE, ResponseCodes.TECHNICAL_ERROR);
		responseMap.put(BackendParams.RESPONSE_MSG, ResponseMessages.TECHNICAL_ERROR);
		responseMap.put(BackendParams.INVOICE_DETAILS, null);
		return responseMap;
	}

	/**
	 * 
	 */
	@Override
	public CheckoutResponse generateApiResponse(String responseCode, String responseMessege, Integer price,
			Map<String, Map<String, Object>> invoiceResponseMap) {
		return new CheckoutResponse(responseCode, responseMessege, price, invoiceResponseMap);
	}
}
