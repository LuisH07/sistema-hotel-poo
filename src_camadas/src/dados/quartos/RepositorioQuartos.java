package dados.quartos;

import excecoes.dados.*;
import negocio.entidade.QuartoAbstrato;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioQuartos {

    private final String NOME_ARQUIVO = "quartos.dat";
    private List<QuartoAbstrato> repositorioQuartos;

    public RepositorioQuartos() throws ErroAoCarregarDadosException {
        this.repositorioQuartos = carregarQuartos();
    }

    private List<QuartoAbstrato> carregarQuartos() throws ErroAoCarregarDadosException {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<QuartoAbstrato>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
            throw new ErroAoCarregarDadosException("Falha ao carregar quartos do arquivo");
        }
    }

    public void salvarQuartos() throws ErroAoSalvarDadosException {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(repositorioQuartos);
        } catch (IOException excecao) {
            throw new ErroAoSalvarDadosException("Falha ao salvar quartos no arquivo");
        }
    }

    public void adicionarQuarto(QuartoAbstrato quarto) throws ErroAoSalvarDadosException {
        repositorioQuartos.add(quarto);
        salvarQuartos();
    }

    public QuartoAbstrato buscarQuartoPorNumero(String numero) {
        return repositorioQuartos.stream()
                .filter(quarto -> quarto.getNumeroIdentificador().equals(numero))
                .findFirst()
                .orElse(null);
    }

    public List<QuartoAbstrato> listarQuartos() {
        return new ArrayList<>(repositorioQuartos);
    }

    public void removerQuarto(String numero) throws QuartoPersistenciaException {
        QuartoAbstrato quarto = obterQuartoPorNumero(numero);
        repositorioQuartos.remove(quarto);
        salvarQuartos();
    }

    public int getTamanho() {
        return repositorioQuartos.size();
    }
}