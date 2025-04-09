package negocio;

import excecoes.negocio.autenticacao.AutenticacaoFalhouException;

public interface IAutenticacao {

    boolean autenticar(String campo1, String campo2) throws AutenticacaoFalhouException;

}
