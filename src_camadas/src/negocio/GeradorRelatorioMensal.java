package negocio;

import dados.quartos.RepositorioQuartos;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Reserva;
import negocio.entidade.enums.CategoriaDoQuarto;
import negocio.entidade.enums.StatusDaReserva;
import dados.reserva.RepositorioReservas;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável por gerar relatórios mensais sobre as reservas e ocupação do hotel.
 * Calcula diversas métricas como quantidade de reservas, taxa de cancelamento,
 * taxa de ocupação, média de permanência, quartos mais reservados e receita total.
 *
 * @author [Luis Henrique]
 * @author [Arthur]
 * @author [Maria Heloisa]
 */
public class GeradorRelatorioMensal {
    private YearMonth mesAno;
    private List<Reserva> reservasDoMes;
    private List<QuartoAbstrato> quartos;

    public GeradorRelatorioMensal(YearMonth mesAno, RepositorioReservas repositorioReservas,
                                  RepositorioQuartos repositorioQuartos) {
        this.mesAno = mesAno;
        this.reservasDoMes = listarReservasDoMes(repositorioReservas.listarReservas(), mesAno);
        this.quartos = repositorioQuartos.listarQuartos();
    }

    private List<Reserva> listarReservasDoMes(List<Reserva> reservas, YearMonth mesAno) {
        LocalDate primeiroDiaDoMes = mesAno.atDay(1);
        LocalDate ultimoDiaDoMes = mesAno.atEndOfMonth();

        return reservas.stream().filter(reserva -> reserva.getDataInicio().isBefore(ultimoDiaDoMes) && reserva.getDataFim().isAfter(primeiroDiaDoMes)).toList();
    }
    public long calcularQuantidadeDeReservasNoMes() {
        return reservasDoMes.size();
    }

    private List<Reserva> listarReservasPorCategoriaDoQuartoNoMes(CategoriaDoQuarto categoria) {
        return reservasDoMes.stream().filter(reserva -> reserva.getQuarto().getCategoria().equals(categoria)).toList();
    }
    public long calcularQuantidadeDeReservasPorCategoriaDeQuartoNoMes(CategoriaDoQuarto categoria) {
        List<Reserva> reservasPorCategoria = listarReservasPorCategoriaDoQuartoNoMes(categoria);
        return reservasPorCategoria.size();
    }

    public long calcularQuantidadeDeReservasPorStatusNoMes(StatusDaReserva status) {
        List<Reserva> reservasPorStatus = reservasDoMes.stream().filter(reserva -> reserva.getStatus().equals(status)).toList();
        return reservasPorStatus.size();
    }
    public double calcularTaxaDeCancelamento() {
        double canceladas = (double) calcularQuantidadeDeReservasPorStatusNoMes(StatusDaReserva.CANCELADA);
        double total = (double) calcularQuantidadeDeReservasNoMes();

        if (total == 0) {
            return 0.0;
        }
        return (canceladas / total) * 100;
    }

    private long calcularQuantidadeDeReservasOcupadasNoMes() {
        long diariasOcupadas = 0;

        for (int dia = 1; dia <= mesAno.lengthOfMonth(); dia++) {
            LocalDate data = mesAno.atDay(dia);

            long ocupadasNesseDia = reservasDoMes.stream()
                    .filter(reserva -> reserva.getStatus().equals(StatusDaReserva.ATIVA) || reserva.getStatus().equals(StatusDaReserva.EM_USO))
                    .filter(reserva -> !reserva.getDataInicio().isAfter(data) && !reserva.getDataFim().isBefore(data))
                    .count();

            diariasOcupadas += ocupadasNesseDia;
        }

        return diariasOcupadas;
    }
    public double calcularTaxaDeOcupacao() {
        long quantidadeDeReservasOcupadasNoMes = calcularQuantidadeDeReservasOcupadasNoMes();
        int diasDoMes = mesAno.lengthOfMonth();
        long quantidadeDeQuartos = quartos.size();

        double totalDisponivel = quantidadeDeQuartos * (double) diasDoMes;

        if (totalDisponivel == 0) {
            return 0.0;
        }

        return (quantidadeDeReservasOcupadasNoMes / totalDisponivel) * 100.0;
    }

    private long calcularQuantidadeDeQuartosPorCategoria(CategoriaDoQuarto categoria) {
        List<QuartoAbstrato> quartosPorCategoria = quartos.stream().filter(quarto -> quarto.getCategoria().equals(categoria)).toList();
        return quartosPorCategoria.size();
    }
    private long calcularQuantidadeDeReservasOcupadasPorCategoriaDeQuartoNoMes(CategoriaDoQuarto categoria) {
        long diariasOcupadas = 0;

        for (int dia = 1; dia <= mesAno.lengthOfMonth(); dia++) {
            LocalDate data = mesAno.atDay(dia);

            long ocupadasNesseDia = reservasDoMes.stream()
                    .filter(reserva -> reserva.getQuarto().getCategoria().equals(categoria))
                    .filter(reserva -> reserva.getStatus().equals(StatusDaReserva.ATIVA) || reserva.getStatus().equals(StatusDaReserva.EM_USO))
                    .filter(reserva -> !reserva.getDataInicio().isAfter(data) && !reserva.getDataFim().isBefore(data))
                    .count();

            diariasOcupadas += ocupadasNesseDia;
        }

        return diariasOcupadas;
    }
    public double calcularTaxaDeOcupacaoPorCategoriaDeQuarto(CategoriaDoQuarto categoria) {
        long quantidadeDeReservasOcupadasPorCategoriaDeQuartoNoMes =
                calcularQuantidadeDeReservasOcupadasPorCategoriaDeQuartoNoMes(categoria);
        int diasDoMes = mesAno.lengthOfMonth();
        long quantidadeDeQuartosPorCategoria = calcularQuantidadeDeQuartosPorCategoria(categoria);

        double totalDisponivel = quantidadeDeQuartosPorCategoria * (double) diasDoMes;

        if (totalDisponivel == 0.0) {
            return 0.0;
        }

        return (quantidadeDeReservasOcupadasPorCategoriaDeQuartoNoMes / totalDisponivel) * 100.0;
    }

