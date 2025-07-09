package theknife;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


/**
 * Gestisce la registrazione e il login degli utenti.
 */
public class GestioneUtenti {

    private List<Utente> utenti;

    public GestioneUtenti() {
        this.utenti = new ArrayList<>();
    }

    /**
     * Registra un nuovo utente.
     *
     * @param nome nome completo
     * @param username username unico
     * @param password password in chiaro
     * @return true se registrazione avvenuta con successo
     */
    public boolean registraUtente(String nome, String cognome, String username, String password, boolean isRistoratore, String domicilio, LocalDate dataDiNascita) {
        for (Utente u : utenti) {
            if (u.getUsername().equals(username)) {
                return false; // già esiste
            }
        }

        String passwordCifrata = cifra(password);
        Utente nuovo = new Utente(nome, cognome, username, passwordCifrata, isRistoratore, domicilio, dataDiNascita);
        utenti.add(nuovo);
        return true;
    }


    /**
     * Effettua il login dato username e password.
     *
     * @param username username inserito
     * @param password password in chiaro
     * @return oggetto Utente se credenziali corrette, null altrimenti
     */
    public Utente login(String username, String password) {
        for (Utente u : utenti) {
            if (u.getUsername().equals(username) && verificaPassword(password, u.getPasswordCifrata())) {
                return u;
            }
        }
        return null;
    }

    /**
     * Cerca un utente dato il suo username.
     *
     * @param username da cercare
     * @return oggetto Utente se trovato, null altrimenti
     */
    public Utente getUtenteDaUsername(String username) {
        for (Utente u : utenti) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Metodo fittizio di cifratura per la password (solo a scopo dimostrativo).
     * In un progetto reale andrebbe usato un hash sicuro come SHA-256 o bcrypt.
     *
     * @param password password in chiaro
     * @return password cifrata
     */
    private String cifra(String password) {
        return new StringBuilder(password).reverse().toString(); // es. "ciao" → "oaic"
    }

    /**
     * Verifica se una password inserita corrisponde a quella cifrata salvata.
     *
     * @param password password in chiaro
     * @param cifrata password cifrata salvata
     * @return true se corrispondono
     */
    private boolean verificaPassword(String password, String cifrata) {
        return cifra(password).equals(cifrata);
    }

    public List<Utente> getTuttiGliUtenti() {
        return utenti;
    }
}
