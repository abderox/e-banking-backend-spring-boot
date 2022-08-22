package com.adria.projetbackend.utils.enums;

public enum TypeVirement {
    MULTIPLE(ToString.MULTIPLE),
    UNITAIRE(ToString.UNITAIRE);



    private final String label;

    public class ToString {

        // ! TRANSACTIONS
        public static final String MULTIPLE = "MULTIPLE";
        public static final String UNITAIRE = "UNITAIRE";



    }

    TypeVirement(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
