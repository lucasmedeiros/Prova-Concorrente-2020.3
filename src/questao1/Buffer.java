package questao1;

import java.util.concurrent.Semaphore;

/**
 * 1 - 5.8 The roller coaster problem
 * 2 - 5.7 River crossing problem
 * 3 - 7.6 Dining Hall problem
 *
 * the little book of semaphores
 */

public class Buffer {
    private final int capacidade;
    private int passageirosEmbarcando = 0;
    private int passageirosDesembarcando = 0;

    private final Semaphore mutexPassageiros = new Semaphore(1); // Contando número de passageiros que vão embarcar no carro.
    private final Semaphore mutexCarro = new Semaphore(1);

    private final Semaphore filaEmbarque = new Semaphore(0);
    private final Semaphore filaDesembarque = new Semaphore(0);

    private final Semaphore todosABordo = new Semaphore(0);
    private final Semaphore todosDesembarcados = new Semaphore(0);

    public Buffer(int capacidade) {
        this.capacidade = capacidade;
    }

    public void carregar() {
        System.out.println("Carro carregando...");
        this.filaEmbarque.release(this.capacidade);

        try {
            this.todosABordo.acquire();
        } catch (InterruptedException e) {}
    }

    public void correr() {
        System.out.println("Carro correndo...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }

    public void descarregar() {
        System.out.println("Carro descarregando...");
        this.filaDesembarque.release(this.capacidade);
        try {
            this.todosDesembarcados.acquire();
        } catch (InterruptedException e) {}
    }

    public void esperaParaEmbarcar() {
        try {
            this.filaEmbarque.acquire();
        } catch (InterruptedException e) {}
    }

    public void embarcar(int id) {
        System.out.println(id + " embarcou.");

        try {
            this.mutexPassageiros.acquire();
        } catch (InterruptedException e) {}

        this.passageirosEmbarcando++;

        if (this.passageirosEmbarcando == this.capacidade) {
            this.todosABordo.release();
            this.passageirosEmbarcando = 0;
        }

        this.mutexPassageiros.release();

        try {
            this.filaDesembarque.acquire();
        } catch (InterruptedException e) {}
    }

    public void desembarcar(int id) {
        System.out.println(id + " desembarcou.");

        try {
            this.mutexCarro.acquire();
        } catch (InterruptedException e) {}

        this.passageirosDesembarcando++;
        if (this.passageirosDesembarcando == this.capacidade) {
            this.todosDesembarcados.release();
            this.passageirosDesembarcando = 0;
        }

        this.mutexCarro.release();
    }
}
