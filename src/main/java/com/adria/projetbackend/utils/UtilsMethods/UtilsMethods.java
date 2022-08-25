package com.adria.projetbackend.utils.UtilsMethods;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UtilsMethods {

    private static final int len = 6;
    private static final String ZERO = "0";

    public static String generateClientId(String codeAgence, String idClient, String idBanque) {
        // ? format de identifiant client : codeAgence + randomUUID +idClient + idBanque
        int calc = idClient.length( )-1;
        String uuid = String.format("%040d", new BigInteger(
                UUID.randomUUID( ).toString( ).
                replace("-", ""), 16)).
                replace("0", "").
                substring(0, len - calc);

        return codeAgence + uuid + ZERO + idClient + idBanque;
    }

    public static Date chooseDate(int year, int month, int day) throws ParseException {
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
        return f2.parse(year + "-" + month + "-" + day);
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
        return f2.parse(date);
    }


}
