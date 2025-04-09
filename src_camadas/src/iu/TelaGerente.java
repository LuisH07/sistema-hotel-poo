package iu;

import dados.quartos.RepositorioQuartos;
import dados.relatorios.RepositorioRelatorios;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.DataInvalidaException;
import excecoes.negocio.autenticacao.EmailInvalidoException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import fachada.FachadaGerente;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import java.util.Scanner;

public class TelaGerente {
    private Scanner scanner;
    private FachadaGerente fachada;

    public TelaGerente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos,
                       RepositorioRelatorios repositorioRelatorios) {
        scanner = new Scanner(System.in);
        fachada = new FachadaGerente(repositorioReservas, repositorioQuartos, repositorioRelatorios);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException, EmailInvalidoException {
        return fachada.autenticar(email, senha);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n\n>>>> MENU GERENTE <<<<");
            System.out.println("1 - Gerar Relatório Gerencial");
            System.out.println("2 - Gerenciar Reservas");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    gerarRelatorio();
                    break;
                case "2":
                    gerenciarReservas();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void gerarRelatorio() {
        System.out.println("\n>>>> GERAR RELATÓRIO GERENCIAL <<<<");
        System.out.print("Mês/Ano (MM/AAAA): ");
        String mesAno = scanner.nextLine();

        try {
            fachada.gerarRelatorio(mesAno);
            System.out.println("<Relatório gerado com sucesso!>");
        } catch (DataInvalidaException | ErroAoSalvarDadosException excecao) {
            System.out.println("<Erro ao gerar relatório: " + excecao.getMessage() + ">");
        }
    }

    private void gerenciarReservas(){
        while (true) {
            System.out.println("\n\n>>>> GERENCIAMENTO DE RESERVAS <<<<");
            System.out.println("1 - Consultar Histórico de Todas as Reservas");
            System.out.println("2 - Cancelar Reserva");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    consultarReservas();
                    break;
                case "2":
                    cancelarReserva();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void consultarReservas() {
        System.out.println("\n>>>> HISTÓRICO DE RESERVAS <<<<\n");
        String historico = fachada.consultarHistorico();
        System.out.println(historico);
    }

    private void cancelarReserva() {
        System.out.println("\n>>>> CANCELAR RESERVA <<<<\n");

        String listaDeReservasAtivas = fachada.listarReservasAtivas();
        System.out.println(listaDeReservasAtivas);

        System.out.print("ID da Reserva: ");
        String idReserva = scanner.nextLine();

        try {
            fachada.cancelarReserva(idReserva);
            System.out.println("<Reserva cancelada com sucesso!>");
        } catch (ErroAoSalvarDadosException | ReservaNaoEncontradaException | ReservaInvalidaException excecao) {
            System.out.println("<Erro ao cancelar reserva: " + excecao.getMessage() + ">");
        }
    }

}
