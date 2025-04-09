package fachada;

import dados.quartos.RepositorioQuartos;
import dados.relatorios.RepositorioRelatorios;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.autenticacao.DataInvalidaException;
import negocio.NegocioAssistenteFinanceiro;

import java.time.YearMonth;

public class FachadaAssistenteFinanceiro {
    private NegocioAssistenteFinanceiro negocioAssistenteFinanceiro;

    public FachadaAssistenteFinanceiro(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos, RepositorioRelatorios repositorioRelatorios) {
        negocioAssistenteFinanceiro = new NegocioAssistenteFinanceiro(repositorioReservas, repositorioQuartos,
                repositorioRelatorios);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return negocioAssistenteFinanceiro.autenticar(email, senha);
    }

    public void gerarRelatorio(String mesAno) throws ErroAoSalvarDadosException, DataInvalidaException {
        try {
            String[] mesAnoArray = mesAno.split("/");
            if (mesAnoArray.length != 2) {
                throw new DataInvalidaException("Formato inválido. O formato esperado é MM/AAAA.");
            }

            int mesInt = Integer.parseInt(mesAnoArray[0]);
            int anoInt = Integer.parseInt(mesAnoArray[1]);

            if (mesInt < 1 || mesInt > 12) {
                throw new DataInvalidaException("Mês inválido. Deve ser entre 1 e 12.");
            }

            YearMonth yearMonth = YearMonth.of(anoInt, mesInt);
            negocioAssistenteFinanceiro.gerarRelatorio(yearMonth);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException excecao) {
            throw new DataInvalidaException("Mês e ano devem ser valores numéricos válidos no formato MM/AAAA.");
        }
    }

}
