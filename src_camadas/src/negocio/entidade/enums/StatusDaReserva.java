package negocio.entidade.enums;

public enum StatusDaReserva {
    ATIVA,
    EM_USO,
    FINALIZADA,
    CANCELADA;

    @Override
    public String toString() {
        String nome = super.toString();
        return (nome.charAt(0) + nome.substring(1).toLowerCase()).replace('_', ' ');
    }
}
