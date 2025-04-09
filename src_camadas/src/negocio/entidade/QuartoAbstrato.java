package negocio.entidade;

import negocio.entidade.enums.CapacidadeDoQuarto;
import negocio.entidade.enums.CategoriaDoQuarto;

import java.io.Serializable;

/**
 * Classe abstrata que representa um quarto de hotel.
 * Contém informações como número de identificação, capacidade e preço da diária.
 * Classes concretas de quartos (Standard e Suite) devem herdar desta classe
 * e implementar os métodos abstratos {@link #calcularPrecoDiaria()} e {@link #getCategoria()}.
 *
 * @author [Arthur]
 */
public abstract class QuartoAbstrato implements Serializable {

    private static final long serialVersionUID = 1L;
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
        return "Quarto " + numeroIdentificador + ": \n" +
                "- Categoria: " + getCategoria() + "\n" +
                "- Capacidade: " + capacidade + "\n" +
                "- Preço por noite: " + precoDiaria + "\n";
    }

    public abstract void calcularPrecoDiaria();

    /**
     * Método abstrato para obter a categoria do quarto.
     * A categoria é definida pela enum {@link CategoriaDoQuarto}.
     *
     * @return A categoria do quarto.
     */
    public abstract CategoriaDoQuarto getCategoria();

}