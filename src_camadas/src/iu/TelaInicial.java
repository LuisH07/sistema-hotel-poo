package iu;

import dados.cliente.RepositorioClientes;
import dados.quartos.RepositorioQuartos;
import dados.relatorios.RepositorioRelatorios;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;

import java.util.Scanner;

public class TelaInicial {
    private Scanner scanner;
    private TelaClienteAutenticacao telaClienteAutenticacao;
    private TelaFuncionario telaFuncionario;

    public TelaInicial(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos, RepositorioClientes repositorioClientes, RepositorioRelatorios repositorioRelatorios) throws ErroAoCarregarDadosException {
        scanner = new Scanner(System.in);
        telaClienteAutenticacao = new TelaClienteAutenticacao(repositorioClientes, repositorioReservas,
                repositorioQuartos);
        telaFuncionario = new TelaFuncionario(repositorioReservas, repositorioQuartos, repositorioRelatorios);
    }

    public void iniciar() {
        while (true) {
            // Limpando o terminal
            System.out.println("\033[H\033[2J");
            System.out.flush();

            System.out.println("*********************************************");
            System.out.println("*                                           *");
            System.out.println("*          Bem-vindo ao Sistema!            *");
            System.out.println("*                                           *");
            System.out.println("*********************************************");
            System.out.println();
            System.out.println(">>>> SELECIONE O MÓDULO <<<<");
            System.out.println("1 - Cliente");
            System.out.println("2 - Funcionário");
            System.out.println("0 - Sair");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("\nVocê escolheu o módulo Cliente.");
                    telaClienteAutenticacao.iniciar();
                    break;
                case "2":
                    System.out.println("\nVocê escolheu o módulo Funcionário.");
                    telaFuncionario.iniciar();
                    break;
                case "0":
                    System.out.println("\nSaindo do sistema...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n<Operação inválida. Tente novamente.>");
                    pausar();
            }
        }
    }

    private void pausar() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

}
