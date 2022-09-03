package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class NoSuchBenificException extends RuntimeException {


    private static final long serialVersionUID = 5861310537366887163L;

    public NoSuchBenificException() {
        super();
    }

    public NoSuchBenificException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchBenificException(final String message) {
        super(message);
    }

    public NoSuchBenificException(final Throwable cause) {
        super(cause);
    }

}
