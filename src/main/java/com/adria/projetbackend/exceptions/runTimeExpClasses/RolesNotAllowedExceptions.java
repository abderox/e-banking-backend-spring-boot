package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class RolesNotAllowedExceptions extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public RolesNotAllowedExceptions() {
        super();
    }

    public RolesNotAllowedExceptions(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RolesNotAllowedExceptions(final String message) {
        super(message);
    }

    public RolesNotAllowedExceptions(final Throwable cause) {
        super(cause);
    }

}
