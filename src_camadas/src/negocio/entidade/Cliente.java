package negocio.entidade;

import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;

    private RepositorioReservas repositorioReservas;

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

    public boolean isValido() {
        if (cpf == null || nome == null || email == null || !email.contains("@")) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "Cliente:" + "\n" +
                "cpf = " + cpf + "\n" +
                "nome = " + nome + "\n" +
                "email = " + email + "\n ";
    }
}
