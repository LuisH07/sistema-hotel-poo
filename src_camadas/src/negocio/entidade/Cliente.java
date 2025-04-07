package negocio.entidade;

import excecoes.dados.ErroAoCarregarDadosException;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;

    public Cliente(String cpf, String nome, String email) throws ErroAoCarregarDadosException {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

}
