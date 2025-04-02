package negocio;

import dados.cliente.IRepositorioClientes;
import negocio.entidade.Cliente;

public class NegocioCliente {

    private IRepositorioClientes repositorioClientes;

    public NegocioCliente(IRepositorioClientes repositorioClientes) {
        this.repositorioClientes = repositorioClientes;
    }



}