    public double calcularMediaDePermanenciaDosHospedesNoMes() {
        long quantidadeDeDias = 0;
        for (Reserva reserva: reservasDoMes) {
            quantidadeDeDias += ChronoUnit.DAYS.between(reserva.getDataInicio(), reserva.getDataFim()) + 1;
        }
        long quantidadeDeReservasNoMes = reservasDoMes.size();

        if (quantidadeDeReservasNoMes == 0) {
            return 0.0;
        }

        return (double) quantidadeDeDias / quantidadeDeReservasNoMes;
    }

    public List<QuartoAbstrato> listarQuartosMaisReservadosNoMes() {
        Map<QuartoAbstrato, Long> contadorDeReservas = new HashMap<>();

        for (Reserva reserva : reservasDoMes) {
            if (reserva.getStatus() == StatusDaReserva.ATIVA || reserva.getStatus() == StatusDaReserva.EM_USO) {
                QuartoAbstrato quarto = reserva.getQuarto();
                contadorDeReservas.put(quarto, contadorDeReservas.getOrDefault(quarto, 0L) + 1);
            }
        }

        return contadorDeReservas.entrySet().stream()
                .sorted((entrada1, entrada2) -> Long.compare(entrada2.getValue(), entrada1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public double calcularReceitaTotalNoMes() {
        List<Reserva> reservasParaReceita = reservasDoMes.stream().filter(reserva -> reserva.getStatus().equals(StatusDaReserva.EM_USO) || reserva.getStatus().equals(StatusDaReserva.FINALIZADA)).toList();

        double receitaTotalNoMes = 0.0;
        for (Reserva reserva: reservasParaReceita) {
            receitaTotalNoMes += reserva.getValorTotal();
        }

        return receitaTotalNoMes;
    }

    public double calcularValorPerdidoPorCancelamentos() {
        List<Reserva> reservasCanceladas =
                reservasDoMes.stream().filter(reserva -> reserva.getStatus().equals(StatusDaReserva.CANCELADA)).toList();
        double valorPerdido = 0.0;
        for (Reserva reserva: reservasCanceladas) {
            valorPerdido += reserva.getValorTotal();
        }

        return valorPerdido;
    }

    public double calcularValorMedioPorDiaria() {
        List<Reserva> reservasValidas =
                reservasDoMes.stream().filter(reserva -> reserva.getStatus().equals(StatusDaReserva.EM_USO) || reserva.getStatus().equals(StatusDaReserva.FINALIZADA)).toList();

        double receitaTotalNoMes = calcularReceitaTotalNoMes();

        long totalDeDiariasVendidas = 0;
        for (Reserva reserva: reservasValidas) {
            totalDeDiariasVendidas += ChronoUnit.DAYS.between(reserva.getDataInicio(), reserva.getDataFim());
        }

        if (totalDeDiariasVendidas == 0) {
            return 0.0;
        }

        return receitaTotalNoMes / totalDeDiariasVendidas;
    }

    public double calcularValorMedioPorReserva() {
        if (reservasDoMes.size() == 0) {
            return 0.0;
        }
        return calcularReceitaTotalNoMes() / reservasDoMes.size();
    }

    public double calcularReceitaPorCategoriaDeQuarto(CategoriaDoQuarto categoria) {
        List<Reserva> reservasValidasPorCategoria = reservasDoMes.stream()
                .filter(reserva -> reserva.getQuarto().getCategoria().equals(categoria))
                .filter(reserva -> reserva.getStatus().equals(StatusDaReserva.EM_USO) || reserva.getStatus().equals(StatusDaReserva.FINALIZADA))
                .toList();

        double receitaPorCategoriaDeQuarto = 0.0;
        for (Reserva reserva: reservasValidasPorCategoria) {
            receitaPorCategoriaDeQuarto += reserva.getValorTotal();
        }

        return receitaPorCategoriaDeQuarto;
    }

    public double calcularValorMedioPorCategoriaDeQuarto(CategoriaDoQuarto categoria) {
        List<Reserva> reservasValidas = reservasDoMes.stream()
                .filter(reserva -> reserva.getQuarto().getCategoria() == categoria)
                .filter(reserva -> reserva.getStatus() == StatusDaReserva.EM_USO ||
                        reserva.getStatus() == StatusDaReserva.FINALIZADA)
                .toList();

        if (reservasValidas.isEmpty()) {
            return 0.0;
        }

        return calcularReceitaPorCategoriaDeQuarto(categoria) / reservasValidas.size();
    }

    public long calcularClientesDistintosNoMes() {
        return reservasDoMes.stream()
                .filter(reserva -> reserva.getStatus().equals(StatusDaReserva.ATIVA) || reserva.getStatus().equals(StatusDaReserva.FINALIZADA) || reserva.getStatus().equals(StatusDaReserva.EM_USO))  // Filtra as reservas válidas
                .map(reserva -> reserva.getCliente().getCpf())
                .distinct()
                .count();
    }

    public double calcularTicketMedioPorCliente() {
        double receitaTotalNoMes = calcularReceitaTotalNoMes();
        long quantidadeDeClientesDistintos = calcularClientesDistintosNoMes();

        if (quantidadeDeClientesDistintos == 0) {
            return 0.0;
        }

        return receitaTotalNoMes/quantidadeDeClientesDistintos;
    }

}
