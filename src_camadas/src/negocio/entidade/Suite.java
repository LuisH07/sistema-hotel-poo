package negocio.entidade;

public class Suite extends QuartoAbstrato {

    public Suite(String numeroIdentificador, int capacidade, double precoDiaria) {
        super(numeroIdentificador, capacidade, precoDiaria);
        setCategoria("Suite");
    }

    @Override
    public void calcularPrecoDiaria() {
        setPrecoDiaria(((getCapacidade() * 50.0) + 100.0) * 1.3);
    }

}