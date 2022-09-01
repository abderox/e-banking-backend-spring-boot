package com.adria.projetbackend.exceptions.runTimeExpClasses;

/**
 * @autor abderox
 */


public class TransactionExp extends RuntimeException {


    private static final long serialVersionUID = 5861310537066287163L;

    public TransactionExp() {
        super();
    }

    public TransactionExp(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TransactionExp(final String message) {
        super(message);
    }

    public TransactionExp(final Throwable cause) {
        super(cause);
    }

}
