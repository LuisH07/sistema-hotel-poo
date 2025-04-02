package fachada;

import negocio.NegocioAssistenteFinanceiro;
import java.time.LocalDate;

public class FachadaAssistenteFinanceiro {
    private NegocioAssistenteFinanceiro negocioAssistenteFinanceiro;

    public FachadaAssistenteFinanceiro(NegocioAssistenteFinanceiro negocioAssistenteFinanceiro) {
        this.negocioAssistenteFinanceiro = negocioAssistenteFinanceiro;
    }

    public double calcularReceita(LocalDate inicio, LocalDate fim) {
        return negocioAssistenteFinanceiro.calcularReceitaTotal();
    } //Calcula a receita em um determinado período de tempo

    public String gerarRelatorio(LocalDate inicio, LocalDate fim) {
        return negocioAssistenteFinanceiro.gerarRelatorio();
    }
}