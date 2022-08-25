package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class NoSuchAgenceException extends RuntimeException {


    private static final long serialVersionUID = 5861310457366887163L;

    public NoSuchAgenceException() {
        super();
    }

    public NoSuchAgenceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchAgenceException(final String message) {
        super(message);
    }

    public NoSuchAgenceException(final Throwable cause) {
        super(cause);
    }

}
