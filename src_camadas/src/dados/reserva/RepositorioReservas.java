package dados.reserva;

import negocio.entidade.Reserva;
import java.util.ArrayList;
import java.util.List;

public class RepositorioReservas {

    private List<Reserva> reservas;

    public RepositorioReservas() {
        this.reservas = new ArrayList<>();
    }

    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public Reserva buscarReservaPorId(String id) {
        return reservas.stream().filter(r -> r.getIdReserva().equals(id)).findFirst().orElse(null);
    }

    public List<Reserva> listarReservas() {
        return reservas;
    }

    public List<Reserva> listarReservasPorCliente(String cpf) {
        return reservas.stream().filter(r -> r.getCliente().getCpf().equals(cpf)).toList();
    }

    public List<Reserva> listarReservasPorStatus(String status) {
        return reservas.stream().filter(r -> r.getStatus().equals(status)).toList();
    }

    public int tamanho() {
        return reservas.size();
    }
}

