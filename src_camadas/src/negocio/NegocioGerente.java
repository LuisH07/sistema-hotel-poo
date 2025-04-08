package negocio;

import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;

public class NegocioGerente implements IFluxoReservas, IFluxoRelatorio {

    private RepositorioReservas repositorioReservas;

    public NegocioGerente(RepositorioReservas repositorioReservas) {
        this.repositorioReservas = repositorioReservas;
    }

    @Override
    public void cancelarReserva(String idReserva) throws ReservaInvalidaException, ReservaNaoEncontradaException, ErroAoSalvarDadosException {
        Reserva reservaCancelada = repositorioReservas.buscarReservaPorId(idReserva);
        if (reservaCancelada == null) {
            throw new ReservaNaoEncontradaException("Essa reserva não foi cadastrada!");
        }
        if (!reservaCancelada.isValida()) {
            throw new ReservaInvalidaException("Informações de reserva inválidas");
        }
        if (reservaCancelada.getStatus() != StatusDaReserva.ATIVA) {
            throw new ReservaInvalidaException("A reserva não está ativa e não pode ser cancelada!");
        }

        reservaCancelada.setStatus(StatusDaReserva.CANCELADA);
        repositorioReservas.atualizarReserva(reservaCancelada);
    }

    @Override
    public void consultarHistorico() {

    }

    @Override
    public void gerarRelatorio() {

    }


}