/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.soop.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author agodino
 */
public class Allievi {

    private Long id;
    private String nome;
    private String cognome;
    private String codicefiscale;
    private Date datanascita;
    private String indirizzodomicilio;
    private String indirizzoresidenza;
    private String civicodomicilio;
    private String civicoresidenza;
    private String capdomicilio;
    private String capresidenza;
    private String protocollo;
    private String esito = "-";
    private String esclusione_prg;
    private Date iscrizionegg;
    private Date datacpi;
    private String neet;
    private String docid;
    private Date scadenzadocid;
    private String stato;
    private String idea_impresa;
    private String email;
    private String sesso;
    private String telefono;
    private double importo;
    private Date data_up;
    private String cittadinanza;
    private String comune_nascita;
    private String comune_residenza;
    private String comune_domicilio;
    private String titoloStudio;
    private String progetto;
    private String soggetto;
    private String cpi;
    private String stato_nascita;
    private String privacy2;
    private String privacy3;
    private String data_anpal;
    private String condizione_mercato;
    private String selfiemployement;
    private String statopartecipazione;
    private String condizione_lavorativa;
    private String motivazione;
    private String canale;

    private boolean pregresso;
    private double ore_fa = 0.0;
    private double ore_fb = 0.0;
    private String orerendicontabili;
    private int gruppo_faseB;
    private int mappatura;
    private String mappatura_note;
    private boolean surveyin;
    private boolean surveyout;

    //NEW
    private String tos_tipofinanziamento; //GOL - PATTO PER IL LAVORO
    private String tos_dirittoindennita;
    private String tos_gruppovulnerabile;
    private String tos_request;

    //MODELLO 0
    private Date tos_m0_datacolloquio;
    private String tos_m0_siglaoperatore;
    private int tos_m0_modalitacolloquio;
    private int tos_m0_gradoconoscenza;
    private int tos_m0_canaleconoscenza;
    private int tos_m0_motivazione;
    private int tos_m0_utilita;
    private int tos_m0_aspettative;
    private int tos_m0_volonta;
    private int tos_m0_noperche;
    private int tos_mailoriginale;

    public Allievi() {
    }

    public String getTos_request() {
        return tos_request;
    }

