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
    private boolean correndo = false;
    private boolean carregado = false;
    private boolean descarregado = false;

    private final Object travaPassageiros = new Object();
    private final Object travaCarro = new Object();

    private synchronized boolean cheio() {
        return vagasDisponiveis == 0;
    }
    private synchronized boolean vazio() {
        return vagasDisponiveis == Main.TOTAL_ASSENTOS;
    }
    private synchronized void decrementarVaga() {
        this.vagasDisponiveis--;
    }
    private synchronized void incrementarVaga() {
        this.vagasDisponiveis++;
    }
    private synchronized void atualizaCorrendo(boolean correndo) {
        this.correndo = correndo;
    }
    private synchronized void atualizaCarregado(boolean carregado) {
        this.carregado = carregado;
    }
    private synchronized void atualizaDescarregado(boolean descarregado) {
        this.descarregado = descarregado;
    }

    public void embarcar(int id) {
        while (true) {
            if (!cheio() && this.carregado && !this.correndo) {
                decrementarVaga();
                System.out.println(id + " embarcou.");

                if (cheio()) {
                    synchronized (travaCarro) {
                        if (cheio()) travaCarro.notifyAll();
                    }

                    synchronized (travaPassageiros) {
                        try {
                            travaPassageiros.wait();
                        } catch (InterruptedException e) {}
                    }
                }
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
        }
    }

    public void desembarcar(int id) {
        if (this.descarregado && !this.correndo) {
            synchronized (travaPassageiros) {
                incrementarVaga();
                System.out.println(id + " desembarcou.");
                synchronized (travaCarro) {
                    if (vazio()) {
                        travaCarro.notifyAll();
                    }
                }
            }
        }
    }

    public void correr() {
        System.out.println(Thread.currentThread().getName() + " está correndo");
        this.atualizaCorrendo(true);
        this.atualizaDescarregado(false);
        this.atualizaCarregado(false);

        try {
            Thread.sleep((int)(Math.random()*2000));
        } catch (InterruptedException e) {}
    }

    public void carregar() {
        this.atualizaCarregado(true);

        System.out.println("Carro carregou.");
        synchronized (travaPassageiros) {
            travaPassageiros.notifyAll();
        }

        synchronized (travaCarro) {
            try {
                travaCarro.wait();
            } catch (InterruptedException e) {}
        }
    }

    public void descarregar() {
        this.atualizaDescarregado(true);
        this.atualizaCorrendo(false);
        System.out.println("Carro descarregou.");

        synchronized (travaPassageiros) {
            travaPassageiros.notifyAll();
        }

        synchronized (travaCarro) {
            try {
                travaCarro.wait();
            } catch (InterruptedException e) {}
        }
    }
}
