package negocio.entidade;

import excecoes.dados.ErroAoCarregarDadosException;

import java.io.Serializable;

/**
 * Representa um cliente do sistema de reservas.
 * Contém informações básicas do cliente como CPF, nome e email.
 *
 * @author [Maria Heloisa]
 */
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String email;
    private String cpf;

    public Cliente(String nome, String email, String cpf) throws ErroAoCarregarDadosException {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
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
