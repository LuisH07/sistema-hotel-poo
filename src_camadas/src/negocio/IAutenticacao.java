package negocio;

public interface IAutenticacao {
    boolean autenticar(String email, String senha);
}