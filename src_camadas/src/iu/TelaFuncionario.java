package iu;

import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import java.util.Scanner;

public class TelaFuncionario {
    private Scanner scanner;
    private TelaAtendente telaAtendente;
    private TelaAssistenteFinanceiro telaAssistenteFinanceiro;
    private TelaGerente telaGerente;

    public TelaFuncionario() {
        this.scanner = new Scanner(System.in);
        this.telaAtendente = new TelaAtendente();
        this.telaAssistenteFinanceiro = new TelaAssistenteFinanceiro();
        this.telaGerente = new TelaGerente();
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n>>>> QUAL O SEU CARGO? <<<<");
            System.out.println("1 - Atendente");
            System.out.println("2 - Assistente Financeiro");
            System.out.println("3 - Gerente");
            System.out.println("0 - Voltar");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    autenticarAtendente();
                    break;
                case "2":
                    autenticarAssistenteFinanceiro();
                    break;
                case "3":
                    autenticarGerente();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void autenticarAtendente() {
        System.out.println("\n>>>> LOGIN ATENDENTE <<<<");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            if (telaAtendente.autenticar(email, senha)) {
                telaAtendente.iniciar();
            } else {
                System.out.println("<Credenciais inválidas.>");
            }
        } catch (AutenticacaoFalhouException e) {
            System.out.println("<Erro: " + e.getMessage() + ">");
        }
    }

    private void autenticarAssistenteFinanceiro() {
        System.out.println("\n>>>> LOGIN ASSISTENTE FINANCEIRO <<<<");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            if (telaAssistenteFinanceiro.autenticar(email, senha)) {
                telaAssistenteFinanceiro.iniciar();
            } else {
                System.out.println("<Credenciais inválidas.>");
            }
        } catch (AutenticacaoFalhouException e) {
            System.out.println("<Erro: " + e.getMessage() + ">");
        }
    }

    private void autenticarGerente() {
        System.out.println("\n>>>> LOGIN GERENTE <<<<");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            if (telaGerente.autenticar(email, senha)) {
                telaGerente.iniciar();
            } else {
                System.out.println("<Credenciais inválidas.>");
            }
        } catch (AutenticacaoFalhouException e) {
            System.out.println("<Erro: " + e.getMessage() + ">");
        }
    }
}