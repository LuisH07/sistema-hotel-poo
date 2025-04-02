package negocio.entidade;

import dados.reserva.RepositorioReservas;
import java.time.LocalDate;
import java.util.List;

public abstract class FuncionarioAbstrato {
    private String login;
    private String senha;

    public FuncionarioAbstrato(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public boolean verificarLogin(String login) {
        return this.login.equals(login);
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public void fazerReserva(Cliente cliente, QuartoAbstrato quarto, LocalDate dataCheckIn, LocalDate dataCheckOut, RepositorioReservas reservas) {
        if (quarto.isDisponibilidade()) {
            Reserva novaReserva = new Reserva(cliente, quarto, dataCheckIn, dataCheckOut, reservas);
            cliente.getHistoricoPessoal().adicionarReserva(novaReserva);
            quarto.setDisponibilidade(false);
            System.out.println("Reserva realizada com sucesso!");
        } else {
            System.out.println("O quarto não está disponível para as datas selecionadas.");
        }
    }

    public void cancelarReserva(Cliente cliente, Reserva reserva) {
        if (cliente.getHistoricoPessoal().buscarReservaPorId(reserva.getIdReserva()) != null) {
            reserva.setStatus("Cancelada");
            reserva.getQuarto().setDisponibilidade(true);
            System.out.println("Reserva cancelada com sucesso!");
        } else {
            System.out.println("Reserva não encontrada no histórico.");
        }
    }

    public abstract List<Reserva> consultarHistorico(RepositorioReservas reservas);

    public void checkin(Reserva reserva) {
        if (reserva.getStatus().equals("Ativa")) {
            reserva.setStatus("Em uso");
            System.out.println("Check-in realizado. Status da reserva alterado para 'Em uso'.");
        } else {
            System.out.println("Erro: A reserva não está ativa para check-in.");
        }
    }

    public void checkout(Reserva reserva) {
        if (reserva.getStatus().equals("Em uso")) {
            reserva.setStatus("Finalizada");
            System.out.println("Check-out realizado. Status da reserva alterado para 'Finalizada'.");
        } else {
            System.out.println("Erro: A reserva não está em uso para check-out.");
        }
    }

}