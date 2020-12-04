package questao2;

/**
 * Na beira do açude de bodocongó existe um barco de um remo só. Tanto
 * os alunos de computação da UFCG quanto os alunos de psicologia da UEPB
 * usam o barco para cruzar o açude. O barco só pode ser usado por exatamente
 * quatro alunos; nem mais, nem menos. Para garantir a segurança dos discentes,
 * é proibido que um aluno da UFCG seja colocado no barco com três alunos da
 * UEPB. Também é proibido colocar um aluno da UEPB com três alunos da UFCG.
 * Os alunos são threads. Para embarcar, cada thread chama a função/método
 * embarcar. Você precisa garantir que toda as quatro threads de um carregamento
 * de alunos chamam a função embarcar antes de qualquer outra threads, de um próximo
 * carregamento. Após todas as quatro threads de um carregamento tenham chamado a função
 * embarcar, uma única thread dessas quatro (não importa qual delas seja) deve executar
 * a função rema, indicando que essa thread será responsável por assumir o papel de remadora.
 * Assuma que depois que o barco chega ao destino, magicamente ele reaparece na origem
 * (ou seja, assuma que estamos interesados no tráfego do barco em somente um sentido).
 * Implemente as threads AlunoUFCG e AlunoUEPB bem como qualquer código utilitário para
 * compor sua solução.
 */

public class Main {
}
