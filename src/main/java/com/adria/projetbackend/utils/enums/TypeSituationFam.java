package com.adria.projetbackend.utils.enums;
/**
 * @autor abderox
 */
public enum TypeSituationFam {

    MARRIED(ToString.MARRIED),
    SINGLE(ToString.SINGLE),
    NODATA(ToString.NODATA)
    ;


    private final String label;

    public class ToString {

        // ! users
        public static final String MARRIED = "MARRIED";
        public static final String SINGLE = "SINGLE";
        public static final String NODATA = "NODATA";


    }

    TypeSituationFam(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

    public static TypeSituationFam getTypeSituationFam(String typeSituationFam) {
        String typeSituationFam_ = typeSituationFam != null ? typeSituationFam.toUpperCase() : "dd";
        switch ( typeSituationFam_ ) {
            case TypeSituationFam.ToString.MARRIED:
                return TypeSituationFam.MARRIED;
            case TypeSituationFam.ToString.SINGLE:
                return TypeSituationFam.SINGLE;
            default:
                return TypeSituationFam.NODATA;
        }
    }
}
