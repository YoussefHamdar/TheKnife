/**
 * Autori:
 * - Hamdar Youssef (Matricola: 753832) – Sede: Como
 * - Dellatorre Federico (Matricola: 755856) – Sede: Como
 */
package theknife;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Classe che gestisce le recensioni degli utenti.
 * Permette di aggiungere, modificare, rimuovere, calcolare medie,
 * e salvare/caricare recensioni su file.
 */

public class RecensioneManager {
    private List<Recensione> recensioni;

    /** * Costruttore: inizializza la lista di recensioni. */
    public RecensioneManager() {
        this.recensioni = new ArrayList<>();
    }

    /**
     * Aggiunge una nuova recensione all'elenco.
     *
     * @param username username dell'autore
     * @param nomeRistorante nome del ristorante recensito
     * @param testo testo della recensione
     * @param stelle numero di stelle (1-5)
     */
    public void aggiungiRecensione(String username, String nomeRistorante, String testo, int stelle) {
        Recensione r = new Recensione(username, nomeRistorante, testo, stelle, LocalDate.now());
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
    /**
     * Salva tutte le recensioni su file binario (.dat).
     *
     * @param path percorso del file di destinazione
     */

    public void salvaSuFile(String path) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(recensioni);
            System.out.println("Recensioni salvate su file.");
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio recensioni: " + e.getMessage());
        }
    }

    /**
     * Carica le recensioni da un file binario (.dat).
     * In caso di errore, inizializza una lista vuota.
     *
     * @param path percorso del file da caricare
     */

    @SuppressWarnings("unchecked")
    public void caricaDaFile(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            recensioni = (List<Recensione>) in.readObject();
            System.out.println("Recensioni caricate da file.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore nel caricamento recensioni: " + e.getMessage());
            recensioni = new ArrayList<>(); // fallback
        }
    }

    public void associaRecensioni(List<Ristorante> ristoranti) {
        for (Recensione recensione : recensioni) {
            for (Ristorante r : ristoranti) {
                if (r.getNome().equalsIgnoreCase(recensione.getNomeRistorante())) {
                    r.aggiungiRecensione(recensione);
                    break;
                }
            }
        }
    }






}
