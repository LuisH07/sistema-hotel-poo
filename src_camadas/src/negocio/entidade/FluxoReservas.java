package negocio.entidade;

import java.time.LocalDate;

public interface FluxoReservas {

    public void fazerReserva(Cliente cliente, QuartoAbstrato quarto, LocalDate dataInicio, LocalDate dataFim);
    public void cancelarReserva(String idReserva);
    public void consultarHistorico();

}
