package negocio.excecao.reserva;

public class ReservaNaoEncontradaException extends ReservaException {
    public ReservaNaoEncontradaException(String id) {
        super("Reserva com ID " + id + " não encontrada.");
    }
}