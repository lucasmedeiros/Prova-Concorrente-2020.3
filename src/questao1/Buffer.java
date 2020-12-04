package questao1;

/**
 * 1 - 5.8 The roller coaster problem
 * 2 - 5.7 River crossing problem
 * 3 - 7.6 Dining Hall problem
 *
 * the little book of semaphores
 */

public class Buffer {
    private int vagasDisponiveis = Main.TOTAL_ASSENTOS;

    private final Object travaPassageiros = new Object();
    private final Object travaCarro = new Object();

    private synchronized boolean cheio() {
        return vagasDisponiveis == 0;
    }

    private synchronized boolean vazio() {
        return vagasDisponiveis == Main.TOTAL_ASSENTOS;
    }

    public void embarcar(int id) {
        synchronized (travaPassageiros) {
            if (!cheio()) {
                vagasDisponiveis--;
                System.out.println("Passageiro " + id + " embarcou.");
                try {
                    synchronized (travaCarro) {
                        if (cheio()) {
                            travaCarro.notifyAll();
                        }
                    }
                    travaPassageiros.wait();
                } catch (InterruptedException e) {}
            }
        }
    }

    public void desembarcar(int id) {
        synchronized (travaPassageiros) {
            vagasDisponiveis++;
            System.out.println("Passageiro " + id + " embarcou.");
            synchronized (travaCarro) {
                if (vazio()) {
                    travaCarro.notifyAll();
                }
            }
        }
    }

    public void correr() {
        System.out.println("Carro está correndo.");

        synchronized (travaPassageiros) {
            try {
                travaPassageiros.wait();
            } catch (InterruptedException e) {}
        }

        synchronized (travaCarro) {
            travaCarro.notifyAll();
        }
    }

    public void carregar() {
        synchronized (travaCarro) {
            System.out.println("Carro carregou.");
            synchronized (travaPassageiros) {
                travaPassageiros.notifyAll();
            }
            try {
                travaCarro.wait();
            } catch (InterruptedException e) {}
        }
    }

    public void descarregar() {
        synchronized (travaCarro) {
            System.out.println("Carro descarregou.");
            synchronized (travaPassageiros) {
                travaPassageiros.notifyAll();
            }
            try {
                travaCarro.wait();
            } catch (InterruptedException e) {}
        }
    }
}
