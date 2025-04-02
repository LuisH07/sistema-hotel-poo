package dados.cliente;

import negocio.entidade.Cliente;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClientes implements IRepositorioClientes {

    private List<Cliente> clientes;

    public RepositorioClientes() {
        this.clientes = new ArrayList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }
}
