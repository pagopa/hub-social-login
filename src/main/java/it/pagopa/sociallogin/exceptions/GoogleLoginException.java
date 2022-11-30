package it.pagopa.sociallogin.exceptions;

public class GoogleLoginException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2126892553767093960L;

	public GoogleLoginException(String msg) {
        super(msg);
    }
}