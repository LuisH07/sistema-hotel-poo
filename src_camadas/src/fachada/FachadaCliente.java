package fachada;

import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.cliente.ClienteNaoEncontradoException;
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

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return negocioCliente.autenticar(email, senha);
    }

    public String listarQuartosDisponiveisNoPeriodo(String dataInicio, String dataFim) {

        List<QuartoAbstrato> quartosDisponiveis = negocioCliente.listarQuartosDisponiveisNoPeriodo();

    }

    public void fazerReserva(String numeroDeQuarto) throws ClienteNaoEncontradoException, ClienteInvalidoException {
        Cliente cliente = negocioCliente.buscarClientePorCpf(cpf);



        Reserva reserva = new Reserva(cliente, quarto, inicio, fim);
        try {
            negocioCliente.fazerReserva(reserva);
        } catch (ReservaInvalidaException | ReservaJaCadastradaException | ErroAoSalvarDadosException |
                 ConflitoDeDatasException excecao) {
            throw new RuntimeException(excecao);
        }
    }

    public String consultarHistorico(String cpf) {
            Cliente cliente = negocioCliente.buscarClientePorCpf(cpf);

            List<Reserva> historicoReservas = negocioCliente.consultarHistorico(cliente);
            String historicoFormatado = "";
            if (historicoReservas != null && !historicoReservas.isEmpty()){
                historicoFormatado += "Histórico de Reservas com cpf " + cpf + ":\n";
                for(Reserva reserva : historicoReservas){
                    historicoFormatado += "Reserva para " + reserva.getCliente()
                            + " de " + reserva.getDataInicio()
                            + "até " + reserva.getDataFim();
                }
            }else{
                historicoFormatado += "Nenhum histórico de reservas encontrado!";
            }
            return historicoFormatado;
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
