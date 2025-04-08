package negocio;

import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.quarto.QuartoInvalidoException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;

import java.util.List;

public interface IFluxoReservas {

    default void fazerReserva(Reserva reserva)
            throws ReservaInvalidaException, ConflitoDeDatasException, ErroAoSalvarDadosException, ReservaJaCadastradaException {}

    void cancelarReserva(Reserva reserva) throws ReservaNaoEncontradaException, ReservaInvalidaException, ErroAoSalvarDadosException;

    List<Reserva> consultarHistorico();

}