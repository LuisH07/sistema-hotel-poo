package negocio.excecao.reserva;

public class ReservaPersistenciaException extends ReservaException {
    public ReservaPersistenciaException(String mensagem, Throwable causa) {
        super(mensagem + ": " + causa.getMessage());
    }
}