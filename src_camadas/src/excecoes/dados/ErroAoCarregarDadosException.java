package excecoes.dados;

public class ErroAoCarregarDadosException extends Exception {
    public ErroAoCarregarDadosException(String mensagem) {
        super(mensagem);
    }

    public ErroAoCarregarDadosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
