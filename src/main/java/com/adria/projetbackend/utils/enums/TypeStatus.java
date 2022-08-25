package com.adria.projetbackend.utils.enums;

import org.slf4j.LoggerFactory;

public enum TypeStatus {


    ACTIVE(ToInt.ACTIVE),
    SUSPENDU(ToInt.SUSPENDU),
    BLOQUE(ToInt.BLOQUE),
    DESACTIVE(ToInt.DESACTIVE);


    private final Integer label;

    public class ToInt {

        public static final int DESACTIVE = 0;
        public static final int ACTIVE = 1;
        public static final int BLOQUE = 2;
        public static final int SUSPENDU = 3;


    }

    TypeStatus(Integer label) {
        this.label = label;
    }

    public Integer ToInt() {
        return this.label;
    }

    public static TypeStatus getStatus(String status) {

        String status_ = status != null ? status.toUpperCase() : "dd";
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
