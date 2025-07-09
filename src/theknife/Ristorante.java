package theknife;

public class Ristorante {
    private String nome;
    private String citta;
    private int stelle;
    private String tipoCucina;
    private String fasciaPrezzo;
    private boolean deliveryDisponibile;
    private boolean prenotazioneOnlineDisponibile;



    public Ristorante(String nome, String citta, int stelle, String tipoCucina, String fasciaPrezzo,boolean deliveryDisponibile, boolean prenotazioneOnlineDisponibile) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.tipoCucina = tipoCucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.deliveryDisponibile = deliveryDisponibile;
        this.prenotazioneOnlineDisponibile = prenotazioneOnlineDisponibile;
    }

    public boolean isDeliveryDisponibile() {
        return deliveryDisponibile;
    }


    public boolean isPrenotazioneOnlineDisponibile() {
        return prenotazioneOnlineDisponibile;
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



    @Override
    public String toString() {
        String stelleStr = "★".repeat(this.stelle);
        return nome + " – " + citta + " (" + stelleStr + ")";
    }
}
