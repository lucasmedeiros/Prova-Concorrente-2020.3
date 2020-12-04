package questao1;

public class Passageiro implements Runnable {
    private final int id;
    private final Buffer buffer;

    public Passageiro(int i, Buffer buffer) {
        this.id = i;
        this.buffer = buffer;
    }

    public void run() {
        buffer.embarcar(id);
        buffer.desembarcar(id);
    }
}
