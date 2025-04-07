package dados.quartos;

import excecoes.dados.*;
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

    public void adicionarQuarto(QuartoAbstrato quarto) throws ErroAoSalvarDadosException {
        quartos.add(quarto);
        salvarQuartos();
    }

    public boolean existe(QuartoAbstrato novoQuarto) {
        return quartos.stream().anyMatch(quarto -> quarto.getNumeroIdentificador().equals(quarto.getNumeroIdentificador()));
    }

    public QuartoAbstrato buscarQuartoPorNumero(String numero){
        return quartos.stream()
                .filter(quarto -> quarto.getNumeroIdentificador().equals(numero))
                .findFirst()
                .orElse(null);
    }

    public List<QuartoAbstrato> listarQuartos() {
        return new ArrayList<>(quartos);
    }

    public void removerQuarto(String numero) throws ErroAoSalvarDadosException {
        quartos.removeIf(quarto -> quarto.getNumeroIdentificador().equals(numero));
        salvarQuartos();
    }

    public void atualizarQuarto(QuartoAbstrato quartoAtualizado) throws ErroAoSalvarDadosException {
        for (int indice = 0; indice < quartos.size(); indice++) {
            QuartoAbstrato quarto = quartos.get(indice);
            if (quarto.getNumeroIdentificador().equals(quartoAtualizado.getNumeroIdentificador())) {
                quartos.set(indice, quartoAtualizado);
                salvarQuartos();
                break;
            }
        }
    }

}