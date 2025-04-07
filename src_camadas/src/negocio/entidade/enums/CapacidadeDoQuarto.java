package negocio.entidade.enums;

public enum CapacidadeDoQuarto {
    SIMPLES(1),
    DUPLO(2),
    CASAL(2);

    private final int quantidadeDePessoas;

    CapacidadeDoQuarto(int quantidadeDePessoas) {
        this.quantidadeDePessoas = quantidadeDePessoas;
    }

    public int getQuantidadeDePessoas() {
        return quantidadeDePessoas;
    }

    @Override
    public String toString() {
        String nome = super.toString();
        return (nome.charAt(0) + nome.substring(1).toLowerCase()).replace('_', ' ');
    }
}