    public void setTos_request(String tos_request) {
        this.tos_request = tos_request;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodicefiscale() {
        return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {
        this.codicefiscale = codicefiscale;
    }

    public Date getDatanascita() {
        return datanascita;
    }

    public void setDatanascita(Date datanascita) {
        this.datanascita = datanascita;
    }

    public String getIndirizzodomicilio() {
        return indirizzodomicilio;
    }

    public void setIndirizzodomicilio(String indirizzodomicilio) {
        this.indirizzodomicilio = indirizzodomicilio;
    }

    public String getIndirizzoresidenza() {
        return indirizzoresidenza;
    }

    public void setIndirizzoresidenza(String indirizzoresidenza) {
        this.indirizzoresidenza = indirizzoresidenza;
    }

    public String getCivicodomicilio() {
        return civicodomicilio;
    }

    public void setCivicodomicilio(String civicodomicilio) {
        this.civicodomicilio = civicodomicilio;
    }

    public String getCivicoresidenza() {
        return civicoresidenza;
    }

    public void setCivicoresidenza(String civicoresidenza) {
        this.civicoresidenza = civicoresidenza;
    }

    public String getCapdomicilio() {
        return capdomicilio;
    }

    public void setCapdomicilio(String capdomicilio) {
        this.capdomicilio = capdomicilio;
    }

    public String getCapresidenza() {
        return capresidenza;
    }

    public void setCapresidenza(String capresidenza) {
        this.capresidenza = capresidenza;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getEsclusione_prg() {
        return esclusione_prg;
    }

    public void setEsclusione_prg(String esclusione_prg) {
        this.esclusione_prg = esclusione_prg;
    }

    public Date getIscrizionegg() {
        return iscrizionegg;
    }

    public void setIscrizionegg(Date iscrizionegg) {
        this.iscrizionegg = iscrizionegg;
    }

    public Date getDatacpi() {
        return datacpi;
    }

    public void setDatacpi(Date datacpi) {
        this.datacpi = datacpi;
    }

    public String getNeet() {
        return neet;
    }

    public void setNeet(String neet) {
        this.neet = neet;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public Date getScadenzadocid() {
        return scadenzadocid;
    }

    public void setScadenzadocid(Date scadenzadocid) {
        this.scadenzadocid = scadenzadocid;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getIdea_impresa() {
        return idea_impresa;
    }

    public void setIdea_impresa(String idea_impresa) {
        this.idea_impresa = idea_impresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public Date getData_up() {
        return data_up;
    }

    public void setData_up(Date data_up) {
        this.data_up = data_up;
    }

    public String getCittadinanza() {
        return cittadinanza;
    }

    public void setCittadinanza(String cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    public String getComune_nascita() {
        return comune_nascita;
    }

    public void setComune_nascita(String comune_nascita) {
        this.comune_nascita = comune_nascita;
    }

    public String getComune_residenza() {
        return comune_residenza;
    }

    public void setComune_residenza(String comune_residenza) {
        this.comune_residenza = comune_residenza;
    }

    public String getComune_domicilio() {
        return comune_domicilio;
    }

    public void setComune_domicilio(String comune_domicilio) {
        this.comune_domicilio = comune_domicilio;
    }

    public String getTitoloStudio() {
        return titoloStudio;
    }

    public void setTitoloStudio(String titoloStudio) {
        this.titoloStudio = titoloStudio;
    }

    public String getProgetto() {
        return progetto;
    }

    public void setProgetto(String progetto) {
        this.progetto = progetto;
    }

    public String getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(String soggetto) {
        this.soggetto = soggetto;
    }

    public String getCpi() {
        return cpi;
    }

    public void setCpi(String cpi) {
        this.cpi = cpi;
    }

    public String getStato_nascita() {
        return stato_nascita;
    }

    public void setStato_nascita(String stato_nascita) {
        this.stato_nascita = stato_nascita;
    }

    public String getPrivacy2() {
        return privacy2;
    }

    public void setPrivacy2(String privacy2) {
        this.privacy2 = privacy2;
    }

    public String getPrivacy3() {
        return privacy3;
    }

    public void setPrivacy3(String privacy3) {
        this.privacy3 = privacy3;
    }

    public String getData_anpal() {
        return data_anpal;
    }

    public void setData_anpal(String data_anpal) {
        this.data_anpal = data_anpal;
    }

    public String getCondizione_mercato() {
        return condizione_mercato;
    }

    public void setCondizione_mercato(String condizione_mercato) {
        this.condizione_mercato = condizione_mercato;
    }

    public String getSelfiemployement() {
        return selfiemployement;
    }

    public void setSelfiemployement(String selfiemployement) {
        this.selfiemployement = selfiemployement;
    }

    public String getStatopartecipazione() {
        return statopartecipazione;
    }

    public void setStatopartecipazione(String statopartecipazione) {
        this.statopartecipazione = statopartecipazione;
    }

    public String getCondizione_lavorativa() {
        return condizione_lavorativa;
    }

    public void setCondizione_lavorativa(String condizione_lavorativa) {
        this.condizione_lavorativa = condizione_lavorativa;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getCanale() {
        return canale;
    }

    public void setCanale(String canale) {
        this.canale = canale;
    }

    public boolean isPregresso() {
        return pregresso;
    }

    public void setPregresso(boolean pregresso) {
        this.pregresso = pregresso;
    }

    public double getOre_fa() {
        return ore_fa;
    }

    public void setOre_fa(double ore_fa) {
        this.ore_fa = ore_fa;
    }

    public double getOre_fb() {
        return ore_fb;
    }

    public void setOre_fb(double ore_fb) {
        this.ore_fb = ore_fb;
    }

    public String getOrerendicontabili() {
        return orerendicontabili;
    }

    public void setOrerendicontabili(String orerendicontabili) {
        this.orerendicontabili = orerendicontabili;
    }

    public int getGruppo_faseB() {
        return gruppo_faseB;
    }

    public void setGruppo_faseB(int gruppo_faseB) {
        this.gruppo_faseB = gruppo_faseB;
    }

    public int getMappatura() {
        return mappatura;
    }

    public void setMappatura(int mappatura) {
        this.mappatura = mappatura;
    }

    public String getMappatura_note() {
        return mappatura_note;
    }

    public void setMappatura_note(String mappatura_note) {
        this.mappatura_note = mappatura_note;
    }

    public boolean isSurveyin() {
        return surveyin;
    }

    public void setSurveyin(boolean surveyin) {
        this.surveyin = surveyin;
    }

    public boolean isSurveyout() {
        return surveyout;
    }

    public void setSurveyout(boolean surveyout) {
        this.surveyout = surveyout;
    }

    public String getTos_tipofinanziamento() {
        return tos_tipofinanziamento;
    }

    public void setTos_tipofinanziamento(String tos_tipofinanziamento) {
        this.tos_tipofinanziamento = tos_tipofinanziamento;
    }

    public String getTos_dirittoindennita() {
        return tos_dirittoindennita;
    }

    public void setTos_dirittoindennita(String tos_dirittoindennita) {
        this.tos_dirittoindennita = tos_dirittoindennita;
    }

    public String getTos_gruppovulnerabile() {
        return tos_gruppovulnerabile;
    }

    public void setTos_gruppovulnerabile(String tos_gruppovulnerabile) {
        this.tos_gruppovulnerabile = tos_gruppovulnerabile;
    }

    public Date getTos_m0_datacolloquio() {
        return tos_m0_datacolloquio;
    }

    public void setTos_m0_datacolloquio(Date tos_m0_datacolloquio) {
        this.tos_m0_datacolloquio = tos_m0_datacolloquio;
    }

    public String getTos_m0_siglaoperatore() {
        return tos_m0_siglaoperatore;
    }

    public void setTos_m0_siglaoperatore(String tos_m0_siglaoperatore) {
        this.tos_m0_siglaoperatore = tos_m0_siglaoperatore;
    }

    public int getTos_m0_modalitacolloquio() {
        return tos_m0_modalitacolloquio;
    }

    public void setTos_m0_modalitacolloquio(int tos_m0_modalitacolloquio) {
        this.tos_m0_modalitacolloquio = tos_m0_modalitacolloquio;
    }

    public int getTos_m0_gradoconoscenza() {
        return tos_m0_gradoconoscenza;
    }

    public void setTos_m0_gradoconoscenza(int tos_m0_gradoconoscenza) {
        this.tos_m0_gradoconoscenza = tos_m0_gradoconoscenza;
    }

    public int getTos_m0_canaleconoscenza() {
        return tos_m0_canaleconoscenza;
    }

    public void setTos_m0_canaleconoscenza(int tos_m0_canaleconoscenza) {
        this.tos_m0_canaleconoscenza = tos_m0_canaleconoscenza;
    }

    public int getTos_m0_motivazione() {
        return tos_m0_motivazione;
    }

    public void setTos_m0_motivazione(int tos_m0_motivazione) {
        this.tos_m0_motivazione = tos_m0_motivazione;
    }

    public int getTos_m0_utilita() {
        return tos_m0_utilita;
    }

    public void setTos_m0_utilita(int tos_m0_utilita) {
        this.tos_m0_utilita = tos_m0_utilita;
    }

    public int getTos_m0_aspettative() {
        return tos_m0_aspettative;
    }

    public void setTos_m0_aspettative(int tos_m0_aspettative) {
        this.tos_m0_aspettative = tos_m0_aspettative;
    }

    public int getTos_m0_volonta() {
        return tos_m0_volonta;
    }

    public void setTos_m0_volonta(int tos_m0_volonta) {
        this.tos_m0_volonta = tos_m0_volonta;
    }

    public int getTos_m0_noperche() {
        return tos_m0_noperche;
    }

    public void setTos_m0_noperche(int tos_m0_noperche) {
        this.tos_m0_noperche = tos_m0_noperche;
    }

    public int getTos_mailoriginale() {
        return tos_mailoriginale;
    }

    public void setTos_mailoriginale(int tos_mailoriginale) {
        this.tos_mailoriginale = tos_mailoriginale;
    }

}
