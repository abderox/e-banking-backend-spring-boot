package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class CustomerHasAccountException extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public CustomerHasAccountException() {
        super();
    }

    public CustomerHasAccountException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomerHasAccountException(final String message) {
        super(message);
    }

    public CustomerHasAccountException(final Throwable cause) {
        super(cause);
    }

}
