package questao1;

import java.util.concurrent.Semaphore;

public class Buffer {
    private final int capacidade;
    private int passageirosEmbarcando = 0;
    private int passageirosDesembarcando = 0;

    private final Semaphore mutexPassageirosEmbarque = new Semaphore(1); // Contando número de passageiros que vão embarcar no carro.
    private final Semaphore mutexPassageirosDesembarque = new Semaphore(1); // Contando número de passageiros que vão desembarcar do carro.

    private final Semaphore filaEmbarque = new Semaphore(0); // Contando número de passageiros que estão esperando no embarque.
    private final Semaphore filaDesembarque = new Semaphore(0); // Contando número de passageiros que estão esperando o desembarque.

    private final Semaphore todosABordo = new Semaphore(0); // Sinalizador que todos embarcaram.
    private final Semaphore todosDesembarcados = new Semaphore(0); // Sinalizador que todos desembarcaram.

    public Buffer(int capacidade) {
        this.capacidade = capacidade;
    }

    /**
     * Método chamado pelo carro para sinalizar que está pronto para carregar. Ao carregar, o
     * carro libera a fila de embarque.
     */
    public void carregar() {
        System.out.println("Carro carregando...");
        this.filaEmbarque.release(this.capacidade);

        try {
            this.todosABordo.acquire();
        } catch (InterruptedException e) {}
    }

    /**
     * Método chamado pelo carro para correr. Adicionado um delay de 2s para simular uma "corrida".
     */
    public void correr() {
        System.out.println("Carro correndo...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }

    /**
     * Método chamado pelo carro pawra sinalizar que está pronto para descarregar. Ao descarregar, o
     * carro libera a fila de desembarque.
     */
    public void descarregar() {
        System.out.println("Carro descarregando...");
        this.filaDesembarque.release(this.capacidade);
        try {
            this.todosDesembarcados.acquire();
        } catch (InterruptedException e) {}
    }

    /**
     * Método chamado pelo passageiro para aguardar o carro chegar e ficar pronto para carregar.
     */
    public void esperaParaEmbarcar() {
        try {
            this.filaEmbarque.acquire();
        } catch (InterruptedException e) {}
    }

    /**
     * Método chamado pelo passageiro para embarcar no carro. O último passageiro a embarcar,
     * vai sinalizar para o carro que todos estão a bordo e resetar a contagem de passageiros
     * embarcando.
     *
     * @param id id do passageiro.
     */
    public void embarcar(int id) {
        System.out.println(id + " embarcou.");

        try {
            this.mutexPassageirosEmbarque.acquire();
        } catch (InterruptedException e) {}

        this.passageirosEmbarcando++;

        if (this.passageirosEmbarcando == this.capacidade) {
            this.todosABordo.release();
            this.passageirosEmbarcando = 0;
        }

        this.mutexPassageirosEmbarque.release();

        try {
            this.filaDesembarque.acquire();
        } catch (InterruptedException e) {}
    }

    /**
     * Método chamado pelo passageiro para desembarcar do carro. O último passageiro a desembarcar,
     * vai sinalizar para o carro que todos estão deembarcados, e resetar a contagen de passageiros
     * desembarcando.
     *
     * @param id id do passageiro.
     */
    public void desembarcar(int id) {
        System.out.println(id + " desembarcou.");

        try {
            this.mutexPassageirosDesembarque.acquire();
        } catch (InterruptedException e) {}

        this.passageirosDesembarcando++;
        if (this.passageirosDesembarcando == this.capacidade) {
            this.todosDesembarcados.release();
            this.passageirosDesembarcando = 0;
        }

        this.mutexPassageirosDesembarque.release();
    }
}
