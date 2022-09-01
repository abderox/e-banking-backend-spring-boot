package com.adria.projetbackend.utils.enums;
/**
 * @autor abderox
 */
public enum TypePieceID {
    PASSPORT(ToString.PASSPORT),
    ID(ToString.ID),
    PERMIS(ToString.PERMIS),
    CIN(ToString.CIN);


    private final String label;

    public class ToString {

        // ! PIECES ID
        public static final String PASSPORT = "PASSPORT";
        public static final String ID = "ID";
        public static final String PERMIS = "PERMIS";
        public static final String CIN = "CIN";



    }

    TypePieceID(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

    public static TypePieceID getTypePieceID(String typePieceID) {
        String typePieceID_ = (typePieceID != null ) ? typePieceID.toUpperCase() : "id";
        switch ( typePieceID_ ) {
            case TypePieceID.ToString.CIN:
                return TypePieceID.CIN;
            case TypePieceID.ToString.PASSPORT:
                return TypePieceID.PASSPORT;
            case TypePieceID.ToString.PERMIS:
                return TypePieceID.PERMIS;
            default:
                return TypePieceID.ID;
        }
    }
}
