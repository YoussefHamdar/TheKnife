package theknife;

import java.util.List;
import java.util.Scanner;

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
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    boolean ok = gestioneUtenti.registraUtente(nome, username, password);
                    if (ok) {
                        System.out.println("Registrazione completata!");
                    } else {
                        System.out.println("Username già esistente.");
                    }
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
                        if (u instanceof Ristoratore) {
                            menuRistoratore((Ristoratore) u, scanner, recensioneManager, ristoranteManager);
                        } else {
                            menuUtente(u, scanner, recensioneManager, ristoranteManager);
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
            System.out.println("2. Aggiungi recensione");
            System.out.println("3. Visualizza recensioni");
            System.out.println("4. Gestisci preferiti");
            System.out.println("5. Logout");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.print("Inserisci città: ");
                    String citta = scanner.nextLine();
                    List<Ristorante> trovati = ristoranteManager.cercaPerCitta(citta);
                    for (Ristorante r : trovati) {
                        System.out.println(r);
                    }
                    break;

                case "2":
                    System.out.print("Scrivi la tua recensione: ");
                    String testo = scanner.nextLine();
                    System.out.print("Quante stelle (1–5): ");
                    int stelle = Integer.parseInt(scanner.nextLine());
                    recensioneManager.aggiungiRecensione(utente.getUsername(), testo, stelle);
                    System.out.println("Recensione salvata.");
                    break;

                case "3":
                    List<Recensione> lista = recensioneManager.getTutteLeRecensioni();
                    for (Recensione r : lista) {
                        System.out.println(r + "\n");
                    }
                    break;

                case "4":
                    boolean esciPreferiti = false;
                    while (!esciPreferiti) {
                        System.out.println("\n Gestione preferiti");
                        System.out.println("1. Aggiungi ristorante dai disponibili");
                        System.out.println("2. Rimuovi dai preferiti");
                        System.out.println("3. Visualizza preferiti");
                        System.out.println("4. Torna al menù utente");
                        System.out.print("Scelta: ");
                        String sottoScelta = scanner.nextLine();

                        switch (sottoScelta) {
                            case "1":
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

                            case "2":
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

                            case "3":
                                System.out.println("I tuoi ristoranti preferiti:");
                                for (Ristorante r : utente.getPreferiti()) {
                                    System.out.println("- " + r);
                                }
                                break;

                            case "4":
                                esciPreferiti = true;
                                break;

                            default:
                                System.out.println("Scelta non valida.");
                        }
                    }
                    break;

                case "5":
                    esci = true;
                    System.out.println("Logout effettuato.");
                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    /**
     * Menù per ristoratore
     */
    public static void menuRistoratore(Ristoratore ristoratore, Scanner scanner, RecensioneManager recensioneManager, RistoranteManager ristoranteManager) {
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

                case "2":
                    List<Recensione> tutte = recensioneManager.getTutteLeRecensioni();
                    for (int i = 0; i < tutte.size(); i++) {
                        System.out.println(i + ". " + tutte.get(i) + "\n");
                    }
                    System.out.print("Numero della recensione: ");
                    int index = Integer.parseInt(scanner.nextLine());
                    System.out.print("Scrivi la risposta: ");
                    String risposta = scanner.nextLine();
                    ristoratore.rispondiARecensione(tutte.get(index), risposta);
                    System.out.println("Risposta inviata.");
                    break;

                case "3":
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Città: ");
                    String citta = scanner.nextLine();
                    System.out.print("Numero stelle (1–5): ");
                    int stelle = Integer.parseInt(scanner.nextLine());

                    Ristorante nuovo = new Ristorante(nome, citta, stelle);
                    ristoranteManager.aggiungiRistorante(nuovo);
                    System.out.println("✅ Ristorante aggiunto con successo.");
                    break;

                case "4":


                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }
}
