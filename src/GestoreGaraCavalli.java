import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe principale che gestisce la simulazione della gara tra cavalli.
 * Permette di scegliere un file di output tramite JFileChooser
 * e di salvare i risultati della gara.
 */
public class GestoreGaraCavalli {
    /** Nome del cavallo vincitore */
    static String primo = "";
    /** Writer per scrivere su file */
    static PrintWriter pw;

    /**
     * Metodo principale che avvia la simulazione della gara.
     * - Chiede all'utente la lunghezza della gara
     * - Chiede i dati dei cavalli
     * - Avvia i thread dei cavalli
     * - Gestisce un thread che può azzoppare i cavalli
     * - Scrive i risultati su file scelto dall'utente
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random r = new Random();

        // Apertura finestra di selezione file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleziona il file di output");
        fileChooser.setFileFilter(new FileNameExtensionFilter("File di testo", "txt"));

        int scelta = fileChooser.showSaveDialog(null);
        if (scelta == JFileChooser.APPROVE_OPTION) {
            File fileSelezionato = fileChooser.getSelectedFile();
            try {
                pw = new PrintWriter(new FileWriter(fileSelezionato));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println("Nessun file selezionato. Programma terminato.");
            return;
        }

        // Inserimento lunghezza gara
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
     * @return nome del primo cavallo arrivato
     */
    public static String getPrimo() {
        return primo;
    }

    /**
     * Imposta il nome del cavallo vincitore.
     * @param primo nome del vincitore
     */
    public static void setPrimo(String primo) {
        GestoreGaraCavalli.primo = primo;
    }

    /**
     * Scrive una riga di testo nel file di output.
     * @param testo testo da scrivere
     */
    public static synchronized void scriviNelFile(String testo) {
        if (pw != null) {
            pw.println(testo);
            pw.flush();
        }
    }
}
