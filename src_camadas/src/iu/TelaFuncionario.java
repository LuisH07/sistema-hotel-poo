package iu;

import fachada.FachadaAtendente;
import fachada.FachadaAssistenteFinanceiro;
import fachada.FachadaGerente;

import java.util.Scanner;

public class TelaFuncionario {

    private Scanner scanner;
    private FachadaAtendente fachadaAtendente;

    public TelaFuncionario() {
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            System.out.println(">>>> QUAL O SEU CARGO? <<<<");
            System.out.println("1 - Atendente");
            System.out.println("2 - Assistente Financeiro");
            System.out.println("3 - Gerente");
            System.out.println("0 - voltar");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println(">>>> DIGITE SEU EMAIL <<<<");
                    String email = scanner.nextLine();
                    System.out.println(">>>> DIGITE SUA SENHA <<<<");
                    String senha = scanner.nextLine();
                    if (fachadaAtendente.autenticar(email, senha)){
                        telaAtendente.iniciar();
                    } else {
                        System.out.println("Login falhou");
                    }
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "0":
                    break;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }
}