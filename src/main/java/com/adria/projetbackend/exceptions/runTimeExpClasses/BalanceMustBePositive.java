package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class BalanceMustBePositive extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public BalanceMustBePositive() {
        super();
    }

    public BalanceMustBePositive(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BalanceMustBePositive(final String message) {
        super(message);
    }

    public BalanceMustBePositive(final Throwable cause) {
        super(cause);
    }

}
