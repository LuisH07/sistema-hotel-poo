package negocio.entidade;

import dados.cliente.RepositorioClientes;
import dados.reserva.RepositorioReservas;

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

    public List<Reserva> consultarHistorico() {
        return historicoPessoal.listarReservas();
    }

}
