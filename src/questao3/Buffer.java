package questao3;

import java.util.concurrent.Semaphore;

public class Buffer {
    private int alunosBebendo = 0;
    private int alunosRemediados = 0;
    private final Semaphore semaforoAlunos = new Semaphore(1);
    private final Semaphore semaforoRemediados = new Semaphore(0);

    public void pegarBebida() {
        try {
            this.semaforoAlunos.acquire();
        } catch (InterruptedException e) {}

        this.alunosBebendo++;

        if (this.alunosBebendo == 2 && this.alunosRemediados == 1) {
            this.semaforoRemediados.release();
            this.alunosRemediados--;
        }

        this.semaforoAlunos.release();
    }

    public void beber(int id) {
        System.out.println(id + " bebeu");

        try {
            this.semaforoAlunos.acquire();
        } catch (InterruptedException e) {}

        this.alunosBebendo--;
        this.alunosRemediados++;

        if (this.alunosBebendo == 1 && this.alunosRemediados == 1) {
            this.semaforoAlunos.release();
            try {
                this.semaforoRemediados.acquire();
            } catch (InterruptedException e) {}
        } else if (this.alunosBebendo == 0 && this.alunosRemediados == 2) {
            this.semaforoRemediados.release();
            this.alunosRemediados -= 2;
            this.semaforoAlunos.release();
        } else {
            this.alunosRemediados--;
            this.semaforoAlunos.release();
        }
    }

    public void sair(int id) {
        System.out.println(id + " saiu");
    }
}
