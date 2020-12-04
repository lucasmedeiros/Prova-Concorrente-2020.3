package questao2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

public class Buffer {
    private int qtdAlunosUFCG = 0;
    private int qtdAlunosUEPB = 0;
    private boolean isRemador = false;
    private final int CAPACIDADE = 4;
    private final int ALUNOS_MESMA_ESCOLA = 2;

    private final Barrier barrier = new Barrier(this.CAPACIDADE);
    private final Semaphore semaforoAlunos = new Semaphore(1);
    private final Semaphore filaAlunosUFCG = new Semaphore(0);
    private final Semaphore filaAlunosUEPB = new Semaphore(0);

    private void embarcaUFCG() {
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

    private void embarcaUEPB() {
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

    public void embarcar(int id, String escola) {
        this.isRemador = false;

        try {
            this.semaforoAlunos.acquire();
        } catch (InterruptedException e) {}

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

    public void rema(int id, String escola) {
        System.out.println(id + " (" + escola + ") remou.");
        this.semaforoAlunos.release();
    }
}
