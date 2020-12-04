package questao1;

/**
 * Q1 - Suponha que exista um conjunto de n threads passageiras e uma thread carro.
 * As threads passageiras, repetidamente, espera para pegar uma carona no carro, o
 * qual pode ser ocupado por C passageiros (C < n). O carro só pode partir quando
 * estiver completo. Escreva o código das abstrações passageiro e carro. Considere
 * os detalhes adicionais abaixo:
 *
 *
 * - As threads passageiras devem chamar os métodos/funções embarcar e desembarcar;
 * - A thread carro pode executar os métodos/funções carregar, correr e descarregar;
 * - Passageiros não podem embarcar até que o carro tenha chamado  executado carregar;
 * - O carro não poder correr até que todos os C passageiros tenham embarcado;
 * - Os passageiros não podem desembarcar até que o carro tenha executado descarregar.
 */

public class Main {
    private static int TOTAL_PASSAGEIROS = 8;

    public static void main(String[] args) {
        Buffer buffer = new Buffer(4);
        Passageiro passageiro;
        Thread[] t1 = new Thread[TOTAL_PASSAGEIROS];
        Carro carro = new Carro(buffer);
        Thread t2 = new Thread(carro, "Carro");

        for (int i = 0; i < TOTAL_PASSAGEIROS; i++) {
            passageiro = new Passageiro(i, buffer);
            t1[i] = new Thread(passageiro, "Passageiro " + i);
        }

        t2.start();

        for (int i = 0; i < TOTAL_PASSAGEIROS; i++) {
            t1[i].start();
        }

    }
}
