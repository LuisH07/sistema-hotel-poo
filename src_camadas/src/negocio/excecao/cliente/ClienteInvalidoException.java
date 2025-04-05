package negocio.excecao.cliente;

public class ClienteInvalidoException extends ClienteException {
    public ClienteInvalidoException(String mensagem) {
        super(mensagem);
    }
}