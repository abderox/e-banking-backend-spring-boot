package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class InsufficientDepositException extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public InsufficientDepositException() {
        super();
    }

    public InsufficientDepositException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InsufficientDepositException(final String message) {
        super(message);
    }

    public InsufficientDepositException(final Throwable cause) {
        super(cause);
    }

}
