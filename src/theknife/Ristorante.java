package theknife;

public class Ristorante {
    private String nome;
    private String citta;
    private int stelle;
    private String tipoCucina;
    private String fasciaPrezzo;

    private boolean deliveryDisponibile;



    public Ristorante(String nome, String citta, int stelle, String tipoCucina, String fasciaPrezzo,boolean deliveryDisponibile) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.tipoCucina = tipoCucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.deliveryDisponibile = deliveryDisponibile;
    }

    public boolean isDeliveryDisponibile() {
        return deliveryDisponibile;
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
