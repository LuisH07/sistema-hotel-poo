package dados.reserva;

import negocio.entidade.Reserva;
import negocio.excecao.reserva.ReservaInvalidaException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class RepositorioReservas {

    private List<Reserva> reservas;
    private final String NOME_ARQUIVO = "reservas.dat";

    public RepositorioReservas() {
        this.reservas = carregarReservas();
    }

    private List<Reserva> carregarReservas() {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Reserva>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
            excecao.printStackTrace();
            return new ArrayList<Reserva>();
        }
    }

    public void salvarReservas() {
        try(ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))){
            arquivoEscrita.writeObject(reservas);
        } catch (IOException excecao) {
            excecao.printStackTrace();
        }
    }

    public void adicionarReserva(Reserva reserva) throws ReservaInvalidaException {
        if (reserva == null) {
            throw new ReservaInvalidaException("Reserva não pode ser nula.");
        }
        reservas.add(reserva);
        salvarReservas();
    }

    public Reserva buscarReservaPorId(String id) throws ReservaInvalidaException {
        if (id == null || id.isEmpty()) {
            throw new ReservaInvalidaException("ID da reserva não pode ser nulo ou vazio.");
        }
        return reservas.stream().filter(reserva -> reserva.getIdReserva().equals(id)).findFirst().orElse(null);
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

