package com.gestor.exceptions;

public class CandidateServiceException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public CandidateServiceException() {
        super();
    }
    
    public CandidateServiceException(String message) {
        super(message);
    }

    public CandidateServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    
    public CandidateServiceException(Throwable cause) {
        super(cause);
    }

    protected CandidateServiceException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
