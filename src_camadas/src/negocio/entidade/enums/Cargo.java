package negocio.entidade.enums;

public enum Cargo {
    ATENDENTE("atendente@hotel.com", "1234"),
    GERENTE("gerente@hotel.com", "admin"),
    ASSISTENTE_FINANCEIRO("financeiro@hotel.com", "senha");

    private final String email;
    private final String senha;

    Cargo(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        String nome = super.toString();
        return (nome.charAt(0) + nome.substring(1).toLowerCase()).replace('_', ' ');
    }

}



