package fachada;

import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.IFluxoReservas;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;
import negocio.entidade.QuartoAbstrato;
import java.time.LocalDate;
import dados.cliente.RepositorioClientes;
import java.util.List;

public class FachadaCliente {

    private RepositorioClientes repositorioClientes;
    private IFluxoReservas fluxoReservas;

    public FachadaCliente(RepositorioClientes repositorioClientes, IFluxoReservas fluxoReservas) {
        this.repositorioClientes = repositorioClientes;
        this.fluxoReservas = fluxoReservas;
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
            fluxoReservas.fazerReserva(reserva);
        } catch (ReservaInvalidaException e) {
            throw new RuntimeException(e);
        } catch (ConflitoDeDatasException e) {
            throw new RuntimeException(e);
        } catch (ErroAoSalvarDadosException e) {
            throw new RuntimeException(e);
        } catch (ReservaJaCadastradaException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return repositorioClientes.buscarClientePorCpf(cpf);
    }

    public List<Reserva> consultarHistorico(String cpfCliente) {
        Cliente cliente = buscarClientePorCpf(cpfCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com CPF " + cpfCliente + " não encontrado.");
        }
        return fluxoReservas.consultarHistorico(cliente);
    }

    public void cancelarReserva(Reserva reserva) throws ReservaNaoEncontradaException, ReservaInvalidaException, ErroAoSalvarDadosException {
        fluxoReservas.cancelarReserva(reserva);
    }
}