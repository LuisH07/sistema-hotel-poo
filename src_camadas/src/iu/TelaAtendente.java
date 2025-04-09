package iu;

import fachada.FachadaAtendente;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import java.util.Scanner;

public class TelaAtendente {
    private Scanner scanner;
    private FachadaAtendente fachada;

    public TelaAtendente() {
        this.scanner = new Scanner(System.in);
        this.fachada = new FachadaAtendente();
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return fachada.autenticar(email, senha);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n>>>> MENU ATENDENTE <<<<");
            System.out.println("1 - Realizar Check-in");
            System.out.println("2 - Realizar Check-out");
            System.out.println("3 - Cancelar Reserva");
            System.out.println("4 - Consultar Reservas Ativas");
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
                    cancelarReserva();
                    break;
                case "4":
                    consultarReservasAtivas();
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
        System.out.print("ID da Reserva: ");
        String idReserva = scanner.nextLine();

        try {
            fachada.fazerCheckIn(idReserva);
            System.out.println("<Check-in realizado com sucesso!>");
        } catch (Exception excecao) {
            System.out.println("<Erro ao realizar check-in: " + e.getMessage() + ">");
        }
    }

    private void realizarCheckOut() {
        System.out.println("\n>>>> CHECK-OUT <<<<");
        System.out.print("ID da Reserva: ");
        String idReserva = scanner.nextLine();

        try {
            fachada.fazerCheckOut(idReserva);
            System.out.println("<Check-out realizado com sucesso!>");
        } catch (Exception excecao) {
            System.out.println("<Erro ao realizar check-out: " + e.getMessage() + ">");
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

    private void consultarReservasAtivas() {
        System.out.println("\n>>>> RESERVAS ATIVAS <<<<");
        try {
            var reservas = fachada.consultarHistorico();
            if (reservas.isEmpty()) {
                System.out.println("<Nenhuma reserva ativa encontrada.>");
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
}