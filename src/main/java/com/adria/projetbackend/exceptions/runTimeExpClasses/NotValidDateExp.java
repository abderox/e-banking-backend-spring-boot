package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class NotValidDateExp extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public NotValidDateExp() {
        super();
    }

    public NotValidDateExp(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotValidDateExp(final String message) {
        super(message);
    }

    public NotValidDateExp(final Throwable cause) {
        super(cause);
    }

}
