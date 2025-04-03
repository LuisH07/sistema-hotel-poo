package negocio.excecao.reserva;

public class ReservaException extends Exception {
    private String msg;

    public ReservaException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
