package negocio.excecao.quarto;

public class QuartoPersistenciaException extends QuartoException {
    public QuartoPersistenciaException(String mensagem, Throwable causa) {
        super(mensagem + ": " + causa.getMessage());
    }
}