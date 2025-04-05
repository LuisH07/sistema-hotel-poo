package dados.quartos;

import negocio.entidade.QuartoAbstrato;
import negocio.excecao.quarto.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioQuartos {

    private final String NOME_ARQUIVO = "quartos.dat";
    private List<QuartoAbstrato> repositorioQuartos;

    public RepositorioQuartos() throws QuartoPersistenciaException {
        this.repositorioQuartos = carregarQuartos();
    }

    private List<QuartoAbstrato> carregarQuartos() throws QuartoPersistenciaException {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<QuartoAbstrato>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
            throw new QuartoPersistenciaException("Falha ao carregar quartos do arquivo", excecao);
        }
    }

    public void salvarQuartos() throws QuartoPersistenciaException {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(repositorioQuartos);
        } catch (IOException excecao) {
            throw new QuartoPersistenciaException("Falha ao salvar quartos no arquivo", excecao);
        }
    }

    public void adicionarQuarto(QuartoAbstrato quarto) throws QuartoInvalidoException, QuartoDuplicadoException, QuartoPersistenciaException {
        if (quarto == null) {
            throw new QuartoInvalidoException("Quarto não pode ser nulo.");
        }
        if (buscarQuartoPorNumero(quarto.getNumeroIdentificador()) != null) {
            throw new QuartoDuplicadoException(quarto.getNumeroIdentificador());
        }
        repositorioQuartos.add(quarto);
        salvarQuartos();
    }

    public QuartoAbstrato buscarQuartoPorNumero(String numero) throws QuartoInvalidoException {
        if (numero == null || numero.isEmpty()) {
            throw new QuartoInvalidoException("Número do quarto não pode ser nulo ou vazio.");
        }
        return repositorioQuartos.stream()
                .filter(quarto -> quarto.getNumeroIdentificador().equals(numero))
                .findFirst()
                .orElse(null);
    }

    public QuartoAbstrato obterQuartoPorNumero(String numero) throws QuartoNaoEncontradoException, QuartoInvalidoException {
        QuartoAbstrato quarto = buscarQuartoPorNumero(numero);
        if (quarto == null) {
            throw new QuartoNaoEncontradoException(numero);
        }
        return quarto;
    }

    public List<QuartoAbstrato> listarQuartos() {
        return new ArrayList<>(repositorioQuartos);
    }

    public void removerQuarto(String numero) throws QuartoNaoEncontradoException, QuartoInvalidoException, QuartoPersistenciaException {
        QuartoAbstrato quarto = obterQuartoPorNumero(numero);
        repositorioQuartos.remove(quarto);
        salvarQuartos();
    }

    public int getTamanho() {
        return repositorioQuartos.size();
    }
}