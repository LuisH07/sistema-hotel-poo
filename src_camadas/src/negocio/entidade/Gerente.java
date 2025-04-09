package negocio.entidade;

import negocio.entidade.enums.Cargo;

/**
 * Representa um funcionário do tipo Gerente.
 * Esta classe herda de FuncionarioAbstrato e define o cargo específico.
 *
 * @author [Arthur]
 */
public class Gerente extends FuncionarioAbstrato {

    public Gerente() {
        super(Cargo.GERENTE);
    }

}
