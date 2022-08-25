package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class IdentityMustBeUnique extends RuntimeException {


    private static final long serialVersionUID = 5861310577366287163L;

    public IdentityMustBeUnique() {
        super();
    }

    public IdentityMustBeUnique(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IdentityMustBeUnique(final String message) {
        super(message);
    }

    public IdentityMustBeUnique(final Throwable cause) {
        super(cause);
    }

}
