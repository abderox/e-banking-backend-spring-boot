package com.adria.projetbackend.utils.enums;

/**
 * @autor abdelhadi mouzafir
 */

public enum RolesE {

    // ! roles
    ROLE_USER(ToString.ROLE_USER),
    ROLE_ADMIN(ToString.ROLE_ADMIN),
    ROLE_SUPER_ADMIN(ToString.ROLE_SUPER_ADMIN),

    ;

    private final String label;

    public class ToString {

        public static final String MANAGE_USERS = "MANAGE_USERS";
        public static final String MANAGE_REQUESTS = "MANAGE_REQUESTS";
        public static final String REGULAR_USER = "REGULAR_USER";
        public static final String VIEW_PRIVILEGE = "REGULAR_USER";
        public static final String EDIT_PRIVILEGE = "REGULAR_USER";

        // ! roles

        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    }

    RolesE(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

}
