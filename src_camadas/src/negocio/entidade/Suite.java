package negocio.entidade;

public class Suite extends QuartoAbstrato {
    private static final double VALOR_BASE = 100.0;
    private static final double VALOR_POR_PESSOA = 50.0;
    private static final double TAXA = 1.3;

    public Suite(String numeroIdentificador, int capacidade, double precoDiaria) {
        super(numeroIdentificador, capacidade, precoDiaria);
        setCategoria("Suíte");
    }

    public Suite(String numeroIdentificador, int capacidade) {
        this(numeroIdentificador, capacidade, 0);
        calcularPrecoDiaria();
    }

    @Override
    public void calcularPrecoDiaria() {
        double valorCalculado = (VALOR_BASE + (VALOR_POR_PESSOA * getCapacidade())) * TAXA;
        setPrecoDiaria(valorCalculado);
    }
}