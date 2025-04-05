package dados.cliente;

import negocio.entidade.Cliente;
import negocio.excecao.cliente.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClientes {

    private final String NOME_ARQUIVO = "clientes.dat";
    private List<Cliente> clientes;

    public RepositorioClientes() throws PersistenciaClienteException {
        this.clientes = carregarClientes();
    }

    private List<Cliente> carregarClientes() throws PersistenciaClienteException {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Cliente>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
            throw new PersistenciaClienteException("Falha ao carregar clientes do arquivo", excecao);
        }
    }

    public void salvarClientes() throws PersistenciaClienteException {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(clientes);
        } catch (IOException excecao) {
            throw new PersistenciaClienteException("Falha ao salvar clientes no arquivo", excecao);
        }
    }

    public void adicionarCliente(Cliente cliente) throws ClienteInvalidoException, ClienteDuplicadoException, PersistenciaClienteException {
        if (cliente == null) {
            throw new ClienteInvalidoException("Cliente não pode ser nulo.");
        }
        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            throw new ClienteDuplicadoException(cliente.getCpf());
        }
        clientes.add(cliente);
        salvarClientes();
    }

    public Cliente buscarClientePorCpf(String cpf) throws ClienteInvalidoException {
        if (cpf == null || cpf.isEmpty()) {
            throw new ClienteInvalidoException("CPF não pode ser nulo ou vazio.");
        }
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public Cliente obterClientePorCpf(String cpf) throws ClienteNaoEncontradoException, ClienteInvalidoException {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException(cpf);
        }
        return cliente;
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }
}