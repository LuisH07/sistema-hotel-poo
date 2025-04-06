package negocio.excecao.cliente;

public class ClientePersistenciaException extends ClienteException {
    public ClientePersistenciaException(String mensagem, Throwable causa) {
        super(mensagem + ": " + causa.getMessage());
    }
}