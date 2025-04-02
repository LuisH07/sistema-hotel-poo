package negocio.entidade;

import dados.reserva.RepositorioReservas;

import java.time.LocalDate;

public class Reserva {
    private String idReserva;
    private Cliente cliente;
    private QuartoAbstrato quarto;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorDiaria;
    private double valorTotal;
    private String status;

    public Reserva(Cliente cliente, QuartoAbstrato quarto, LocalDate dataInicio, LocalDate dataFim, RepositorioReservas reservas) {
        this.idReserva = Integer.toString(reservas.tamanho() + 1);
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorDiaria = quarto.getPrecoDiaria();
        this.valorTotal = calcularValorTotal();
        this.status = "Ativa";
        reservas.adicionarReserva(this);
    }

    private double calcularValorTotal() {
        long dias = dataInicio.until(dataFim).getDays();
        return dias * valorDiaria;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public QuartoAbstrato getQuarto() {
        return quarto;
    }

    public void setQuarto(QuartoAbstrato quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", cliente=" + cliente.getNome() + // Mostrando o nome do cliente
                ", quarto=" + quarto.getNumeroIdentificador() + " (" + quarto.getCategoria() + ")" +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", valorTotal=" + valorTotal +
                ", status='" + status + '\'' +
                '}';
    }

}
