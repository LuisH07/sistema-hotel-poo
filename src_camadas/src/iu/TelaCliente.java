package iu;

import fachada.FachadaCliente;
import negocio.IFluxoReservas;
import negocio.NegocioCliente;
import negocio.entidade.Cliente;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;
import dados.quartos.RepositorioQuartos;
import excecoes.dados.ErroAoCarregarDadosException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TelaCliente {

    private Scanner scanner;
    private FachadaCliente fachadaCliente;
    private IFluxoReservas fluxoReservas;
    private RepositorioQuartos repositorioQuartos;

    public TelaCliente(FachadaCliente fachadaCliente, IFluxoReservas fluxoReservas, RepositorioQuartos repositorioQuartos) {
        scanner = new Scanner(System.in);
        this.fachadaCliente = fachadaCliente;
        this.fluxoReservas = fluxoReservas;
        this.repositorioQuartos = repositorioQuartos;
    }

    public void cancelarReserva() {
        System.out.println(">>>> CANCELAMENTO DE RESERVA <<<<");
        System.out.print("Digite seu CPF: ");
        String cpfCliente = scanner.nextLine();

        Cliente cliente = fachadaCliente.buscarClientePorCpf(cpfCliente);

        if (cliente != null) {
            if (fluxoReservas instanceof NegocioCliente) {
                List<Reserva> reservasAtivas = ((NegocioCliente) fluxoReservas).consultarHistorico(cliente).stream()
                        .filter(reserva -> reserva.getStatus() == StatusDaReserva.ATIVA)
                        .toList();

                if (reservasAtivas.isEmpty()) {
                    System.out.println("<Não há reservas ativas para este CPF.>");
                    return;
                }

                System.out.println(">>>> SUAS RESERVAS ATIVAS <<<<");
                for (int i = 0; i < reservasAtivas.size(); i++) {
                    System.out.println((i + 1) + " - ID: " + reservasAtivas.get(i).getIdReserva() +
                            ", Quarto: " + reservasAtivas.get(i).getQuarto().getNumeroIdentificador() +
                            ", De: " + reservasAtivas.get(i).getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                            ", Até: " + reservasAtivas.get(i).getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }

                System.out.print("Digite o número da reserva que deseja cancelar (ou 0 para voltar): ");
                String escolhaStr = scanner.nextLine();
                try {
                    int escolha = Integer.parseInt(escolhaStr);
                    if (escolha > 0 && escolha <= reservasAtivas.size()) {
                        Reserva reservaParaCancelar = reservasAtivas.get(escolha - 1);
                        System.out.print("Tem certeza que deseja cancelar a reserva com ID " + reservaParaCancelar.getIdReserva() + "? (S/N): ");
                        String confirmacao = scanner.nextLine().trim().toUpperCase();
                        if (confirmacao.equals("S")) {
                            try {
                                fachadaCliente.cancelarReserva(reservaParaCancelar);
                                System.out.println("<Reserva com ID " + reservaParaCancelar.getIdReserva() + " cancelada com sucesso.>");
                            } catch (Exception e) {
                                System.out.println("<Erro ao cancelar reserva: " + e.getMessage() + ">");
                            }
                        } else {
                            System.out.println("<Cancelamento da reserva abortado.>");
                        }
                    } else if (escolha != 0) {
                        System.out.println("<Opção inválida.>");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("<Entrada inválida. Digite o número da reserva.>");
                }
            } else {
                System.out.println("<Erro ao acessar o histórico de reservas.>");
            }
        } else {
            System.out.println("<Cliente com CPF " + cpfCliente + " não encontrado.>");
        }
    }

    public void consultarHistoricoDeReservas() {
        System.out.println(">>>> HISTÓRICO DE RESERVAS <<<<");
        System.out.print("Digite seu CPF: ");
        String cpfCliente = scanner.nextLine();

        Cliente cliente = fachadaCliente.buscarClientePorCpf(cpfCliente);

        if (cliente != null) {
            if (fluxoReservas instanceof NegocioCliente) {
                List<Reserva> historicoReservas = ((NegocioCliente) fluxoReservas).consultarHistorico(cliente);

                if (historicoReservas.isEmpty()) {
                    System.out.println("<Não há histórico de reservas para este CPF.>");
                    return;
                }

                System.out.println(">>>> SEU HISTÓRICO DE RESERVAS <<<<");
                for (Reserva reserva : historicoReservas) {
                    System.out.println("ID: " + reserva.getIdReserva() +
                            ", Quarto: " + reserva.getQuarto().getNumeroIdentificador() + " (" + reserva.getQuarto().getCategoria() + ")" +
                            ", De: " + reserva.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                            ", Até: " + reserva.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                            ", Status: " + reserva.getStatus());
                }
            } else {
                System.out.println("<Erro: Não foi possível acessar o histórico de reservas.>");
            }
        } else {
            System.out.println("<Cliente com CPF " + cpfCliente + " não encontrado.>");
        }
    }

    public void reservarQuarto() {
        System.out.println(">>>> DIGITE O PERÍODO QUE DESEJA SE HOSPEDAR <<<<");

        System.out.print("Data de Início (dd/MM/yyyy): ");
        String dataInicioStr = scanner.nextLine();
        LocalDate dataInicio = null;
        try {
            dataInicio = LocalDate.parse(dataInicioStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("<Formato de data inválido.>");
            return;
        }

        System.out.print("Data de Fim (dd/MM/yyyy): ");
        String dataFimStr = scanner.nextLine();
        LocalDate dataFim = null;
        try {
            dataFim = LocalDate.parse(dataFimStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("<Formato de data inválido.>");
            return;
        }

        if (dataInicio.isAfter(dataFim)) {
            System.out.println("<Data de início não pode ser depois da data de fim.>");
            return;
        }

        List<QuartoAbstrato> todosOsQuartos = null;
        try {
            todosOsQuartos = repositorioQuartos.listarQuartos();
        } catch (ErroAoCarregarDadosException e) {
            System.out.println("<Erro ao acessar a lista de quartos: " + e.getMessage() + ">");
            return;
        }

        List<Reserva> reservasAtivas;
        if (fluxoReservas instanceof NegocioCliente) {
            reservasAtivas = ((NegocioCliente) fluxoReservas).consultarHistorico(null).stream()
                    .filter(reserva -> reserva.getStatus() == StatusDaReserva.ATIVA || reserva.getStatus() == StatusDaReserva.EM_USO)
                    .collect(Collectors.toList());
        } else {
            reservasAtivas = null;
        }

        LocalDate finalDataInicio = dataInicio;
        LocalDate finalDataFim = dataFim;
        List<QuartoAbstrato> quartosDisponiveis = todosOsQuartos.stream()
                .filter(quarto -> {
                    if (reservasAtivas != null) {
                        return reservasAtivas.stream().noneMatch(reserva ->
                                reserva.getQuarto().getNumeroIdentificador().equals(quarto.getNumeroIdentificador()) &&
                                        reserva.getDataInicio().isBefore(finalDataFim) &&
                                        reserva.getDataFim().isAfter(finalDataInicio)
                        );
                    }
                    return true;
                })
                .collect(Collectors.toList());

        if (quartosDisponiveis != null && !quartosDisponiveis.isEmpty()) {
            System.out.println(">>>> QUARTOS DISPONÍVEIS PARA O PERÍODO DE " +
                    dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    " A " +
                    dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    " <<<<");
            for (int i = 0; i < quartosDisponiveis.size(); i++) {
                System.out.println((i + 1) + " - " + quartosDisponiveis.get(i));
            }

            System.out.print("Selecione o número do quarto desejado: ");
            String escolhaQuartoStr = scanner.nextLine();
            try {
                int escolhaQuarto = Integer.parseInt(escolhaQuartoStr);
                if (escolhaQuarto > 0 && escolhaQuarto <= quartosDisponiveis.size()) {
                    QuartoAbstrato quartoEscolhido = quartosDisponiveis.get(escolhaQuarto - 1);
                    System.out.print("Digite seu CPF para realizar a reserva: ");
                    String cpfCliente = scanner.nextLine();
                    Cliente cliente = fachadaCliente.buscarClientePorCpf(cpfCliente);
                    if (cliente != null) {
                        try {
                            fachadaCliente.fazerReserva(cpfCliente, quartoEscolhido, dataInicio, dataFim);
                            System.out.println("<Reserva realizada com sucesso!>");
                        } catch (IllegalArgumentException e) {
                            System.out.println("<Erro ao realizar reserva: " + e.getMessage() + ">");
                        } catch (RuntimeException e) {
                            System.out.println("<Erro interno ao realizar reserva: " + e.getMessage() + ">");
                        }
                    } else {
                        System.out.println("<Cliente com CPF " + cpfCliente + " não encontrado.>");
                    }
                } else {
                    System.out.println("<Seleção de quarto inválida.>");
                }
            } catch (NumberFormatException e) {
                System.out.println("<Entrada inválida. Digite o número do quarto.>");
            }
        } else {
            System.out.println("<Não há quartos disponíveis para o período selecionado.>");
        }
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
                    return;
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
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }
}