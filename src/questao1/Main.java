package questao1;

public class Main {
    public static int TOTAL_PASSAGEIROS = 8;
    public static int TOTAL_ASSENTOS = 4;

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Passageiro passageiro;
        Thread[] t1 = new Thread[TOTAL_PASSAGEIROS];
        Carro carro = new Carro(buffer);
        Thread t2 = new Thread(carro, "Carro");

        for (int i = 0; i < TOTAL_PASSAGEIROS; i++) {
            passageiro = new Passageiro(i, buffer);
            t1[i] = new Thread(passageiro, "Passageiro " + i);
        }

        t2.start();

        for (int i = 0; i < TOTAL_PASSAGEIROS; i++) {
            t1[i].start();
        }

    }
}
