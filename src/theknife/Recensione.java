package theknife;

import java.time.LocalDate;
import java.io.Serializable;



/**
 * Rappresenta una recensione lasciata da un utente su un ristorante.
 */
public class Recensione implements Serializable {
    private static final long serialVersionUID = 1L;

    private String autore;
    private String nomeRistorante;
    private String testo;
    private int stelle;
    private LocalDate data;
    private String rispostaDelRistoratore;

    /**
     * Costruttore base della recensione.
     *
     * @param autore autore della recensione (username o nome)
     * @param testo contenuto della recensione
     * @param stelle numero di stelle (1–5)
     * @param data data in cui è stata scritta
     */
    public Recensione(String autore, String nomeRistorante, String testo, int stelle, LocalDate data) {
        this.autore = autore;
        this.nomeRistorante = nomeRistorante;
        this.testo = testo;
        this.stelle = stelle;
        this.data = data;
        this.rispostaDelRistoratore = null; // inizialmente nessuna risposta
    }

    public String getAutore() { return autore; }
    public String getNomeRistorante() { return nomeRistorante; }
    public String getTesto() { return testo; }
    public int getStelle() { return stelle; }
    public LocalDate getData() { return data; }
    public String getRispostaDelRistoratore() { return rispostaDelRistoratore; }

    public void setTesto(String nuovoTesto) {
        this.testo = nuovoTesto;
    }

    public void setStelle(int nuoveStelle) {
        this.stelle = nuoveStelle;
    }

    public void setRispostaDelRistoratore(String risposta) {
        this.rispostaDelRistoratore = risposta;
    }

    @Override
    public String toString() {
        String output = "Recensione di " + autore +
                " sul ristorante '" + nomeRistorante +
                "' (" + stelle + ") [" + data + "]\n" + testo;
        if (rispostaDelRistoratore != null) {
            output += "\n Risposta del ristoratore: " + rispostaDelRistoratore;
        }
        return output;
    }
}