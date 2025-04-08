package negocio;

import excecoes.negocio.autenticacao.AutenticacaoFalhouException;

public interface IAutenticacao {
    boolean autenticar(String email, String senha);
}