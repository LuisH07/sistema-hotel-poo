package negocio;

import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import negocio.entidade.Cliente;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Reserva;
import negocio.entidade.enums.*;
import excecoes.negocio.cliente.*;
import excecoes.negocio.quarto.*;
import excecoes.negocio.reserva.*;
import excecoes.dados.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class NegocioAtendente implements IFluxoReservas {

    RepositorioReservas repositorioReservas;
    RepositorioQuartos repositorioQuartos;

    public NegocioAtendente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos){
        this.repositorioReservas = repositorioReservas;
        this.repositorioQuartos = repositorioQuartos;
    }

    @Override
    public void fazerReserva(Cliente cliente, String numeroQuarto, String dataInicioString, String dataFimString)
                                    throws ReservaInvalidaException, ClienteInvalidoException,
                                    QuartoInvalidoException, ConflitoDeDatasException, ErroAoSalvarDadosException {

        if (!cliente.isValido()) {
            throw new ClienteInvalidoException("Cliente inválido.");
        }

        LocalDate dataInicio, dataFim;
        try {
            dataInicio = converterData(dataInicioString);
            dataFim = converterData(dataFimString);
        } catch (DateTimeParseException excecao) {
            throw new ReservaInvalidaException("Formato de data inválido. Use dd/MM/yyyy.");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new ReservaInvalidaException("Data de início não pode ser após a data final.");
        }
        if (dataInicio.isBefore(LocalDate.now())) {
            throw new ReservaInvalidaException("Data de início não pode ser no passado.");
        }

        QuartoAbstrato quarto = repositorioQuartos.buscarQuartoPorNumero(numeroQuarto);
        if (!quarto.isValido()) {
            throw new QuartoInvalidoException("Quarto inválido");
        }

        boolean quartoDisponivel = repositorioReservas.listarReservasPorQuarto(numeroQuarto).stream()
                .noneMatch(reserva ->
                        !reserva.getStatus().equals(StatusDaReserva.CANCELADA) &&
                                dataInicio.isBefore(reserva.getDataFim()) &&
                                dataFim.isAfter(reserva.getDataInicio())
                );

        if (!quartoDisponivel) {
            throw new ConflitoDeDatasException("Quarto " + numeroQuarto + " já reservado para o período selecionado.");
        }

        Reserva novaReserva = new Reserva(cliente, quarto, dataInicio, dataFim);
        repositorioReservas.adicionarReserva(novaReserva);
    }

    @Override
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

    @Override
    public List<Reserva> consultarHistorico() {
        return repositorioReservas.listarReservasPorStatus("ATIVA");
    }

    public void fazerCheckIn(String idReserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException, ErroAoSalvarDadosException {

        Reserva reserva = repositorioReservas.buscarReservaPorId(idReserva);

        if (reserva == null) {
            throw new ReservaNaoEncontradaException("Reserva não encontrada!");
        }

        if (reserva.getStatus() != StatusDaReserva.ATIVA) {
            throw new ReservaInvalidaException("Check-in só pode ser feito para reservas ATIVAS");
        }

        if (LocalDate.now().isBefore(reserva.getDataInicio()) || LocalDate.now().isAfter(reserva.getDataFim())) {
            throw new ReservaInvalidaException("Check-in só pode ser feito dentro do período da reserva");
        }

        reserva.setStatus(StatusDaReserva.EM_USO);
        repositorioReservas.atualizarReserva(reserva);
    }

    public void fazerCheckOut(String idReserva) throws ReservaNaoEncontradaException,
            ReservaInvalidaException, ErroAoSalvarDadosException {

        Reserva reserva = repositorioReservas.buscarReservaPorId(idReserva);

        if (reserva == null) {
            throw new ReservaNaoEncontradaException("Reserva não encontrada!");
        }

        if (reserva.getStatus() != StatusDaReserva.EM_USO) {
            throw new ReservaInvalidaException("Check-out só pode ser feito para reservas EM USO");
        }

        reserva.setStatus(StatusDaReserva.FINALIZADA);
        repositorioReservas.atualizarReserva(reserva);
    }

    private LocalDate converterData(String dataString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataString, formatter);
    }
}