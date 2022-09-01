package com.adria.projetbackend.utils.enums;
/**
 * @autor abderox
 */
public enum TypeUser {
    CLIENT(ToString.CLIENT),
    BANQUIER(ToString.BANQUER);


    private final String label;

    public class ToString {

        // ! users
        public static final String CLIENT = "CLIENT";
        public static final String BANQUER = "BANQUER";


    }

    TypeUser(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
