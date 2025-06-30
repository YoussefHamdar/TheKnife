package theknife;

public class Ristorante {
    private String nome;
    private String citta;
    private int stelle;

    public Ristorante(String nome, String citta, int stelle) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
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

    @Override
    public String toString() {
        String stelleStr = "★".repeat(this.stelle);
        return nome + " – " + citta + " (" + stelleStr + ")";
    }
}
