package theknife;

import java.util.ArrayList;
import java.util.List;

public class Ristorante {
    private String nome;
    private String citta;
    private int stelle;
    private String tipoCucina;
    private String fasciaPrezzo;
    private boolean deliveryDisponibile;
    private boolean prenotazioneOnlineDisponibile;
    private String indirizzo;
    private String nazione;
    private double latitudine;
    private double longitudine;
    private int prezzoMedio;
    private String descrizione;



    private List<Recensione> recensioni = new ArrayList<>();




    public Ristorante(String nome, String citta, int stelle, String tipoCucina, String fasciaPrezzo, boolean deliveryDisponibile, boolean prenotazioneOnlineDisponibile, int prezzoMedio, String nazione, String indirizzo, double latitudine, double longitudine) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.tipoCucina = tipoCucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.deliveryDisponibile = deliveryDisponibile;
        this.prenotazioneOnlineDisponibile = prenotazioneOnlineDisponibile;
        this.prezzoMedio = prezzoMedio;
        this.nazione = nazione;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }





    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public int getStelle() {
        return stelle;
    }

    public String getTipoCucina() {
        return tipoCucina;
    }
    public String getFasciaPrezzo() {
        return fasciaPrezzo;
    }

    public boolean isDeliveryDisponibile() {
        return deliveryDisponibile;
    }

    public boolean isPrenotazioneOnlineDisponibile() {
        return prenotazioneOnlineDisponibile;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void aggiungiRecensione(Recensione r) {
        recensioni.add(r);
    }

    public boolean haRecensioneDellUtente(String username) {
        for (Recensione r : recensioni) {
            if (r.getAutore().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }



    public double getMediaStelle() {
        if (recensioni.isEmpty()) return 0;
        double somma = 0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        return somma / recensioni.size();
    }
    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNazione() {
        return nazione;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public int getPrezzoMedio() {
        return prezzoMedio;
    }

    public String getDescrizione() {
        return descrizione;
    }






    @Override
    public String toString() {
        String stelleStr = "★".repeat(this.stelle);
        String mediaStr = recensioni.isEmpty() ? "– nessuna recensione" : String.format("– media %.1f★", getMediaStelle());
        String deliveryStr = deliveryDisponibile ? " | Delivery " : "";
        String prenotazioneStr = prenotazioneOnlineDisponibile ? " | Prenotazione Online " : "";

        return nome + " – " + citta + " (" + stelleStr + ") " + mediaStr + deliveryStr + prenotazioneStr;
    }
}