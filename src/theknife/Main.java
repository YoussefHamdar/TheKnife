package theknife;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String percorsoCSV = "data/michelin_my_maps.csv";

        List<Ristorante> ristoranti = RistoranteManager.caricaDaCSV(percorsoCSV);

        for (Ristorante r : ristoranti) {
            System.out.println(r);
        }
    }
}
