package theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RistoranteManager {

    public static List<Ristorante> caricaDaCSV(String pathCSV) {
        List<Ristorante> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathCSV))) {
            String linea;
            br.readLine(); // salta intestazione

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(";");
                if (campi.length >= 3) {
                    String nome = campi[0].trim();
                    String citta = campi[1].trim();
                    String campoStelle = campi[2].trim();

                    // Conta quante stelle ★ contiene il campo
                    int stelle = campoStelle.replaceAll("[^★]", "").length();

                    Ristorante r = new Ristorante(nome, citta, stelle);
                    lista.add(r);
                }
            }

        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }

        return lista;
    }
}
