package questao1;

public class Carro implements Runnable {
    private Buffer buffer;

    public Carro(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for(int i = 1; i <= 2; i++) {
            this.buffer.carregar();
            this.buffer.correr();
            this.buffer.descarregar();
        }
    }
}
