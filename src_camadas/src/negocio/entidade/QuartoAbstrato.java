package negocio.entidade;

public abstract class QuartoAbstrato {
    private String numeroIdentificador;
    private String categoria;
    private int capacidade;
    private double precoDiaria;
    private boolean disponibilidade;

    public QuartoAbstrato(String numeroIdentificador, int capacidade, double precoDiaria) {
        this.numeroIdentificador = numeroIdentificador;
        this.categoria = null;
        this.capacidade = capacidade;
        this.precoDiaria = precoDiaria;
        this.disponibilidade = true;
    }

    public String getNumeroIdentificador() {
        return numeroIdentificador;
    }

    public void setNumeroIdentificador(String numero) {
        this.numeroIdentificador = numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    @Override
    public String toString() {
        return "Quarto{" +
                "numero=" + numeroIdentificador +
                ", categoria=" + categoria +
                ", capacidade=" + capacidade +
                ", precoDiaria=" + precoDiaria +
                ", disponibilidade=" + disponibilidade +
                '}';
    }

    public void alterarDisponibilidade() {
        disponibilidade = !disponibilidade;
    }

    public abstract void calcularPrecoDiaria();

}
