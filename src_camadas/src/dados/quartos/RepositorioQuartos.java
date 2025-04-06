package dados.quartos;

import excecoes.dados.*;
import excecoes.negocio.quarto.*;
import negocio.entidade.QuartoAbstrato;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioQuartos {

    private final String NOME_ARQUIVO = "quartos.dat";
    private List<QuartoAbstrato> quartos;

    public RepositorioQuartos() throws ErroAoCarregarDadosException {
        this.quartos = carregarQuartos();
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
            arquivoEscrita.writeObject(quartos);
        } catch (IOException excecao) {
            throw new ErroAoSalvarDadosException("Falha ao salvar quartos no arquivo");
        }
    }

    public void adicionarQuarto(QuartoAbstrato quarto) throws QuartoDuplicadoException, ErroAoSalvarDadosException {
        if (existe(quarto)) {
            throw new QuartoDuplicadoException(quarto.getNumeroIdentificador());
        }
        quartos.add(quarto);
        salvarQuartos();
    }

    public boolean existe(QuartoAbstrato quarto) {
        return quartos.stream()
                .anyMatch(q -> q.getNumeroIdentificador().equals(quarto.getNumeroIdentificador()));
    }

    public QuartoAbstrato buscarQuartoPorNumero(String numero) throws QuartoNaoEncontradoException {
        return quartos.stream()
                .filter(q -> q.getNumeroIdentificador().equals(numero))
                .findFirst()
                .orElseThrow(() -> new QuartoNaoEncontradoException(numero));
    }

    public List<QuartoAbstrato> listarQuartos() {
        return new ArrayList<>(quartos);
    }

    public void removerQuarto(String numero) throws QuartoNaoEncontradoException, ErroAoSalvarDadosException {
        QuartoAbstrato quarto = buscarQuartoPorNumero(numero);
        quartos.remove(quarto);
        salvarQuartos();
    }

    public void atualizarQuarto(QuartoAbstrato quartoAtualizado)
            throws QuartoNaoEncontradoException, ErroAoSalvarDadosException {

        QuartoAbstrato quartoExistente = buscarQuartoPorNumero(quartoAtualizado.getNumeroIdentificador());
        int index = quartos.indexOf(quartoExistente);
        quartos.set(index, quartoAtualizado);
        salvarQuartos();
    }

    public int getTamanho() {
        return quartos.size();
    }
}