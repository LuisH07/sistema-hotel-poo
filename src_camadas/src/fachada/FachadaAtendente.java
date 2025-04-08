package fachada;

import negocio.NegocioAtendente;
import negocio.entidade.Reserva;

import java.util.List;

public class FachadaAtendente {
    private NegocioAtendente negocioAtendente;

    public FachadaAtendente(NegocioAtendente negocioAtendente) {
        this.negocioAtendente = negocioAtendente;
    }

    public List<Reserva> listarReservasAtivas() {
        return negocioAtendente.consultarHistorico();
    }
}