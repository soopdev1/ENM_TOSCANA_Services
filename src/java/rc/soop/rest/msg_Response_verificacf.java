/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

/**
 *
 * @author Administrator
 */
public class msg_Response_verificacf {
     Long id;
    String codiceFiscale,nome,cognome,dataNascita,sesso,codCatastaleComuneNascita,codCatastaleStatoCittadinanza,denominazioneLuogoEsteroNascita;

    public msg_Response_verificacf() {
    }

    public msg_Response_verificacf(Long id, String codiceFiscale, String nome, String cognome, String dataNascita, String sesso,
            String codCatastaleComuneNascita, String codCatastaleStatoCittadinanza, String denominazioneLuogoEsteroNascita) {
        this.id = id;
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.codCatastaleComuneNascita = codCatastaleComuneNascita;
        this.codCatastaleStatoCittadinanza = codCatastaleStatoCittadinanza;
        this.denominazioneLuogoEsteroNascita = denominazioneLuogoEsteroNascita;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
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

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getCodCatastaleComuneNascita() {
        return codCatastaleComuneNascita;
    }

    public void setCodCatastaleComuneNascita(String codCatastaleComuneNascita) {
        this.codCatastaleComuneNascita = codCatastaleComuneNascita;
    }

    public String getCodCatastaleStatoCittadinanza() {
        return codCatastaleStatoCittadinanza;
    }

    public void setCodCatastaleStatoCittadinanza(String codCatastaleStatoCittadinanza) {
        this.codCatastaleStatoCittadinanza = codCatastaleStatoCittadinanza;
    }

    public String getDenominazioneLuogoEsteroNascita() {
        return denominazioneLuogoEsteroNascita;
    }

    public void setDenominazioneLuogoEsteroNascita(String denominazioneLuogoEsteroNascita) {
        this.denominazioneLuogoEsteroNascita = denominazioneLuogoEsteroNascita;
    }
}
