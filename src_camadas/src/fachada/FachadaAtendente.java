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

    public FachadaAtendente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos) {
        negocioAtendente = new NegocioAtendente(repositorioReservas, repositorioQuartos);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return negocioAtendente.autenticar(email, senha);
    }

    public String listarReservasAtivas() {
        List<Reserva> reservasAtivas = negocioAtendente.listarReservasAtivas();

        StringBuilder listaDeReservasAtivas = new StringBuilder();
        if (reservasAtivas != null && !reservasAtivas.isEmpty()){
            listaDeReservasAtivas.append("Reservas ativas: \n\n");
            for (Reserva reserva : reservasAtivas) {
                listaDeReservasAtivas.append(reserva.toString());
            }
        } else{
            listaDeReservasAtivas.append("Nenhuma reserva ativa encontrada!");
        }
        return listaDeReservasAtivas.toString();
    }

    public void cancelarReserva(String idReserva) throws ReservaInvalidaException,
            ReservaNaoEncontradaException,
            ErroAoSalvarDadosException {
        Reserva reserva = negocioAtendente.buscarReservaPorId(idReserva);
        negocioAtendente.cancelarReserva(reserva);
    }

    public String consultarHistorico() {
        List<Reserva> historicoReservas = negocioAtendente.consultarHistorico();
        StringBuilder historicoFormatado = new StringBuilder();
        if (historicoReservas != null && !historicoReservas.isEmpty()){
            historicoFormatado.append("Histórico de Reservas: \n\n");
            for (Reserva reserva : historicoReservas) {
                historicoFormatado.append(reserva.toString());
            }
        } else{
            historicoFormatado.append("Nenhum histórico de reservas encontrado!");
        }
        return historicoFormatado.toString();
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

    public String listarReservasEmUso() {
        List<Reserva> reservasAtivas = negocioAtendente.listarReservasEmUso();

        StringBuilder listaDeReservasAtivas = new StringBuilder();
        if (reservasAtivas != null && !reservasAtivas.isEmpty()){
            listaDeReservasAtivas.append("Reservas Em Uso: \n\n");
            for (Reserva reserva : reservasAtivas) {
                listaDeReservasAtivas.append(reserva.toString());
            }
        } else{
            listaDeReservasAtivas.append("Nenhuma reserva em uso encontrada!");
        }
        return listaDeReservasAtivas.toString();
    }

}
