package questao2;

public class Barrier {
    private final int n;

    public Barrier(int n) {
        this.n = n;
    }


    public synchronized void block() throws InterruptedException {
        wait();
    }

    public synchronized void release() {
        notify();
    }

    public synchronized void releaseAll() {
        notifyAll();
    }
}
