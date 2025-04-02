package fachada;

import negocio.NegocioAtendente;
import negocio.entidade.Reserva;
import java.util.List;

ppublic class FachadaAtendente {
    private NegocioAtendente negocioAtendente;

    public FachadaAtendente(NegocioAtendente negocioAtendente) {
        this.negocioAtendente = negocioAtendente;
    }

    public List<Reserva> listarReservasAtivas() {
        return negocioAtendente.listarReservasAtivas();
    }
}