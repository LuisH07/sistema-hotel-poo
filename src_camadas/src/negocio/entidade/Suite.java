package negocio.entidade;

import negocio.entidade.enums.CapacidadeDoQuarto;
import negocio.entidade.enums.CategoriaDoQuarto;

import java.io.Serializable;

/**
 * Representa um quarto de categoria Suite.
 * Herda da classe abstrata {@link QuartoAbstrato} e implementa a lógica
 * específica para o cálculo do preço da diária (incluindo uma taxa adicional)
 * e define sua categoria.
 *
 * @author [Arthur]
 * @author [Maria Heloisa]
 */
public class Suite extends QuartoAbstrato implements Serializable {
    private static final long serialVersionUID = 1L;

    private final CategoriaDoQuarto CATEGORIA = CategoriaDoQuarto.SUITE;
    private static final double VALOR_BASE = 100.0;
    private static final double VALOR_POR_PESSOA = 70.0;
    private static final double TAXA = 1.3;

    public Suite(String numeroIdentificador, CapacidadeDoQuarto capacidade) {
        super(numeroIdentificador, capacidade);
        calcularPrecoDiaria();
    }

    @Override
    public void calcularPrecoDiaria() {
        double valorCalculado =
                (VALOR_BASE + (VALOR_POR_PESSOA * getCapacidade().getQuantidadeDePessoas())) * TAXA;
        setPrecoDiaria(valorCalculado);
    }

    @Override
    public CategoriaDoQuarto getCategoria() {
        return CATEGORIA;
    }

}