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

    public Utente(String nome, String username, String passwordCifrata) {
        this.nome = nome;
        this.username = username;
        this.passwordCifrata = passwordCifrata;
        this.preferiti = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public String getUsername() { return username; }
    public String getPasswordCifrata() { return passwordCifrata; }
    public List<Ristorante> getPreferiti() { return preferiti; }

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
