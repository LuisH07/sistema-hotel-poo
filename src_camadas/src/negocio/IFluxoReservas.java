package negocio;

import negocio.entidade.Cliente;
import negocio.entidade.QuartoAbstrato;

import java.time.LocalDate;

public interface IFluxoReservas {

    default void fazerReserva(Cliente cliente, QuartoAbstrato quarto, LocalDate dataInicio,
                              LocalDate dataFim){}
    void cancelarReserva(String idReserva);
    void consultarHistorico();

}
