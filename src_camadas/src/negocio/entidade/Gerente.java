package negocio.entidade;

import negocio.entidade.enums.Cargo;

public class Gerente extends FuncionarioAbstrato {

    public Gerente() {
        super(Cargo.GERENTE);
    }

}
