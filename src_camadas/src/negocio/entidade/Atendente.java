package negocio.entidade;

import negocio.entidade.enums.Cargo;

/**
 * Representa um funcionário do tipo Atendente.
 * Esta classe herda de FuncionarioAbstrato e define o cargo específico.
 *
 * @author [Luiz Henrique]
 */
public class Atendente extends FuncionarioAbstrato {

    public Atendente() {
        super(Cargo.ATENDENTE);
    }

}