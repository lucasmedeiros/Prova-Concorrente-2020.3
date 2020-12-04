package questao2;

public class Aluno implements Runnable {
    private final int id;
    private final String escola;
    private Buffer buffer;

    public Aluno(int id, String escola, Buffer buffer) {
        this.id = id;
        this.escola = escola;
        this.buffer = buffer;
    }

    public int getId() {
        return this.id;
    }

    public String getEscola() {
        return this.escola;
    }

    @Override
    public void run() {
        // Thread do Aluno
    }
}
