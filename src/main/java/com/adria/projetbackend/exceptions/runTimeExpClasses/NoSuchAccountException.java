package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class NoSuchAccountException extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public NoSuchAccountException() {
        super();
    }

    public NoSuchAccountException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchAccountException(final String message) {
        super(message);
    }

    public NoSuchAccountException(final Throwable cause) {
        super(cause);
    }

}
