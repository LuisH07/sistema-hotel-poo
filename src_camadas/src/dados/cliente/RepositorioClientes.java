package dados.cliente;

import negocio.entidade.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClientes {

    private final String NOME_ARQUIVO = "clientes.dat";
    private List<Cliente> clientes;

    public RepositorioClientes() {
        this.clientes = carregarClientes();
    }

    private List<Cliente> carregarClientes() {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Cliente>) arquivoLeitura.readObject();
        } catch (FileNotFoundException execao) {
            return new ArrayList<Cliente>();
        } catch (IOException | ClassNotFoundException execao) {
            execao.printStackTrace();
            return new ArrayList<Cliente>();
        }
    }

    public void salvarClientes() {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(clientes);
        } catch (IOException execao) {
            execao.printStackTrace();
        }
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
        salvarClientes();
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream().filter(cliente -> cliente.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }
}