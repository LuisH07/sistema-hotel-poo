package negocio.entidade;

import dados.reserva.RepositorioReservas;
import java.util.ArrayList;
import java.util.List;

public class Atendente extends FuncionarioAbstrato {

    public Atendente(String login, String senha) {
        super(login, senha);
    }

    @Override
    public List<Reserva> consultarHistorico(RepositorioReservas reservas) {
        List<Reserva> reservasAtuais = new ArrayList<>();
        reservasAtuais.addAll(reservas.listarReservasPorStatus("Ativa"));
        reservasAtuais.addAll(reservas.listarReservasPorStatus("Em uso"));
        return reservasAtuais;
    }

}