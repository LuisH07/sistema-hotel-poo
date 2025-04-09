package negocio;

import dados.quartos.RepositorioQuartos;
import dados.relatorios.RepositorioRelatorios;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Reserva;
import negocio.entidade.enums.Cargo;
import negocio.entidade.enums.CategoriaDoQuarto;
import negocio.entidade.enums.StatusDaReserva;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NegocioGerente implements IFluxoReservas, IFluxoRelatorio, IAutenticacao {

    private RepositorioReservas repositorioReservas;
    private RepositorioQuartos repositorioQuartos;
    private RepositorioRelatorios repositorioRelatorios;

    public NegocioGerente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos,
                          RepositorioRelatorios repositorioRelatorios) {
        this.repositorioReservas = repositorioReservas;
        this.repositorioQuartos = repositorioQuartos;
        this.repositorioRelatorios = repositorioRelatorios;
    }

    @Override
    public boolean autenticar(String email, String senha) {
        return email.equals(Cargo.GERENTE.getEmail()) && senha.equals(Cargo.GERENTE.getSenha());
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
    public void gerarRelatorio(YearMonth mesAno) throws ErroAoSalvarDadosException {
        GeradorRelatorioMensal geradorRelatorioMensal = new GeradorRelatorioMensal(mesAno, repositorioReservas, repositorioQuartos);

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("Relatório Mensal De Gerente\n");
        relatorio.append("Data do relatório: ").append(mesAno.format(DateTimeFormatter.ofPattern("MM/yyyy"))).append("\n\n");

        relatorio.append("Visão Geral das Reservas\n");
        relatorio.append("Quantidade total de reservas realizadas no mês\n");
        relatorio.append("Total: ").append(geradorRelatorioMensal.calcularQuantidadeDeReservasNoMes()).append("\n\n");

        relatorio.append("Detalhes por categoria de quarto\n");
        relatorio.append("Standard: ").append(geradorRelatorioMensal.calcularQuantidadeDeReservasPorCategoriaDeQuartoNoMes(CategoriaDoQuarto.STANDARD)).append("\n");
        relatorio.append("Suíte: ").append(geradorRelatorioMensal.calcularQuantidadeDeReservasPorCategoriaDeQuartoNoMes(CategoriaDoQuarto.SUITE)).append("\n");

        relatorio.append("Quantidade de reservas canceladas\n");
        relatorio.append("Total: ").append(geradorRelatorioMensal.calcularQuantidadeDeReservasPorStatusNoMes(StatusDaReserva.CANCELADA)).append(" cancelamentos\n\n");

        relatorio.append("Taxa de cancelamento\n");
        double taxaCancelamento = geradorRelatorioMensal.calcularTaxaDeCancelamento();
        relatorio.append("Taxa de Cancelamento: ").append(String.format("%.2f", taxaCancelamento)).append("%\n\n");

        relatorio.append("Taxa de ocupação\n");
        double taxaOcupacao = geradorRelatorioMensal.calcularTaxaDeOcupacao();
        relatorio.append("Taxa de Ocupação: ").append(String.format("%.2f", taxaOcupacao)).append("%\n\n");

        relatorio.append("Distribuição por tipo de quarto\n");
        relatorio.append("Standard: ").append(geradorRelatorioMensal.calcularTaxaDeOcupacaoPorCategoriaDeQuarto(CategoriaDoQuarto.STANDARD)).append("%\n");
        relatorio.append("Suíte: ").append(geradorRelatorioMensal.calcularTaxaDeOcupacaoPorCategoriaDeQuarto(CategoriaDoQuarto.SUITE)).append("%\n\n");

        double mediaPermanencia = geradorRelatorioMensal.calcularMediaDePermanenciaDosHospedesNoMes();
        relatorio.append("Média de permanência dos hóspedes\n");
        relatorio.append("Média de Permanência: ").append(String.format("%.2f", mediaPermanencia)).append(" dias\n\n");

        long quantidadeDeClientesDistintosNoMes = geradorRelatorioMensal.calcularClientesDistintosNoMes();
        relatorio.append("Clientes distintos no mês\n");
        relatorio.append("Clientes distintos: ").append(quantidadeDeClientesDistintosNoMes).append(" clientes\n\n");

        relatorio.append("Lista dos quartos mais utilizados: \n");
        List<QuartoAbstrato> quartosMaisReservados = geradorRelatorioMensal.listarQuartosMaisReservadosNoMes();
        for (int indice = 0; indice < quartosMaisReservados.size(); indice++) {
            QuartoAbstrato quarto = quartosMaisReservados.get(indice);
            relatorio.append(indice + 1).append(". ").append(quarto.toString()).append(repositorioReservas.listarReservasPorQuarto(quarto.getNumeroIdentificador()).size()).append("\n");
        }

        String nomeRelatorio = "RelatorioGerente_" + mesAno.format(DateTimeFormatter.ofPattern("MM_yyyy"));
        repositorioRelatorios.salvarRelatorio(relatorio.toString(), nomeRelatorio);
    }

}
