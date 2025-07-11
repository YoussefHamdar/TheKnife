package theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.*;


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
                    String fasciaPrezzo = campi[3].replace("\"", "").trim(); // esempio: €€, €€€, $$$
                    String servizi = campi[12].toLowerCase(); // oppure campi[13]

                    boolean deliveryDisponibile = servizi.contains("delivery");
                    boolean prenotazioneOnlineDisponibile = servizi.contains("reservation") || servizi.contains("prenotazione");

                    int prezzoMedio;
                    switch (fasciaPrezzo.trim()) {
                        case "€":
                            prezzoMedio = 20;
                            break;
                        case "€€":
                            prezzoMedio = 40;
                            break;
                        case "€€€":
                            prezzoMedio = 70;
                            break;
                        case "$$$":
                            prezzoMedio = 100;
                            break;
                        default:
                            prezzoMedio = 0; // valore neutro se la fascia non è riconosciuta
                    }

                    int stelle = campoStelle.replaceAll("[^0-9]", "").isEmpty() ? 0 :
                            Integer.parseInt(campoStelle.replaceAll("[^0-9]", ""));
                    String location = campi[2].replace("\"", "").trim();
                    String nazione = location.substring(location.lastIndexOf(",") + 1).trim();
                    String indirizzo = campi[1].replace("\"", "").trim(); // Colonna "Address"

                    double longitudine = Double.parseDouble(campi[5]);
                    double latitudine = Double.parseDouble(campi[6]);


                    Ristorante r = new Ristorante(
                            nome, citta, stelle, tipoCucina, fasciaPrezzo,
                            deliveryDisponibile, prenotazioneOnlineDisponibile,
                            prezzoMedio, nazione, indirizzo, latitudine, longitudine
                    );



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
    public List<Ristorante> cercaPerFasciaPrezzo(String prezzo) {
        return ristoranti.stream()
                .filter(r -> r.getFasciaPrezzo().equalsIgnoreCase(prezzo))
                .collect(Collectors.toList());
    }
    public List<Ristorante> cercaConDelivery() {
        return ristoranti.stream()
                .filter(Ristorante::isDeliveryDisponibile)
                .collect(Collectors.toList());
    }
    public List<Ristorante> cercaConPrenotazioneOnline() {
        return ristoranti.stream()
                .filter(Ristorante::isPrenotazioneOnlineDisponibile)
                .collect(Collectors.toList());
    }
    public List<Ristorante> cercaPerMediaStelle(double minimo) {
        return ristoranti.stream()
                .filter(r -> r.getMediaStelle() >= minimo)
                .collect(Collectors.toList());
    }
    public List<Ristorante> cercaCombinata(
            String citta,
            String tipoCucina,
            int prezzoMax,
            boolean requireDelivery,
            boolean requirePrenotazioneOnline,
            double minStelle
    ) {
        return ristoranti.stream()
                .filter(r -> r.getCitta().equalsIgnoreCase(citta))
                .filter(r -> r.getTipoCucina().toLowerCase().contains(tipoCucina.toLowerCase()))
                .filter(r -> r.getPrezzoMedio() <= prezzoMax)
                .filter(r -> !requireDelivery || r.isDeliveryDisponibile())
                .filter(r -> !requirePrenotazioneOnline || r.isPrenotazioneOnlineDisponibile())
                .filter(r -> r.getMediaStelle() >= minStelle)
                .collect(Collectors.toList());
    }
    public Ristorante cercaPerNome(String nome) {
        return ristoranti.stream()
                .filter(r -> r.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public void visualizzaDettagli(Ristorante r) {
        System.out.println(" Nome: " + r.getNome());
        System.out.println(" Città: " + r.getCitta() + ", " + r.getNazione());
        System.out.println(" Indirizzo: " + r.getIndirizzo());
        System.out.printf(" Coordinate: %.5f, %.5f\n", r.getLatitudine(), r.getLongitudine());
        System.out.println(" Prezzo medio: " + r.getPrezzoMedio() + "€  (" + r.getFasciaPrezzo() + ")");
        System.out.println(" Cucina: " + r.getTipoCucina());
        System.out.println(" Delivery: " + (r.isDeliveryDisponibile() ? " sì" : " no"));
        System.out.println(" Prenotazione online: " + (r.isPrenotazioneOnlineDisponibile() ? " sì" : " no"));
        System.out.println(" Stelle Michelin: " + r.getStelle());
        System.out.printf(" Media recensioni: %.2f stelle\n", r.getMediaStelle());
        System.out.println(" Descrizione: " + (r.getDescrizione() != null ? r.getDescrizione() : "Nessuna"));
    }

    public List<Ristorante> getRecensitiDa(String username) {
        return ristoranti.stream()
                .filter(r -> r.haRecensioneDellUtente(username))
                .collect(Collectors.toList());
    }



    public void salvaSuFile(String path) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(ristoranti);
            System.out.println("✅ Ristoranti salvati su file.");
        } catch (IOException e) {
            System.err.println("❌ Errore salvataggio ristoranti: " + e.getMessage());
        }
    }




    @SuppressWarnings("unchecked")
    public void caricaDaFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("📁 Nessun file ristoranti trovato, lista vuota creata.");
            ristoranti = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            ristoranti = (List<Ristorante>) in.readObject();
            System.out.println("✅ Ristoranti caricati da file.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Errore caricamento ristoranti: " + e.getMessage());
            ristoranti = new ArrayList<>();
        }
    }






}

