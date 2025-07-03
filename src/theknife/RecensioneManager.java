package theknife;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce l'elenco delle recensioni e le operazioni associate.
 */
public class RecensioneManager {
    private List<Recensione> recensioni;

    /**
     * Costruttore: inizializza la lista di recensioni.
     */
    public RecensioneManager() {
        this.recensioni = new ArrayList<>();
    }

    /**
     * Aggiunge una nuova recensione all'elenco.
     *
     * @param autore username dell'autore
     * @param testo testo della recensione
     * @param stelle numero di stelle (1-5)
     */
    public void aggiungiRecensione(String autore, String testo, int stelle) {
        Recensione r = new Recensione(autore, testo, stelle, LocalDate.now());
        recensioni.add(r);
    }

    /**
     * Restituisce l'elenco di tutte le recensioni.
     *
     * @return lista di recensioni
     */
    public List<Recensione> getTutteLeRecensioni() {
        return recensioni;
    }

    /**
     * Cancella una recensione dall'elenco.
     *
     * @param r la recensione da rimuovere
     */
    public void rimuoviRecensione(Recensione r) {
        recensioni.remove(r);
    }

    /**
     * Modifica il testo e/o le stelle di una recensione esistente.
     *
     * @param r recensione da modificare
     * @param nuovoTesto nuovo contenuto
     * @param nuoveStelle nuovo voto in stelle
     */
    public void modificaRecensione(Recensione r, String nuovoTesto, int nuoveStelle) {
        r.setTesto(nuovoTesto);
        r.setStelle(nuoveStelle);
    }

    /**
     * Calcola la valutazione media di tutte le recensioni esistenti.
     *
     * @return media numerica o 0 se non ci sono recensioni
     */
    public double calcolaMediaStelle() {
        if (recensioni.isEmpty()) return 0;
        int somma = 0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        return (double) somma / recensioni.size();
    }
}
