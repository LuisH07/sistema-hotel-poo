package negocio.entidade;

import negocio.entidade.enums.Cargo;

public class Gerente extends FuncionarioAbstrato implements FluxoReservas {

    public Gerente() {
        super(Cargo.GERENTE);
    }

    @Override
    public void cancelarReserva(String idReserva) {

    }

    @Override
    public void consultarHistorico() {

    }
}
