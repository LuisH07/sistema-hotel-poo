package excecoes.negocio.reserva;

public class ReservaInvalidaException extends Exception {
    public ReservaInvalidaException(String mensagem) {
        super(mensagem);
    }
}