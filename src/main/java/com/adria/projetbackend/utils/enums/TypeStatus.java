package com.adria.projetbackend.utils.enums;

public enum TypeStatus {

    ACTIVE(ToInt.ACTIVE),
    SUSPENDU(ToInt.SUSPENDU),
    BLOQUE(ToInt.BLOQUE);


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
    }}
