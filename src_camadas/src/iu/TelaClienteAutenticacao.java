package iu;

import dados.cliente.RepositorioClientes;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoSalvarDadosException;
import excecoes.negocio.autenticacao.AutenticacaoFalhouException;
import dados.quartos.RepositorioQuartos;
import excecoes.dados.ErroAoCarregarDadosException;
import excecoes.negocio.autenticacao.CpfInvalidoException;
import excecoes.negocio.autenticacao.EmailInvalidoException;
import excecoes.negocio.cliente.ClienteInvalidoException;
import excecoes.negocio.cliente.ClienteJaExisteException;

import java.util.Scanner;

public class TelaClienteAutenticacao {

    private Scanner scanner;
    private TelaCliente telaCliente;

    public TelaClienteAutenticacao(RepositorioClientes repositorioClientes,
                       RepositorioReservas repositorioReservas, RepositorioQuartos repositorioQuartos) throws ErroAoCarregarDadosException {
        scanner = new Scanner(System.in);
        telaCliente = new TelaCliente(repositorioClientes, repositorioReservas, repositorioQuartos);
    }

    public void iniciar() {
        while (true) {
            System.out.println(">>>> MENU DE CLIENTE <<<<");
            System.out.println("1 - Entrar");
            System.out.println("2 - Cadastrar");
            System.out.println("0 - Voltar");

            String operacao = scanner.nextLine();

            switch (operacao) {
                case "1":
                    autenticarCliente();
                    break;
                case "2":
                    cadastrarCliente();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }

    private void autenticarCliente() {
        System.out.println("\n>>>> LOGIN Cliente <<<<");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        try {
            if (telaCliente.autenticar(email, cpf)) {
                telaCliente.iniciar(cpf);
            }
        } catch (AutenticacaoFalhouException | EmailInvalidoException | CpfInvalidoException excecao) {
            System.out.println("<Erro: " + excecao.getMessage() + ">");
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n>>>> CADASTRAR CLIENTE <<<<");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        try {
            telaCliente.cadastrarCliente(nome, email, cpf);
        } catch (ErroAoSalvarDadosException | EmailInvalidoException | ClienteJaExisteException |
                ClienteInvalidoException | CpfInvalidoException | ErroAoCarregarDadosException excecao) {
            System.out.println("<Erro: " + excecao.getMessage() + ">");
        }
    }

}
