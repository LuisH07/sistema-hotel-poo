package negocio.entidade;

import negocio.entidade.enums.CapacidadeDoQuarto;
import negocio.entidade.enums.CategoriaDoQuarto;

/**
 * Representa um quarto de categoria Standard.
 * Herda da classe abstrata {@link QuartoAbstrato} e implementa a lógica
 * específica para o cálculo do preço da diária e define sua categoria.
 *
 * @author [Maria Heloisa]
 */
public class Standard extends QuartoAbstrato {

    private final CategoriaDoQuarto CATEGORIA = CategoriaDoQuarto.STANDARD;
    private static final double VALOR_BASE = 50.0;
    private static final double VALOR_POR_PESSOA = 50.0;

    public Standard(String numeroIdentificador, CapacidadeDoQuarto capacidade) {
        super(numeroIdentificador, capacidade);
        calcularPrecoDiaria();
    }

    @Override
    public void calcularPrecoDiaria() {
        double valorCalculado = VALOR_BASE + (VALOR_POR_PESSOA * getCapacidade().getQuantidadeDePessoas());
        setPrecoDiaria(valorCalculado);
    }

    @Override
    public CategoriaDoQuarto getCategoria() {
        return CATEGORIA;
    }

}