package negocio;

import dados.cliente.IRepositorioClientes;
import negocio.entidade.Cliente;

public class NegocioCliente {

    private IRepositorioClientes repositorioClientes;

    public NegocioCliente(IRepositorioClientes repositorioClientes) {
        this.repositorioClientes = repositorioClientes;
    }

    public Cliente buscarCliente(String cpf){
        // Implementar metodo que busca cliente pelo CPF no repositorio de clientes
    }
}