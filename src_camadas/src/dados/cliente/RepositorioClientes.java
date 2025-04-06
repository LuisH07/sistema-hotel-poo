package dados.cliente;

import excecoes.dados.*;
import excecoes.negocio.cliente.ClienteJaExisteException;
import excecoes.negocio.cliente.ClienteNaoEncontradoException;
import negocio.entidade.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void adicionarCliente(Cliente cliente) throws ErroAoSalvarDadosException, ClienteJaExisteException {
        if (existe(cliente.getCpf())){
            throw new ClienteJaExisteException(cliente.getCpf());
        }
        clientes.add(cliente);
        salvarClientes();
    }

    public boolean existe(String cpf){
        return clientes.stream().anyMatch(cliente -> cliente.getCpf().equals(cpf));
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public void atualizarCliente(Cliente novoCliente) throws ErroAoSalvarDadosException, ClienteNaoEncontradoException{
        Optional<Cliente> clienteOptional = clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(novoCliente.getCpf()))
                .findFirst();

        if (clienteOptional.isPresent()){
            int indice = clientes.indexOf(clienteOptional.get());
            clientes.set(indice, novoCliente);
            salvarClientes();
        }else{
            throw new ClienteNaoEncontradoException(novoCliente.getCpf());
        }
    }

    public void removerCliente(String cpf) throws ErroAoSalvarDadosException, ClienteNaoEncontradoException{
        boolean removido = clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));

        if (removido){
            salvarClientes();
        }else {
            throw new ClienteNaoEncontradoException(cpf);
        }
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }
}