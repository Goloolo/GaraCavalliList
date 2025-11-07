import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe principale che gestisce la simulazione della gara tra cavalli.
 */
public class GestoreGaraCavalli {
    static String primo = "";
    static PrintWriter pw;

    /**
     * Metodo principale che avvia la simulazione della gara.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random r = new Random();

        try {
            pw = new PrintWriter(new FileWriter("risultati_gara.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Inserisci la lunghezza della gara (in metri): ");
        int lunghezza = Integer.parseInt(input.nextLine());
        scriviNelFile("Lunghezza gara: " + lunghezza + " metri");

        ArrayList<Cavallo> listaCavallo = new ArrayList<>();

        // Inserimento dei cavalli
        for (int i = 1; i <= 4; i++) {
            System.out.print("Inserisci il nome del cavallo " + i + ": ");
            String nome = input.nextLine();
            System.out.print("Inserisci la lentezza del cavallo " + i + " (ms tra passi): ");
            int lentezza = Integer.parseInt(input.nextLine());
            Cavallo c = new Cavallo(nome, lentezza, lunghezza);
            listaCavallo.add(c);
        }

        // Avvio dei thread cavallo
        for (Cavallo c : listaCavallo) {
            c.start();
        }

        // Thread che azzoppa casualmente i cavalli
        Thread interruptManager = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000 + r.nextInt(3000));
                } catch (InterruptedException e) {
                    break;
                }
                if (!listaCavallo.isEmpty()) {
                    int index = r.nextInt(listaCavallo.size());
                    Cavallo scelto = listaCavallo.get(index);
                    if (scelto.isAlive()) {
                        scelto.azzoppa();
                    }
                }
            }
        });

        interruptManager.start();

        // Attesa della fine della gara
        for (Cavallo c : listaCavallo) {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        interruptManager.interrupt();

        System.out.println("\nVincitore: " + getPrimo());
        scriviNelFile("\nVincitore: " + getPrimo());
        System.out.println("Gara terminata!");
        scriviNelFile("Gara terminata!");

        pw.close();
    }

    /**
     * Restituisce il nome del cavallo vincitore.
     *
     * @return nome del primo cavallo arrivato
     */
    public static String getPrimo() {
        return primo;
    }

    /**
     * Imposta il nome del cavallo vincitore.
     *
     * @param primo nome del vincitore
     */
    public static void setPrimo(String primo) {
        GestoreGaraCavalli.primo = primo;
    }

    /**
     * Scrive una riga di testo nel file di output.
     *
     * @param testo testo da scrivere
     */
    public static synchronized void scriviNelFile(String testo) {
        if (pw != null) {
            pw.println(testo);
            pw.flush();
        }
    }
}
