import java.util.Random;

public class Cavallo extends Thread {
    private int lunghezza;
    private String nome;
    private int lentezza;
    private final int PASSO = 5;
    private boolean azzoppato = false;

    public Cavallo(String nome, int lentezza, int lunghezza) {
        this.nome = nome;
        this.lentezza = lentezza;
        this.lunghezza = lunghezza;
    }

    @Override
    public void run() {
        System.out.println(" Cavallo " + nome + " comincia il suo galoppo!");
        lunghezza = lunghezza / PASSO;

        for (int i = 1; i <= lunghezza; i++) {
            try {
                sleep(lentezza);
            } catch (InterruptedException e) {
                if (azzoppato) {
                    System.out.println(" Cavallo " + nome + " e' stato AZZOPPATO e si ferma al metro " + i*PASSO);
                    return; // termina la corsa
                } else {
                    e.printStackTrace();
                }
            }

            System.out.println(nome + " cavalca - passo: " + i);

            // se nessuno ha ancora finito, segna il vincitore
            if (Main.getPrimo().equals("")) {
                Main.setPrimo(this.nome);
            }
        }

        System.out.println( nome + " ha terminato la gara!");
    }

    public void azzoppa() {
        azzoppato = true;
        this.interrupt(); // manda l’interrupt al thread
    }

    public String getNome() {
        return nome;
    }
}
