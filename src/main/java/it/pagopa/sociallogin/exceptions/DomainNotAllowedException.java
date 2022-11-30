package it.pagopa.sociallogin.exceptions;

public class DomainNotAllowedException extends Exception {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1936525194389416299L;

	public DomainNotAllowedException(String msg) {
        super(msg);
    }
}