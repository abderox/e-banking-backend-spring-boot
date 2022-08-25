package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class NoSuchCustomerException extends RuntimeException {


    private static final long serialVersionUID = 5861310537366887163L;

    public NoSuchCustomerException() {
        super();
    }

    public NoSuchCustomerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchCustomerException(final String message) {
        super(message);
    }

    public NoSuchCustomerException(final Throwable cause) {
        super(cause);
    }

}
