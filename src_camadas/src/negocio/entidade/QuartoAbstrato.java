package negocio.entidade;

public abstract class QuartoAbstrato {
    private final String numeroIdentificador;
    private String categoria;
    private int capacidade;
    private double precoDiaria;
    private boolean disponivel;

    public QuartoAbstrato(String numeroIdentificador, int capacidade, double precoDiaria) {
        if (numeroIdentificador == null || numeroIdentificador.trim().isEmpty()) {
            throw new IllegalArgumentException("Número identificador não pode ser nulo ou vazio");
        }
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        if (precoDiaria <= 0) {
            throw new IllegalArgumentException("Preço diária deve ser maior que zero");
        }

        this.numeroIdentificador = numeroIdentificador;
        this.capacidade = capacidade;
        this.precoDiaria = precoDiaria;
        this.disponivel = true;
    }
    public String getNumeroIdentificador() {
        return numeroIdentificador;
    }

    public String getCategoria() {
        return categoria;
    }

    protected void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        this.capacidade = capacidade;
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

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return String.format("Quarto %s [%s] - Capacidade: %d - Preço: R$%.2f - %s",
                numeroIdentificador,
                categoria,
                capacidade,
                precoDiaria,
                disponivel ? "Disponível" : "Ocupado");
    }

    public void alterarDisponibilidade() {
        this.disponivel = !this.disponivel;
    }

    public abstract void calcularPrecoDiaria();
}