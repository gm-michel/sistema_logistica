package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.LogisticaException;
import models.Motorista;
import models.Veiculo;
import utils.ConexaoBD;

// Serviço responsável pela persistência e relatórios de veículos
public class VeiculoService {

    // Insere o veículo no banco com chave estrangeira para o motorista
    public boolean cadastrar(Veiculo veiculo) throws LogisticaException {
        String sql = "INSERT INTO veiculos (marca, modelo, placa, id_motorista) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            if (veiculo.getMotorista() != null && veiculo.getMotorista().getId() != null) {
                stmt.setInt(4, veiculo.getMotorista().getId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao cadastrar veículo no banco de dados.", e);
        }
    }

    // Retorna os veículos cadastrados e seus respectivos motoristas
    public List<Veiculo> listar() throws LogisticaException {
        String sql = "SELECT v.id AS v_id, v.marca, v.modelo, v.placa, " +
                "m.id AS m_id, m.nome, m.cpf, m.telefone, m.cnh, m.tipo_cnh " +
                "FROM veiculos v " +
                "LEFT JOIN motoristas m ON v.id_motorista = m.id " +
                "ORDER BY v.id";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Motorista m = null;
                if (rs.getObject("m_id") != null) {
                    m = new Motorista(
                            rs.getInt("m_id"), rs.getString("nome"), rs.getString("cpf"),
                            rs.getString("telefone"), rs.getString("cnh"), rs.getString("tipo_cnh")
                    );
                }
                Veiculo v = new Veiculo(
                        rs.getInt("v_id"), rs.getString("marca"), rs.getString("modelo"),
                        rs.getString("placa"), m
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao listar veículos.", e);
        }
        return lista;
    }

    // Busca o veículo pelo ID
    public Veiculo buscarPorId(int id) throws LogisticaException {
        String sql = "SELECT v.id AS v_id, v.marca, v.modelo, v.placa, " +
                "m.id AS m_id, m.nome, m.cpf, m.telefone, m.cnh, m.tipo_cnh " +
                "FROM veiculos v " +
                "LEFT JOIN motoristas m ON v.id_motorista = m.id " +
                "WHERE v.id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Motorista m = null;
                    if (rs.getObject("m_id") != null) {
                        m = new Motorista(
                                rs.getInt("m_id"), rs.getString("nome"), rs.getString("cpf"),
                                rs.getString("telefone"), rs.getString("cnh"), rs.getString("tipo_cnh")
                        );
                    }
                    return new Veiculo(
                            rs.getInt("v_id"), rs.getString("marca"), rs.getString("modelo"),
                            rs.getString("placa"), m
                    );
                }
            }
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao buscar veículo por ID.", e);
        }
        return null;
    }

    // Atualiza os dados do veículo no banco
    public boolean alterar(Veiculo veiculo) throws LogisticaException {
        String sql = "UPDATE veiculos SET marca = ?, modelo = ?, placa = ?, id_motorista = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            if (veiculo.getMotorista() != null && veiculo.getMotorista().getId() != null) {
                stmt.setInt(4, veiculo.getMotorista().getId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.setInt(5, veiculo.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao alterar veículo no banco de dados.", e);
        }
    }

    // Exclui o veículo do banco de dados
    public boolean excluir(int id) throws LogisticaException {
        String sql = "DELETE FROM veiculos WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao excluir veículo do banco de dados.", e);
        }
    }
}