package negocio;

import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Reserva;
import negocio.entidade.enums.CategoriaDoQuarto;
import negocio.entidade.enums.StatusDaReserva;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NegocioGerente implements IFluxoReservas, IFluxoRelatorio {

    private RepositorioReservas repositorioReservas;
    private RepositorioQuartos repositorioQuartos;

    public NegocioGerente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos) {
        this.repositorioReservas = repositorioReservas;
        this.repositorioQuartos = repositorioQuartos;
    }

    @Override
    public void cancelarReserva(Reserva reserva) throws ReservaInvalidaException, ReservaNaoEncontradaException,
            ErroAoSalvarDadosException {
        if (!reserva.isValida()) {
            throw new ReservaInvalidaException("Informações de reserva inválidas!");
        }
        if (!repositorioReservas.existeReserva(reserva.getIdReserva())) {
            throw new ReservaNaoEncontradaException("Reserva não cadastrada!");
        }
        if (reserva.getStatus() != StatusDaReserva.ATIVA) {
            throw new ReservaInvalidaException("A reserva não está ativa e não pode ser cancelada!");
        }

        reserva.setStatus(StatusDaReserva.CANCELADA);
        repositorioReservas.atualizarReserva(reserva);
    }

    @Override
    public List<Reserva> consultarHistorico() {
        return repositorioReservas.listarReservas();
    }

    @Override
    public void gerarRelatorio(LocalDate dataInicio, LocalDate dataFim, String caminhoArquivo) throws IOException {
        List<Reserva> reservasNoPeriodo = repositorioReservas.listarReservasPorPeriodo(dataInicio, dataFim);

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write("===== RELATÓRIO DE RESERVAS =====\n");
            writer.write("Período: " + dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " a " + dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\n");

            writer.write("=== ESTATÍSTICAS GERAIS ===\n");
            writer.write("Total de reservas: " + reservasNoPeriodo.size() + "\n");
            writer.write("Reservas ativas: " + contarReservasPorStatus(reservasNoPeriodo, StatusDaReserva.ATIVA) + "\n");
            writer.write("Reservas em uso: " + contarReservasPorStatus(reservasNoPeriodo, StatusDaReserva.EM_USO) + "\n");
            writer.write("Reservas finalizadas: " + contarReservasPorStatus(reservasNoPeriodo, StatusDaReserva.FINALIZADA) + "\n");
            writer.write("Reservas canceladas: " + contarReservasPorStatus(reservasNoPeriodo, StatusDaReserva.CANCELADA) + "\n");

            double taxaCancelamento = calcularTaxaCancelamento(reservasNoPeriodo);
            double taxaOcupacao = calcularTaxaOcupacao(reservasNoPeriodo);
            writer.write("\n=== TAXAS ===\n");
            writer.write(String.format("Taxa de cancelamento: %.2f%%\n", taxaCancelamento * 100));
            writer.write(String.format("Taxa de ocupação: %.2f%%\n", taxaOcupacao * 100));

            double receitaTotal = calcularReceitaTotal(reservasNoPeriodo);
            writer.write("\n=== RECEITA ===\n");
            writer.write(String.format("Receita total: R$ %.2f\n", receitaTotal));
        }
    }

    private List<Reserva> listarReservasPorCategoriaDoQuarto(CategoriaDoQuarto categoria) {
        List<Reserva> reservasPorCategoria;
        return reservasPorCategoria = repositorioReservas.listarReservas().stream().filter(reserva -> reserva.getQuarto().getCategoria().equals(categoria)).toList();
    }

    private long calcularQuantidadeDeReservasPorCateogiraDeQuarto(CategoriaDoQuarto categoria) {
        List<Reserva> reservasPorCategoria = listarReservasPorCategoriaDoQuarto(categoria);
        return reservasPorCategoria.size();
    }

    private long calcularQuantidadeTotalDeReservas() {
        List<Reserva> reservas = repositorioReservas.listarReservas();
        return reservas.size();
    }

    private double calcularTaxaDeCancelamento() {
        double canceladas = (double) calcularQuantidadeDeReservasPorStatus(StatusDaReserva.CANCELADA);
        double total = (double) calcularQuantidadeTotalDeReservas();

        if (total == 0) {
            return 0.0;
        }
        return (canceladas / total) * 100;
    }

    private long calcularQuantidadeDeReservasPorStatus(StatusDaReserva status) {
        List<Reserva> reservasPorStatus = repositorioReservas.listarReservasPorStatus(status);
        return reservasPorStatus.size();
    }

    private long calcularQuantidadeDeQuartos() {
        List<QuartoAbstrato> quartos = repositorioQuartos.listarQuartos();
        return quartos.size();
    }

    private double calcularTaxaDeOcupacao() {
        long quantidadeDeReservasOcupadas =
                calcularQuantidadeDeReservasPorStatus(StatusDaReserva.ATIVA) + calcularQuantidadeDeReservasPorStatus(StatusDaReserva.EM_USO);


    }

    private double calcularTaxaDeOcupacao(LocalDate data) {
        long quantidadeDeReservasOcupadas =
                calcularQuantidadeDeReservasPorStatus(StatusDaReserva.ATIVA) + calcularQuantidadeDeReservasPorStatus(StatusDaReserva.EM_USO);



        return taxa;
    }

    private int calcularDiasDoMes(int mes, int ano) {
        YearMonth anoMes = YearMonth.of(ano, mes);
        return anoMes.lengthOfMonth();
    }


}