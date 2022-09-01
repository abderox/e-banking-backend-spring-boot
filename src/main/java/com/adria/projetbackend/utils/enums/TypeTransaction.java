package com.adria.projetbackend.utils.enums;
/**
 * @autor abderox
 */
public enum TypeTransaction {
    VIREMENT(ToString.VIREMENT),
    RETRAIT(ToString.RETRAIT),
    DEPOT(ToString.DEPOT);


    private final String label;

    public class ToString {

        // ! TRANSACTIONS
        public static final String VIREMENT = "VIREMENT";
        public static final String RETRAIT = "RETRAIT";
        public static final String DEPOT = "DEPOT";



    }

    TypeTransaction(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
