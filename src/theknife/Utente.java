package theknife;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un utente registrato nell'app.
 * Contiene nome, username, password cifrata e preferiti.
 */
public class Utente {

    private String nome;
    private String username;
    private String passwordCifrata;
    private List<Ristorante> preferiti;
    private boolean isRistoratore;


    public Utente(String nome, String username, String passwordCifrata, boolean isRistoratore) {
        this.nome = nome;
        this.username = username;
        this.passwordCifrata = passwordCifrata;
        this.isRistoratore = isRistoratore;
        this.preferiti = new ArrayList<>();
    }


    public String getNome() { return nome; }
    public String getUsername() { return username; }
    public String getPasswordCifrata() { return passwordCifrata; }
    public List<Ristorante> getPreferiti() { return preferiti; }
    public boolean isRistoratore() {
        return isRistoratore;
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
        return "Utente: " + nome + " (" + username + ")";
    }
}
