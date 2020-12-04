package questao3;

public class Aluno implements Runnable {
    private final int id;
    private final Buffer buffer;

    public Aluno(int id, Buffer buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        this.buffer.pegarBebida();
        this.buffer.beber(id);
        this.buffer.sair(id);
    }
}
