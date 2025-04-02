package fachada;

import negocio.NegocioGerente;
import negocio.entidade.Reserva;
import java.util.List;
import java.time.LocalDate;

public class FachadaGerente {
    private NegocioGerente negocioGerente;

    public FachadaGerente(NegocioGerente negocioGerente) {
        this.negocioGerente = negocioGerente;
    }

    public List<Reserva> listarReservas() {
        return negocioGerente.listarReservas();
    }

    public String gerarRelatorio(LocalDate inicio, LocalDate fim) {
        return negocioGerente.gerarRelatorio(inicio, fim);
    }
}