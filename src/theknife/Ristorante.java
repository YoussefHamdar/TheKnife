package theknife;

public class Ristorante {
    private String nome;
    private String citta;
    private int stelle;
    private String tipoCucina;


    public Ristorante(String nome, String citta, int stelle, String tipoCucina) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.tipoCucina = tipoCucina;
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


    @Override
    public String toString() {
        String stelleStr = "★".repeat(this.stelle);
        return nome + " – " + citta + " (" + stelleStr + ")";
    }
}
