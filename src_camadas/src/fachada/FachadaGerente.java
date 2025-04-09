package fachada;

import dados.relatorios.RepositorioRelatorios;
import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.autenticacao.DataInvalidaException;
import excecoes.negocio.autenticacao.EmailInvalidoException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import negocio.NegocioGerente;
import negocio.entidade.Reserva;

import java.time.YearMonth;
import java.util.List;

public class FachadaGerente {

    private NegocioGerente negocioGerente;

    public FachadaGerente(RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos,
                          RepositorioRelatorios repositorioRelatorios) {
        negocioGerente = new NegocioGerente(repositorioReservas, repositorioQuartos, repositorioRelatorios);
    }

    public boolean autenticar(String email, String senha) throws AutenticacaoFalhouException, EmailInvalidoException {
        return negocioGerente.autenticar(email, senha);
    }

    public String consultarHistorico() {
        List<Reserva> historicoReservas = negocioGerente.consultarHistorico();
        StringBuilder historicoFormatado = new StringBuilder();
        if (historicoReservas != null && !historicoReservas.isEmpty()){
            historicoFormatado.append("Histórico de Reservas: \n\n");
            for (Reserva reserva : historicoReservas) {
                historicoFormatado.append(reserva.toString());
            }
        } else{
            historicoFormatado.append("Nenhum histórico de reservas encontrado!");
        }
        return historicoFormatado.toString();
    }

    public String listarReservasAtivas() {
        List<Reserva> reservasAtivas = negocioGerente.listarReservasAtivas();

        StringBuilder listaDeReservasAtivas = new StringBuilder();
        if (reservasAtivas != null && !reservasAtivas.isEmpty()){
            listaDeReservasAtivas.append("Reservas ativas: \n\n");
            for (Reserva reserva : reservasAtivas) {
                listaDeReservasAtivas.append(reserva.toString());
            }
        } else{
            listaDeReservasAtivas.append("Nenhuma reserva ativa encontrada!");
        }
        return listaDeReservasAtivas.toString();
    }

    public void cancelarReserva(String idReserva) throws ReservaInvalidaException,
            ReservaNaoEncontradaException,
            ErroAoSalvarDadosException {
        Reserva reserva = negocioGerente.buscarReservaPorId(idReserva);
        negocioGerente.cancelarReserva(reserva);
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
            negocioGerente.gerarRelatorio(yearMonth);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException excecao) {
            throw new DataInvalidaException("Mês e ano devem ser valores numéricos válidos no formato MM/AAAA.");
        }
    }

}
