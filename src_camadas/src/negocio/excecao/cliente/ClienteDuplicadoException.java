package negocio.excecao.cliente;

public class ClienteDuplicadoException extends ClienteException {
    public ClienteDuplicadoException(String cpf) {
        super("Cliente com CPF " + cpf + " já está cadastrado.");
    }
}