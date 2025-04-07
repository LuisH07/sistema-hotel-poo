package negocio.entidade;

import dados.reserva.RepositorioReservas;
import negocio.entidade.enums.Cargo;

import java.util.List;

public class Gerente extends FuncionarioAbstrato {

    public Gerente() {
        super(Cargo.GERENTE);
    }

}
