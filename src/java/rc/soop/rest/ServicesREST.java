/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import static rc.soop.rest.Utils.validateCF;

/**
 *
 * @author Raffaele
 */
/**
 * Root resource (exposed at "ServicesREST" path)
 */
@Path("enm/1.0")
public class ServicesREST {

    @GET
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
    @Path("/verificacf")
    @Produces("application/json")
    public Response verificacf(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("cf") String cf) {

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        if (username.equals(usrIDOL) && password.equals(pswIDOL)) {

            String errorcf = validateCF(cf);
            if (errorcf == null) {
                Database db1 = new Database();
                Response_verificacf OUT1 = db1.verificacf(cf);
                db1.closeDB();
                return Response.status(200).entity(new Gson().toJson(OUT1)).build();
            } else {
                Response_verificacf OUT = new Response_verificacf(null, "KO", "ERRORE CODICE FISCALE: " + errorcf);
                return Response.status(200).entity(new Gson().toJson(OUT)).build();
            }
        }
        Response_verificacf OUT = new Response_verificacf(null, "KO", "CREDENZIALI NON VALIDE");
        return Response.status(401).entity(new Gson().toJson(OUT)).build();
    }

    @POST
    @Path("/insertanagrafica")
    @Produces("application/json")
    public Response insertanagrafica(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("jsonparameter") String jsonparameter) {

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        if (username.equals(usrIDOL) && password.equals(pswIDOL)) {
            JsonObject convertedObject = new Gson().fromJson(jsonparameter, JsonObject.class);

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
        Response_insertanagrafica OUT = new Response_insertanagrafica(null, "KO", "CREDENZIALI NON VALIDE");
        return Response.status(401).entity(new Gson().toJson(OUT)).build();

    }

    @POST
    @Path("/insertiscrizione")
    @Produces("application/json")
    public Response insertiscrizione(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("jsonparameter") String jsonparameter) {
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        if (username.equals(usrIDOL) && password.equals(pswIDOL)) {
            JsonObject convertedObject = new Gson().fromJson(jsonparameter, JsonObject.class);

            String codiceFiscale = getJsonString(convertedObject, "codiceFiscale");
            String dataIscrizione = getJsonString(convertedObject, "dataIscrizione");
            String note = getJsonString(convertedObject, "note");
            String finanziamento = getJsonString(convertedObject, "finanziamento");

            JsonObject datiIscrizione = convertedObject.getAsJsonObject("datiIscrizione");
            JsonObject indirizzoResidenza = datiIscrizione.getAsJsonObject("indirizzoResidenza");
            JsonObject titoloStudio = datiIscrizione.getAsJsonObject("titoloStudio");
            JsonObject centroImpiego = datiIscrizione.getAsJsonObject("centroImpiego");
            JsonObject condizioneProfessionale = datiIscrizione.getAsJsonObject("condizioneProfessionale");

            String codCatastaleComune = getJsonString(indirizzoResidenza, "codCatastaleComune");
            String localita = getJsonString(indirizzoResidenza, "localita");
            String via = getJsonString(indirizzoResidenza, "via");
            String cap = getJsonString(indirizzoResidenza, "cap");
            String idGruppoVulnerabile = getJsonString(datiIscrizione, "idGruppoVulnerabile");

            String idTitoloStudio = getJsonString(titoloStudio, "idTitoloStudio");
            String idCentroImpiego = getJsonString(centroImpiego, "idCentroImpiego");
            String dataIscrizioneCentroImpiego = getJsonString(centroImpiego, "dataIscrizioneCentroImpiego");
            String tipoCondizioneProfessionale = getJsonString(condizioneProfessionale, "tipoCondizioneProfessionale");

            ArrayList<LinkedHashMap<String, String>> esito = new ArrayList<>();
            LinkedHashMap<String, String> mapesito = Maps.newLinkedHashMap();
            mapesito.put("result", "true");
            mapesito.put("idBooking", finanziamento);
            mapesito.put("error", "-");
            mapesito.put("error_description", "-");
            esito.add(mapesito);
            return Response.status(200).entity(new Gson().toJson(esito)).build();
        } else {
            ArrayList<LinkedHashMap<String, String>> esito = new ArrayList<>();
            LinkedHashMap<String, String> mapesito = Maps.newLinkedHashMap();
            mapesito.put("result", "false");
            mapesito.put("idBooking", "");
            mapesito.put("error", "unauthorized");
            mapesito.put("error_description", "Credentials invalid");
            esito.add(mapesito);
            return Response.status(401).entity(new Gson().toJson(esito)).build();
        }
    }

    @POST
    @Path("/annulla")
    @Produces("application/json")
    public Response annulla(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("jsonparameter") String jsonparameter) {
        ArrayList<LinkedHashMap<String, String>> esito = new ArrayList<>();
        LinkedHashMap<String, String> mapesito = Maps.newLinkedHashMap();
        mapesito.put("result", "false");
        mapesito.put("idBooking", "");
        mapesito.put("error", "unauthorized");
        mapesito.put("error_description", "Credentials invalid");
        esito.add(mapesito);
        return Response.status(401).entity(new Gson().toJson(esito)).build();
    }

    private String usrIDOL = "Idol2023";
    private String pswIDOL = "6bffcb071bb012fc5a8592108630a977";// m7CW96}\DHgQ

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
