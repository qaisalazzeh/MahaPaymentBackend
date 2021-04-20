package com.maha.payment.services.Impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maha.payment.services.CheckoutService;
import com.maha.payment.services.CommonServices;
import com.maha.payment.services.ResponseService;
import com.maha.payment.services.cache.CatalogCache;
import com.maha.payment.services.constants.BackendParams;
import com.maha.payment.services.constants.BackendParams.ResponseCodes;
import com.maha.payment.services.constants.BackendParams.ResponseMessages;
import com.maha.payment.services.exceptions.BackendException;
import com.maha.payment.services.mongo.entities.WatchCatalog;
import com.maha.payment.services.mongo.repo.WatchCatalogRepo;
import com.maha.payment.services.pojos.CheckoutResponse;

/**
 * 
 * @author qazzeh
 *
 */
@Service
public class CheckoutServiceImpl extends CommonServices implements CheckoutService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutServiceImpl.class);

	@Autowired
	WatchCatalogRepo watchCatalogRepo;

	@Autowired
	ResponseService responseService;

	@Override
	public CheckoutResponse calculatePriceAndCheckoutTheCart(List<String> itemList) throws BackendException {
		LOGGER.info("inside calculatePriceAndCheckoutTheCart");
		LOGGER.debug("inside calculatePriceAndCheckoutTheCart {}", itemList);

		loadCatalogDetailsAndSavethemIntoCacheIfNeeded();

		Map<String, Object> cartItems = groupCartItems(itemList);

		Map<String, Map<String, Object>> invoiceDetails = new HashMap<String, Map<String, Object>>();

		Integer finalPrice = calculateFinalPriceForSentCart(cartItems, invoiceDetails);

		return responseService.generateApiResponse(ResponseCodes.SUCCESS, ResponseMessages.SUCCESS, finalPrice,
				invoiceDetails);
	}

	/**
	 * 
	 * @param cartItems
	 * @return
	 * @throws BackendException
	 */
	private Integer calculateFinalPriceForSentCart(Map<String, Object> cartItems,
			Map<String, Map<String, Object>> invoiceDetails) throws BackendException {
		LOGGER.info("inside calculateFinalPriceForSentCart");
		LOGGER.debug("inside calculateFinalPriceForSentCart {}", cartItems);

		Integer totalPrice = 0;
		for (String key : cartItems.keySet()) {
			WatchCatalog watchCatalog = CatalogCache.getWatchCatalogById(key);

			if (!Objects.isNull(watchCatalog)) {
				Integer itemPrice = checkAndApplyDiscountIfItemIsEligibleFor(
						Integer.valueOf(String.valueOf(cartItems.get(key))),
						Integer.valueOf(String.valueOf(watchCatalog.getDiscountThreshold())),
						Integer.valueOf(String.valueOf(watchCatalog.getPrice())),
						Integer.valueOf(String.valueOf(watchCatalog.getDiscountPrice())));

				totalPrice = totalPrice + itemPrice;

				prepareInvoiceMapForResponse(invoiceDetails, itemPrice, key, watchCatalog.getName(),
						Integer.valueOf(String.valueOf(cartItems.get(key))));
			} else {
				prepareInvoiceMapForResponse(invoiceDetails, null, key, null,
						Integer.valueOf(String.valueOf(cartItems.get(key))));
			}
		}
		return totalPrice;
	}

	/**
	 * 
	 * @param invoiceDetails
	 * @param totalPrice
	 * @param key
	 * @param name
	 * @param count
	 */
	private void prepareInvoiceMapForResponse(Map<String, Map<String, Object>> invoiceDetails, Integer totalPrice,
			String key, String name, Integer count) {
		LOGGER.info("inside prepareInvoiceMapForResponse");
		LOGGER.debug("inside prepareInvoiceMapForResponse {}", key);
		Map<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put(BackendParams.COUNT, count);
		itemMap.put(BackendParams.TOTAL_ITEM_PRICE, totalPrice);
		itemMap.put(BackendParams.ITEM_NAME, name);
		invoiceDetails.put(key, itemMap);
	}

	/**
	 * 
	 * @param numberOfItem
	 * @param discountThreshold
	 * @param price
	 * @param salePrice
	 * @return
	 */
	private Integer checkAndApplyDiscountIfItemIsEligibleFor(Integer numberOfItem, Integer discountThreshold,
			Integer price, Integer salePrice) {
		LOGGER.info("Calculate Price Per Item ");
		if (discountThreshold.equals(0)) {
			LOGGER.info("no Discount for this item ");
			return numberOfItem * price;
		}
		// calculation for the price with/without Discount
		return ((numberOfItem / discountThreshold) * salePrice) + ((numberOfItem % discountThreshold) * price);
	}

	/**
	 * 
	 * @param itemList
	 * @return
	 */
	private Map<String, Object> groupCartItems(List<String> itemList) {
		LOGGER.info("Group Item with its frequency ");
		Set<String> unique = new HashSet<String>(itemList);
		Map<String, Object> cartItems = new HashMap<String, Object>();
		for (String key : unique) {
			cartItems.put(key, Collections.frequency(itemList, key));
		}
		return cartItems;
	}

	/**
	 * 
	 * @param itemList
	 */
	private void loadCatalogDetailsAndSavethemIntoCacheIfNeeded() {
		if (CatalogCache.CATALOG.isEmpty() || CatalogCache.CATALOG.size() == 0) {
			LOGGER.info("Load Catalog Information From Mongo Db and Save them into Map");
			List<WatchCatalog> watchCatalogs = watchCatalogRepo.findAll();
			for (WatchCatalog watchCatalog : watchCatalogs) {
				CatalogCache.put(watchCatalog.getId(), watchCatalog);
			}
		}
	}

}
