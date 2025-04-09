package negocio;

import dados.cliente.RepositorioClientes;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.cliente.ClienteJaExisteException;
import excecoes.negocio.cliente.ClienteNaoEncontradoException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;

import java.util.List;
import java.util.stream.Stream;

public class NegocioCliente implements IFluxoReservas, IAutenticacao {

    private RepositorioClientes repositorioClientes;
    private RepositorioReservas repositorioReservas;

    public NegocioCliente(RepositorioClientes repositorioClientes, RepositorioReservas repositorioReservas) throws ErroAoCarregarDadosException {
        this.repositorioClientes = repositorioClientes;
        this.repositorioReservas = repositorioReservas;
    }

    @Override
    public boolean autenticar(String email, String cpf) throws AutenticacaoFalhouException {
        Cliente cliente = repositorioClientes.buscarClientePorCpf(cpf);
        if (cliente == null || !email.equals(cliente.getEmail())){
            throw new AutenticacaoFalhouException("Credenciais inválidas.");
        }

        return true;
    }

    public void fazerReserva(Reserva novaReserva) throws ErroAoSalvarDadosException, ReservaInvalidaException, ReservaJaCadastradaException, ConflitoDeDatasException {
        if (!novaReserva.isValida()) {
            throw new ReservaInvalidaException("Informações de reserva inválidas!");
        }
        if (repositorioReservas.existeReserva(novaReserva.getIdReserva())) {
            throw new ReservaJaCadastradaException("Reserva já existe!");
        }

        List<Reserva> reservasNoQuarto = Stream.concat(repositorioReservas.listarReservasPorStatus(StatusDaReserva.ATIVA).stream(), repositorioReservas.listarReservasPorStatus(StatusDaReserva.EM_USO).stream())
                .filter(reserva -> reserva.getQuarto().getNumeroIdentificador().equals(novaReserva.getQuarto().getNumeroIdentificador())).toList();

        boolean existeConflitoDeDatas =
                reservasNoQuarto.stream().anyMatch(reserva -> reserva.getDataInicio().isBefore(novaReserva.getDataFim()) && reserva.getDataFim().isAfter(novaReserva.getDataInicio()));
        if (existeConflitoDeDatas) {
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

    public Reserva buscarReservaPorId(String idReserva) throws ReservaNaoEncontradaException {
        Reserva reserva = repositorioReservas.buscarReservaPorId(idReserva);
        if (reserva == null) {
            throw new ReservaNaoEncontradaException("Reserva não encontrada!");
        }
        return reserva;
    }

}
