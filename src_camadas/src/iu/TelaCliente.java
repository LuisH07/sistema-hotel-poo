package iu;

import java.util.Scanner;

public class TelaCliente {

    private Scanner scanner;

    public TelaCliente() {
        scanner = new Scanner(System.in);
    }

    public void cancelarReserva() {
        // Função para cancelar de reservas.
        // A função listarReservas será chamada aqui,
        // exibindo apenas reservas canceláveis
    }

    public void consultarHistoricoDeReservas() {
        // Função para consultar o histórico de reservas.
        // A função listarReservas será chamada aqui.
    }

    public void reservarQuarto() {
        System.out.println(">>>> DIGITE O PERÍODO QUE DESEJA SE HOSPEDAR <<<<");
        // Aqui o usuário digita a data desejada,
        // Então a função consultarQuartos será iniciada com a data digitada
    }

    public void gerenciarReservas() {
        while (true) {
            System.out.println(">>>> MENU DE GERENCIAMENTO DE RESERVAS <<<<");
            System.out.println("1 - Cancelar reserva");
            System.out.println("2 - Consultar histórico de reservas");
            System.out.println("0 - Voltar");

            String operacao = scanner.nextLine();

            switch (operacao) {
                case "1":
                    cancelarReserva();
                    break;
                case "2":
                    consultarHistoricoDeReservas();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    public void iniciar() {
        while (true) {
            System.out.println(">>>> SELECIONE A OPERAÇÃO <<<<");
            System.out.println("1 - Reservar quarto");
            System.out.println("2 - Gerenciar reservas");
            System.out.println("0 - Voltar");

            String operacao = scanner.nextLine();

            switch (operacao) {
                case "1":
                    reservarQuarto();
                    break;
                case "2":
                    gerenciarReservas();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }
}