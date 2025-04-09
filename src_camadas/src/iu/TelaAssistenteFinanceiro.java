package iu;

import dados.quartos.RepositorioQuartos;
import dados.relatorios.RepositorioRelatorios;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.DataInvalidaException;
import excecoes.negocio.autenticacao.EmailInvalidoException;
import fachada.FachadaAssistenteFinanceiro;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import java.util.Scanner;

public class TelaAssistenteFinanceiro {
    private Scanner scanner;
    private FachadaAssistenteFinanceiro fachada;

    public TelaAssistenteFinanceiro(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos,
                                    RepositorioRelatorios repositorioRelatorios) {
        scanner = new Scanner(System.in);
        fachada = new FachadaAssistenteFinanceiro(repositorioReservas, repositorioQuartos, repositorioRelatorios);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException, EmailInvalidoException {
        return fachada.autenticar(email, senha);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n\n>>>> MENU ASSISTENTE FINANCEIRO <<<<");
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
        System.out.print("Mês/Ano (MM/AAAA): ");
        String mesAno = scanner.nextLine();

        try {
            fachada.gerarRelatorio(mesAno);
            System.out.println("<Relatório gerado com sucesso!>");
        } catch (DataInvalidaException | ErroAoSalvarDadosException excecao) {
            System.out.println("<Erro ao gerar relatório: " + excecao.getMessage() + ">");
        }
    }

}
