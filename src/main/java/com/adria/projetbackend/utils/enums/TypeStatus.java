package com.adria.projetbackend.utils.enums;
/**
 * @autor abderox
 */
import org.slf4j.LoggerFactory;

public enum TypeStatus {


    ACTIVE(ToString.ACTIVE),
    SUSPENDU(ToString.SUSPENDU),
    BLOQUE(ToString.BLOQUE),
    DESACTIVE(ToString.DESACTIVE);


    private final String label;

    public class ToString {

        public static final String DESACTIVE = "DESACTIVE";
        public static final String ACTIVE = "ACTIVE";
        public static final String BLOQUE = "BLOQUE";
        public static final String SUSPENDU = "SUSPENDU";


    }

    TypeStatus(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

    public static TypeStatus getStatus(String status) {

        String status_ = status != null ? status : "dd";

        System.out.println(status_);


        switch (status_) {
            case "ACTIVE":
                return TypeStatus.ACTIVE;
            case "BLOQUE":
                return TypeStatus.BLOQUE;
            case "SUSPENDU":
                return TypeStatus.SUSPENDU;
            case "DESACTIVE":
                return TypeStatus.DESACTIVE;
            default:
                return TypeStatus.DESACTIVE;
        }
    }
}
