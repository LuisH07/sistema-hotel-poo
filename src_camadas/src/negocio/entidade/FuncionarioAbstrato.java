package negocio.entidade;

import negocio.entidade.enums.Cargo;

import java.io.Serializable;

public abstract class FuncionarioAbstrato implements Serializable {
    private Cargo cargo;

    public FuncionarioAbstrato(Cargo cargo) {
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public String getEmail() {
        return cargo.getEmail();
    }

    public String getSenha() {
        return cargo.getSenha();
    }

    public boolean isValido() {
        if (cargo == null || getEmail() == null || getSenha() == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Funcionario:" + "\n" +
                "cargo = " + cargo + "\n" +
                "email = " + getEmail() + "\n" +
                "senha = " + getSenha() + "\n";
    }
}
