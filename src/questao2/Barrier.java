package questao2;

/**
 * Implementação simples de uma barreira para auxiliar na questão 02.
 */
public class Barrier {
    private final int total;
    private int threadsAwaiting;

    public Barrier(int total) {
        this.total = total;
        this.threadsAwaiting = total;
    }

    public synchronized void await() throws InterruptedException {
        this.threadsAwaiting--;

        if (this.threadsAwaiting > 0) {
            this.wait();
        } else {
            this.threadsAwaiting = this.total;
            notifyAll();
        }
    }
}
