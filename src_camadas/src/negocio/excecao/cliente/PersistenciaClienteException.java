package negocio.excecao.cliente;

public class PersistenciaClienteException extends ClienteException {
    public PersistenciaClienteException(String mensagem, Throwable causa) {
        super(mensagem + ": " + causa.getMessage());
    }
}