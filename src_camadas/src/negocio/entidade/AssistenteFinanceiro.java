package negocio.entidade;

import dados.reserva.RepositorioReservas;
import negocio.entidade.enums.Cargo;

import java.util.List;

public class AssistenteFinanceiro extends FuncionarioAbstrato {

    public AssistenteFinanceiro() {
        super(Cargo.ASSISTENTE_FINANCEIRO);
    }

}
