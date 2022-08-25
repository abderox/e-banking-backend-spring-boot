package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class AlreadyUsedTeleNumber extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public AlreadyUsedTeleNumber() {
        super();
    }

    public AlreadyUsedTeleNumber(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AlreadyUsedTeleNumber(final String message) {
        super(message);
    }

    public AlreadyUsedTeleNumber(final Throwable cause) {
        super(cause);
    }

}
