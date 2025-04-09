package iu;

import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.EmailInvalidoException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import fachada.FachadaAtendente;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import java.util.Scanner;

public class TelaAtendente {
    private Scanner scanner;
    private FachadaAtendente fachada;

    public TelaAtendente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos) {
        scanner = new Scanner(System.in);
        fachada = new FachadaAtendente(repositorioReservas, repositorioQuartos);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException, EmailInvalidoException {
        return fachada.autenticar(email, senha);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n\n>>>> MENU ATENDENTE <<<<");
            System.out.println("1 - Realizar Check-in");
            System.out.println("2 - Realizar Check-out");
            System.out.println("3 - Gerenciar Reservas");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    realizarCheckIn();
                    break;
                case "2":
                    realizarCheckOut();
                    break;
                case "3":
                    gerenciarReservas();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void realizarCheckIn() {
        System.out.println("\n>>>> CHECK-IN <<<<");

        String listaDeReservasAtivas = fachada.listarReservasAtivas();
        System.out.println(listaDeReservasAtivas);

        System.out.print("ID da Reserva: ");
        String idReserva = scanner.nextLine();

        try {
            fachada.fazerCheckIn(idReserva);
            System.out.println("<Check-in realizado com sucesso!>");
        } catch (ErroAoSalvarDadosException | ReservaNaoEncontradaException | ReservaInvalidaException excecao) {
            System.out.println("<Erro ao realizar check-in: " + excecao.getMessage() + ">");
        }
    }

    private void realizarCheckOut() {
        System.out.println("\n>>>> CHECK-OUT <<<<");
        System.out.print("ID da Reserva: ");
        String idReserva = scanner.nextLine();

        String listaDeReservasAtivas = fachada.listarReservasEmUso();
        System.out.println(listaDeReservasAtivas);

        try {
            fachada.fazerCheckOut(idReserva);
            System.out.println("<Check-out realizado com sucesso!>");
        } catch (ErroAoSalvarDadosException | ReservaNaoEncontradaException | ReservaInvalidaException excecao) {
            System.out.println("<Erro ao realizar check-out: " + excecao.getMessage() + ">");
        }
    }

    private void gerenciarReservas() {
        while (true) {
            System.out.println("\n\n>>>> GERENCIAMENTO DE RESERVAS <<<<");
            System.out.println("1 - Consultar Reservas Ativas ou Em uso");
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
        System.out.println("\n>>>> HISTÓRICO DE RESERVAS ATIVAS OU EM USO <<<<\n");
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
