package theknife;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale dell'app TheKnife.
 * Gestisce registrazione, login, e il menù principale.
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
            System.out.println("\n🔐 BENVENUTO SU THEKNIFE 🔪");
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
                        System.out.println("✅ Registrazione completata!");
                    } else {
                        System.out.println("❌ Username già esistente.");
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
                        System.out.println("✅ Login riuscito. Ciao, " + u.getNome() + "!");
                        if (u instanceof Ristoratore) {
                            menuRistoratore((Ristoratore) u, scanner, recensioneManager, ristoranteManager);
                        } else {
                            menuUtente(u, scanner, recensioneManager, ristoranteManager);
                        }
                    } else {
                        System.out.println("❌ Credenziali errate.");
                    }
                    break;

                case "3":
                    esci = true;
                    System.out.println("👋 Grazie per aver usato TheKnife!");
                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
    }