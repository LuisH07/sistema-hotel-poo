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
        if (cliente != null) {
            Reserva reserva = new Reserva(cliente, quarto, inicio, fim);
            negocioReserva.fazerReserva(reserva);
        }
    } //Busca o cliente pelo CPF e cria um objeto reserva