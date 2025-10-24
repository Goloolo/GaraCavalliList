import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static String primo = "";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String tmpS;
        int tmpV;
        Integer tmpL;
        System.out.println("Inserisci la lunghezza della gara (in metri): ");
        tmpL = Integer.parseInt(input.nextLine());
        ArrayList<Cavallo> listaCavallo = new ArrayList<Cavallo>();
        for (int i = 1; i <= 4; i++) {
            System.out.println("Inserisci il nome del cavallo " + i);
            tmpS = input.nextLine();
            System.out.println("Inserisci la lentezza del cavallo " + i);
            tmpV = input.nextInt();
            String v = input.nextLine();
            Cavallo c = new Cavallo(tmpS, tmpV, tmpL);
            listaCavallo.add(c);
        }
        for (Cavallo c : listaCavallo) {
            c.start();
        }
        for (Cavallo c : listaCavallo) {
            try {
                c.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static String getPrimo() {
        return primo;
    }

    public static void setPrimo(String primo) {
        Main.primo = primo;
    }
}
