package theknife;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.LocalDate;

/**
 * Classe principale dell'app TheKnife.
 * Gestisce registrazione, login e i menù utente e ristoratore.
 */
public class TheKnife {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        RistoranteManager ristoranteManager = new RistoranteManager(); // da CSV
        RecensioneManager recensioneManager = new RecensioneManager();

        Utente utenteLoggato = null;
        boolean esci = false;

        while (!esci) {
            System.out.println("\n BENVENUTO SU THEKNIFE ");
            System.out.println("1. Registrati");
            System.out.println("2. Login");
            System.out.println("3. Esci");
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
                                menuRistoratore(u, scanner, recensioneManager, ristoranteManager);
                            } else {
                                menuUtente(u, scanner, recensioneManager, ristoranteManager);
                            }
                        }

                    } else {
                        System.out.println("Credenziali errate.");
                    }
                    break;

                case "3":
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
     * Menù per utente normale
     */
    public static void menuUtente(Utente utente, Scanner scanner, RecensioneManager recensioneManager, RistoranteManager ristoranteManager) {
        boolean esci = false;
        while (!esci) {
            System.out.println("\n Menù utente (" + utente.getUsername() + ")");
            System.out.println("1. Cerca ristoranti per città");
            System.out.println("2. Cerca ristoranti per tipo cucina");
            System.out.println("3. Aggiungi recensione");
            System.out.println("4. Visualizza recensioni");
            System.out.println("5. Gestisci preferiti");
            System.out.println("6. Logout");
            System.out.println("7. Modifica una tua recensione");
            System.out.println("8. Cancella una tua recensione");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.print("Inserisci città: ");
                    String citta = scanner.nextLine().trim(); // elimina spazi extra

                    List<Ristorante> trovati = ristoranteManager.cercaPerCitta(citta);

                    if (trovati.isEmpty()) {
                        System.out.println("Nessun ristorante trovato per '" + citta + "'");
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
                    String tipo = scanner.nextLine().trim();
                    List<Ristorante> risultati = ristoranteManager.cercaPerTipoCucina(tipo);

                    if (risultati.isEmpty()) {
                        System.out.println("⚠️ Nessun ristorante trovato.");
                    } else {
                        System.out.println("🍽️ Ristoranti trovati:");
                        for (Ristorante r : risultati) {
                            System.out.println("- " + r);
                        }
                    }
                    break;



                case "3":
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

                        System.out.println(" Recensione salvata per " + scelto.getNome());
                    } else {
                        System.out.println(" Scelta non valida.");
                    }
                    break;


                case "4":
                    List<Recensione> tutte = recensioneManager.getTutteLeRecensioni();
                    if (tutte.isEmpty()) {
                        System.out.println(" Nessuna recensione disponibile.");
                    } else {
                        System.out.println(" Tutte le recensioni:\n");
                        for (Recensione r : tutte) {
                            System.out.println(r);
                            System.out.println("--------------------------------------------------");
                        }
                    }
                    break;


                case "5":
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
                                for (Ristorante r : utente.getPreferiti()) {
                                    System.out.println("- " + r);
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

                case "6":
                    esci = true;
                    System.out.println("Logout effettuato.");
                    break;

                default:
                    System.out.println("Scelta non valida.");

                    switch (scelta) {
                        case "7": {
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

                        case "8": {
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
                                    System.out.println(" Recensione rimossa con successo.");
                                } else {
                                    System.out.println(" Scelta non valida.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Inserisci un numero valido.");
                            }
                            break;
                        }

                        default:
                            System.out.println("Scelta non valida.");
                            break;
                    }
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
    public static void menuRistoratore(Utente ristoratore, Scanner scanner, RecensioneManager recensioneManager, RistoranteManager ristoranteManager)
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
                    Ristorante nuovo = new Ristorante(nome, citta, stelle, tipoCucina);
                    ristoranteManager.aggiungiRistorante(nuovo);
                    System.out.println("Ristorante aggiunto con successo.");
                    break;

                case "4":
                    esci = true;
                    System.out.println("Logout effettuato.");
                    break;


                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }
}
