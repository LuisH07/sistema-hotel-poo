package excecoes.negocio.cliente;

public class ClienteJaExisteException extends ClienteException {
    public ClienteJaExisteException(String cpf) {
        super("Cliente com CPF " + cpf + " já está cadastrado.");
    }
}