package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class IdentityPieceMustBeUnique extends RuntimeException {


    private static final long serialVersionUID = 5861310037366287163L;

    public IdentityPieceMustBeUnique() {
        super();
    }

    public IdentityPieceMustBeUnique(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IdentityPieceMustBeUnique(final String message) {
        super(message);
    }

    public IdentityPieceMustBeUnique(final Throwable cause) {
        super(cause);
    }

}
