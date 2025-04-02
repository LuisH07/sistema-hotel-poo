package iu;

import java.util.Scanner;

public class TelaInicial {

    private Scanner scanner;
    private TelaCliente telaCliente;
    private TelaFuncionario telaFuncionario;

    public TelaInicial() {
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            System.out.println(">>>> SELECIONE O MÓDULO QUE DESEJA INICIAR <<<<");
            System.out.println("1 - Cliente");
            System.out.println("2 - Funcionário");
            System.out.println("0 - Sair");

            String operacao = scanner.nextLine();

            switch (operacao) {
                case "1":
                    telaCliente.iniciar();
                    break;
                case "2":
                    telaFuncionario.iniciar();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }
}