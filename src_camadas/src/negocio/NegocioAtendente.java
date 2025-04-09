package negocio;

import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import negocio.entidade.Reserva;
import negocio.entidade.enums.*;
import excecoes.negocio.reserva.*;
import excecoes.dados.*;

import java.util.List;
import java.util.stream.Stream;

public class NegocioAtendente implements IFluxoReservas, IAutenticacao {

    RepositorioReservas repositorioReservas;
    RepositorioQuartos repositorioQuartos;

    public NegocioAtendente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos){
        this.repositorioReservas = repositorioReservas;
        this.repositorioQuartos = repositorioQuartos;
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
        return Stream.concat(repositorioReservas.listarReservasPorStatus(StatusDaReserva.ATIVA).stream(),
                repositorioReservas.listarReservasPorStatus(StatusDaReserva.EM_USO).stream()).toList();
    }

    public void fazerCheckIn(Reserva reserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException, ErroAoSalvarDadosException {
        if (!reserva.isValida()) {
            throw new ReservaInvalidaException("Informações de reserva inválidas!");
        }
        if (!repositorioReservas.existeReserva(reserva.getIdReserva())) {
            throw new ReservaNaoEncontradaException("Reserva não cadastrada!");
        }
        if (reserva.getStatus() != StatusDaReserva.ATIVA) {
            throw new ReservaInvalidaException("Check-in só pode ser feito para reservas ativas!");
        }

        reserva.setStatus(StatusDaReserva.EM_USO);
        repositorioReservas.atualizarReserva(reserva);
    }

    public void fazerCheckOut(Reserva reserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException, ErroAoSalvarDadosException {
        if (!reserva.isValida()) {
            throw new ReservaInvalidaException("Informações de reserva inválidas!");
        }
        if (!repositorioReservas.existeReserva(reserva.getIdReserva())) {
            throw new ReservaNaoEncontradaException("Reserva não cadastrada!");
        }
        if (reserva.getStatus() != StatusDaReserva.EM_USO) {
            throw new ReservaInvalidaException("Check-out só pode ser feito para reservas em uso!");
        }

        reserva.setStatus(StatusDaReserva.FINALIZADA);
        repositorioReservas.atualizarReserva(reserva);
    }

    @Override
    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        if (!email.equals(Cargo.ATENDENTE.getEmail()) || !senha.equals(Cargo.ATENDENTE.getSenha())) {
            throw new AutenticacaoFalhouException("Credenciais inválidas.");
        }
        return true;
    }

    public Reserva buscarReservaPorId(String idReserva) {
        return repositorioReservas.buscarReservaPorId(idReserva);
    }

}