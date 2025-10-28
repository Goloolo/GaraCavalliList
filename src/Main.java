import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static String primo = "";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random r = new Random();

        System.out.print("Inserisci la lunghezza della gara (in metri): ");
        int lunghezza = Integer.parseInt(input.nextLine());

        ArrayList<Cavallo> listaCavallo = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            System.out.print("Inserisci il nome del cavallo " + i + ": ");
            String nome = input.nextLine();

            System.out.print("Inserisci la lentezza del cavallo " + i + " (ms tra passi): ");
            int lentezza = Integer.parseInt(input.nextLine());

            Cavallo c = new Cavallo(nome, lentezza, lunghezza);
            listaCavallo.add(c);
        }

        // Avvia i cavalli
        for (Cavallo c : listaCavallo) {
            c.start();
        }

        // Thread "interrupt manager"
        Thread interruptManager = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000 + r.nextInt(3000)); // ogni 2–5 secondi
                } catch (InterruptedException e) {
                    break;
                }

                // Scegli un cavallo a caso da azzoppare
                if (!listaCavallo.isEmpty()) {
                    int index = r.nextInt(listaCavallo.size());
                    Cavallo scelto = listaCavallo.get(index);

                    // Solo se è ancora vivo
                    if (scelto.isAlive()) {
                        scelto.azzoppa();
                    }
                }
            }
        });

        interruptManager.start();

        // Aspetta che tutti i cavalli finiscano
        for (Cavallo c : listaCavallo) {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ferma l’interrupt manager
        interruptManager.interrupt();

        System.out.println("\n Vincitore: " + getPrimo());
        System.out.println("Gara terminata!");
    }

    public static String getPrimo() {
        return primo;
    }

    public static void setPrimo(String primo) {
        Main.primo = primo;
    }
}
