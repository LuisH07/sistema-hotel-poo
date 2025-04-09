package iu;

import java.util.Scanner;

public class TelaInicial {
    private Scanner scanner;
    private TelaCliente telaCliente;
    private TelaFuncionario telaFuncionario;

    public TelaInicial() {
        scanner = new Scanner(System.in);
        this.telaCliente = new TelaCliente();
        this.telaFuncionario = new TelaFuncionario();
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n>>>> SELECIONE O MÓDULO <<<<");
            System.out.println("1 - Cliente");
            System.out.println("2 - Funcionário");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    telaCliente.iniciar();
                    break;
                case "2":
                    telaFuncionario.iniciar();
                    break;
                case "0":
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }
}