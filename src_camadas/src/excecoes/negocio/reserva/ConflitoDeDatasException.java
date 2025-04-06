package excecoes.negocio.reserva;

public class ConflitoDeDatasException extends Exception {
    public ConflitoDeDatasException(String mensagem) {
        super(mensagem);
    }
}