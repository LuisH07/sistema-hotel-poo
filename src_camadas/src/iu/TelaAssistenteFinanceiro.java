package iu;

import fachada.FachadaAssistenteFinanceiro;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import java.util.Scanner;

public class TelaAssistenteFinanceiro {
    private Scanner scanner;
    private FachadaAssistenteFinanceiro fachada;

    public TelaAssistenteFinanceiro() {
        this.scanner = new Scanner(System.in);
        this.fachada = new FachadaAssistenteFinanceiro();
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return fachada.autenticar(email, senha);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n>>>> MENU ASSISTENTE FINANCEIRO <<<<");
            System.out.println("1 - Gerar Relatório Financeiro");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    gerarRelatorio();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void gerarRelatorio() {
        System.out.println("\n>>>> GERAR RELATÓRIO FINANCEIRO <<<<");
        System.out.print("Mês (MM): ");
        String mes = scanner.nextLine();
        System.out.print("Ano (AAAA): ");
        String ano = scanner.nextLine();

        try {
            fachada.gerarRelatorio(mes, ano);
            System.out.println("<Relatório gerado com sucesso!>");
        } catch (Exception excecao) {
            System.out.println("<Erro ao gerar relatório: " + e.getMessage() + ">");
        }
    }
}