package excecoes.negocio.quarto;

public class QuartoNaoEncontradoException extends QuartoException {
    public QuartoNaoEncontradoException(String numero) {
        super("Quarto com número " + numero + " não encontrado.");
    }
}