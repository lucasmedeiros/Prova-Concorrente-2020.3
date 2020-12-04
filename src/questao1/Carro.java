package questao1;

public class Carro implements Runnable {
    private Buffer buffer;

    public Carro(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while(true) {
            this.buffer.carregar();
            this.buffer.correr();
            this.buffer.descarregar();
        }
    }
}
