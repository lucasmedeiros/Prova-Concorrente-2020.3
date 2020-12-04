package questao2;

public class Aluno implements Runnable {
    private final int id;
    private final String escola;
    private final Buffer buffer;

    public Aluno(int id, String escola, Buffer buffer) {
        this.id = id;
        this.escola = escola;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        this.buffer.embarcar(id, escola);
    }
}
