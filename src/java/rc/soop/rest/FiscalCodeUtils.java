/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Administrator
 */
public class FiscalCodeUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int SETDISP[] = {1, 0, 5, 7, 9, 13, 15, 17,
        19, 21,
        2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};

    private static final Map<Character, Integer> DIGITCONVERSIONMAP;

    static {
        TreeMap<Character, Integer> map = new TreeMap<>();
        map.put('L', 0);
        map.put('M', 1);
        map.put('N', 2);
        map.put('P', 3);
        map.put('Q', 4);
        map.put('R', 5);
        map.put('S', 6);
        map.put('T', 7);
        map.put('U', 8);
        map.put('V', 9);
        DIGITCONVERSIONMAP = Collections.unmodifiableSortedMap(map);

    }

    public FiscalCodeUtils() {

    }

    private static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String[] validateFiscalCode(String cf) {
        FiscalCodeParserResult result = parseFiscalCode(cf);
        switch (result.getResultCode()) {

            case 1: {
                String[] out = {"false", "LUNGHEZZA ERRATA"};
                return out;
            }
            case 2: {
                String[] out = {"false", "COGNOME O NOME NON COERENTE"};
                return out;
            }
            case 3:
            case 9: {
                String[] out = {"false", "CODICE CONTROLLO ERRATO"};
                return out;
            }
            case 4: {
                String[] out = {"false", "ANNO NASCITA ERRATO"};
                return out;
            }
            case 5: {
                String[] out = {"false", "MESE NASCITA ERRATO"};
                return out;
            }
            case 6: {
                String[] out = {"false", "GIORNO NASCITA ERRATO"};
                return out;
            }
            case 7: {
                String[] out = {"false", "DATA NASCITA ERRATA"};
                return out;
            }
            case 8: {
                String[] out = {"false", "CODICE ISTAT ERRATO"};
                return out;
            }
        }
        String[] out = {"true", "OK"};
        return out;
    }

    private static FiscalCodeParserResult parseFiscalCode(String cf) {
        FiscalCodeParserResult result = new FiscalCodeParserResult();
        // Elimino eventuali spazi
        cf = deleteWhitespace(cf);

        // Controllo della lunghezza
        if (isEmpty(cf) || cf.length() != 16) {
            result.setResultCode(1);
            return result;
        }

        cf = cf.toUpperCase();

        // Controllo dei primi 6 caratteri
        for (int i = 0; i < 6; i++) {
            if (!(Character.isLetter(cf.charAt(i)))) { // controllo dei 
//                primi 6
                result.setResultCode(2);
                return result;
            }
        }

        // Controllo dei campi numerici che possono essere numeri 
//        oppure lettere
        if (!validateDigits(cf)) {
            result.setResultCode(3);
            return result;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setLenient(false);
        // Validazione Anno
        try {
            calendar.set(Calendar.YEAR, getYear(cf.substring(6, 8)));
        } catch (Exception e) {
            result.setErrorCause(e);
            result.setResultCode(4);
            return result;
        }
        // Validazione Mese
        try {
            Integer month = getMonth(cf.charAt(8));
            if (month != null) {
                calendar.set(Calendar.MONTH, month);
            } else {
                result.setResultCode(5);
                return result;
            }
        } catch (Exception e) {
            result.setErrorCause(e);
            result.setResultCode(5);
            return result;
        }
        boolean male = true;
        // Validazione del Giorno
        try {
            String dd = cf.substring(9, 11);
            int day = Integer.parseInt(getDigit(dd.charAt(0)) + ""
                    + getDigit(dd.charAt(1)));
            if (day > 40) {
                male = false;
                day -= 40;
            }

            calendar.set(Calendar.DAY_OF_MONTH, day);
        } catch (Exception e) {
            result.setErrorCause(e);
            result.setResultCode(6);
            return result;
        }
        // Validazione Correttezza Data di Nascita
        try {
            Date birthDay = calendar.getTime();
        } catch (Exception e) {
            result.setErrorCause(e);
            result.setResultCode(7);
            return result;
        }

        // Validazione Primo Carattere codice Istat del Comune. Deve 
        //essere una // Lettera tra quelle in elenco
        if ((cf.charAt(11) != 'A') && (cf.charAt(11) != 'B')
                && // controllo del 1° carattere
                (cf.charAt(11) != 'C') && (cf.charAt(11) != 'D')
                && // del codice catastale
                (cf.charAt(11) != 'E') && (cf.charAt(11) != 'F')
                && (cf.charAt(11) != 'G') && (cf.charAt(11) != 'H')
                && (cf.charAt(11) != 'I') && (cf.charAt(11) != 'L')
                && (cf.charAt(11) != 'M') && (cf.charAt(11) != 'Z')) {
            result.setResultCode(8);
            return result;
        }
        int s = 0;
        for (int i = 1; i <= 13; i += 2) {
            int c = cf.charAt(i);
            if (c >= '0' && c <= '9') {
                s = s + c - '0';
            } else {
                s = s + c - 'A';
            }
        }
        for (int i = 0; i <= 14; i += 2) {
            int c = cf.charAt(i);
            if (c >= '0' && c <= '9') {
                c = c - '0' + 'A';
            }
            s = s + SETDISP[c - 'A'];
        }
        if (s % 26 + 'A' != cf.charAt(15)) {
            result.setResultCode(9);
            return result;
        }
        result.setResultCode(0);
        return result;

    }

    private static boolean validateDigits(String cf) {
        StringBuilder digits = new StringBuilder();
        digits.append(cf.substring(6, 8));
        digits.append(cf.substring(9, 11));
        digits.append(cf.substring(12, 15));
        for (int i = 0; i < digits.length(); i++) {
            char currentChar = digits.charAt(i);
            if (!(Character.isDigit(currentChar) || DIGITCONVERSIONMAP
                    .containsKey(currentChar))) {
                return false;
            }
        }
        return true;

    }

    private static String getBirthCountry(String cf) {
        if (cf == null || cf.length() < 16) {
            return null;
        }
        cf = cf.toLowerCase();
        String result = cf.substring(11, 15);
        result = result.charAt(0) + "" + getDigit(result.charAt(1)) + ""
                + getDigit(result.charAt(2)) + ""
                + getDigit(result.charAt(3));
        return result;
    }

    private static int getYear(String yy) {
        if (yy == null || yy.length() != 2) {
            throw new IllegalArgumentException("Invalid Year " + yy);

        } else {
            yy = getDigit(yy.charAt(0)) + "" + getDigit(yy.charAt(1));

            Calendar c = Calendar.getInstance();
            int yyyy = c.get(Calendar.YEAR);

            String yy_sup = String.valueOf(yyyy).substring(0, 2);
            String yy_inf = String.valueOf(yyyy).substring(2, 4);

            yyyy = Integer.parseInt(yy_inf);
            if (yyyy > Integer.parseInt(yy)) {
                yyyy = Integer.parseInt(yy_sup + yy);
            } else {
                yyyy = Integer.parseInt(yy_sup + "00") - 100
                        + Integer.parseInt(yy);
            }

            return yyyy;
        }

    }

    private static int getDigit(char character) {
        if (Character.isDigit(character)) {
            return Integer.parseInt(String.valueOf(character));
        } else {
            Integer result = DIGITCONVERSIONMAP.get(Character
                    .toUpperCase(character));
            if (result == null) {
                throw new IllegalArgumentException("Invalid Digit "
                        + character);
            }
            return result;
        }

    }

    private static Integer getMonth(char character) {
        Integer mm = null;
        switch ((int) character) {
            case (int) 'A': // gennaio
                mm = Calendar.JANUARY;
                break;
            case (int) 'B':  // febbraio
                mm = Calendar.FEBRUARY;
                break;
            case (int) 'C': // marzo
                mm = Calendar.MARCH;
                break;
            case (int) 'D': // aprile
                mm = Calendar.APRIL;
                break;
            case (int) 'E': // maggio
                mm = Calendar.MAY;
                break;
            case (int) 'H': // giugno
                mm = Calendar.JUNE;
                break;
            case (int) 'L': // luglio
                mm = Calendar.JULY;
                break;
            case (int) 'M': // agosto
                mm = Calendar.AUGUST;
                break;
            case (int) 'P': // settembre
                mm = Calendar.SEPTEMBER;
                break;
            case (int) 'R': // ottobre
                mm = Calendar.OCTOBER;
                break;
            case (int) 'S':  // novembre
                mm = Calendar.NOVEMBER;
                break;
            case (int) 'T': // dicembre
                mm = Calendar.DECEMBER;
                break;
        }
        return mm;
    }

}

class FiscalCodeParserResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Date birthDay;

    private int resultCode;

    private boolean male;

    private String birthCountry;

    private transient Throwable errorCause;

    /**
     * @return the birthDay
     */
    public Date getBirthDay() {
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * @return the resultCode
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * @return the male
     */
    public boolean isMale() {
        return male;
    }

    /**
     * @param male the male to set
     */
    public void setMale(boolean male) {
        this.male = male;
    }

    /**
     * @return the birthCountry
     */
    public String getBirthCountry() {
        return birthCountry;
    }

    /**
     * @param birthCountry the birthCountry to set
     */
    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    /**
     * @return the errorCause
     */
    public Throwable getErrorCause() {
        return errorCause;
    }

    /**
     * @param errorCause the errorCause to set
     */
    public void setErrorCause(Throwable errorCause) {
        this.errorCause = errorCause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FiscalCodeParserResult [birthDay=" + "]";
    }

}
