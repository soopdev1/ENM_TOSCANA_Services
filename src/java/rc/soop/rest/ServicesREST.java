/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import rc.soop.auth.Secured;
import static rc.soop.rest.Utils.PATTERN1;
import static rc.soop.rest.Utils.convertDate;
import static rc.soop.rest.Utils.validateCF;

/**
 *
 * @author Raffaele
 */
/**
 * Root resource (exposed at "ServicesREST" path)
 */
@Path("enm")
public class ServicesREST {

    private static final String usrIDOL = "Idol2023";
    private static final String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdRR";

    @GET
    @Path("/authentication")
    @Produces("application/json")
    public Response authenticateUser() {
        try {
            //Credentials credentials = new Gson().fromJson(jo_credentials, Credentials.class);
            // Authenticate the user using the credentials provided
            //authenticate(credentials.getUsername(), credentials.getPassword());
            // Issue a token for the user
            String token = issueToken(usrIDOL);
//            String token = issueToken(credentials.getUsername());
            // Return the token on the response
            return Response.status(200).entity(new Gson().toJson(new Response_auth(token))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

//    private void authenticate(String username, String password) throws Exception {
//        // Authenticate against a database, LDAP, file or whatever
//        // Throw an Exception if the credentials are invalid
//        try {
//            boolean e1 = username.trim().equals(usrIDOL);
//            boolean e2 = password.trim().equals(pswIDOL);
//            if (e1 && e2) {
//
//            } else {
//                throw new IOException();
//            }
//        } catch (Exception e) {
//             e.printStackTrace();
//            throw new IOException();
//        }
//    }
    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        Instant is1 = Instant.now();
        Key ju = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .claim("name", username)
                .claim("email", username + "@microcredito.gov.it")
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(is1))
                .setExpiration(Date.from(is1.plus(5, ChronoUnit.MINUTES)))
                .signWith(ju)
                .compact();
    }

    @GET
    @Secured
    @Path("/testservices")
    @Produces("application/json")
    public Response testservices() {
        ArrayList<LinkedHashMap<String, String>> esito = new ArrayList<>();
        LinkedHashMap<String, String> mapesito = Maps.newLinkedHashMap();
        mapesito.put("result", "The service is active.");
        esito.add(mapesito);
        return Response.status(200).entity(new Gson().toJson(esito)).build();
    }

    @POST
    @Secured
    @Path("/verificacf")
    @Consumes("application/json")
    @Produces("application/json")
    public Response verificacf(String json_cf) {
        Utils.insertRequest("REQ verificacf: " + json_cf);

        JsonObject convertedObject = new Gson().fromJson(json_cf, JsonObject.class);
        String codiceFiscale = getJsonString(convertedObject, "codiceFiscale");
        String errorcf = validateCF(codiceFiscale);
        if (errorcf == null) {
            Database db1 = new Database();
            Response_verificacf OUT1 = db1.verificacf(codiceFiscale);
            db1.closeDB();
            Utils.insertRequest("RESPONSE verificacf: " + new Gson().toJson(OUT1));
            return Response.status(200).entity(new Gson().toJson(OUT1)).build();
        } else {
            Response_verificacf OUT = new Response_verificacf(null, "KO", "ERRORE CODICE FISCALE: " + errorcf);
            return Response.status(200).entity(new Gson().toJson(OUT)).build();
        }
    }

    @POST
    @Secured
    @Path("/insertanagrafica")
    @Consumes("application/json")
    @Produces("application/json")
    public Response insertanagrafica(String json_values) {
        Utils.insertRequest("REQ insertanagrafica: " + json_values);
        JsonObject convertedObject = new Gson().fromJson(json_values, JsonObject.class);
        String codiceFiscale = getJsonString(convertedObject, "codiceFiscale");
        String errorcf = validateCF(codiceFiscale);
        if (errorcf == null) {
            Database db1 = new Database();
            Response_verificacf OUT1 = db1.verificacf(codiceFiscale);
            db1.closeDB();
            if (OUT1.getMsg() != null) {
                if (OUT1.getOperationStatus().equals("SUCCESS")) {
                    Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", "ANAGRAFICA GIA' PRESENTE.");
                    return Response.status(200).entity(new Gson().toJson(OUT)).build();
                } else {
                    Response_insertanagrafica OUT = new Response_insertanagrafica(null, OUT1.getOperationStatus(), OUT1.getOperationMessage());
                    return Response.status(200).entity(new Gson().toJson(OUT)).build();
                }
            } else {
                String nome = getJsonString(convertedObject, "nome");
                String cognome = getJsonString(convertedObject, "cognome");
                String dataNascita = getJsonString(convertedObject, "dataNascita");
                String sesso = getJsonString(convertedObject, "sesso");
                String codCatastaleComuneNascita = getJsonString(convertedObject, "codCatastaleComuneNascita");
                String codCatastaleStatoCittadinanza = getJsonString(convertedObject, "codCatastaleStatoCittadinanza");
                Database db2 = new Database();
                Long idallievo = db2.insertAnagrafica(nome, cognome, codiceFiscale, sesso, dataNascita, codCatastaleComuneNascita, codCatastaleStatoCittadinanza);
                db2.closeDB();
                if (idallievo > 0) {
                    Response_insertanagrafica OUT = new Response_insertanagrafica(new msg_Response_insertanagrafica(idallievo), "SUCCESS", "Operazione eseguita con successo");
                    Utils.insertRequest("RESPONSE insertanagrafica: " + new Gson().toJson(OUT));

                    return Response.status(200).entity(new Gson().toJson(OUT)).build();
                } else {
                    String message = "ERRORE DURANTE L'INSERIMENTO DEI DATI. CONTATTARE ADMIN.";
                    switch (String.valueOf(idallievo)) {
                        case "-1": {
                            message = "COMUNE NASCITA NON TROVATO.";
                            break;
                        }
                        case "-2": {
                            message = "STATO NASCITA NON TROVATO.";
                            break;
                        }
                    }
                    Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", message);
                    return Response.status(200).entity(new Gson().toJson(OUT)).build();
                }
            }
        } else {
            Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", "ERRORE CODICE FISCALE: " + errorcf);
            return Response.status(200).entity(new Gson().toJson(OUT)).build();
        }
    }

    @POST
    @Secured
    @Path("/annulla")
    @Consumes("application/json")
    @Produces("application/json")
    public Response annulla(String json_values) {
        Utils.insertRequest("annulla: " + json_values);
        JsonObject convertedObject = new Gson().fromJson(json_values, JsonObject.class);
        String codiceFiscale = getJsonString(convertedObject, "codiceFiscale");
        String codiceAttivita = getJsonString(convertedObject, "codiceAttivita");
        String esito = getJsonString(convertedObject, "esito");
        String dataEsito = getJsonString(convertedObject, "dataEsito");
        String errorcf = validateCF(codiceFiscale);
        if (errorcf == null) {
            Database db1 = new Database();
            Response_verificacf OUT1 = db1.verificacf(codiceFiscale);
            db1.closeDB();
            if (OUT1.getMsg() != null) {
                if (OUT1.getOperationStatus().equals("SUCCESS") || OUT1.getOperationMessage().contains("ISCRIZIONE GIA' EFFETTUATA")) {
                    String esitodest = "09";
                    if (esito.equals("ANNULLATA_CPI_APL")) {
                        Database db2 = new Database();
                        boolean update_esito = db2.cambiostatoallievo(codiceFiscale, esitodest);
                        db2.insertTracking("SERVICE IDOL", "ANNULLAMENTO ANAGRAFICA ALLIEVO " + codiceFiscale + " - ATTIVITA: " + codiceAttivita + " - DATA ESITO: " + dataEsito);
                        db2.closeDB();
                        if (update_esito) {
                            Response_insertanagrafica OUT = new Response_insertanagrafica(null, "SUCCESS", "Operazione eseguita con successo");
                            Utils.insertRequest("RESPONSE annulla: " + new Gson().toJson(OUT1));
                            return Response.status(200).entity(new Gson().toJson(OUT)).build();
                        } else {
                            Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", "STATO ALLIEVO NON COERENTE.");
                            return Response.status(200).entity(new Gson().toJson(OUT)).build();
                        }
                    } else {
                        Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", "ESITO NON RICONOSCIUTO.");
                        return Response.status(200).entity(new Gson().toJson(OUT)).build();
                    }

                } else {
                    Response_insertanagrafica OUT = new Response_insertanagrafica(null, OUT1.getOperationStatus(), OUT1.getOperationMessage());
                    return Response.status(200).entity(new Gson().toJson(OUT)).build();
                }
            } else {
                Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", "CODICE FISCALE NON TROVATO.");
                return Response.status(200).entity(new Gson().toJson(OUT)).build();
            }
        } else {
            Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", "ERRORE CODICE FISCALE: " + errorcf);
            return Response.status(200).entity(new Gson().toJson(OUT)).build();
        }
    }

    @POST
    @Secured
    @Path("/insertiscrizione")
    @Consumes("application/json")
    @Produces("application/json")
    public Response insertiscrizione(String json_values) {
        Utils.insertRequest("insertiscrizione: " + json_values);

        JsonObject convertedObject = new Gson().fromJson(json_values, JsonObject.class);
        String codiceFiscale = getJsonString(convertedObject, "codiceFiscale");
        String dataIscrizione = getJsonString(convertedObject, "dataIscrizione");
//            String note = getJsonString(convertedObject, "note");
        String dirittoIndennita = getJsonString(convertedObject, "dirittoIndennita");
        String finanziamento = getJsonString(convertedObject, "finanziamento");
        String telefono = getJsonString(convertedObject, "telefono");
        String email = getJsonString(convertedObject, "email");
        JsonObject datiIscrizione = convertedObject.getAsJsonObject("datiIscrizione");
        JsonObject indirizzoResidenza = datiIscrizione.getAsJsonObject("indirizzoResidenza");
        JsonObject titoloStudio = datiIscrizione.getAsJsonObject("titoloStudio");
        JsonObject centroImpiego = datiIscrizione.getAsJsonObject("centroImpiego");
        JsonObject condizioneProfessionale = datiIscrizione.getAsJsonObject("condizioneProfessionale");
        String codCatastaleComune = getJsonString(indirizzoResidenza, "codCatastaleComune");
//            String localita = getJsonString(indirizzoResidenza, "localita");
        String via = getJsonString(indirizzoResidenza, "via");
        String cap = getJsonString(indirizzoResidenza, "cap");
        String idGruppoVulnerabile = getJsonString(datiIscrizione, "idGruppoVulnerabile");
        String idTitoloStudio = getJsonString(titoloStudio, "idTitoloStudio");
        String idCentroImpiego = getJsonString(centroImpiego, "idCentroImpiego");
        String dataIscrizioneCentroImpiego = getJsonString(centroImpiego, "dataIscrizioneCentroImpiego");
        String tipoCondizioneProfessionale = getJsonString(condizioneProfessionale, "tipoCondizioneProfessionale");

        String errorcf = validateCF(codiceFiscale);
        if (errorcf == null) {
            Database db1 = new Database();
            Response_verificacf OUT1 = db1.verificacf(codiceFiscale);
            db1.closeDB();

            if (OUT1.getMsg() != null) {
                if (OUT1.getOperationStatus().equals("SUCCESS")) {

                    Allievi al1 = new Allievi();
                    al1.setCodicefiscale(codiceFiscale);
                    al1.setTelefono(telefono);
                    al1.setEmail(email); // DA FARE CHECK
                    al1.setTos_dirittoindennita(dirittoIndennita);//NON SO
                    al1.setTos_tipofinanziamento(finanziamento); // DA FARE CHECK
                    al1.setIscrizionegg(convertDate(dataIscrizione, PATTERN1)); // DA FARE CHECK SU NULLA
                    Database db2 = new Database();
                    Long idComuneResidenza = db2.getIdComune(codCatastaleComune);
                    
                    al1.setComune_residenza(String.valueOf(idComuneResidenza)); //CHECK SE DIVERSO DA 0
                    al1.setCapresidenza(cap);
                    al1.setIndirizzoresidenza(via);

                    al1.setTos_gruppovulnerabile(idGruppoVulnerabile); //CHECK
                    al1.setTitoloStudio(idTitoloStudio); // CHECK
                    al1.setCpi(idCentroImpiego); //CHECK
                    al1.setDatacpi(convertDate(dataIscrizioneCentroImpiego, PATTERN1)); // DA FARE CHECK SU NULLA
                    al1.setCondizione_lavorativa(tipoCondizioneProfessionale); //CHECK

                    //CHECK
                    
                    
                    //UPDATE VALORE PER PRODUZIONE
                    
                    
                    
                    
                    
                    db2.closeDB();
                    


                    //INSERT
                    Response_insertiscrizione OUT = new Response_insertiscrizione(
                            new msg_Response_insertiscrizione(al1.getId()),
                            "SUCCESS", "Operazione eseguita con successo");
                    Utils.insertRequest("RESPONSE insertiscrizione: " + new Gson().toJson(OUT1));

                    return Response.status(200).entity(new Gson().toJson(OUT)).build();

                } else {
                    Response_insertiscrizione OUT = new Response_insertiscrizione(null, OUT1.getOperationStatus(), OUT1.getOperationMessage());
                    return Response.status(200).entity(new Gson().toJson(OUT)).build();
                }
            } else {

                if (OUT1.getOperationMessage() != null) {
                    Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", OUT1.getOperationMessage());
                    return Response.status(200).entity(new Gson().toJson(OUT)).build();

                } else {

                    Response_insertiscrizione OUT = new Response_insertiscrizione(null, "KO", "CODICE FISCALE NON TROVATO.");
                    return Response.status(200).entity(new Gson().toJson(OUT)).build();
                }
            }

        } else {
            Response_insertiscrizione OUT = new Response_insertiscrizione(null, "KO", "ERRORE CODICE FISCALE: " + errorcf);
            return Response.status(200).entity(new Gson().toJson(OUT)).build();
        }
    }

    private String getJsonString(JsonObject base, String fieldname) {
        try {
            JsonPrimitive o1 = base.getAsJsonPrimitive(fieldname);
            if (o1 != null) {
                return o1.getAsString().trim();
            } else {
                Object o2 = base.getAsJsonObject(fieldname).toString();
                if (o2 != null) {
                    return o2.toString().trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
