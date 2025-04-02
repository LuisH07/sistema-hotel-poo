package dados.quartos;

import negocio.entidade.QuartoAbstrato;

import java.util.ArrayList;
import java.util.List;

public class RepositorioQuartos implements IRepositorioQuartos {

    private List<QuartoAbstrato> quartos;

    public RepositorioQuartos() {
        this.quartos = new ArrayList<>();
    }

    public void adicionarQuarto(QuartoAbstrato quarto) {
        quartos.add(quarto);
    }

    public QuartoAbstrato buscarQuartoPorNumero(String numero) {
        return quartos.stream().filter(quarto -> quarto.getNumeroIdentificador().equals(numero)).findFirst().orElse(null);
    }

    public List<QuartoAbstrato> listarQuartos() {
        return quartos;
    }

    public int tamanho() {
        return quartos.size();
    }

}
