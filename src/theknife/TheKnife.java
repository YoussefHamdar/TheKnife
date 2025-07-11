package theknife;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.io.File;


/**
 * Classe principale dell'app TheKnife.
 * Gestisce registrazione, login e i menù utente e ristoratore.
 */
public class TheKnife {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        gestioneUtenti.caricaDaFile("data/utenti.dat");
        gestioneUtenti.salvaSuFile("data/utenti.dat"); // <-- li salva nel file binario


        RistoranteManager ristoranteManager = new RistoranteManager(); // da CSV


        File fileRisto = new File("data/ristoranti.dat");
        if (fileRisto.exists()) {
            ristoranteManager.caricaDaFile("data/ristoranti.dat"); //  già salvato in precedenza
        } else {
            // ⬇ Prima volta → carica dal CSV
            List<Ristorante> iniziali = RistoranteManager.caricaDaCSV("data/michelin_my_maps.csv");
            for (Ristorante r : iniziali) {
                ristoranteManager.aggiungiRistorante(r);
            }
            ristoranteManager.salvaSuFile("data/ristoranti.dat");
            System.out.println(" Ristoranti iniziali caricati dal CSV e salvati nel .dat.");
        }




        RecensioneManager recensioneManager = new RecensioneManager();
        recensioneManager.caricaDaFile("data/recensioni.dat");


        Utente utenteLoggato = null;
        boolean esci = false;

        while (!esci) {
            System.out.println("\n BENVENUTO SU THEKNIFE ");
            System.out.println("1. Registrati");
            System.out.println("2. Login");
            System.out.println("3. Accedi come ospite");
            System.out.println("4. Esci");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Cognome: ");
                    String cognome = scanner.nextLine();

                    System.out.print("Domicilio: ");
                    String domicilio = scanner.nextLine();

                    System.out.print("Data di nascita (aaaa-mm-gg): ");
                    String dataStr = scanner.nextLine();
                    LocalDate dataDiNascita = null;
                    try {
                        dataDiNascita = LocalDate.parse(dataStr);
                    } catch (Exception e) {
                        System.out.println("Formato data non valido. Continuiamo senza.");
                    }

                    System.out.print("Username: ");
                    String username = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    System.out.println("Sei un:\n1. Utente normale\n2. Ristoratore");
                    System.out.print("Scelta: ");
                    String sceltaRuolo = scanner.nextLine();
                    boolean isRistoratore = sceltaRuolo.equals("2");

                    boolean ok = gestioneUtenti.registraUtente(
                            nome, cognome, username, password,
                            isRistoratore, domicilio, dataDiNascita



                    );
                    gestioneUtenti.salvaSuFile("data/utenti.dat");

                    break;


                case "2":
                    System.out.print("Username: ");
                    String user = scanner.nextLine();
                    System.out.print("Password: ");
                    String pass = scanner.nextLine();

                    Utente u = gestioneUtenti.login(user, pass);
                    if (u != null) {
                        utenteLoggato = u;
                        System.out.println("Login riuscito. Ciao, " + u.getNome() + "!");
                        if (u != null) {
                            System.out.println("Login riuscito. Ciao, " + u.getNome() + "!");

                            if (u.isRistoratore()) {
                                menuRistoratore(u, scanner, recensioneManager, ristoranteManager, gestioneUtenti);
                            } else {
                                menuUtente(u, scanner, recensioneManager, ristoranteManager, gestioneUtenti);

                            }
                        }

                    } else {
                        System.out.println("Credenziali errate.");
                    }
                    break;
                case "3":
                    menuGuest(scanner, ristoranteManager, recensioneManager);
                    break;


                case "4":
                    gestioneUtenti.salvaSuFile("data/utenti.dat");
                    esci = true;
                    System.out.println("Grazie per aver usato TheKnife!");
                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
    }





    /**
     * Menù per ospite
     */


