package theknife;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.io.Serializable;
public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;
/**
 * Rappresenta un utente registrato nell'app.
 * Contiene nome, username, password cifrata e preferiti.
 */


    private String nome;
    private String username;
    private String passwordCifrata;
    private List<Ristorante> preferiti;
    private boolean isRistoratore;
    private String cognome;
    private String domicilio;
    private LocalDate dataDiNascita;


    public Utente(String nome, String cognome, String username, String password, boolean isRistoratore, String domicilio, LocalDate dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.passwordCifrata = password;
        this.isRistoratore = isRistoratore;
        this.domicilio = domicilio;
        this.dataDiNascita = dataDiNascita;
        this.preferiti = new ArrayList<>();
    }



    public String getNome() { return nome; }
    public String getUsername() { return username; }
    public String getPasswordCifrata() { return passwordCifrata; }
    public List<Ristorante> getPreferiti() { return preferiti; }
    public boolean isRistoratore() {
        return isRistoratore;
    }
    public String getCognome() {
        return cognome;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }



    public void aggiungiPreferito(Ristorante r) {
        if (!preferiti.contains(r)) {
            preferiti.add(r);
        }
    }

    public void rimuoviPreferito(Ristorante r) {
        preferiti.remove(r);
    }

    @Override
    public String toString() {
        return "Utente: " + nome + " " + cognome + " (" + username + "), domicilio: " + domicilio + ", nato il: " + dataDiNascita;
    }

}




