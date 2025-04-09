package negocio.entidade;

import negocio.entidade.enums.Cargo;

/**
 * Representa um funcionário do tipo Assistente Financeiro.
 * Esta classe herda de FuncionarioAbstrato e define o cargo específico.
 *
 * @author [Luis Henrique]
 */
public class AssistenteFinanceiro extends FuncionarioAbstrato {

    public AssistenteFinanceiro() {
        super(Cargo.ASSISTENTE_FINANCEIRO);
    }

}
