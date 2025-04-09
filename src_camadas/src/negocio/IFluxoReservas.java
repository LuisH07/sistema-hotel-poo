package negocio;

import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;

import java.util.List;

public interface IFluxoReservas {

    void cancelarReserva(Reserva reserva) throws ReservaNaoEncontradaException, ReservaInvalidaException, ErroAoSalvarDadosException;

    default List<Reserva> consultarHistorico() {
        return null;
    }
    default List<Reserva> consultarHistorico(Cliente cliente) {
        return null;
    }

}
