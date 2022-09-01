package com.adria.projetbackend.utils.enums;

/**
 * @autor abdelhadi mouzafir
 */

public enum RolesE {

    // ! roles
    ROLE_USER(ToString.ROLE_USER),
    ROLE_ADMIN(ToString.ROLE_ADMIN),
    ROLE_CLIENT(ToString.ROLE_CLIENT),
    ROLE_ACTIVE_USER(ToString.ROLE_ACTIVE_USER),
    ROLE_SUPER_ADMIN(ToString.ROLE_SUPER_ADMIN),

    ;

    private final String label;

    public class ToString {

        // ! roles
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_CLIENT = "ROLE_CLIENT";
        public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
        public static final String ROLE_ACTIVE_USER = "ROLE_ACTIVE_USER";


    }

    RolesE(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

}
