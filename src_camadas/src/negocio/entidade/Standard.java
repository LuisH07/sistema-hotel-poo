package negocio.entidade;

import negocio.entidade.enums.CapacidadeDoQuarto;
import negocio.entidade.enums.CategoriaDoQuarto;

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

}