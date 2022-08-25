package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class UsernameMustBeUnique extends RuntimeException {


    private static final long serialVersionUID = 5861310537369287163L;

    public UsernameMustBeUnique() {
        super();
    }

    public UsernameMustBeUnique(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsernameMustBeUnique(final String message) {
        super(message);
    }

    public UsernameMustBeUnique(final Throwable cause) {
        super(cause);
    }

}
