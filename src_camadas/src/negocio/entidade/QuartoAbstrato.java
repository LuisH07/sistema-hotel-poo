package negocio.entidade;

import dados.quartos.RepositorioQuartos;
import excecoes.negocio.quarto.*;
import negocio.entidade.enums.CapacidadeDoQuarto;
import negocio.entidade.enums.CategoriaDoQuarto;

public abstract class QuartoAbstrato {
    private final String numeroIdentificador;
    private CapacidadeDoQuarto capacidade;
    private double precoDiaria;

    public QuartoAbstrato(String numeroIdentificador, CapacidadeDoQuarto capacidade) {
        this.numeroIdentificador = numeroIdentificador;
        this.capacidade = capacidade;
    }

    public String getNumeroIdentificador() {
        return numeroIdentificador;
    }

    public CapacidadeDoQuarto getCapacidade() {
        return capacidade;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        if (precoDiaria <= 0) {
            throw new IllegalArgumentException("Preço diária deve ser maior que zero");
        }
        this.precoDiaria = precoDiaria;
    }

    @Override
    public String toString() {
        return String.format("Quarto %s [%s] - Capacidade: %d - Preço: R$%.2f - %s",
                numeroIdentificador,
                capacidade,
                precoDiaria);
    }

    public abstract void calcularPrecoDiaria();

}