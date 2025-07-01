package theknife;

/**
 * Estensione della classe Utente per i ristoratori.
 * I ristoratori possono rispondere alle recensioni degli utenti.
 * Eredita nome, username, password e lista preferiti.
 */
public class Ristoratore extends Utente {

    /**
     * Costruttore del ristoratore.
     *
     * @param nome nome completo del ristoratore
     * @param username nome utente
     * @param passwordCifrata password cifrata
     */
    public Ristoratore(String nome, String username, String passwordCifrata) {
        super(nome, username, passwordCifrata);
    }

    /**
     * Permette al ristoratore di rispondere a una recensione.
     *
     * @param recensione la recensione a cui rispondere
     * @param risposta testo della risposta del ristoratore
     */
    public void rispondiARecensione(Recensione recensione, String risposta) {
        recensione.setRispostaDelRistoratore(risposta);
    }
}
