package com.maha.payment.services.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maha.payment.services.CheckoutService;
import com.maha.payment.services.MerchantValidationService;
import com.maha.payment.services.ResponseService;
import com.maha.payment.services.cache.CatalogCache;
import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.pojos.CheckoutRequest;
import com.maha.payment.services.pojos.CheckoutResponse;
import com.maha.payment.services.utils.BackendUtils;
import com.maha.payment.services.utils.CollectionsUtil;

/**
 * 
 * @author qazzeh
 *
 */
@RestController
public class CheckoutControllers {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutControllers.class);

	@Autowired
	MerchantValidationService merchantValidationService;

	@Autowired
	CheckoutService checkoutService;

	@Autowired
	ResponseService responseService;

	/**
	 * 
	 * @param httpRequest
	 * @param apiRequest
	 * @return
	 * @throws BackendException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	@CrossOrigin("http://localhost:8080")
	@PostMapping(value = "/checkout", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<String> checkout(HttpServletRequest httpRequest,
			@RequestBody CheckoutRequest checkoutRequest)
			throws BackendException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
		LOGGER.info("Request Received, Start Processing..... ");

		merchantValidationService.isValidCheckoutRequest(checkoutRequest);

		CheckoutResponse checkoutResponse = checkoutService
				.calculatePriceAndCheckoutTheCart(checkoutRequest.getItemsBasket());

		return new ResponseEntity<>(
				CollectionsUtil.resolveJsonContentType(BackendUtils.getObjectAsMap(checkoutResponse)),
				CollectionsUtil.getHeader(MediaType.APPLICATION_JSON_VALUE), HttpStatus.OK);

	}

	/**
	 * 
	 * @param httpRequest
	 * @param apiRequest
	 * @return
	 * @throws BackendException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	@CrossOrigin("http://localhost:8080")
	@PostMapping(value = "/clear", consumes = { "application/json" })
	public @ResponseBody void cleanCache(HttpServletRequest httpRequest, @RequestBody CheckoutRequest checkoutRequest) {
		LOGGER.info("Request Received, Clean Cache..... ");
		CatalogCache.clearCache();
	}

	/**
	 * 
	 * @param exception
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler({ BackendException.class, IOException.class, NoSuchAlgorithmException.class,
			NoSuchProviderException.class, Exception.class })
	public @ResponseBody ResponseEntity<String> handleBackendException(BackendException exception) throws IOException {
		CheckoutResponse apiResponse = new CheckoutResponse(exception.getResponseCode(), exception.getMessage(), null,
				null);
		LOGGER.error("Exception While Processing your Request {}", exception);
		try {
			return new ResponseEntity<>(
					CollectionsUtil.resolveJsonContentType(BackendUtils.getObjectAsMap(apiResponse)),
					CollectionsUtil.getHeader(MediaType.APPLICATION_JSON_VALUE), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(
					CollectionsUtil.resolveJsonContentType(responseService.generateTechnicalProblemResponse()),
					CollectionsUtil.getHeader(MediaType.APPLICATION_JSON_VALUE), HttpStatus.OK);
		}

	}
}
