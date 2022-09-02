package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class DomesticBenifOnlyExp extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public DomesticBenifOnlyExp() {
        super();
    }

    public DomesticBenifOnlyExp(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DomesticBenifOnlyExp(final String message) {
        super(message);
    }

    public DomesticBenifOnlyExp(final Throwable cause) {
        super(cause);
    }

}
