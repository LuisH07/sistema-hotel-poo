package negocio.entidade;

public class Standard extends QuartoAbstrato {

    public Standard(String numeroIdentificador, int capacidade, double precoDiaria) {
        super(numeroIdentificador, capacidade, precoDiaria);
        setCategoria("Standard");
    }

    @Override
    public void calcularPrecoDiaria() {
        setPrecoDiaria((getCapacidade() * 50.0) + 50.0);
    }

}