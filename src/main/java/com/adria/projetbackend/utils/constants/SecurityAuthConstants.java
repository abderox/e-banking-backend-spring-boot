package com.adria.projetbackend.utils.constants;

public class SecurityAuthConstants {


    public static final String API_URL_V1 = "/api/v1";
    public static final String ANY_URL_V1 = API_URL_V1 + "/**";
    public static final String API_URL_V2 = "/api/v2";
    public static final String ANY_URL_V2 = API_URL_V2 + "/**";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/sign-up";
    public static final String SIGN_IN_URL = "/auth/sign-in";
    public static final String VERIFY_URL = "/auth/verify";
    public static final String REFRESH_URL = "/auth/refresh";
    public static final String SIGN_OUT_URL = "/auth/sign-out";
    public static final String SIGN_UP_URL_ADMIN = "/auth/sign-up-admin";
    public static final String SIGN_IN_URL_ADMIN = "/auth/sign-in-admin";
    public static final String VERIFY_URL_ADMIN = "/auth/verify-admin";
    public static final String REFRESH_URL_ADMIN = "/auth/refresh-admin";
    public static final String SIGN_OUT_URL_ADMIN = "/auth/sign-out-admin";
    public static final String SIGN_UP_URL_CLIENT = "/auth/register-client";
    public static final String SIGN_IN_URL_CLIENT = "/auth/sign-in-client";
    public static final String VERIFY_URL_CLIENT = "/auth/verify-client";
    public static final String REFRESH_URL_CLIENT = "/auth/refresh-client";
    public static final String SIGN_OUT_URL_CLIENT = "/auth/sign-out-client";
    public static final String GET_BANKER_INFO = "/banker/info";
    public static final String SEND_OTP = "/auth/send-otp";
    public static final String VERIFY_OTP = "/auth/verify-otp";
    public static final String UPDATE_PASSWORD = "/auth/update-password";
    public static final long EXPIRATION_TIME = 3900000; // 1 hour
    public static final String GET_ACTIVE_SESSIONS = "/auth/active-sessions";
    public static final String SIGN_OUT_SESSION = "/auth/sign-out-session";
}
