package com.maha.payment.services.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Qais Azzeh
 *
 */
@Setter
@Getter
public class BackendException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String responseCode;
	private String causeLog;
	private String status;

	public BackendException(String responseCode, String causeLog, String status, String message, Throwable cause,
			Object... args) {
		super(message, cause);
		this.responseCode = responseCode;
		this.causeLog = causeLog;
		this.status = status;
	}

	public BackendException(String responseCode, String causeLog, String status) {
		this(responseCode, causeLog, status, null, null);
	}

	public BackendException(String responseCode, String message, Throwable cause) {
		this(responseCode, null, null, message, cause);

	}

	public BackendException(String responseCode, String message) {
		this(responseCode, null, null, message, null);
	}

	public BackendException(String responseCode) {
		this(responseCode, null, null, null, null);
	}

}
