package fachada;

import dados.relatorios.RepositorioRelatorios;
import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.NegocioGerente;
import negocio.entidade.Reserva;
import java.time.YearMonth;
import java.util.List;

public class FachadaGerente {

    private NegocioGerente negocioGerente;

    public FachadaGerente() throws ErroAoCarregarDadosException {
        negocioGerente = new NegocioGerente(new RepositorioReservas(), new RepositorioQuartos(), new RepositorioRelatorios());
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException {
        return negocioGerente.autenticar(email, senha);
    }

    public List<Reserva> consultarHistorico() {
        return negocioGerente.consultarHistorico();
    }

    public void cancelarReserva(String idReserva) throws ReservaInvalidaException, ReservaNaoEncontradaException,
            ErroAoSalvarDadosException {

        negocioGerente.cancelarReserva(reserva);
    }

    public void gerarRelatorio(YearMonth mesAno) throws ErroAoSalvarDadosException {
        negocioGerente.gerarRelatorio(mesAno);
    }

}
