package negocio;

import java.io.IOException;
import java.time.LocalDate;

public interface IFluxoRelatorio {

    public void gerarRelatorio(LocalDate dataInicio, LocalDate dataFim, String caminhoArquivo) throws IOException;

}
