package dados.cliente;

import excecoes.dados.*;
import negocio.entidade.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClientes {

    private final String NOME_ARQUIVO = "clientes.dat";
    private List<Cliente> clientes;

    public RepositorioClientes() throws ErroAoCarregarDadosException {
        this.clientes = carregarClientes();
    }

    private List<Cliente> carregarClientes() throws ErroAoCarregarDadosException {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Cliente>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
            throw new ErroAoCarregarDadosException("Falha ao carregar clientes do arquivo");
        }
    }

    public void salvarClientes() throws ErroAoSalvarDadosException {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(clientes);
        } catch (IOException excecao) {
            throw new ErroAoSalvarDadosException("Falha ao salvar clientes no arquivo");
        }
    }

    public void adicionarCliente(Cliente cliente) throws ErroAoSalvarDadosException {
        clientes.add(cliente);
        salvarClientes();
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }
}