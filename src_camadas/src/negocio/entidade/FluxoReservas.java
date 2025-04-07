package negocio.entidade;

public interface FluxoReservas {

    public abstract void fazerReserva(Cliente cliente);
    public abstract void cancelarReserva(String idReserva);
    public abstract void consultarHistorico();

}
