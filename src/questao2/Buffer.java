package questao2;

import java.util.concurrent.Semaphore;

public class Buffer {
    private final int CAPACIDADE = 4;
    private final int ALUNOS_MESMA_ESCOLA = 2;

    private int qtdAlunosUFCG = 0;
    private int qtdAlunosUEPB = 0;
    private boolean isRemador = false; // Indicador de qual thread deve chamar o método rema.

    private final Barrier barrier = new Barrier(this.CAPACIDADE);
    private final Semaphore semaforoAlunos = new Semaphore(1); // Semáforo para proteger a contagem de alunos da UFCG e da UEPB.
    private final Semaphore filaAlunosUFCG = new Semaphore(0); // Semáforo para controlar o número de alunos da UFCG que embarcam.
    private final Semaphore filaAlunosUEPB = new Semaphore(0); // Semáforo para controlar o número de alunos da UEPB que embarcam.

    /**
     * Método chamado por um aluno da UFCG para embarcar no barco.
     */
    private void embarcaUFCG() {
        try {
            this.semaforoAlunos.acquire();
        } catch (InterruptedException e) {}

        this.qtdAlunosUFCG++;
        if (this.qtdAlunosUFCG == this.CAPACIDADE) {
            this.filaAlunosUFCG.release(this.CAPACIDADE);
            this.qtdAlunosUFCG = 0;
            this.isRemador = true;
        } else if (this.qtdAlunosUFCG == ALUNOS_MESMA_ESCOLA && this.qtdAlunosUEPB >= ALUNOS_MESMA_ESCOLA) {
            this.filaAlunosUFCG.release(ALUNOS_MESMA_ESCOLA);
            this.filaAlunosUEPB.release(ALUNOS_MESMA_ESCOLA);
            this.qtdAlunosUEPB -= 2;
            this.qtdAlunosUFCG = 0;
            this.isRemador = true;
        } else {
            this.semaforoAlunos.release();
        }

        try {
            this.filaAlunosUFCG.acquire();
        } catch (InterruptedException e) { }
    }

    /**
     * Método chamado por um aluno da UEPB para embarcar no barco.
     */
    private void embarcaUEPB() {
        try {
            this.semaforoAlunos.acquire();
        } catch (InterruptedException e) {}

        this.qtdAlunosUEPB++;
        if (this.qtdAlunosUEPB == this.CAPACIDADE) {
            this.filaAlunosUEPB.release(this.CAPACIDADE);
            this.qtdAlunosUEPB = 0;
            this.isRemador = true;
        } else if (this.qtdAlunosUEPB == ALUNOS_MESMA_ESCOLA && this.qtdAlunosUFCG >= ALUNOS_MESMA_ESCOLA) {
            this.filaAlunosUEPB.release(ALUNOS_MESMA_ESCOLA);
            this.filaAlunosUFCG.release(ALUNOS_MESMA_ESCOLA);
            this.qtdAlunosUFCG -= 2;
            this.qtdAlunosUEPB = 0;
            this.isRemador = true;
        } else {
            this.semaforoAlunos.release();
        }

        try {
            this.filaAlunosUEPB.acquire();
        } catch (InterruptedException e) {}
    }

    /**
     * Método chamado pelos alunos para embarcar no barco.
     *
     * @param id id do aluno.
     * @param escola escola a qual o aluno pertence.
     */
    public void embarcar(int id, String escola) {
        if (escola == "UFCG") {
            this.embarcaUFCG();
        } else {
            this.embarcaUEPB();
        }

        System.out.println(id + " (" + escola + ") embarcou.");

        try {
            this.barrier.await();
        } catch (InterruptedException ignored) {}

        if (this.isRemador) {
            this.rema(id, escola);
        }
    }

    /**
     * Método chamado por um aluno que embarcou, para que reme o barco.
     *
     * @param id id do aluno.
     * @param escola escola a qual o aluno pertence.
     */
    public void rema(int id, String escola) {
        System.out.println(id + " (" + escola + ") remou.");
        this.semaforoAlunos.release();
    }
}
