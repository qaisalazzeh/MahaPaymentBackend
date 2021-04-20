package com.maha.payment.services.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import com.maha.payment.services.constants.BackendParams;
import com.maha.payment.services.constants.BackendParams.ResponseCodes;
import com.maha.payment.services.constants.BackendParams.ResponseMessages;
import com.maha.payment.services.exceptions.BackendException;

/**
 * 
 * @author Qais Azzeh
 *
 */
public class SecurityBackendUtils {

	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws BackendException
	 */
	public static String generateSignature(Map<String, Object> parameters, String shaPass) throws BackendException {
		parameters.remove(BackendParams.SIGNATURE);
		String signature = new String();
		String concatenatedData = new String();
		Map<String, Object> sortedParameters = new TreeMap<String, Object>(parameters);

		for (String key : sortedParameters.keySet()) {
			Object value = sortedParameters.get(key);
			if (value != null && !value.equals("")) {
				concatenatedData += key + "=" + String.valueOf(value);
			}
		}
		concatenatedData = shaPass + concatenatedData + shaPass;
		signature = generateShaSign(concatenatedData);
		return signature;
	}

	private static String generateShaSign(String rawData) throws BackendException {
		return generateSha1(rawData);

	}

	private static String generateSha1(String rawData) throws BackendException {
		try {
			byte[] shaSignBytes = MessageDigest.getInstance("SHA-1").digest(rawData.getBytes("UTF-8"));

			return String.format("%040x", new BigInteger(1, shaSignBytes));
		} catch (NoSuchAlgorithmException e) {
			throw new BackendException(ResponseCodes.TECHNICAL_ERROR, ResponseMessages.TECHNICAL_ERROR);
		} catch (UnsupportedEncodingException e) {
			throw new BackendException(ResponseCodes.TECHNICAL_ERROR, ResponseMessages.TECHNICAL_ERROR);

		}
	}

}