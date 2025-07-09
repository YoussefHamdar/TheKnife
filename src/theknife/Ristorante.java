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

    private List<Recensione> recensioni = new ArrayList<>();




    public Ristorante(String nome, String citta, int stelle, String tipoCucina, String fasciaPrezzo,boolean deliveryDisponibile, boolean prenotazioneOnlineDisponibile) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.tipoCucina = tipoCucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.deliveryDisponibile = deliveryDisponibile;
        this.prenotazioneOnlineDisponibile = prenotazioneOnlineDisponibile;
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

    public int getPrezzoMedio() {
        switch (fasciaPrezzo.trim()) {
            case "€":
                return 1;
            case "€€":
                return 2;
            case "€€€":
                return 3;
            case "$$$":
                return 4;
            default:
                return 0; // valore neutro se fascia non riconosciuta
        }
    }

    public double getMediaStelle() {
        if (recensioni.isEmpty()) return 0;
        double somma = 0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        return somma / recensioni.size();
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