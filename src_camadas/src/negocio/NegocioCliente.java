package negocio;

import dados.cliente.RepositorioClientes;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.cliente.ClienteJaExisteException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;

import java.util.List;
import java.util.stream.Stream;

public class NegocioCliente implements IFluxoReservas {

    private RepositorioClientes repositorioClientes;
    private RepositorioReservas repositorioReservas;

    public NegocioCliente() throws ErroAoCarregarDadosException {
        this.repositorioClientes = new RepositorioClientes();
        this.repositorioReservas = new RepositorioReservas();
    }

    @Override
    public void fazerReserva(Reserva novaReserva) throws ErroAoSalvarDadosException, ReservaInvalidaException, ReservaJaCadastradaException, ConflitoDeDatasException {
        if (!novaReserva.isValida()) {
            throw new ReservaInvalidaException("Informações de reserva inválidas!");
        }
        if (repositorioReservas.existeReserva(novaReserva.getIdReserva())) {
            throw new ReservaJaCadastradaException("Reserva já existe!");
        }

        List<Reserva> reservasNoQuarto = Stream.concat(repositorioReservas.listarReservasPorStatus(StatusDaReserva.ATIVA).stream(), repositorioReservas.listarReservasPorStatus(StatusDaReserva.EM_USO).stream())
                .filter(reserva -> reserva.getQuarto().getNumeroIdentificador().equals(novaReserva.getQuarto().getNumeroIdentificador())).toList();
        boolean quartoDisponivel = reservasNoQuarto.stream().noneMatch(reserva -> reserva.getDataInicio().isBefore(novaReserva.getDataFim()) && reserva.getDataFim().isAfter(novaReserva.getDataInicio()));

        if (!quartoDisponivel) {
            throw new ConflitoDeDatasException("Quarto " + novaReserva.getQuarto().getNumeroIdentificador() + " já reservado para o período selecionado.");
        }

        repositorioReservas.adicionarReserva(novaReserva);
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

    public void cadastrarCliente(Cliente novoCliente) throws ClienteInvalidoException, ClienteJaExisteException, ErroAoSalvarDadosException {
        if (!novoCliente.isValido()){
            throw new ClienteInvalidoException("Informações do cliente inválidas!");
        }
        if (repositorioClientes.existeCliente(novoCliente.getCpf())){
            throw new ClienteJaExisteException("Cliente já cadastrado!");
        }
        repositorioClientes.adicionarCliente(novoCliente);
    }

    @Override
    public List<Reserva> consultarHistorico(Cliente cliente) {
        return repositorioReservas.listarReservasPorCliente(cliente.getCpf());
    }

    public boolean autenticar(String email, String cpf) {
        Cliente cliente = repositorioClientes.buscarClientePorCpf(cpf);
        if (cliente == null){
            return false;
        }
        return email.equals(cliente.getEmail());
    }
}
