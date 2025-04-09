package fachada;

import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import negocio.NegocioAssistenteFinanceiro;
import java.time.LocalDate;
import java.time.YearMonth;

public class FachadaAssistenteFinanceiro {
    private NegocioAssistenteFinanceiro negocioAssistenteFinanceiro;

    public FachadaAssistenteFinanceiro(NegocioAssistenteFinanceiro negocioAssistenteFinanceiro) {
        this.negocioAssistenteFinanceiro = negocioAssistenteFinanceiro;
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return negocioAssistenteFinanceiro.autenticar(email, senha);
    }

    public void gerarRelatorio(String mes, String ano) throws ErroAoSalvarDadosException, IllegalArgumentException {
        try {
            int mesInt = Integer.parseInt(mes);
            int anoInt = Integer.parseInt(ano);

            if (mesInt < 1 || mesInt > 12) {
                throw new IllegalArgumentException("Mês inválido. Deve ser entre 1 e 12.");
            }

            if (anoInt < 2000 || anoInt > LocalDate.now().getYear() + 1) {
                throw new IllegalArgumentException("Ano inválido.");
            }

            YearMonth mesAno = YearMonth.of(anoInt, mesInt);
            negocioAssistenteFinanceiro.gerarRelatorio(mesAno);

        } catch (NumberFormatException excecao) {
            throw new IllegalArgumentException("Mês e ano devem ser valores numéricos válidos.");
        }
    }
}