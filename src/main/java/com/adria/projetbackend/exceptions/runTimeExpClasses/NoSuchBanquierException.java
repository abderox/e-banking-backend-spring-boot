package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class NoSuchBanquierException extends RuntimeException {


    private static final long serialVersionUID = 5861310537366887163L;

    public NoSuchBanquierException() {
        super();
    }

    public NoSuchBanquierException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchBanquierException(final String message) {
        super(message);
    }

    public NoSuchBanquierException(final Throwable cause) {
        super(cause);
    }

}
