package com.maha.payment.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.pojos.CheckoutRequest;

@SpringBootTest
public class ValidationServiceTest {

	@Autowired
	MerchantValidationService merchantValidationService;

	@Test
	public void testIsValidCheckoutRequest() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		assertNotNull(checkoutRequest);

	}

	@Test
	public void testIsValidCheckoutRequestSigMissMatch() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("aaec33be8ef57d5730e27d6f92b91861a306f1e0");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Signature Mismatch"));

	}

	@Test
	public void testIsValidCheckoutRequestMissingMahaCode() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("aaec33be8ef57d5730e27d6f92b91861a306f1e0");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Missing Mandatory Parameter - mahaCode"));

	}

	@Test
	public void testIsValidCheckoutRequestMissingAccessCode() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setSignature("aaec33be8ef57d5730e27d6f92b91861a306f1e0");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Missing Mandatory Parameter - accessCode"));

	}

	@Test
	public void testIsValidCheckoutRequestMissingSignature() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Missing Mandatory Parameter - signature"));

	}

	@Test
	public void testIsValidCheckoutRequestMissingIdentifier() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Missing Mandatory Parameter - identifier"));

	}

	@Test
	public void testIsValidCheckoutRequestMissingItemBasket() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzeh");

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Missing Mandatory Parameter - cartList"));

	}

	@Test
	public void testIsValidCheckoutRequestMissingItemBasketEmpty() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList();
		checkoutRequest.setItemsBasket(itemsBasket);
		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Basket Is Empty - cartList"));

	}

	@Test
	public void testIsValidCheckoutRequestInvalidMahaCode() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha002341");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Merchant not onboarded correctly - please contact Support Team"));
	}

	@Test
	public void testIsValidCheckoutRequestInvalidAccessCode() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("codasde293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Merchant not onboarded correctly - please contact Support Team"));
	}

	@Test
	public void testIsValidCheckoutRequestInvalidIdentifier() throws BackendException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzsdgfeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);

		BackendException exception = assertThrows(BackendException.class, () -> {
			merchantValidationService.isValidCheckoutRequest(checkoutRequest);
		});
		assertTrue(exception.getMessage().contains("Merchant not onboarded correctly - please contact Support Team"));
	}

}
