package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class CustmerAlreadyExistsException extends RuntimeException {


    private static final long serialVersionUID = 5861310519366287163L;


    public CustmerAlreadyExistsException() {
        super();
    }

    public CustmerAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustmerAlreadyExistsException(final String message) {
        super(message);
    }

    public CustmerAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

}
