package theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Gestisce il caricamento e la ricerca dei ristoranti.
 */
public class RistoranteManager {

    private List<Ristorante> ristoranti = new ArrayList<>();

    /**
     * Costruttore: carica i ristoranti da un file CSV.
     */
    public RistoranteManager() {
        this.ristoranti = caricaDaCSV("data/michelin_my_maps.csv"); // puoi cambiare il nome file se serve
    }

    /**
     * Carica i ristoranti da un file CSV.
     *
     * @param pathCSV percorso del file
     * @return lista di ristoranti
     */
    public static List<Ristorante> caricaDaCSV(String pathCSV) {
        List<Ristorante> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathCSV))) {
            String linea;
            br.readLine(); // salta intestazione

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if (campi.length >= 12) {
                    String nome = campi[0].replace("\"", "").trim();
                    String citta = campi[2].replace("\"", "").trim();
                    String campoStelle = campi[11].replace("\"", "").trim();
                    String tipoCucina = campi[4].replace("\"", "").trim();

                    int stelle = campoStelle.replaceAll("[^0-9]", "").isEmpty() ? 0 :
                            Integer.parseInt(campoStelle.replaceAll("[^0-9]", ""));

                    Ristorante r = new Ristorante(nome, citta, stelle,tipoCucina );
                    System.out.println(" Caricato: " + r);
                    lista.add(r);
                }


            }

        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Cerca ristoranti in base alla città (case-insensitive).
     *
     * @param citta città da cercare
     * @return lista filtrata
     */
    public List<Ristorante> cercaPerCitta(String citta) {
        List<Ristorante> risultati = new ArrayList<>();
        for (Ristorante r : ristoranti) {
            if (r.getCitta().toLowerCase().contains(citta.toLowerCase())) {
                risultati.add(r);
            }
        }
        return risultati;
    }


    /**
     * Aggiunge un nuovo ristorante alla lista (per ristoratori).
     *
     * @param r ristorante da aggiungere
     */
    public void aggiungiRistorante(Ristorante r) {
        ristoranti.add(r);
    }

    /**
     * Restituisce tutti i ristoranti.
     *
     * @return lista completa
     */
    public List<Ristorante> getTuttiIRistoranti() {
        return ristoranti;
    }

    public List<Ristorante> cercaPerTipoCucina(String tipo) {
        return ristoranti.stream()
                .filter(r -> r.getTipoCucina().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

}

