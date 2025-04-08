package negocio;

import dados.cliente.RepositorioClientes;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;

import java.util.List;

public class NegocioCliente {

    private RepositorioClientes repositorioClientes;
    private RepositorioReservas repositorioReservas;

    public NegocioCliente() throws ErroAoCarregarDadosException {
        this.repositorioClientes = new RepositorioClientes();
        this.repositorioReservas = new RepositorioReservas();
    }

    public void fazerReserva(Reserva reserva) throws ErroAoSalvarDadosException {
        String cpf = reserva.getCliente().getCpf();
        if (!repositorioClientes.existeCliente(cpf)) {
            throw new IllegalArgumentException("Cliente com CPF " + cpf + " não está cadastrado.");
        }
        if (repositorioReservas.existeReserva(reserva)) {
            throw new IllegalArgumentException("Já existe uma reserva com este ID.");
        }
        repositorioReservas.adicionarReserva(reserva);
    }

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

    public List<Reserva> consultarHistorico(String cpfCliente) {
        return repositorioReservas.listarReservasPorCliente(cpfCliente);
    }

    public Cliente buscarCliente(String cpf) {
        return repositorioClientes.buscarClientePorCpf(cpf);
    }

}
