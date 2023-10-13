/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

/**
 *
 * @author Administrator
 */
public class msg_Response_insertiscrizione {

    Long idattivita,idindividuo;

    public msg_Response_insertiscrizione() {
    }

    public msg_Response_insertiscrizione(Long idattivita, Long idindividuo) {
        this.idattivita = idattivita;
        this.idindividuo = idindividuo;
    }

    public msg_Response_insertiscrizione(Long idindividuo) {
        this.idindividuo = idindividuo;
    }

    public Long getIdattivita() {
        return idattivita;
    }

    public void setIdattivita(Long idattivita) {
        this.idattivita = idattivita;
    }

    public Long getIdindividuo() {
        return idindividuo;
    }

    public void setIdindividuo(Long idindividuo) {
        this.idindividuo = idindividuo;
    }

}