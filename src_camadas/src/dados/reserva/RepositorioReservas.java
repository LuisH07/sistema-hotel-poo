package dados.reserva;

import negocio.entidade.Reserva;
import negocio.excecao.reserva.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class RepositorioReservas {

    private List<Reserva> reservas;
    private final String NOME_ARQUIVO = "reservas.dat";

    public RepositorioReservas() throws ReservaPersistenciaException {
        this.reservas = carregarReservas();
    }

    private List<Reserva> carregarReservas() throws ReservaPersistenciaException {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Reserva>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
            throw new ReservaPersistenciaException("Falha ao carregar reservas do arquivo", excecao);
        }
    }

    public void salvarReservas() throws ReservaPersistenciaException {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(reservas);
        } catch (IOException excecao) {
            throw new ReservaPersistenciaException("Falha ao salvar reservas no arquivo", excecao);
        }
    }

    public void adicionarReserva(Reserva reserva) throws ReservaException {
        if (reserva == null) {
            throw new ReservaInvalidaException("Reserva não pode ser nula.");
        }
        if (buscarReservaPorId(reserva.getIdReserva()) != null) {
            throw new ReservaDuplicadaException(reserva.getIdReserva());
        }
        try {
            reservas.add(reserva);
            salvarReservas();
        } catch (Exception e) {
            throw new ReservaPersistenciaException("Falha ao salvar reserva", e);
        }
    }

    public Reserva buscarReservaPorId(String id) throws ReservaException {
        if (id == null || id.isEmpty()) {
            throw new ReservaInvalidaException("ID da reserva não pode ser nulo ou vazio.");
        }
        Reserva encontrada = reservas.stream()
                .filter(reserva -> reserva.getIdReserva().equals(id))
                .findFirst()
                .orElse(null);

        if (encontrada == null) {
            throw new ReservaNaoEncontradaException(id);
        }
        return encontrada;
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    public List<Reserva> listarReservasPorCliente(String cpf) throws ReservaInvalidaException {
        if (cpf == null || cpf.isEmpty()) {
            throw new ReservaInvalidaException("CPF do cliente não pode ser nulo ou vazio.");
        }
        return reservas.stream().filter(reserva -> reserva.getCliente().getCpf().equals(cpf)).toList();
    }

    public List<Reserva> listarReservasPorStatus(String status) throws ReservaInvalidaException {
        if (status == null || status.isEmpty()) {
            throw new ReservaInvalidaException("Status da reserva não pode ser nulo ou vazio.");
        }
        return reservas.stream().filter(reserva -> reserva.getStatus().equals(status)).toList();
    }

    public int getTamanho() {
        return reservas.size();
    }
}

