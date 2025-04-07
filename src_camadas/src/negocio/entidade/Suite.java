package negocio.entidade;

import negocio.entidade.enums.CapacidadeDoQuarto;
import negocio.entidade.enums.CategoriaDoQuarto;

public class Suite extends QuartoAbstrato {

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