package fachada;

import dados.relatorios.RepositorioRelatorios;
import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.NegocioGerente;
import negocio.entidade.Reserva;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class FachadaGerente {

    private NegocioGerente negocioGerente;

    public FachadaGerente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos,
                          RepositorioRelatorios repositorioRelatorios) {
        negocioGerente = new NegocioGerente(repositorioReservas, repositorioQuartos, repositorioRelatorios);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return negocioGerente.autenticar(email, senha);
    }

    public List<Reserva> consultarHistorico() {
        return negocioGerente.consultarHistorico();
    }

    public void cancelarReserva(String idReserva) throws ReservaInvalidaException,
            ReservaNaoEncontradaException,
            ErroAoSalvarDadosException {
        Reserva reserva = negocioGerente.buscarReservaPorId(idReserva);
        negocioGerente.cancelarReserva(reserva);
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
            negocioGerente.gerarRelatorio(mesAno);

        } catch (NumberFormatException excecao) {
            throw new IllegalArgumentException("Mês e ano devem ser valores numéricos válidos.");
        }
    }

}
