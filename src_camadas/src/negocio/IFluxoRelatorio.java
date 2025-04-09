package negocio;

import excecoes.dados.ErroAoSalvarDadosException;

import java.time.YearMonth;

public interface IFluxoRelatorio {

    void gerarRelatorio(YearMonth mesAno) throws ErroAoSalvarDadosException;

}
