import java.util.Random;

public class Cavallo extends Thread {
    private int lunghezza;
    private String nome;
    private int lentezza;
    private final int PASSO = 5;
    Random r = new Random();
    int r1 = r.nextInt(2);

    public Cavallo(String nome, int lentezza, int lunghezza) {
        this.nome = nome;
        this.lentezza = lentezza;
        this.lunghezza = lunghezza;
    }

@Override
    public void run(){
        System.out.println("cavallo " + nome + " comincia il suo galoppo");
        lunghezza = lunghezza / 5;
        for (int i = 1; i <= lunghezza; i++){
            try {
                sleep(lentezza);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println (nome + " cavalca - passo: " + i);
            if (Main.getPrimo().equals("")){
                Main.setPrimo(this.nome);
            }
        }
        System.out.println(nome + " ha terminato la gara!");
    }

    public void azzoppa(){
        if(r1 == 0){
            System.out.println("il cavallo " + nome + " si e' azzoppato");
            this.interrupt();
        }
    }
}
