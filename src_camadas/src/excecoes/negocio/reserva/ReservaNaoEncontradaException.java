package excecoes.negocio.reserva;

public class ReservaNaoEncontradaException extends Exception {
    public ReservaNaoEncontradaException(String id) {
        super("Reserva com ID " + id + " não encontrada.");
    }
}