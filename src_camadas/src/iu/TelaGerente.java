package iu;

import fachada.FachadaGerente;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import java.util.Scanner;

public class TelaGerente {
    private Scanner scanner;
    private FachadaGerente fachada;

    public TelaGerente() {
        this.scanner = new Scanner(System.in);
        this.fachada = new FachadaGerente();
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return fachada.autenticar(email, senha);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n>>>> MENU GERENTE <<<<");
            System.out.println("1 - Gerar Relatório Gerencial");
            System.out.println("2 - Consultar Todas as Reservas");
            System.out.println("3 - Cancelar Reserva");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    gerarRelatorio();
                    break;
                case "2":
                    consultarReservas();
                    break;
                case "3":
                    cancelarReserva();
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

    private void consultarReservas() {
        System.out.println("\n>>>> TODAS AS RESERVAS <<<<");
        try {
            var reservas = fachada.consultarHistorico();
            if (reservas.isEmpty()) {
                System.out.println("<Nenhuma reserva encontrada.>");
                return;
            }

            reservas.forEach(reserva -> {
                System.out.println("ID: " + reserva.getIdReserva() +
                        ", Quarto: " + reserva.getQuarto().getNumeroIdentificador() +
                        ", Status: " + reserva.getStatus());
            });
        } catch (Exception excecao) {
            System.out.println("<Erro ao consultar reservas: " + e.getMessage() + ">");
        }
    }

    private void cancelarReserva() {
        System.out.println("\n>>>> CANCELAR RESERVA <<<<");
        System.out.print("ID da Reserva: ");
        String idReserva = scanner.nextLine();

        try {
            fachada.cancelarReserva(idReserva);
            System.out.println("<Reserva cancelada com sucesso!>");
        } catch (Exception excecao) {
            System.out.println("<Erro ao cancelar reserva: " + e.getMessage() + ">");
        }
    }
}