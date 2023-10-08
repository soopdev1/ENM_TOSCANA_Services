/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Administrator
 */
public class Utils {
    
    public static final String PATTERN1 = "dd/MM/yyyy";
    public static final String PATTERN2 = "yyyy-MM-dd";
    public static final DateTimeFormatter DTFPATTERN1 = DateTimeFormat.forPattern(PATTERN1);
    
    public static String estraiEccezione(Exception ec1) {
        try {
            String stack_nam = ec1.getStackTrace()[0].getMethodName();
            String stack_msg = ExceptionUtils.getStackTrace(ec1);
            return stack_nam + " - " + stack_msg;
        } catch (Exception e) {
        }
        return ec1.getMessage();

    }
    
    //CODICE FISCALE
    private static String validateCF_regular(String cf) {
        if (!cf.matches("^[0-9A-Z]{16}$")) {
            return "CARATTERI NON VALIDI";
        }
        int s = 0;
        String even_map = "BAFHJNPRTVCESULDGIMOQKWZYX";
        for (int i = 0; i < 15; i++) {
            int c = cf.charAt(i);
            int n;
            if ('0' <= c && c <= '9') {
                n = c - '0';
            } else {
                n = c - 'A';
            }
            if ((i & 1) == 0) {
                n = even_map.charAt(n) - 'A';
            }
            s += n;
        }
        if (s % 26 + 'A' != cf.charAt(15)) {
            return "CARATTERE DI CONTROLLO NON VALIDO.";
        }
        return null;
    }

    public static String validateCF(String cf) {
        try {
            cf = cf.replaceAll("[ \t\r\n]", "");
            cf = cf.toUpperCase();
            switch (cf.length()) {
                case 16:
                    return validateCF_regular(cf);
                default:
                    return "LUNGHEZZA ERRATA.";
            }
        } catch (Exception e) {
            return estraiEccezione(e);
        }
    }
    
}
