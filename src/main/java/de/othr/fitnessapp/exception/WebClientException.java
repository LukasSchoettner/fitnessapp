package de.othr.fitnessapp.exception;

public class WebClientException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public WebClientException(String statement){
        super(statement);
    }
}
