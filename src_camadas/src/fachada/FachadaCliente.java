package fachada;

import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.NegocioCliente;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;
import negocio.entidade.QuartoAbstrato;
import java.time.LocalDate;
import dados.cliente.RepositorioClientes;
import negocio.entidade.enums.StatusDaReserva;

import java.util.List;

public class FachadaCliente {

    private RepositorioClientes repositorioClientes;
    private NegocioCliente negocioCliente;

    public FachadaCliente(RepositorioClientes repositorioClientes, NegocioCliente negocioCliente) {
        this.repositorioClientes = repositorioClientes;
        this.negocioCliente = negocioCliente;
    }

    public void fazerReserva(String cpf, QuartoAbstrato quarto, LocalDate inicio, LocalDate fim) {
        Cliente cliente = repositorioClientes.buscarClientePorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com CPF " + cpf + " não encontrado.");
        }

        if (!cliente.isValido()) {
            throw new IllegalArgumentException("Cliente com dados inválidos.");
        }

        if (quarto == null || inicio == null || fim == null || !inicio.isBefore(fim)) {
            throw new IllegalArgumentException("Dados de reserva inválidos: verifique quarto e datas.");
        }

        Reserva reserva = new Reserva(cliente, quarto, inicio, fim);
        try {
            negocioCliente.fazerReserva(reserva);
        } catch (ReservaInvalidaException | ReservaJaCadastradaException | ErroAoSalvarDadosException |
                 ConflitoDeDatasException excecao) {
            throw new RuntimeException(excecao);
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return repositorioClientes.buscarClientePorCpf(cpf);
    }

    public List<Reserva> consultarHistorico(String cpf) {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com CPF " + cpf + " não encontrado.");
        }
        return negocioCliente.consultarHistorico(cliente);
    }

    public void cancelarReserva(String cpf, String idReserva) throws ReservaInvalidaException,
            ReservaNaoEncontradaException,
            ErroAoSalvarDadosException,
            IllegalArgumentException, ClienteInvalidoException {

        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            throw new ClienteInvalidoException("Cliente com CPF " + cpf + " não encontrado.");
        }

        Reserva reserva = negocioCliente.buscarReservaPorId(idReserva);

        if (!reserva.getCliente().getCpf().equals(cpf)) {
            throw new ReservaInvalidaException("Esta reserva não pertence ao cliente informado.");
        }

        negocioCliente.cancelarReserva(reserva);
    }
}