package main;

import dados.cliente.RepositorioClientes;
import dados.quartos.RepositorioQuartos;
import dados.relatorios.RepositorioRelatorios;
import dados.reserva.RepositorioReservas;
import excecoes.dados.ErroAoCarregarDadosException;
import iu.TelaInicial;

public class Main {

    public static void main(String[] args) throws ErroAoCarregarDadosException {

        RepositorioReservas repositorioReservas = new RepositorioReservas();
        RepositorioQuartos repositorioQuartos = new RepositorioQuartos();
        RepositorioClientes repositorioClientes = new RepositorioClientes();
        RepositorioRelatorios repositorioRelatorios = new RepositorioRelatorios();

        TelaInicial telaInicial = new TelaInicial(repositorioReservas, repositorioQuartos, repositorioClientes, repositorioRelatorios);
        telaInicial.iniciar();
    }

}
