package com.maha.payment.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maha.payment.services.constants.BackendParams.ResponseCodes;
import com.maha.payment.services.constants.BackendParams.ResponseMessages;
import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.mysql.entities.MerchantEntity;
import com.maha.payment.services.mysql.repo.MerchantsRepo;
import com.maha.payment.services.pojos.CheckoutRequest;
import com.maha.payment.services.utils.BackendUtils;
import com.maha.payment.services.utils.CollectionsUtil;
import com.maha.payment.services.utils.SecurityBackendUtils;

/**
 * 
 * @author qazzeh
 *
 */
@Service
public abstract class CommonServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonServices.class);

	@Autowired
	MerchantsRepo merchantsRepo;

	/**
	 * 
	 * @param name
	 * @return
	 * @throws BackendException
	 */
	public MerchantEntity isValidMerchant(CheckoutRequest checkoutRequest) throws BackendException {
		LOGGER.info("inside isValidMerchant()");
		MerchantEntity merchantEntity = merchantsRepo.findByIdentifierAndAccessCodeAndMahaCode(
				checkoutRequest.getIdentifier(), checkoutRequest.getAccessCode(), checkoutRequest.getMahaCode());
		LOGGER.debug("Function Param {}", checkoutRequest);
		if (BackendUtils.isObjectEmpty(merchantEntity)) {
			throw new BackendException(ResponseCodes.MERCHANT_NOT_FOUND, ResponseMessages.MERCHANT_NOT_FOUND);
		}
		return merchantEntity;
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws BackendException
	 */
	public void isValidMahaCode(String mahaCode) throws BackendException {
		LOGGER.info("inside isValidName()");

		LOGGER.debug("Function Param {}", mahaCode);
		if (BackendUtils.isObjectEmpty(mahaCode)) {
			throw new BackendException(ResponseCodes.MISSING_PARAM, ResponseMessages.MISSING_PARAM + " - mahaCode");
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws BackendException
	 */
	public void isValidIdentifier(String identifier) throws BackendException {
		LOGGER.info("inside isValidName()");

		LOGGER.debug("Function Param {}", identifier);
		if (BackendUtils.isObjectEmpty(identifier)) {
			throw new BackendException(ResponseCodes.MISSING_PARAM, ResponseMessages.MISSING_PARAM + " - identifier");
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws BackendException
	 */
	public void isValidAccessCode(String accessCode) throws BackendException {
		LOGGER.info("inside isValidName()");

		LOGGER.debug("Function Param {}", accessCode);

		if (BackendUtils.isObjectEmpty(accessCode)) {
			throw new BackendException(ResponseCodes.MISSING_PARAM, ResponseMessages.MISSING_PARAM + " - accessCode");
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws BackendException
	 */
	public void isValidCartList(List<String> cartList) throws BackendException {
		LOGGER.info("inside isValidName()");

		LOGGER.debug("Function Param {}", cartList);
		if (BackendUtils.isObjectEmpty(cartList)) {
			throw new BackendException(ResponseCodes.MISSING_PARAM, ResponseMessages.MISSING_PARAM + " - cartList");
		}

		if (cartList.size() == 0) {
			throw new BackendException(ResponseCodes.BASKET_IS_EMPTY, ResponseMessages.BASKET_IS_EMPTY + " - cartList");
		}

	}

	/**
	 * 
	 * @param checkoutRequest
	 * @throws BackendException
	 */
	public void isValidSignature(CheckoutRequest checkoutRequest, String shaPass) throws BackendException {

		LOGGER.info("inside isValidSignature()");

		LOGGER.debug("Function Param {}", checkoutRequest);

		if (BackendUtils.isObjectEmpty(checkoutRequest.getSignature())) {
			throw new BackendException(ResponseCodes.MISSING_PARAM, ResponseMessages.MISSING_PARAM + " - signature");
		}

		String generatedSig = SecurityBackendUtils.generateSignature(CollectionsUtil.fromObjectToMap(checkoutRequest),
				shaPass);

		if (!generatedSig.equals(checkoutRequest.getSignature())) {
			LOGGER.error("Sent Sig {} , Generated Sig {}", checkoutRequest.getSignature(), generatedSig);
			throw new BackendException(ResponseCodes.SIG_MISS_MATCH, ResponseMessages.SIG_MISS_MATCH);

		}
	}
}
