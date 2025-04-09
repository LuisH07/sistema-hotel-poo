package negocio;

import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.cliente.ClienteNaoEncontradoException;

public interface IAutenticacao {

    boolean autenticar(String campo1, String campo2) throws AutenticacaoFalhouException;

}
