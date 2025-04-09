package dados.quartos;

import excecoes.dados.*;
import negocio.entidade.QuartoAbstrato;
import negocio.entidade.Standard;
import negocio.entidade.Suite;
import negocio.entidade.enums.CapacidadeDoQuarto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioQuartos {

    private final String NOME_ARQUIVO = "quartos.dat";
    private List<QuartoAbstrato> quartos;

    public RepositorioQuartos() throws ErroAoCarregarDadosException {
        this.quartos = carregarQuartos();
        inicializarQuartos();
    }

    private List<QuartoAbstrato> carregarQuartos() throws ErroAoCarregarDadosException {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<QuartoAbstrato>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
        throw new ErroAoCarregarDadosException("Falha ao carregar quartos do arquivo", excecao);
        }

    }

    private void inicializarQuartos() {
        if (quartos.isEmpty()) {
            String numeroIdentificador = "Q" + quartos.size() + 101;
            quartos.add(new Standard(numeroIdentificador, CapacidadeDoQuarto.SIMPLES));
            quartos.add(new Standard(numeroIdentificador, CapacidadeDoQuarto.DUPLO));
            quartos.add(new Standard(numeroIdentificador, CapacidadeDoQuarto.CASAL));
            quartos.add(new Standard(numeroIdentificador, CapacidadeDoQuarto.SIMPLES));
            quartos.add(new Standard(numeroIdentificador, CapacidadeDoQuarto.CASAL));
            quartos.add(new Suite(numeroIdentificador, CapacidadeDoQuarto.SIMPLES));
            quartos.add(new Suite(numeroIdentificador, CapacidadeDoQuarto.DUPLO));
            quartos.add(new Suite(numeroIdentificador, CapacidadeDoQuarto.CASAL));
            quartos.add(new Suite(numeroIdentificador, CapacidadeDoQuarto.SIMPLES));
            quartos.add(new Suite(numeroIdentificador, CapacidadeDoQuarto.CASAL));
            try {
                salvarQuartos();
            } catch (ErroAoSalvarDadosException excecao) {
                excecao.printStackTrace();
            }
        }
    }

    public void salvarQuartos() throws ErroAoSalvarDadosException {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(quartos);
        } catch (IOException excecao) {
            throw new ErroAoSalvarDadosException("Falha ao salvar quartos no arquivo");
        }
    }

    public boolean existeQuarto(String numero) {
        return quartos.stream().anyMatch(quarto -> quarto.getNumeroIdentificador().equals(numero));
    }

    public List<QuartoAbstrato> listarQuartos() {
        return new ArrayList<>(quartos);
    }

    public QuartoAbstrato buscarQuartoPorNumero(String numero) {
        return quartos.stream()
                .filter(quarto -> quarto.getNumeroIdentificador().equals(numero))
                .findFirst()
                .orElse(null);
    }

}
