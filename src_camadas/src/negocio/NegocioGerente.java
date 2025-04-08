package negocio;

import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;

import java.util.List;

public class NegocioGerente implements IFluxoReservas, IFluxoRelatorio {

    private RepositorioReservas repositorioReservas;

    public NegocioGerente(RepositorioReservas repositorioReservas) {
        this.repositorioReservas = repositorioReservas;
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
    public void gerarRelatorio() {

    }

}