package questao3;

import java.util.concurrent.Semaphore;

public class Buffer {
    private int alunosBebendo = 0;
    private int alunosRemediados = 0;
    private final Semaphore semaforoAlunos = new Semaphore(1); // Semáforo para proteger as threads de alunos que acessam o bar.
    private final Semaphore semaforoRemediados = new Semaphore(0); // Semáforo para alunos remediados esperarem outro aluno sentar para sairem.

    /**
     * Método chamado pelos alunos para pegar uma bebida no bar.
     */
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

    /**
     * Método chamado pelos alunos para beber a bebida escolhida. Se um estudante estiver remediado,
     * mas outro estudante estiver sendo deixado sozinho no bar, ele espera no semáforo de alunos
     * remediados.
     *
     * @param id id do aluno.
     */
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

    /**
     * Método chamado pelo aluno para sair do bar.
     *
     * @param id id do aluno.
     */
    public void sair(int id) {
        System.out.println(id + " saiu");
    }
}
