package com.adria.projetbackend.utils.enums;

public enum TypeSituationFam {

    MARRIED(ToString.MARRIED),
    SINGLE(ToString.SINGLE);


    private final String label;

    public class ToString {

        // ! users
        public static final String MARRIED = "MARRIED";
        public static final String SINGLE = "SINGLE";


    }

    TypeSituationFam(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
