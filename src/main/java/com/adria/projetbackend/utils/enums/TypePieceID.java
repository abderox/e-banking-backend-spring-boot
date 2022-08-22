package com.adria.projetbackend.utils.enums;

public enum TypePieceID {
    PASSPORT(ToString.PASSPORT),
    ID(ToString.ID),
    PERMIS(ToString.PERMIS);


    private final String label;

    public class ToString {

        // ! PIECES ID
        public static final String PASSPORT = "PASSPORT";
        public static final String ID = "ID";
        public static final String PERMIS = "PERMIS";



    }

    TypePieceID(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