    public static void menuGuest(Scanner scanner, RistoranteManager ristoranteManager, RecensioneManager recensioneManager) {
        boolean esci = false;

        while (!esci) {
            System.out.println("\n Menu ospite");
            System.out.println("1. Cerca ristorante per nome");
            System.out.println("2. Visualizza recensioni di un ristorante");
            System.out.println("3. Filtra per città");
            System.out.println("4. Filtra per tipo di cucina");
            System.out.println("5. Filtra per fascia di prezzo");
            System.out.println("6. Solo con delivery");
            System.out.println("7. Solo con prenotazione online");
            System.out.println("8. Filtra per stelle minime");
            System.out.println("9. Ricerca combinata");
            System.out.println("10. Esci");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.print(" Inserisci nome ristorante: ");
                    String nome = scanner.nextLine();
                    Ristorante r = ristoranteManager.cercaPerNome(nome);
                    if (r != null) {
                        ristoranteManager.visualizzaDettagli(r);
                    } else {
                        System.out.println(" Ristorante non trovato.");
                    }
                    break;

                case "2":
                    System.out.print(" Inserisci nome ristorante: ");
                    String nomeRec = scanner.nextLine();
                    Ristorante risto = ristoranteManager.cercaPerNome(nomeRec);
                    if (risto != null) {
                        List<Recensione> recs = risto.getRecensioni();
                        if (recs.isEmpty()) {
                            System.out.println("Nessuna recensione disponibile.");
                        } else {
                            System.out.println(" Recensioni:");
                            for (Recensione rec : recs) {
                                System.out.println(rec);
                                System.out.println("--------------------------------------------------");
                            }
                        }
                    } else {
                        System.out.println(" Ristorante non trovato.");
                    }
                    break;

                case "3":
                    System.out.print(" Inserisci città: ");
                    String citta = scanner.nextLine();
                    stampaLista(ristoranteManager.cercaPerCitta(citta));
                    break;

                case "4":
                    System.out.print("Inserisci tipo cucina: ");
                    String tipo = scanner.nextLine();
                    stampaLista(ristoranteManager.cercaPerTipoCucina(tipo));
                    break;

                case "5":
                    System.out.print(" Inserisci fascia di prezzo (€ / €€ / €€€ / $$$): ");
                    String prezzo = scanner.nextLine();
                    stampaLista(ristoranteManager.cercaPerFasciaPrezzo(prezzo));
                    break;

                case "6":
                    stampaLista(ristoranteManager.cercaConDelivery());
                    break;

                case "7":
                    stampaLista(ristoranteManager.cercaConPrenotazioneOnline());
                    break;

                case "8":
                    System.out.print(" Inserisci numero minimo di stelle (es. 3.5): ");
                    double minStelle = Double.parseDouble(scanner.nextLine());
                    stampaLista(ristoranteManager.cercaPerMediaStelle(minStelle));
                    break;

                case "9":
                    System.out.print(" Città: ");
                    String city = scanner.nextLine();
                    System.out.print(" Tipo cucina: ");
                    String tipoCucina = scanner.nextLine();
                    System.out.print(" Prezzo massimo: ");
                    int maxPrezzo = Integer.parseInt(scanner.nextLine());
                    System.out.print(" Delivery richiesto (sì/no): ");
                    boolean delivery = scanner.nextLine().equalsIgnoreCase("sì");
                    System.out.print(" Prenotazione online richiesta (sì/no): ");
                    boolean prenotazione = scanner.nextLine().equalsIgnoreCase("sì");
                    System.out.print(" Media stelle minima: ");
                    double mediaMinima = Double.parseDouble(scanner.nextLine());

                    List<Ristorante> risultati = ristoranteManager.cercaCombinata(city, tipoCucina, maxPrezzo, delivery, prenotazione, mediaMinima);
                    stampaLista(risultati);
                    break;

                case "10":
                    esci = true;
                    System.out.println(" Uscita dal menu ospite.");
                    break;

                default:
                    System.out.println(" Scelta non valida.");
            }
        }
    }


    /**
     * Menù per utente normale
     */

    public static void menuUtente(Utente utente, Scanner scanner, RecensioneManager recensioneManager, RistoranteManager ristoranteManager, GestioneUtenti gestioneUtenti) {
        boolean esci = false;

        while (!esci) {
            System.out.println("\n Menù utente (" + utente.getUsername() + ")");
            System.out.println("1. Cerca ristoranti per città");
            System.out.println("2. Cerca ristoranti per tipo cucina");
            System.out.println("3. Cerca per fascia di prezzo");
            System.out.println("4. Cerca ristoranti con servizio delivery");
            System.out.println("5. Cerca ristoranti con prenotazione online");
            System.out.println("6. Cerca ristoranti con media stelle");
            System.out.println("7. Ricerca avanzata (combinata)");
            System.out.println("8. Visualizza dettagli ristorante");
            System.out.println("9. Aggiungi recensione");
            System.out.println("10. Visualizza recensioni");
            System.out.println("11. Gestisci preferiti");
            System.out.println("12. Modifica una tua recensione");
            System.out.println("13. Cancella una tua recensione");
            System.out.println("14. Visualizza ristoranti che hai recensito");
            System.out.println("15. Logout");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.print("Inserisci città: ");
                    String cittaInput = scanner.nextLine().trim(); // elimina spazi extra

                    List<Ristorante> trovati = ristoranteManager.cercaPerCitta(cittaInput);

                    if (trovati.isEmpty()) {
                        System.out.println("Nessun ristorante trovato per '" + cittaInput + "'");
                    } else {
                        System.out.println("Ristoranti trovati:");
                        for (Ristorante r : trovati) {
                            System.out.println("- " + r);
                        }
                    }

                    System.out.println(); // spazio per tornare al menù
                    break;
                case "2":
                    System.out.print("Inserisci tipo di cucina: ");
                    String tipoCucinaInput = scanner.nextLine().trim();
                    List<Ristorante> risultati = ristoranteManager.cercaPerTipoCucina(tipoCucinaInput);

                    if (risultati.isEmpty()) {
                        System.out.println(" Nessun ristorante trovato.");
                    } else {
                        System.out.println(" Ristoranti trovati:");
                        for (Ristorante r : risultati) {
                            System.out.println("- " + r);
                        }
                    }
                    break;
                case "3":
                    System.out.print("Inserisci fascia prezzo (es. €, €€, €€€, $$$): ");
                    String prezzo = scanner.nextLine().trim();

                    List<Ristorante> filtrati = ristoranteManager.cercaPerFasciaPrezzo(prezzo);

                    if (filtrati.isEmpty()) {
                        System.out.println(" Nessun ristorante trovato con fascia '" + prezzo + "'");
                    } else {
                        System.out.println(" Ristoranti trovati:");
                        for (Ristorante r : filtrati) {
                            System.out.println("- " + r);
                        }
                    }
                    break;

                case "4":
                    List<Ristorante> conDelivery = ristoranteManager.cercaConDelivery();

                    if (conDelivery.isEmpty()) {
                        System.out.println(" Nessun ristorante con delivery trovato.");
                    } else {
                        System.out.println(" Ristoranti che offrono delivery:");
                        for (Ristorante r : conDelivery) {
                            System.out.println("- " + r);
                        }
                    }
                    break;

                case "5":
                    List<Ristorante> conPrenotazione = ristoranteManager.cercaConPrenotazioneOnline();

                    if (conPrenotazione.isEmpty()) {
                        System.out.println(" Nessun ristorante offre prenotazione online.");
                    } else {
                        System.out.println(" Ristoranti con prenotazione online:");
                        for (Ristorante r : conPrenotazione) {
                            System.out.println("- " + r);
                        }
                    }
                    break;

                case "6":
                    System.out.print("Inserisci minimo stelle (es. 3.5): ");
                    double minStelle = Double.parseDouble(scanner.nextLine());

                    List<Ristorante> filtratiMedia = ristoranteManager.cercaPerMediaStelle(minStelle);

                    if (filtratiMedia.isEmpty()) {
                        System.out.println(" Nessun ristorante con media ≥ " + minStelle);
                    } else {
                        System.out.println(" Ristoranti con media stelle:");
                        for (Ristorante r : filtratiMedia) {
                            System.out.printf("- %s (Media: %.2f)\n", r.getNome(), r.getMediaStelle());
                        }
                    }
                    break;

                case "7":
                    System.out.print("Città: ");
                    String citta = scanner.nextLine();

                    System.out.print("Tipo cucina: ");
                    String tipo = scanner.nextLine();

                    System.out.print("Prezzo massimo: ");
                    int prezzoMax = Integer.parseInt(scanner.nextLine());

                    System.out.print("Richiedi delivery? (sì/no): ");
                    boolean delivery = scanner.nextLine().equalsIgnoreCase("sì");

                    System.out.print("Richiedi prenotazione online? (sì/no): ");
                    boolean prenotazione = scanner.nextLine().equalsIgnoreCase("sì");

                    System.out.print("Media stelle minima: ");
                    double minMediaStelle = Double.parseDouble(scanner.nextLine());

                    List<Ristorante> ristorantiFiltrati = ristoranteManager.cercaCombinata(
                            citta, tipo, prezzoMax, delivery, prenotazione, minMediaStelle
                    );

                    if (ristorantiFiltrati.isEmpty()) {
                        System.out.println(" Nessun ristorante trovato con i criteri specificati.");
                    } else {
                        System.out.println(" Ristoranti trovati:");
                        for (Ristorante r : ristorantiFiltrati) {
                            System.out.printf("- %s (%s, %.1f stelle)\n", r.getNome(), r.getFasciaPrezzo(), r.getMediaStelle());
                        }
                    }
                    break;
                case "8":
                    System.out.print("Inserisci nome ristorante: ");
                    String nome = scanner.nextLine();

                    Ristorante r = ristoranteManager.cercaPerNome(nome);

                    if (r != null) {
                        ristoranteManager.visualizzaDettagli(r);
                    } else {
                        System.out.println(" Ristorante non trovato.");
                    }
                    break;





                case "9":
                    List<Ristorante> ristorantiDisponibili = ristoranteManager.getTuttiIRistoranti();

                    System.out.println("Scegli il ristorante da recensire:");
                    for (int i = 0; i < ristorantiDisponibili.size(); i++) {
                        System.out.println(i + ". " + ristorantiDisponibili.get(i));
                    }

                    System.out.print("Numero ristorante: ");
                    int index = Integer.parseInt(scanner.nextLine());

                    if (index >= 0 && index < ristorantiDisponibili.size()) {
                        Ristorante scelto = ristorantiDisponibili.get(index);
                        System.out.println("Hai scelto: " + scelto.getNome());

                        System.out.print("Scrivi la tua recensione: ");
                        String testo = scanner.nextLine();

                        System.out.print("Quante stelle (1–5): ");
                        int stelle = Integer.parseInt(scanner.nextLine());

                        recensioneManager.aggiungiRecensione(
                                utente.getUsername(),
                                scelto.getNome(),
                                testo,
                                stelle
                        );
                        recensioneManager.salvaSuFile("data/recensioni.dat");


                        System.out.println(" Recensione salvata per " + scelto.getNome());
                    } else {
                        System.out.println(" Scelta non valida.");
                    }
                    break;


                case "10":
                    List<Recensione> tutte = recensioneManager.getTutteLeRecensioni();
                    if (tutte.isEmpty()) {
                        System.out.println(" Nessuna recensione disponibile.");
                    } else {
                        System.out.println(" Tutte le recensioni:\n");
                        for (Recensione rec : tutte) {
                            System.out.println(rec);
                            System.out.println("--------------------------------------------------");
                        }
                    }
                    break;


                case "11":
                    boolean esciPreferiti = false;
                    while (!esciPreferiti) {
                        System.out.println("\n Gestione preferiti");
                        System.out.println("1. Aggiungi ristorante dai disponibili");
                        System.out.println("2. Rimuovi dai preferiti");
                        System.out.println("3. Visualizza preferiti");
                        System.out.println("4. Torna al menù utente");
                        System.out.println("6. Modifica una tua recensione");
                        System.out.println("7. Cancella una tua recensione");
                        System.out.print("Scelta: ");
                        String sottoScelta = scanner.nextLine();

                        switch (sottoScelta) {
                            case "1": {
                                List<Ristorante> elenco = ristoranteManager.getTuttiIRistoranti();
                                for (int i = 0; i < elenco.size(); i++) {
                                    System.out.println(i + ". " + elenco.get(i));
                                }
                                System.out.print("Numero da aggiungere: ");
                                int indexAggiungi = Integer.parseInt(scanner.nextLine());
                                if (indexAggiungi >= 0 && indexAggiungi < elenco.size()) {
                                    utente.aggiungiPreferito(elenco.get(indexAggiungi));
                                    System.out.println("Ristorante aggiunto ai preferiti.");
                                }
                                break;
                            }
                            case "2": {
                                List<Ristorante> pref = utente.getPreferiti();
                                for (int i = 0; i < pref.size(); i++) {
                                    System.out.println(i + ". " + pref.get(i));
                                }
                                System.out.print("Numero da rimuovere: ");
                                int indexRimuovi = Integer.parseInt(scanner.nextLine());
                                if (indexRimuovi >= 0 && indexRimuovi < pref.size()) {
                                    utente.rimuoviPreferito(pref.get(indexRimuovi));
                                    System.out.println("Ristorante rimosso dai preferiti.");
                                }
                                break;
                            }
                            case "3": {
                                System.out.println("I tuoi ristoranti preferiti:");
                                for (Ristorante preferito : utente.getPreferiti()) {
                                    System.out.println("- " + preferito);
                                }
                                break;
                            }
                            case "4": {
                                esciPreferiti = true;
                                break;
                            }
                            default:
                                System.out.println("Scelta non valida.");
                        }
                    }
                    break;


                default:
                    System.out.println("Scelta non valida.");

                    switch (scelta) {
                        case "12": {
                            List<Recensione> mie = getRecensioniUtente(utente, recensioneManager);

                            if (mie.isEmpty()) {
                                System.out.println("Non hai ancora scritto recensioni.");
                                break;
                            }

                            System.out.println("Le tue recensioni:");
                            for (int i = 0; i < mie.size(); i++) {
                                System.out.println(i + ". " + mie.get(i));
                            }

                            System.out.print("Numero recensione da modificare: ");
                            try {
                                int sceltaModifica = Integer.parseInt(scanner.nextLine());

                                if (sceltaModifica >= 0 && sceltaModifica < mie.size()) {
                                    Recensione daModificare = mie.get(sceltaModifica);
                                    System.out.println("Recensione attuale:\n" + daModificare);

                                    System.out.print("Nuovo testo: ");
                                    String nuovoTesto = scanner.nextLine();

                                    System.out.print("Nuove stelle (1–5): ");
                                    int nuoveStelle = Integer.parseInt(scanner.nextLine());

                                    if (nuoveStelle >= 1 && nuoveStelle <= 5) {
                                        recensioneManager.modificaRecensione(daModificare, nuovoTesto, nuoveStelle);
                                        recensioneManager.salvaSuFile("data/recensioni.dat");

                                        System.out.println(" Recensione aggiornata.");
                                    } else {
                                        System.out.println(" Numero di stelle non valido.");
                                    }
                                } else {
                                    System.out.println(" Scelta non valida.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Inserisci un numero valido.");
                            }
                            break;
                        }

                        case "13": {
                            List<Recensione> mie = getRecensioniUtente(utente, recensioneManager);

                            if (mie.isEmpty()) {
                                System.out.println("Non hai ancora scritto recensioni.");
                                break;
                            }

                            System.out.println("Le tue recensioni:");
                            for (int i = 0; i < mie.size(); i++) {
                                System.out.println(i + ". " + mie.get(i));
                            }

                            System.out.print("Numero recensione da eliminare: ");
                            try {
                                int sceltaElimina = Integer.parseInt(scanner.nextLine());

                                if (sceltaElimina >= 0 && sceltaElimina < mie.size()) {
                                    System.out.println("Recensione eliminata:\n" + mie.get(sceltaElimina));
                                    recensioneManager.rimuoviRecensione(mie.get(sceltaElimina));
                                    recensioneManager.salvaSuFile("data/recensioni.dat");

                                    System.out.println(" Recensione rimossa con successo.");
                                } else {
                                    System.out.println(" Scelta non valida.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Inserisci un numero valido.");
                            }
                            break;
                        }

                        case "14":
                            List<Ristorante> recensiti = ristoranteManager.getRecensitiDa(utente.getUsername());
                            if (recensiti.isEmpty()) {
                                System.out.println(" Non hai ancora recensito alcun ristorante.");
                            } else {
                                System.out.println(" Ristoranti che hai recensito:");
                                for (Ristorante r2 : recensiti) {
                                    System.out.println("- " + r2.getNome() + " (" + r2.getCitta() + ")");
                                }
                            }
                            break;

                        default:
                            System.out.println("Scelta non valida.");
                            break;
                    }
                case "15":
                    gestioneUtenti.salvaSuFile("data/utenti.dat");

                    esci = true;
                    System.out.println("Logout effettuato.");
                    break;
            }
        }
    }
    private static List<Recensione> getRecensioniUtente(Utente utente, RecensioneManager manager) {
        return manager.getTutteLeRecensioni().stream()
                .filter(r -> r.getAutore().equals(utente.getUsername()))
                .collect(Collectors.toList());
    }

    /**
     * Menù per ristoratore
     */
    public static void menuRistoratore(Utente ristoratore, Scanner scanner, RecensioneManager recensioneManager, RistoranteManager ristoranteManager, GestioneUtenti gestioneUtenti)

    {
        boolean esci = false;
        while (!esci) {
            System.out.println("\n Menù ristoratore (" + ristoratore.getUsername() + ")");
            System.out.println("1. Visualizza recensioni");
            System.out.println("2. Rispondi a una recensione");
            System.out.println("3. Aggiungi ristorante");
            System.out.println("4. Logout");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    List<Recensione> lista = recensioneManager.getTutteLeRecensioni();
                    for (int i = 0; i < lista.size(); i++) {
                        System.out.println(i + ". " + lista.get(i) + "\n");
                    }
                    break;

                case "2": {
                    List<Recensione> tutte = recensioneManager.getTutteLeRecensioni();

                    if (tutte.isEmpty()) {
                        System.out.println(" Nessuna recensione disponibile a cui rispondere.");
                        break;
                    }

                    for (int i = 0; i < tutte.size(); i++) {
                        System.out.println(i + ". " + tutte.get(i));
                    }

                    System.out.print("Numero della recensione: ");
                    int index = Integer.parseInt(scanner.nextLine());

                    if (index >= 0 && index < tutte.size()) {
                        System.out.print("Scrivi la risposta: ");
                        String risposta = scanner.nextLine();
                        tutte.get(index).setRispostaDelRistoratore(risposta);
                        System.out.println("Risposta salvata.");
                    } else {
                        System.out.println(" Numero non valido.");
                    }
                    break;
                }


                case "3":
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Città: ");
                    String citta = scanner.nextLine();
                    System.out.print("Numero stelle (1–5): ");
                    int stelle = Integer.parseInt(scanner.nextLine());
                    System.out.print("Tipo cucina (es. Giapponese, Italiana, Messicana): ");
                    String tipoCucina = scanner.nextLine();
                    System.out.print("Fascia di prezzo (es. €, €€, €€€, $$$): ");
                    String fasciaPrezzo = scanner.nextLine();
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
                            prezzoMedio = 0;
                    }
                    System.out.print(" Inserisci nazione: ");
                    String nazione = scanner.nextLine();

                    System.out.print(" Inserisci indirizzo: ");
                    String indirizzo = scanner.nextLine();

                    System.out.print("Inserisci latitudine (es. 45.95): ");
                    double latitudine = Double.parseDouble(scanner.nextLine());

                    System.out.print("Inserisci longitudine (es. 8.43): ");
                    double longitudine = Double.parseDouble(scanner.nextLine());

                    Ristorante nuovo = new Ristorante(nome, citta, stelle, tipoCucina, fasciaPrezzo, true, true, prezzoMedio, nazione, indirizzo,latitudine,longitudine);

                    ristoranteManager.aggiungiRistorante(nuovo);
                    ristoranteManager.salvaSuFile("data/ristoranti.dat");



                    System.out.println("Ristorante aggiunto con successo.");
                    break;

                case "4":
                    gestioneUtenti.salvaSuFile("data/utenti.dat");
                    ristoranteManager.salvaSuFile("data/ristoranti.dat");
                    esci = true;

                    System.out.println("Logout effettuato.");
                    break;


                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }
    private static void stampaLista(List<Ristorante> lista) {
        if (lista.isEmpty()) {
            System.out.println(" Nessun ristorante trovato.");
        } else {
            System.out.println("Ristoranti trovati:");
            for (Ristorante r : lista) {
                System.out.println("- " + r);
            }
        }
    }

}
