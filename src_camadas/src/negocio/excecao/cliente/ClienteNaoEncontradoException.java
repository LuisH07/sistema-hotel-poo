package negocio.excecao.cliente;

public class ClienteNaoEncontradoException extends ClienteException {
    public ClienteNaoEncontradoException(String cpf) {
        super("Cliente com CPF " + cpf + " não encontrado.");
    }
}