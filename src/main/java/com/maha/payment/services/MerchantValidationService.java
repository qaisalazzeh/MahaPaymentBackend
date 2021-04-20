package com.maha.payment.services;

import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.pojos.CheckoutRequest;

/**
 * 
 * @author qazzeh
 *
 */
public interface MerchantValidationService {

	void isValidCheckoutRequest(CheckoutRequest checkoutRequest) throws BackendException;
}
