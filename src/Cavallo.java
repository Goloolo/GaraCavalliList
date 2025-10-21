

public class Cavallo extends Thread {
    private String nome;
    private int lentezza;

    public Cavallo(String nome, int lunghezzaGara) {
        this.nome = nome;
        this.lentezza = lentezza;
    }

@Override
    public void run(){
        System.out.println("cavallo" + nome + "comincia il suo galoppo");
        for (int i = 1; i <= 10; i++){
            try {
                sleep(lentezza);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println (nome + "cavalca - passo: " + i);
            if (Main.getPrimo().equals("")){
                Main.setPrimo(this.nome);
            }
        }
        System.out.println(nome + " ha terminato la gara!");
    }
}