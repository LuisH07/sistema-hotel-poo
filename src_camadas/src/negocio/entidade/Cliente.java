package negocio.entidade;

import excecoes.dados.ErroAoCarregarDadosException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private List<Reserva> historicoReservas;

    public Cliente(String cpf, String nome, String email) throws ErroAoCarregarDadosException {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.historicoReservas = new ArrayList<>();
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void fazerReserva(LocalDate dataInicio, LocalDate dataFim, String detalhes) {
        if (dataInicio == null || dataFim == null) {
            System.out.println("Erro: As datas de início e fim da reserva não podem ser nulas");
            return;
        }

        if (dataInicio.isAfter(dataFim)) {
            System.out.println("Erro: A data de início da reserva não pode ser posterior à data de fim");
            return;
        }

        if (dataInicio.isBefore(LocalDate.now())) {
            System.out.println("Erro: A data de início da reserva não pode ser no passado");
            return;
        }

        Reserva novaReserva = new Reserva(dataInicio, dataFim, detalhes);
        historicoReservas.add(novaReserva);
        System.out.println("Reserva realizada com sucesso para o período de " + dataInicio + " a " + dataFim);
    }

    public void cancelarReserva(LocalDate dataInicio) {
        historicoReservas.removeIf(reserva -> reserva.getDataInicio().equals(dataInicio));
        System.out.println("Reserva para o dia " + dataInicio + " cancelada");
    }

    public void consultarHistorico() {
        if (historicoReservas.isEmpty()) {
            System.out.println("O histórico de reservas está vazio");
        } else {
            System.out.println("Histórico de Reservas: ");
            for (Reserva reserva : historicoReservas) {
                System.out.println(" De " + reserva.getDataInicio() + " até " + reserva.getDataFim() + ": " + reserva.getDetalhes());
            }
        }
    }

    // classe interna que vai representar uma reserva
    private static class Reserva {
        private LocalDate dataInicio;
        private LocalDate dataFim;
        private String detalhes;

        public Reserva(LocalDate dataInicio, LocalDate dataFim, String detalhes) {
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.detalhes = detalhes;
        }

        public LocalDate getDataInicio() {
            return dataInicio;
        }

        public LocalDate getDataFim() {
            return dataFim;
        }

        public String getDetalhes() {
            return detalhes;
        }
    }
}