package negocio.excecao.reserva;

public class ReservaInvalidaException extends ReservaException {
    public ReservaInvalidaException(String mensagem) {
        super(mensagem);
    }
}