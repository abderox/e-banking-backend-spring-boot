package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class CodeAgencyMustBeUnique extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public CodeAgencyMustBeUnique() {
        super();
    }

    public CodeAgencyMustBeUnique(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CodeAgencyMustBeUnique(final String message) {
        super(message);
    }

    public CodeAgencyMustBeUnique(final Throwable cause) {
        super(cause);
    }

}
