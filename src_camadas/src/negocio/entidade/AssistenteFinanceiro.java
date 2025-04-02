package negocio.entidade;

import dados.reserva.RepositorioReservas;

import java.util.List;

public class AssistenteFinanceiro extends FuncionarioAbstrato {

    public AssistenteFinanceiro(String login, String senha) {
        super(login, senha);
    }

    @Override
    public List<Reserva> consultarHistorico(RepositorioReservas reservas) {
        return List.of();
    }
}
