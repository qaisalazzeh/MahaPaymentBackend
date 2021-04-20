package com.maha.payment.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.pojos.CheckoutRequest;
import com.maha.payment.services.pojos.CheckoutResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiServiceTest {

	@Test
	public void testMahaCheckoutPaymentApi() throws BackendException, IOException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CheckoutRequest> requestEntity = new HttpEntity<>(checkoutRequest, headers);

		ResponseEntity<CheckoutResponse> responseEntity = restTemplate.exchange("http://localhost:8090/checkout",
				HttpMethod.POST, requestEntity, CheckoutResponse.class);

		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseEntity.getBody().getResponseMessage().equals("Request Processed Successfully"));
		assertTrue(responseEntity.getBody().getResponseCode().equals("00000"));

	}

	@Test
	public void testMahaCheckoutPaymentApiUnSupportedMidaType() throws BackendException, IOException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		checkoutRequest.setMahaCode("maha001");
		checkoutRequest.setAccessCode("code293084");
		checkoutRequest.setSignature("bd92e5bc96087ac908af22f9650b670e35a55a69");
		checkoutRequest.setIdentifier("qazzeh");
		List<String> itemsBasket = Arrays.asList("001", "001", "002", "001", "002", "001", "001", "002", "001", "004",
				"004", "004", "003", "003", "003", "003", "001", "005");
		checkoutRequest.setItemsBasket(itemsBasket);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CheckoutRequest> requestEntity = new HttpEntity<>(checkoutRequest, headers);

		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.exchange("http://localhost:8090/checkout", HttpMethod.GET, requestEntity,
					CheckoutResponse.class);

		});
		assertTrue(exception.getClass().getName()
				.equals("org.springframework.web.client.HttpClientErrorException$MethodNotAllowed"));

	}

	@Test
	public void testMahaCheckoutPaymentApiBadRequest() throws BackendException, IOException {
		CheckoutRequest checkoutRequest = new CheckoutRequest();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CheckoutRequest> requestEntity = new HttpEntity<>(checkoutRequest, headers);

		ResponseEntity<CheckoutResponse> responseEntity = restTemplate.exchange("http://localhost:8090/checkout",
				HttpMethod.POST, requestEntity, CheckoutResponse.class);

		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseEntity.getBody().getResponseMessage().equals("Missing Mandatory Parameter - mahaCode"));
		assertTrue(responseEntity.getBody().getResponseCode().equals("00001"));
	}
}
