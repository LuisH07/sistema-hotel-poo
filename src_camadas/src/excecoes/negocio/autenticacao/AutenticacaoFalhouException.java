package excecoes.negocio.autenticacao;

public class AutenticacaoFalhouException extends Exception {
    public AutenticacaoFalhouException(String mensagem) {
        super(mensagem);
    }
}