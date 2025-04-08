package negocio;

import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.quarto.QuartoInvalidoException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;

import java.util.List;

public interface IFluxoReservas {

    void fazerReserva(Cliente cliente, String numeroQuarto, String dataInicioString, String dataFimString)
            throws ReservaInvalidaException, ClienteInvalidoException,
            QuartoInvalidoException, ConflitoDeDatasException, ErroAoSalvarDadosException;

    void cancelarReserva(String idReserva) throws ReservaNaoEncontradaException, ReservaInvalidaException, ErroAoSalvarDadosException;

    List<Reserva> consultarHistorico();

    void fazerCheckIn(String idReserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException, ErroAoSalvarDadosException;

    void fazerCheckOut(String idReserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException, ErroAoSalvarDadosException;

}