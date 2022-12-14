package com.adria.projetbackend.utils.UtilsMethods;

/**
 * @autor abderox
 */

import com.adria.projetbackend.utils.enums.TypeTransaction;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsMethods {

    private static final int len = 6;
    private static final String ZERO = "0";
    private static final int OTP_LEN = 6;

    public static String generateClientId(String codeAgence, String idClient, String idBanque) {
        // ? format de identifiant client : codeAgence + randomUUID +idClient + idBanque
        int calc = idClient.length( ) - 1;
        String uuid = String.format("%040d", new BigInteger(
                UUID.randomUUID( ).toString( ).
                        replace("-", ""), 16)).
                replace("0", "").
                substring(0, len - calc);

        return codeAgence + uuid + ZERO + idClient + idBanque;
    }

    public static String generateAccountNumber(String codeAgence, String idClient, String idBanque, String idCompte) {
        int calc = idCompte.length( );
        int calc2 = idClient.length( );
        int calc3 = idBanque.length( );
        int calc4 = codeAgence.length( );
        System.out.println((24 - (calc + calc2 + calc3 + calc4)));
        String uuid = String.format("%040d", new BigInteger(idCompte, 16)).substring(0, (24 - (calc + calc2 + calc3 + calc4)));
        return codeAgence + uuid + idCompte + idClient + idBanque;
    }

    public static Date chooseDate(int year, int month, int day) throws ParseException {
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
        return f2.parse(year + "-" + month + "-" + day);
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
        return f2.parse(date);
    }

    public static String generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random( );
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length( )));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length( )));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length( )));
        password[3] = numbers.charAt(random.nextInt(numbers.length( )));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length( )));
        }
        return new String(password);
    }

    public static String generateRefTransaction(String idTransaction, String idCompte, TypeTransaction type) {
        int calc = idCompte.length( );
        int calc2 = idTransaction.length( );
        String uuid = String.format("%040d", new BigInteger(idCompte, 16)).substring(0, (15 - (calc + calc2)));
        if ( type.equals(TypeTransaction.VIREMENT) ) {
            return "V_" + uuid + idTransaction + idCompte;
        } else if ( type.equals(TypeTransaction.RETRAIT) ) {
            return "R_" + uuid + idTransaction + idCompte;
        } else if ( type.equals(TypeTransaction.DEPOT) ) {
            return "D_" + uuid + idTransaction + idCompte;
        } else {
            return uuid + idTransaction + idCompte;
        }
    }

    public static String OTP()
    {
        String numbers = "0123456789";

        Random rndm_method = new Random();

        char[] otp = new char[OTP_LEN];

        for (int i = 0; i < OTP_LEN; i++)
        {
            otp[i] =
                    numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return new String(otp);
    }

    public static boolean isValidPassword(String password)
    {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";


        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);


        return m.matches();
    }

    public static Date getDateAfterPeriod(Date date, String periodicity) {
        LocalDate localDate = date.toInstant( ).atZone(ZoneId.systemDefault( )).toLocalDate( );
        LocalDate newLocalDate = null;
        switch (periodicity) {
            case "D":
                newLocalDate = localDate.plusDays(1);
                break;
            case "F":
                newLocalDate = localDate.plusWeeks(2);
                break;
            case "M":
                newLocalDate = localDate.plusMonths(1);
                break;
            case "W":
                newLocalDate = localDate.plusWeeks(1);
                break;
            default:
                newLocalDate = localDate;
                break;
        }
        assert newLocalDate != null;
        return Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault( )).toInstant( ));
    }
}
