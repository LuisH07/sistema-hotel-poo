package negocio.excecao.cliente;

public class ClienteException extends Exception {
    public ClienteException(String mensagem) {
        super(mensagem);
    }
}