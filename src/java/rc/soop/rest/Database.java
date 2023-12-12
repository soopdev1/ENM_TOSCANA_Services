/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

import static java.lang.Class.forName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
import org.joda.time.DateTime;
import static rc.soop.rest.Utils.DTFPATTERN1;
import static rc.soop.rest.Utils.PATTERN1;
import static rc.soop.rest.Utils.PATTERN2;
import static rc.soop.rest.Utils.estraiEccezione;

/**
 *
 * @author Administrator
 */
public class Database {

    private Connection c = null;
    private static final ResourceBundle conf = ResourceBundle.getBundle("conf.conf");

    public Database() {
        String driver = conf.getString("db.driver");
        String user = conf.getString("db.user");
        String password = conf.getString("db.pass");
        String host = conf.getString("db.hostname");
        try {
            forName(driver).newInstance();
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", password);
            p.put("characterEncoding", "UTF-8");
            p.put("passwordCharacterEncoding", "UTF-8");
            p.put("useSSL", "false");
            p.put("connectTimeout", "1000");
            p.put("useUnicode", "true");
            this.c = DriverManager.getConnection("jdbc:mysql://" + host, p);
        } catch (Exception ex) {
            if (this.c != null) {
                try {
                    this.c.close();
                } catch (Exception ex1) {
                }
            }
            this.c = null;
        }
    }

    public boolean connesso(Connection con) {
        try {
            return con != null && !con.isClosed();
        } catch (Exception ignored) {
        }
        return false;
    }

    public void closeDB() {
        try {
            if (this.c != null) {
                this.c.close();
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
    }

    public boolean insertTracking(String idUser, String azione) {
        try {
            String ins = "INSERT INTO tracking (idUser,azione) VALUES (?,?)";
            try (PreparedStatement ps = this.c.prepareStatement(ins)) {
                ps.setString(1, idUser);
                ps.setString(2, azione);
                ps.execute();
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public Response_verificacf verificacf(String cf) {
        try {
            String sql = "SELECT * FROM allievi a WHERE a.codicefiscale = ? AND a.id_statopartecipazione <> ?";
            try (PreparedStatement ps = this.c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setString(1, cf);
                ps.setString(2, "11");
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        msg_Response_verificacf msg = new msg_Response_verificacf(
                                rs.getLong("idallievi"),
                                cf,
                                rs.getString("nome"),
                                rs.getString("cognome"),
                                new DateTime(rs.getDate("datanascita").getTime()).toString(PATTERN1),
                                rs.getString("sesso"),
                                getIstatComune(rs.getLong("comune_nascita")),
                                rs.getString("stato_nascita").equals("100") ? "----" : rs.getString("stato_nascita"),
                                rs.getString("stato_nascita").equals("100") ? null : getNazioneIstat(rs.getString("stato_nascita"))
                        );
                        if (rs.getString("id_statopartecipazione").equals("00")) {
                            return new Response_verificacf(msg, "SUCCESS", "Operazione eseguita con successo");

                        } else {
                            return new Response_verificacf(null, "KO", "ISCRIZIONE GIA' EFFETTUATA");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
            return new Response_verificacf(null, "KO", estraiEccezione(ex));
        }
        return new Response_verificacf(null, "KO", "CF NON TROVATO");

    }

    public String getIstatComune(Long id) {
        try {
            String sql = "SELECT istat FROM comuni a WHERE a.idcomune = ?";
            try (PreparedStatement ps = this.c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString(1).trim();
                    }
                }
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
        return "----";
    }

    public Long getIdComune(String istat) {
        try {
            String sql = "SELECT a.idcomune FROM comuni a WHERE a.istat = ?";
            try (PreparedStatement ps = this.c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setString(1, istat);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
        return 0L;
    }

    public String getNazioneIstat(String istat) {
        try {
            String sql = "SELECT nome FROM nazioni_rc a WHERE a.codicefiscale = ?";
            try (PreparedStatement ps = this.c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setString(1, istat);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString(1).trim().toUpperCase();
                    }
                }
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
        return null;
    }

    public Long getIdNazioneIstat(String istat) {
        try {
            String sql = "SELECT a.idnazione FROM nazioni_rc a WHERE a.istat = ?";
            try (PreparedStatement ps = this.c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setString(1, istat);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
        return null;
    }

    public Long getIdNazioneCF(String cf) {
        try {
            String sql = "SELECT a.idnazione FROM nazioni_rc a WHERE a.codicefiscale = ?";
            try (PreparedStatement ps = this.c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setString(1, cf);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
        return null;
    }

    public Long insertAnagrafica(String nome, String cognome, String codiceFiscale,
            String sesso, String dataNascita, String codCatastaleComuneNascita, String codCatastaleStatoCittadinanza) {
        try {
            Long comune_nascita = getIdComune(codCatastaleComuneNascita);
            if (comune_nascita == 0L) {
                return -1L;
            }
            if (codCatastaleStatoCittadinanza.equals("----")) {
                codCatastaleStatoCittadinanza = "100";
            }

            String insert = "INSERT INTO allievi (nome,cognome,codicefiscale,sesso,datanascita,comune_nascita,stato_nascita)"
                    + " VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement ps = this.c.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nome);
                ps.setString(2, cognome);
                ps.setString(3, codiceFiscale);
                ps.setString(4, sesso);
                ps.setString(5, DTFPATTERN1.parseDateTime(dataNascita).toString(PATTERN2));
                ps.setLong(6, comune_nascita);
                ps.setString(7, codCatastaleStatoCittadinanza);
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 0) {
                    return -3L;
                }
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    } else {
                        return -4L;
                    }
                }
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
        return -5L;
    }

    public boolean cambiostatoallievo(String cf, String statodest) {
        try {
            String upd = "UPDATE allievi SET id_statopartecipazione = '" + statodest + "' WHERE codicefiscale = '" + cf + "' AND id_statopartecipazione IN ('00','10','12','13','14')";
            try (Statement st = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                return st.executeUpdate(upd) > 0;
            }
        } catch (Exception ex) {
            insertTracking("ERROR SYSTEM", estraiEccezione(ex));
        }
        return false;
    }

}
