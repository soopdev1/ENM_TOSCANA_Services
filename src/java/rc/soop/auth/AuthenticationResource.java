/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administrator
 */
@Path("/authentication")
public class AuthenticationResource {

    private static final String usrIDOL = "Idol2023";
    private static final String pswIDOL = "6bffcb071bb012fc5a8592108630a977";// m7CW96}\DHgQ
    private static final String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdRR";

    @GET
    public Response authenticateUser() {
        try {
            //Credentials credentials = new Gson().fromJson(jo_credentials, Credentials.class);
            // Authenticate the user using the credentials provided
            //authenticate(credentials.getUsername(), credentials.getPassword());
            // Issue a token for the user
            String token = issueToken(usrIDOL);
//            String token = issueToken(credentials.getUsername());
            // Return the token on the response
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        try {
            boolean e1 = username.trim().equals(usrIDOL);
            boolean e2 = password.trim().equals(pswIDOL);
            if (e1 && e2) {

            } else {
                throw new IOException();
            }
        } catch (Exception e) {
             e.printStackTrace();
            throw new IOException();
        }
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        Instant is1 = Instant.now();
        Key ju = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .claim("name", username)
                .claim("email", username + "@enm.it")
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(is1))
                .setExpiration(Date.from(is1.plus(5l, ChronoUnit.MINUTES)))
                .signWith(ju)
                .compact();
    }

    
}
