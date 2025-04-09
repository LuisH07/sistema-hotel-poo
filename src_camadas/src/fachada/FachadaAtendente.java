package fachada;

import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.*;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.reserva.*;
import negocio.NegocioAtendente;
import negocio.entidade.Reserva;
import java.util.List;


public class FachadaAtendente {
    private NegocioAtendente negocioAtendente;

    public FachadaAtendente(NegocioAtendente negocioAtendente) {
        this.negocioAtendente = negocioAtendente;
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return negocioAtendente.autenticar(email, senha);
    }

    public void cancelarReserva(String idReserva) throws ReservaInvalidaException,
            ReservaNaoEncontradaException,
            ErroAoSalvarDadosException {
        Reserva reserva = negocioAtendente.buscarReservaPorId(idReserva);
        negocioAtendente.cancelarReserva(reserva);
    }

    public List<Reserva> consultarHistorico() {
        return negocioAtendente.consultarHistorico();
    }

    public void fazerCheckIn(String idReserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException,
            ErroAoSalvarDadosException {
        Reserva reserva = negocioAtendente.buscarReservaPorId(idReserva);
        negocioAtendente.fazerCheckIn(reserva);
    }

    public void fazerCheckOut(String idReserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException,
            ErroAoSalvarDadosException {
        Reserva reserva = negocioAtendente.buscarReservaPorId(idReserva);
        negocioAtendente.fazerCheckOut(reserva);
    }
}
