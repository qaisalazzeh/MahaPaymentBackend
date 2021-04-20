package com.maha.payment.services.constants;

/**
 * 
 * @author Qais Azzeh
 *
 */
public class BackendParams {

	public static String RESPONSE_CODE = "responseCode";
	public static String RESPONSE_MSG = "responseMessage";
	public static String SIGNATURE = "signature";
	public static String ACCESS_CODE = "accessCode";
	public static String MAHA_CODE = "mahaCode";
	public static String INVOICE_DETAILS = "invoiceDetails";
	public static String COUNT = "count";
	public static String TOTAL_ITEM_PRICE = "totalItemPrice";
	public static String ITEM_NAME = "itemName";

	public static final class ResponseCodes {
		public static String SUCCESS = "00000";
		public static String MISSING_PARAM = "00001";
		public static String SIG_MISS_MATCH = "00002";
		public static String MERCHANT_NOT_FOUND = "00003";
		public static String ITEM_NOT_FOUND = "00004";
		public static String BASKET_IS_EMPTY = "00005";

		public static String TECHNICAL_ERROR = "99999";
	}

	public static final class ResponseMessages {
		public static String SUCCESS = "Request Processed Successfully";
		public static String TECHNICAL_ERROR = "TechnicalError - Service Unavailable";
		public static String MISSING_PARAM = "Missing Mandatory Parameter";
		public static String SIG_MISS_MATCH = "Signature Mismatch";
		public static String MERCHANT_NOT_FOUND = "Merchant not onboarded correctly - please contact Support Team";
		public static String ITEM_NOT_FOUND = "Item Not Found";
		public static String BASKET_IS_EMPTY = "Basket Is Empty";

	}
}
