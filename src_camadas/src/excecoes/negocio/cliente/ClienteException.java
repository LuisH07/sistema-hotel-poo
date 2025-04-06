package excecoes.negocio.cliente;

public class ClienteException extends Exception {
    public ClienteException(String mensagem) {
        super(mensagem);
    }
}