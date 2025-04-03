package dados.quartos;

import negocio.entidade.Cliente;
import negocio.entidade.QuartoAbstrato;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioQuartos {

    private final String NOME_ARQUIVO = "quartos.dat";
    private List<QuartoAbstrato> repositorioQuartos;

    public RepositorioQuartos() {
        this.repositorioQuartos = carregarQuartos();
    }

    private List<QuartoAbstrato> carregarQuartos()  {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<QuartoAbstrato>) arquivoLeitura.readObject();
        } catch (FileNotFoundException execao) {
            return new ArrayList<QuartoAbstrato>();
        } catch (IOException | ClassNotFoundException execao) {
            execao.printStackTrace();
            return new ArrayList<QuartoAbstrato>();
        }
    }

    public void salvarQuartos() {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(repositorioQuartos);
        } catch (IOException execao) {
            execao.printStackTrace();
        }
    }

    public void adicionarQuarto(QuartoAbstrato quarto) {
        repositorioQuartos.add(quarto);
        salvarQuartos();
    }

    public QuartoAbstrato buscarQuartoPorNumero(String numero) {
        return repositorioQuartos.stream().filter(quarto -> quarto.getNumeroIdentificador().equals(numero)).findFirst().orElse(null);
    }

    public List<QuartoAbstrato> listarQuartos() {
        return new ArrayList<QuartoAbstrato>(repositorioQuartos);
    }

    public int getTamanho() {
        return repositorioQuartos.size();
    }

}
