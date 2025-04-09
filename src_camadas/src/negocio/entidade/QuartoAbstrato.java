package negocio.entidade;

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

    public boolean isValido() {
        if (numeroIdentificador == null || capacidade == null || precoDiaria <= 0 || getCategoria() == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Quarto " + numeroIdentificador + ":\n" +
                "- Categoria: " + getCategoria() + "\n" +
                "- Capacidade: " + capacidade + "\n" +
                "- Preço por noite: " + precoDiaria + "\n";
    }

    public abstract void calcularPrecoDiaria();

    public abstract CategoriaDoQuarto getCategoria();

}