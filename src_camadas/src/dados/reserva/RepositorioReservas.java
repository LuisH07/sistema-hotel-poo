package dados.reserva;

import excecoes.dados.*;
import negocio.entidade.Reserva;
import negocio.entidade.enums.StatusDaReserva;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Repositório para armazenar e gerenciar informações de reservas.
 * Esta classe permite adicionar, buscar, atualizar, remover e listar reservas,
 * persistindo os dados em um arquivo. Também oferece métodos para listar reservas
 * por cliente, status e quarto.
 *
 * @author [Luiz Henrique]
 * @author [Maria Heloisa]
 */
public class RepositorioReservas {

    private List<Reserva> reservas;
    private final String NOME_ARQUIVO = "reservas.dat";

    public RepositorioReservas() throws ErroAoCarregarDadosException {
        this.reservas = carregarReservas();
    }

    private List<Reserva> carregarReservas() throws ErroAoCarregarDadosException {
        try (ObjectInputStream arquivoLeitura = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Reserva>) arquivoLeitura.readObject();
        } catch (FileNotFoundException excecao) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException excecao) {
            throw new ErroAoCarregarDadosException("Falha ao carregar reservas do arquivo");
        }
    }

    public void salvarReservas() throws ErroAoSalvarDadosException {
        try (ObjectOutputStream arquivoEscrita = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            arquivoEscrita.writeObject(reservas);
        } catch (IOException excecao) {
            throw new ErroAoSalvarDadosException("Falha ao salvar reservas no arquivo");
        }
    }

    public void adicionarReserva(Reserva reserva) throws ErroAoSalvarDadosException {

            reservas.add(reserva);
            salvarReservas();
    }

    public boolean existeReserva(String idReserva) {
        return reservas.stream().anyMatch(reserva -> reserva.getIdReserva().equals(idReserva));
    }

    public Reserva buscarReservaPorId(String idReserva) {
        return reservas.stream()
                .filter(reserva -> reserva.getIdReserva().equals(idReserva))
                .findFirst()
                .orElse(null);
    }

    /**
     * Atualiza os dados de uma reserva existente no repositório. A reserva é identificada pelo ID.
     * Se uma reserva com o mesmo ID da nova reserva for encontrada, seus dados serão substituídos
     * pelos dados da nova reserva e a lista atualizada será salva no arquivo.
     *
     * @param novaReserva A reserva com os dados atualizados. O ID da nova reserva é usado para
     * identificar a reserva a ser atualizada.
     * @throws ErroAoSalvarDadosException Se ocorrer um erro ao salvar os dados no arquivo
     * após a atualização da reserva.
     */
    public void atualizarReserva(Reserva novaReserva) throws ErroAoSalvarDadosException {
        for (int indice = 0; indice < reservas.size(); indice++) {
            Reserva reserva = reservas.get(indice);
            if (reserva.getIdReserva().equals(novaReserva.getIdReserva())) {
                reservas.set(indice, novaReserva);
                salvarReservas();
                break;
            }
        }
    }

    public void removerReserva(String idReserva) throws ErroAoSalvarDadosException {
        reservas.removeIf(reserva -> reserva.getIdReserva().equals(idReserva));
        salvarReservas();
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<Reserva>(reservas);
    }

    public List<Reserva> listarReservasPorCliente(String cpf) {
        return reservas.stream().filter(reserva -> reserva.getCliente().getCpf().equals(cpf)).toList();
    }

    public List<Reserva> listarReservasPorStatus(StatusDaReserva status) {
        return reservas.stream().filter(reserva -> reserva.getStatus().equals(status)).toList();
    }

    public List<Reserva> listarReservasPorQuarto(String numeroIdentificador) {
        return reservas.stream().filter(reserva -> reserva.getQuarto().getNumeroIdentificador().equals(numeroIdentificador)).toList();
    }

}