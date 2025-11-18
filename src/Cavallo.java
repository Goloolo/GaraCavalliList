/**
 * La classe Cavallo rappresenta un cavallo che partecipa a una gara.
 * Estende la classe Thread per simulare il movimento in parallelo.
 */
public class Cavallo extends Thread {
    private int lunghezza;
    private String nome;
    private int lentezza;
    private final int PASSO = 5;
    private boolean azzoppato = false;

    /**
     * Costruttore della classe Cavallo.
     *
     * @param nome      Nome del cavallo
     * @param lentezza  Tempo in millisecondi tra un passo e l'altro
     * @param lunghezza Lunghezza totale della gara in metri
     */
    public Cavallo(String nome, int lentezza, int lunghezza) {
        this.nome = nome;
        this.lentezza = lentezza;
        this.lunghezza = lunghezza;
    }

    /**
     * Metodo eseguito quando il thread viene avviato.
     * Simula il galoppo del cavallo lungo la pista.
     */
    @Override
    public void run() {
        System.out.println("Cavallo " + nome + " comincia il suo galoppo!");
        GestoreGaraCavalli.scriviNelFile("Cavallo " + nome + " comincia il suo galoppo!");
        lunghezza = lunghezza / PASSO;

        for (int i = 1; i <= lunghezza; i++) {
            try {
                sleep(lentezza);
            } catch (InterruptedException e) {
                if (azzoppato) {
                    System.out.println("Cavallo " + nome + " e' stato AZZOPPATO e si ferma al metro " + i * PASSO);
                    GestoreGaraCavalli.scriviNelFile("Cavallo " + nome + " e' stato AZZOPPATO e si ferma al metro " + i * PASSO);
                    return;
                } else {
                    e.printStackTrace();
                }
            }

            System.out.println(nome + " cavalca - passo: " + i);
            GestoreGaraCavalli.scriviNelFile(nome + " cavalca - passo: " + i);

            if (GestoreGaraCavalli.getPrimo().equals("")) {
                GestoreGaraCavalli.setPrimo(this.nome);
            }
        }

        System.out.println(nome + " ha terminato la gara!");
        GestoreGaraCavalli.scriviNelFile(nome + " ha terminato la gara!");
    }

    /**
     * Azzoppa il cavallo, interrompendo il suo thread.
     */
    public void azzoppa() {
        azzoppato = true;
        this.interrupt();
    }

    /**
     * Restituisce il nome del cavallo.
     * @return nome del cavallo
     */
    public String getNome() {
        return nome;
    }
}
