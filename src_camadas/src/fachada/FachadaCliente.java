package fachada;

import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.autenticacao.CpfInvalidoException;
import excecoes.negocio.autenticacao.DataInvalidaException;
import excecoes.negocio.autenticacao.EmailInvalidoException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.cliente.ClienteJaExisteException;
import excecoes.negocio.cliente.ClienteNaoEncontradoException;
import excecoes.negocio.quarto.QuartoInvalidoException;
import excecoes.negocio.quarto.QuartoNaoEncontradoException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.NegocioCliente;
import negocio.entidade.Cliente;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Reserva;
import java.time.LocalDate;
import dados.cliente.RepositorioClientes;

import java.util.List;

public class FachadaCliente {

    private NegocioCliente negocioCliente;

    public FachadaCliente(RepositorioClientes repositorioClientes, RepositorioReservas repositorioReservas,
                          RepositorioQuartos repositorioQuartos) throws ErroAoCarregarDadosException {
        negocioCliente = new NegocioCliente(repositorioClientes, repositorioReservas, repositorioQuartos);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException, EmailInvalidoException, CpfInvalidoException {
        return negocioCliente.autenticar(email, senha);
    }

    public void cadastrarCliente(String nome, String email, String cpf) throws ErroAoCarregarDadosException, ErroAoSalvarDadosException, EmailInvalidoException, ClienteJaExisteException, ClienteInvalidoException, CpfInvalidoException {
        Cliente cliente = new Cliente(nome, email, cpf);
        negocioCliente.cadastrarCliente(cliente);
    }

    public String listarQuartosDisponiveisNoPeriodo(String dataInicio, String dataFim) throws DataInvalidaException {
        LocalDate dataInicioLocalDate = negocioCliente.converterDataStringParaLocalDate(dataInicio);
        LocalDate dataFimLocalDate = negocioCliente.converterDataStringParaLocalDate(dataFim);
        List<QuartoAbstrato> quartosDisponiveis = negocioCliente.listarQuartosDisponiveisNoPeriodo(dataInicioLocalDate, dataFimLocalDate);

        StringBuilder quartosDisponiveisFormatados = new StringBuilder();
        if (quartosDisponiveis != null && !quartosDisponiveis.isEmpty()){
            quartosDisponiveisFormatados.append("Quartos Disponíveis no período: \n\n");
            for (QuartoAbstrato quarto : quartosDisponiveis) {
                quartosDisponiveisFormatados.append(quarto.toString());
            }
        } else{
            quartosDisponiveisFormatados.append("Nenhum quarto disponível encontrado!");
        }
        return quartosDisponiveisFormatados.toString();
    }

    public void fazerReserva(String cpf, String numeroDeQuarto, String dataInicio, String dataFim) throws ClienteNaoEncontradoException,
            ClienteInvalidoException, QuartoNaoEncontradoException, QuartoInvalidoException, DataInvalidaException, ConflitoDeDatasException, ErroAoSalvarDadosException, ReservaInvalidaException, ReservaJaCadastradaException {
        Cliente cliente = negocioCliente.buscarClientePorCpf(cpf);
        QuartoAbstrato quarto = negocioCliente.buscarQuartoPorNumeroIdentificador(numeroDeQuarto);
        LocalDate dataInicioLocalDate = negocioCliente.converterDataStringParaLocalDate(dataInicio);
        LocalDate dataFimLocalDate = negocioCliente.converterDataStringParaLocalDate(dataFim);

        Reserva reserva = new Reserva(cliente, quarto, dataInicioLocalDate, dataFimLocalDate);

        negocioCliente.fazerReserva(reserva);
    }

    public String consultarHistorico(String cpf) throws ClienteInvalidoException, ClienteNaoEncontradoException {
            Cliente cliente = negocioCliente.buscarClientePorCpf(cpf);
            List<Reserva> historicoReservas = negocioCliente.consultarHistorico(cliente);
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

    public String listarReservasAtivasPorCliente(String cpf) {
        List<Reserva> reservasAtivas = negocioCliente.listarReservasAtivasPorCliente(cpf);

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

    public void cancelarReserva(String idReserva, String cpf) throws ReservaInvalidaException,
            ReservaNaoEncontradaException,
            ErroAoSalvarDadosException, ClienteInvalidoException, ClienteNaoEncontradoException {

        Cliente cliente = negocioCliente.buscarClientePorCpf(cpf);
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
