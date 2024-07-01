package com.app.attendance.response.advice;

public class AdminRegistrationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminRegistrationException(String message) {
        super(message);
    }

    public AdminRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

