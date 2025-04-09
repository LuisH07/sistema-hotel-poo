package iu;

import dados.cliente.RepositorioClientes;
import dados.quartos.RepositorioQuartos;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import excecoes.negocio.autenticacao.CpfInvalidoException;
import excecoes.negocio.autenticacao.DataInvalidaException;
import excecoes.negocio.autenticacao.EmailInvalidoException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.cliente.ClienteJaExisteException;
import excecoes.negocio.cliente.ClienteNaoEncontradoException;
import excecoes.negocio.quarto.QuartoInvalidoException;
import excecoes.negocio.quarto.QuartoNaoEncontradoException;
import excecoes.negocio.reserva.ConflitoDeDatasException;
import excecoes.negocio.reserva.ReservaInvalidaException;
import excecoes.negocio.reserva.ReservaJaCadastradaException;
import excecoes.negocio.reserva.ReservaNaoEncontradaException;
import fachada.FachadaCliente;

import java.util.Scanner;

public class TelaCliente {
    private Scanner scanner;
    private FachadaCliente fachada;

    public TelaCliente(RepositorioClientes repositorioClientes,
                                   RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos) throws ErroAoCarregarDadosException {
        scanner = new Scanner(System.in);
        fachada = new FachadaCliente(repositorioClientes, repositorioReservas, repositorioQuartos);
    }

    public boolean autenticar(String email, String cpf) throws AutenticacaoFalhouException, EmailInvalidoException, CpfInvalidoException {
        return fachada.autenticar(email, cpf);
    }

    public void cadastrarCliente(String nome, String email, String cpf) throws ErroAoSalvarDadosException, EmailInvalidoException, ClienteJaExisteException, ClienteInvalidoException, CpfInvalidoException, ErroAoCarregarDadosException {
        fachada.cadastrarCliente(nome, email, cpf);
    }

    public void iniciar(String cpf) {
        while (true) {
            System.out.println("\n\n>>>> MENU CLIENTE <<<<");
            System.out.println("1 - Fazer reserva");
            System.out.println("2 - Gerenciar Reservas");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    fazerReserva(cpf);
                    break;
                case "2":
                    gerenciarReservas(cpf);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void fazerReserva(String cpf) {
        System.out.println("\n>>>> FAZER RESERVA <<<<");
        System.out.print("Data de entrada (dd/mm/aaaa): ");
        String dataEntrada = scanner.nextLine();
        System.out.print("Data de saída (dd/mm/aaaa): ");
        String dataSaida = scanner.nextLine();

        try {
            fachada.listarQuartosDisponiveisNoPeriodo(dataEntrada, dataSaida);
            System.out.println("ID do quarto: ");
            String idQuarto = scanner.nextLine();
            fachada.fazerReserva(cpf, idQuarto, dataEntrada, dataSaida);
        } catch (DataInvalidaException | ConflitoDeDatasException | ErroAoSalvarDadosException |
                QuartoNaoEncontradoException | ReservaInvalidaException | ClienteInvalidoException |
                ReservaJaCadastradaException | QuartoInvalidoException | ClienteNaoEncontradoException excecao) {
            System.out.println("<Erro ao gerar relatório: " + excecao.getMessage() + ">");
        }
    }

    private void gerenciarReservas(String cpf){
        while (true) {
            System.out.println("\n\n>>>> GERENCIAMENTO DE RESERVAS <<<<");
            System.out.println("1 - Consultar Histórico de Todas as Reservas");
            System.out.println("2 - Cancelar Reserva");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    consultarReservas(cpf);
                    break;
                case "2":
                    cancelarReserva(cpf);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void consultarReservas(String cpf) {
        try {
            System.out.println("\n>>>> HISTÓRICO DE RESERVAS <<<<\n");
            String historico = fachada.consultarHistorico(cpf);
            System.out.println(historico);
        } catch (ClienteInvalidoException | ClienteNaoEncontradoException excecao) {
            System.out.println("<Erro ao gerar relatório: " + excecao.getMessage() + ">");
        }
    }

    private void cancelarReserva(String cpf) {
        System.out.println("\n>>>> CANCELAR RESERVA <<<<\n");

        String listaDeReservasAtivas = fachada.listarReservasAtivasPorCliente(cpf);
        System.out.println(listaDeReservasAtivas);

        System.out.print("ID da Reserva: ");
        String idReserva = scanner.nextLine();

        try {
            fachada.cancelarReserva(idReserva, cpf);
            System.out.println("<Reserva cancelada com sucesso!>");
        } catch (ErroAoSalvarDadosException | ReservaNaoEncontradaException | ReservaInvalidaException excecao) {
            System.out.println("<Erro ao cancelar reserva: " + excecao.getMessage() + ">");
        } catch (ClienteInvalidoException | ClienteNaoEncontradoException excecao) {
            System.out.println("<Erro ao gerar relatório: " + excecao.getMessage() + ">");
        }
    }

}
