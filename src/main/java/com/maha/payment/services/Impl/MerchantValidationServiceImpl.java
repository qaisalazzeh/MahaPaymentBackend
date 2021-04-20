package com.maha.payment.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maha.payment.services.CommonServices;
import com.maha.payment.services.MerchantValidationService;
import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.mysql.entities.MerchantEntity;
import com.maha.payment.services.mysql.repo.MerchantsRepo;
import com.maha.payment.services.pojos.CheckoutRequest;
import com.maha.payment.services.utils.CrossScriptingUtil;

/**
 * 
 * @author qazzeh
 *
 */
@Service
public class MerchantValidationServiceImpl extends CommonServices implements MerchantValidationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantValidationServiceImpl.class);

	@Autowired
	MerchantsRepo merchantsRepo;

	/**
	 * 
	 */
	@Override
	public void isValidCheckoutRequest(CheckoutRequest checkoutRequest) throws BackendException {
		LOGGER.info("inside isValidCheckoutRequest()");

		LOGGER.debug("Functoion Param ()", checkoutRequest);

		CrossScriptingUtil.cleanXSS(checkoutRequest);

		isValidMahaCode(checkoutRequest.getMahaCode());

		isValidIdentifier(checkoutRequest.getIdentifier());

		isValidAccessCode(checkoutRequest.getAccessCode());

		isValidCartList(checkoutRequest.getItemsBasket());

		MerchantEntity merchantEntity = isValidMerchant(checkoutRequest);

		isValidSignature(checkoutRequest, merchantEntity.getShaPass());
	}

}
