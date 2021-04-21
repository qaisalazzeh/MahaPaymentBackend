package com.maha.payment.services;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maha.payment.services.constants.BackendParams;
import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.pojos.CheckoutResponse;

@SpringBootTest
public class CheckoutServiceTest {

	@Autowired
	CheckoutService checkoutService;

	@Test
	public void testCalculatePriceAndCheckoutTheCart() throws BackendException {
		CheckoutResponse checkoutResponse = checkoutService
				.calculatePriceAndCheckoutTheCart(Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002",
						"001", "004", "004", "004", "003", "003", "003", "003", "001", "005"));
		assertTrue(checkoutResponse.getPrice().equals(990));
	}

	@Test
	public void testCalculatePriceAndCheckoutTheCartWith001Offer() throws BackendException {
		CheckoutResponse checkoutResponse = checkoutService
				.calculatePriceAndCheckoutTheCart(Arrays.asList("001", "001", "001"));
		assertTrue(checkoutResponse.getPrice().equals(200));
	}

	@Test
	public void testCalculatePriceAndCheckoutTheCartWith002Offer() throws BackendException {
		CheckoutResponse checkoutResponse = checkoutService
				.calculatePriceAndCheckoutTheCart(Arrays.asList("002", "002"));
		assertTrue(checkoutResponse.getPrice().equals(120));
	}

	@Test
	public void testCalculatePriceAndCheckoutTheCartWith001OfferAndOthersWithoutOffer() throws BackendException {
		CheckoutResponse checkoutResponse = checkoutService
				.calculatePriceAndCheckoutTheCart(Arrays.asList("001", "001", "001", "001"));
		assertTrue(checkoutResponse.getPrice().equals(300));
	}

	@Test
	public void testCalculatePriceAndCheckoutTheCartWithoutOffers() throws BackendException {
		CheckoutResponse checkoutResponse = checkoutService
				.calculatePriceAndCheckoutTheCart(Arrays.asList("001", "002", "001", "004", "003"));
		assertTrue(checkoutResponse.getPrice().equals(360));
	}

	@Test
	public void testCalculatePriceAndCheckoutTheCartWithoutOffersAndOneItem() throws BackendException {
		CheckoutResponse checkoutResponse = checkoutService.calculatePriceAndCheckoutTheCart(Arrays.asList("001"));
		assertTrue(checkoutResponse.getPrice().equals(100));
	}

	@Test
	public void testInvoiceDetailsForCheckoutRequest() throws BackendException {
		CheckoutResponse checkoutResponse = checkoutService
				.calculatePriceAndCheckoutTheCart(Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002",
						"001", "004", "004", "004", "003", "003", "003", "003", "001", "005"));

		assertTrue(checkoutResponse.getInvoiceResponseMap().get("001").get(BackendParams.TOTAL_ITEM_PRICE).equals(500));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("001").get(BackendParams.COUNT).equals(7));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("001").get(BackendParams.ITEM_NAME).equals("Rolex"));

		assertTrue(checkoutResponse.getInvoiceResponseMap().get("002").get(BackendParams.TOTAL_ITEM_PRICE).equals(200));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("002").get(BackendParams.COUNT).equals(3));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("002").get(BackendParams.ITEM_NAME)
				.equals("Michael Kors"));

		assertTrue(checkoutResponse.getInvoiceResponseMap().get("003").get(BackendParams.TOTAL_ITEM_PRICE).equals(200));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("003").get(BackendParams.COUNT).equals(4));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("003").get(BackendParams.ITEM_NAME).equals("Swatch"));

		assertTrue(checkoutResponse.getInvoiceResponseMap().get("004").get(BackendParams.TOTAL_ITEM_PRICE).equals(90));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("004").get(BackendParams.COUNT).equals(3));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("004").get(BackendParams.ITEM_NAME).equals("Casio"));

		assertTrue(Objects
				.isNull(checkoutResponse.getInvoiceResponseMap().get("005").get(BackendParams.TOTAL_ITEM_PRICE)));
		assertTrue(checkoutResponse.getInvoiceResponseMap().get("005").get(BackendParams.ITEM_NAME)
				.equals("Item Not Found"));

		assertTrue(checkoutResponse.getPrice().equals(990));
		assertTrue(checkoutResponse.getResponseCode().equals("00000"));
		assertTrue(checkoutResponse.getResponseMessage().equals("Request Processed Successfully"));

	}

}
