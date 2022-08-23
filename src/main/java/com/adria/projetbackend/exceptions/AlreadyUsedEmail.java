package com.adria.projetbackend.exceptions;

/**
 * @autor abderox
 */


public class AlreadyUsedEmail extends RuntimeException {


    private static final long serialVersionUID = 5861310537366287163L;

    public AlreadyUsedEmail() {
        super();
    }

    public AlreadyUsedEmail(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AlreadyUsedEmail(final String message) {
        super(message);
    }

    public AlreadyUsedEmail(final Throwable cause) {
        super(cause);
    }

}
