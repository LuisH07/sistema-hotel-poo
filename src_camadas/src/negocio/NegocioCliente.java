package negocio;

import dados.cliente.RepositorioClientes;
import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.autenticacao.DataInvalidaException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.cliente.ClienteJaExisteException;
import excecoes.negocio.cliente.ClienteNaoEncontradoException;
import excecoes.negocio.quarto.QuartoInvalidoException;
import excecoes.negocio.quarto.QuartoNaoEncontradoException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.entidade.Cliente;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

public class NegocioCliente implements IFluxoReservas, IAutenticacao {

    private RepositorioClientes repositorioClientes;
    private RepositorioReservas repositorioReservas;
    private RepositorioQuartos repositorioQuartos;

    public NegocioCliente(RepositorioClientes repositorioClientes, RepositorioReservas repositorioReservas,
                          RepositorioQuartos repositorioQuartos) throws ErroAoCarregarDadosException {
        this.repositorioClientes = repositorioClientes;
        this.repositorioReservas = repositorioReservas;
        this.repositorioQuartos = repositorioQuartos;
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

    public Reserva buscarReservaPorId(String idReserva) throws ReservaNaoEncontradaException, ReservaInvalidaException {
        if (!repositorioReservas.existeReserva(idReserva)) {
            throw new ReservaNaoEncontradaException("Reserva não encontrado!");
        }
        Reserva reserva = repositorioReservas.buscarReservaPorId(idReserva);

        if (!reserva.isValida()) {
            throw new ReservaInvalidaException("Informações de reserva inválidas!");
        }
        return reserva;
    }

    public Cliente buscarClientePorCpf(String cpf) throws ClienteNaoEncontradoException, ClienteInvalidoException {
        if (!repositorioClientes.existeCliente(cpf)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado!");
        }
        Cliente cliente = repositorioClientes.buscarClientePorCpf(cpf);

        if (!cliente.isValido()) {
            throw new ClienteInvalidoException("Informações de cliente inválidas!");
        }
        return cliente;
    }

    public QuartoAbstrato buscarQuartoPorNumeroIdentificador(String numero) throws QuartoNaoEncontradoException, QuartoInvalidoException {
        if (!repositorioQuartos.existeQuarto(numero)) {
            throw new QuartoNaoEncontradoException("Quarto não encontrado!");
        }
        QuartoAbstrato quarto = repositorioQuartos.buscarQuartoPorNumero(numero);

        if (!quarto.isValido()) {
            throw new QuartoInvalidoException("Informações de quarto inválidas!");
        }
        return quarto;
    }

    public List<QuartoAbstrato> listarQuartosDisponiveisNoPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<QuartoAbstrato> todosQuartos = repositorioQuartos.listarQuartos();
        List<Reserva> reservasAtivasOuEmUso = Stream.concat(
                repositorioReservas.listarReservasPorStatus(StatusDaReserva.ATIVA).stream(),
                repositorioReservas.listarReservasPorStatus(StatusDaReserva.EM_USO).stream()
        ).toList();

        return todosQuartos.stream().filter(quarto -> {
            return reservasAtivasOuEmUso.stream().filter(reserva -> reserva.getQuarto().getNumeroIdentificador().equals(quarto.getNumeroIdentificador())).noneMatch(reserva -> reserva.getDataInicio().isBefore(dataFim) && reserva.getDataFim().isAfter(dataInicio));
        }).toList();
    }

    private LocalDate validarData(String dataString) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate dataLocalDate = LocalDate.parse(dataString, formatador);
            return dataLocalDate;
        } catch (DateTimeParseException excecao) {
            throw new DataInvalidaException("Data inválida. O formato esperado é dd/MM/yyyy");
        }
    }

}
