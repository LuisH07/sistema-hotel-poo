package dados.relatorios;

import excecoes.dados.ErroAoSalvarDadosException;

import java.io.FileWriter;
import java.io.IOException;

public class RepositorioRelatorios {

    public void salvarRelatorio(String conteudoRelatorio, String nomeRelatorio) throws ErroAoSalvarDadosException {
        try (FileWriter escritor = new FileWriter(nomeRelatorio)) {
            escritor.write(conteudoRelatorio);
        } catch (IOException excecao) {
            throw new ErroAoSalvarDadosException("Falha ao salvar relatorio");
        }
    }

}