package negocio.entidade;

import dados.reserva.RepositorioReservas;

import java.util.List;

public class Gerente extends FuncionarioAbstrato {

    public Gerente(String login, String senha) {
        super(login, senha);
    }

    @Override
    public List<Reserva> consultarHistorico(RepositorioReservas reservas) {
        return reservas.listarReservas();
    }

    public String gerarRelatorio(List<Reserva> reservas, List<QuartoAbstrato> quartos) {
        int totalReservas = reservas.size();
        int totalCanceladas =
                (int) reservas.stream().filter(reserva -> reserva.getStatus().equals("cancelada")).count();
        int totalOcupados = (int) reservas.stream().filter(reserva -> reserva.getStatus().equals("em uso")).count();
        double totalReceita = reservas.stream().filter(reserva -> reserva.getStatus().equals("concluída")).mapToDouble(Reserva::getValorTotal).sum();
        double porcentagemOcupacao = (totalOcupados / (double) quartos.size()) * 100;

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório de Reservas\n");
        relatorio.append("Total de Reservas: ").append(totalReservas).append("\n");
        relatorio.append("Total de Reservas Canceladas: ").append(totalCanceladas).append("\n");
        relatorio.append("Total de Reservas Ocupadas: ").append(totalOcupados).append("\n");
        relatorio.append("Total de Receita: R$ ").append(totalReceita).append("\n");
        relatorio.append("Porcentagem de Ocupação: ").append(String.format("%.2f", porcentagemOcupacao)).append("%\n");

        return relatorio.toString();
    }

}
