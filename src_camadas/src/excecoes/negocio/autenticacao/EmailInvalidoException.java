package excecoes.negocio.autenticacao;

public class EmailInvalidoException extends Exception {
    public EmailInvalidoException(String mensagem) {
        super(mensagem);
    }
}
