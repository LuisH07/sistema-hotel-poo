package negocio.entidade;

import negocio.entidade.enums.StatusDaReserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma reserva de quarto feita por um cliente.
 * Contém informações sobre o cliente, o quarto reservado, as datas da reserva,
 * o valor da diária, o valor total e o status da reserva.
 *
 * @author [Maria Heloisa]
 */
public class Reserva {
    private String idReserva;
    private Cliente cliente;
    private QuartoAbstrato quarto;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorDiaria;
    private double valorTotal;
    private StatusDaReserva status;

    public Reserva(Cliente cliente, QuartoAbstrato quarto, LocalDate dataInicio,
                   LocalDate dataFim) {
        idReserva = gerarIdReserva();
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorDiaria = quarto.getPrecoDiaria();
        this.valorTotal = calcularValorTotal();
        this.status = StatusDaReserva.ATIVA;
    }

    private String gerarIdReserva() {
        LocalDateTime horario = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "RES" + horario.format(formato);
    }

    private double calcularValorTotal() {
        int dias = dataInicio.until(dataFim).getDays();
        return dias * valorDiaria;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public QuartoAbstrato getQuarto() {
        return quarto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public StatusDaReserva getStatus() {
        return status;
    }

    public void setStatus(StatusDaReserva status) {
        this.status = status;
    }

    public boolean isValida() {
        if (idReserva == null || cliente == null || quarto == null || dataInicio == null || dataFim == null || valorDiaria <= 0 || valorTotal <= 0 || status == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reserva " + idReserva + ":\n" +
                "- Cliente: " + cliente.getNome() + "\n" +
                "- Quarto: " + quarto.getNumeroIdentificador() + " " + quarto.getCategoria() + " " + quarto.getCapacidade() + "\n" +
                "- Data de entrada: " + dataInicio + "\n" +
                "- Data de saída: " + dataFim + "\n" +
                "- Valor total: R$" + valorTotal + "\n" +
                "- Status da reserva: " + status + "\n";
    }

}
