package negocio.excecao.reserva;

public class ReservaDuplicadaException extends ReservaException {
    public ReservaDuplicadaException(String id) {
        super("Reserva com ID " + id + " já existe no sistema.");
    }
}