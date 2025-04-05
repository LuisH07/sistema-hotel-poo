package negocio.excecao.quarto;

public class QuartoDuplicadoException extends QuartoException {
    public QuartoDuplicadoException(String numero) {
        super("Quarto com número " + numero + " já existe no sistema.");
    }
}