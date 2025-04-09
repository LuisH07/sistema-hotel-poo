package negocio;

import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.autenticacao.CpfInvalidoException;
import excecoes.negocio.autenticacao.EmailInvalidoException;

public interface IAutenticacao {

    boolean autenticar(String campo1, String campo2) throws AutenticacaoFalhouException, CpfInvalidoException,
            EmailInvalidoException;

}
