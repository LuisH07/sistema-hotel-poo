package negocio.entidade;

import dados.cliente.RepositorioClientes;
import dados.reserva.RepositorioReservas;
import excecoes.negocio.reserva.ReservaException;
import excecoes.dados.ReservaPersistenciaException;

import java.time.LocalDate;
import java.util.List;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private RepositorioReservas historicoPessoal;

    public Cliente(String cpf, String nome, String email, RepositorioClientes clientes) throws ReservaPersistenciaException {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        historicoPessoal = new RepositorioReservas();
        clientes.adicionarCliente(this);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RepositorioReservas getHistoricoPessoal() {
        return historicoPessoal;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void fazerReserva(QuartoAbstrato quarto, LocalDate dataCheckIn, LocalDate dataCheckOut, RepositorioReservas reservas) {
        if (quarto.isDisponibilidade()) {
            Reserva novaReserva = new Reserva(this, quarto, dataCheckIn, dataCheckOut, reservas);
            historicoPessoal.adicionarReserva(novaReserva);
            quarto.setDisponibilidade(false);
            System.out.println("Reserva realizada com sucesso!");
        } else {
            System.out.println("O quarto não está disponível para as datas selecionadas.");
        }
    }

    public void cancelarReserva(Reserva reserva) throws ReservaException {
        if (historicoPessoal.buscarReservaPorId(reserva.getIdReserva()) != null) {
            reserva.setStatus("Cancelada");
            reserva.getQuarto().setDisponibilidade(true);
            System.out.println("Reserva cancelada com sucesso!");
        } else {
            System.out.println("Reserva não encontrada no histórico.");
        }
    }

    public List<Reserva> consultarHistorico() {
        return historicoPessoal.listarReservas();
    }

}
