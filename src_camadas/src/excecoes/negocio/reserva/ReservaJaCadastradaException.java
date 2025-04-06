package excecoes.negocio.reserva;

public class ReservaJaCadastradaException extends Exception {
    public ReservaJaCadastradaException(String id) {
        super("Reserva com ID " + id + " já existe no sistema.");
    }
}