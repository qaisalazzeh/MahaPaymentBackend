package com.maha.payment.services;

import java.util.List;

import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.pojos.CheckoutResponse;

/**
 * 
 * @author qazzeh
 *
 */
public interface CheckoutService {

	/**
	 * 
	 * @param itemList
	 * @return
	 * @throws BackendException
	 */
	CheckoutResponse calculatePriceAndCheckoutTheCart(List<String> itemList) throws BackendException;
}
