package rc.soop.rest;

import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class SO_AuthService {

    private static final String PTEMP = "/mnt/mcn/test/temp/";
    private static final Logger LOGGER = Utils.createLog("Toscana_Service_", PTEMP, true);
    private static final String USRIDOL = "Idol2023";
    private static final String PSWIDOL = "6bffcb071b?b012fc5-a8592108630a977m7CW96}&&DHgQ";// 

    public boolean authenticate(final String authCredentials) {
        if (null == authCredentials) {
            return false;
        }
        // Header value format for Basic authentication will be "Basic encodedstring"
        final String encodedUserAuthCredentials = authCredentials.replaceFirst("Basic ", "");
        String decodedUserAuthCredentials = null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedUserAuthCredentials);
            decodedUserAuthCredentials = new String(decodedBytes, "UTF-8");
        } catch (Exception e) {
            LOGGER.severe(Utils.estraiEccezione(e));
        }
        try {
            final StringTokenizer tokenizer = new StringTokenizer(decodedUserAuthCredentials, ":");
            final String userName = tokenizer.nextToken();
            final String userPassword = tokenizer.nextToken();
            return USRIDOL.equals(userName) && PSWIDOL.equals(userPassword);
        } catch (Exception e) {
            LOGGER.severe(Utils.estraiEccezione(e));
        }
        return false;
    }
}
