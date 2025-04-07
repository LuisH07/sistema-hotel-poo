package negocio.entidade;

public class Standard extends QuartoAbstrato {
    private static final double VALOR_BASE = 50.0;
    private static final double VALOR_POR_PESSOA = 50.0;

    public Standard(String numeroIdentificador, int capacidade, double precoDiaria) {
        super(numeroIdentificador, capacidade, precoDiaria);
        setCategoria("Standard");
    }

    public Standard(String numeroIdentificador, int capacidade) {
        this(numeroIdentificador, capacidade, 0);
        calcularPrecoDiaria();
    }

    @Override
    public void calcularPrecoDiaria() {
        double valorCalculado = VALOR_BASE + (VALOR_POR_PESSOA * getCapacidade());
        setPrecoDiaria(valorCalculado);
    }
}