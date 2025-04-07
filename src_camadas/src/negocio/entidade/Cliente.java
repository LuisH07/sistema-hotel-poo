package negocio.entidade;

import excecoes.dados.ErroAoCarregarDadosException;
import java.util.Objects;

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
    public void setCpf(String cpf) { this.cpf = cpf;}

    public String getNome() {
        return nome;
    }
    public void setNome(String nome){this.nome = nome;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) { this.email = email;}

    @Override
    public String toString(){
        return "Cliente(" + "cpf: '"+cpf+ '\'' + ", nome: '"+nome+'\''+", email: '"+email+'\'' + ')';
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if(!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
