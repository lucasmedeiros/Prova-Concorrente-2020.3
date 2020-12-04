package questao3;

/**
 * Os alunos do período 2003.1 adoravam beber no bar de Auri. Esse era um bar bastante movimentado.
 * Então, a única mesa do bar era compartilhada por desconhecidos. As threads Aluno podem chamar as
 * funções/métodos bebe e depois disso, sai. Após chamar a função bebe e antes de chamar a função
 * sai,  o Aluno é considerado remediado. Existia uma única regra no bar de Auri: ninguém pode ser
 * deixando bebendo sozinho na mesa. Um aluno bebe sozinho se todo mundo que estava compartilhando a
 * mesa com ele, ou seja, todo os demais que chamaram a função bebe, chamam a função sai antes do
 * bebedor terminar de executar sua função bebe. Escreva o código das threads Aluno e qualquer outro
 * código utilitário que garanta a restrição de não deixar nínguem bebendo sozinho (e, obviamente,
 * apresente progresso, ou seja, que permita que os Alunos bebam).
 */

public class Main {

    private static int TOTAL_ALUNOS = 10;

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread[] alunos = new Thread[TOTAL_ALUNOS];

        for (int i = 0; i < TOTAL_ALUNOS; i++) {
            Aluno aluno = new Aluno(i, buffer);
            alunos[i] = new Thread(aluno);
        }

        for (int i = 0; i < TOTAL_ALUNOS; i++) {
            alunos[i].start();
        }
    }
}
