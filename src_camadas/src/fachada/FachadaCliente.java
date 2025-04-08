package fachada;

import negocio.NegocioCliente;
import negocio.NegocioReserva;
import negocio.entidade.Cliente;
import negocio.entidade.Reserva;
import negocio.entidade.QuartoAbstrato;
import java.time.LocalDate;

public class FachadaCliente {

    private NegocioCliente negocioCliente;
    private NegocioReserva negocioReserva;

    public FachadaCliente(NegocioCliente negocioCliente, NegocioReserva negocioReserva) {
        this.negocioCliente = negocioCliente;
        this.negocioReserva = negocioReserva;
    }

    public void fazerReserva(String cpf, QuartoAbstrato quarto, LocalDate inicio, LocalDate fim) {
        Cliente cliente = negocioCliente.buscarCliente(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com CPF " + cpf + " não encontrado.");
        }

        if (!cliente.isValido()) {
            throw new IllegalArgumentException("Cliente com dados inválidos.");
        }

        if (quarto == null || inicio == null || fim == null || !inicio.isBefore(fim)) {
            throw new IllegalArgumentException("Dados de reserva inválidos: verifique quarto e datas.");
        }

        Reserva reserva = new Reserva(cliente, quarto, inicio, fim);
        negocioReserva.fazerReserva(reserva);

    }
}