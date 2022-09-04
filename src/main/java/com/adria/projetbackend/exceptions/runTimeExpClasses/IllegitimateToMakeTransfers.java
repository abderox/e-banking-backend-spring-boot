package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class IllegitimateToMakeTransfers extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public IllegitimateToMakeTransfers() {
        super();
    }

    public IllegitimateToMakeTransfers(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IllegitimateToMakeTransfers(final String message) {
        super(message);
    }

    public IllegitimateToMakeTransfers(final Throwable cause) {
        super(cause);
    }

}
