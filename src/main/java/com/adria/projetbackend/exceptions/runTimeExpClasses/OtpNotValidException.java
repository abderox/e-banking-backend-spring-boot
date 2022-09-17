package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class OtpNotValidException extends RuntimeException {


    private static final long serialVersionUID = 5861210537066287163L;

    public OtpNotValidException() {
        super();
    }

    public OtpNotValidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OtpNotValidException(final String message) {
        super(message);
    }

    public OtpNotValidException(final Throwable cause) {
        super(cause);
    }

}
