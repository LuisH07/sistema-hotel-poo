package negocio;

import dados.quartos.RepositorioQuartos;
import dados.relatorios.RepositorioRelatorios;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import negocio.entidade.enums.Cargo;
import negocio.entidade.enums.CategoriaDoQuarto;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Classe que implementa a lógica de negócios para o Assistente Financeiro.
 * Oferece funcionalidades de autenticação e geração de relatórios financeiros mensais.
 * Implementa as interfaces {@link IAutenticacao} e {@link IFluxoRelatorio}.
 *
 * @author [Luiz Henrique]
 * @author [Maria Heloisa]
 */
public class NegocioAssistenteFinanceiro implements IAutenticacao, IFluxoRelatorio {

    private RepositorioReservas repositorioReservas;
    private RepositorioQuartos repositorioQuartos;
    private RepositorioRelatorios repositorioRelatorios;

    public NegocioAssistenteFinanceiro(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos,
                          RepositorioRelatorios repositorioRelatorios) {
        this.repositorioReservas = repositorioReservas;
        this.repositorioQuartos = repositorioQuartos;
        this.repositorioRelatorios = repositorioRelatorios;
    }

    /**
     * Autentica um funcionário com base no email e senha fornecidos.
     * Para o Assistente Financeiro, verifica se as credenciais coincidem
     * com as definidas para o cargo {@link Cargo#ASSISTENTE_FINANCEIRO}.
     *
     * @param email O email do funcionário.
     * @param senha A senha do funcionário.
     * @return {@code true} se a autenticação for bem-sucedida.
     * @throws AutenticacaoFalhouException Se as credenciais forem inválidas.
     */
    @Override
    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        if (!email.equals(Cargo.ASSISTENTE_FINANCEIRO.getEmail()) || !senha.equals(Cargo.ASSISTENTE_FINANCEIRO.getSenha())) {
            throw new AutenticacaoFalhouException("Credenciais inválidas.");
        }
        return true;
    }

    /**
     * Gera um relatório financeiro mensal para o mês e ano especificados.
     * O relatório inclui informações sobre receita total, receita por categoria de quarto,
     * receita perdida com cancelamentos, ticket médio por cliente e valores médios por diária e reserva.
     * O relatório é salvo em um arquivo de texto.
     *
     * @param mesAno O mês e ano para o qual o relatório deve ser gerado.
     * @throws ErroAoSalvarDadosException Se ocorrer um erro ao salvar o relatório no arquivo.
     */
    @Override
    public void gerarRelatorio(YearMonth mesAno) throws ErroAoSalvarDadosException {
        GeradorRelatorioMensal geradorRelatorio = new GeradorRelatorioMensal(mesAno, repositorioReservas, repositorioQuartos);

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("Relatório Financeiro Mensal\n");
        relatorio.append("Data do relatório: ").append(mesAno.format(DateTimeFormatter.ofPattern("MM/yyyy"))).append("\n" +
                "\n");

        double receitaTotal = geradorRelatorio.calcularReceitaTotalNoMes();
        relatorio.append("Visão Geral de Receitas\n");
        relatorio.append("Receita do mês\n");
        relatorio.append("Receita total: R$").append(String.format("%.2f", receitaTotal)).append("\n");

        relatorio.append("Receitas por categoria de quarto\n");
        double receitaStandard = geradorRelatorio.calcularReceitaPorCategoriaDeQuarto(CategoriaDoQuarto.STANDARD);
        double mediaStandard = geradorRelatorio.calcularValorMedioPorCategoriaDeQuarto(CategoriaDoQuarto.STANDARD);
        double receitaSuite = geradorRelatorio.calcularReceitaPorCategoriaDeQuarto(CategoriaDoQuarto.SUITE);
        double mediaSuite = geradorRelatorio.calcularValorMedioPorCategoriaDeQuarto(CategoriaDoQuarto.SUITE);

        relatorio.append("Standard:").append("\n");
        relatorio.append("- Receita: R$").append(String.format("%.2f", receitaStandard)).append("\n");
        relatorio.append("- Média por reserva: R$").append(String.format("%.2f", mediaStandard)).append("\n\n");

        relatorio.append("Suíte:").append("\n");
        relatorio.append("- Receita: R$").append(String.format("%.2f", receitaSuite)).append("\n");
        relatorio.append("- Média por reserva: R$").append(String.format("%.2f", mediaSuite)).append("\n\n");

        double valorPerdido = geradorRelatorio.calcularValorPerdidoPorCancelamentos();
        relatorio.append("Receita perdida com cancelamento").append("\n");
        relatorio.append("Perdas com Cancelamentos: R$").append(String.format("%.2f", valorPerdido)).append("\n\n");

        double ticketMedio = geradorRelatorio.calcularTicketMedioPorCliente();

        relatorio.append("Ticket medio por cliente\n");
        relatorio.append("Ticket médio: R$").append(String.format("%.2f", ticketMedio)).append("\n\n");

        relatorio.append("Valores médios\n");
        relatorio.append("Por diária: R$").append(String.format("%.2f", geradorRelatorio.calcularValorMedioPorDiaria())).append("\n");
        relatorio.append("Por reserva: R$ ").append(String.format("%.2f", geradorRelatorio.calcularValorMedioPorReserva())).append("\n");

        String nomeArquivo = "RelatorioFinanceiro_" + mesAno.format(DateTimeFormatter.ofPattern("MM_yyyy")) + ".txt";
        repositorioRelatorios.salvarRelatorio(relatorio.toString(), nomeArquivo);
    }

}
