/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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

    public static Date convertDate(String ing, String pattern) {
        try {
            return DateTimeFormat.forPattern(pattern).parseDateTime(ing).toDate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createDir(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (Exception e) {
        }
    }

    public static Logger createLog(String nameapp, String logpath, boolean neet) {
        try {
            File dir1 = new File(logpath);
            createDir(logpath);
            Date d = new Date();
            String dataOdierna = (new SimpleDateFormat("ddMMyyyy")).format(d);
            File dir2 = new File(dir1.getPath() + File.separator + dataOdierna);
            createDir(dir1.getPath() + File.separator + dataOdierna);
            String ora = (new SimpleDateFormat("HHmmss")).format(d);
            Logger logger = Logger.getLogger("MyLog");
            FileHandler fh = new FileHandler(dir2.getPath() + File.separator + nameapp + "_" + ora + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            return logger;
        } catch (Exception e) {
            e.printStackTrace();
            return Logger.getLogger(Utils.class.getName());
        }
    }

    public static void insertRequest(String request) {
        try {
            Database db2 = new Database();
            db2.insertTracking("SERVICE IDOL", request);
            db2.closeDB();
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
    }

}
