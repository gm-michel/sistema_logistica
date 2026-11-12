package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.LogisticaException;
import models.Motorista;
import models.Rota;
import models.Veiculo;
import utils.ConexaoBD;

// Serviço responsável pelas operações de persistência e cálculo de rotas
public class RotaService {

    // Salva a rota vinculando o motorista e o veículo no banco
    public boolean cadastrar(Rota rota) throws LogisticaException {
        String sql = "INSERT INTO rotas (origem, destino, distancia, carga, valor_frete, id_motorista, id_veiculo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rota.getOrigem());
            stmt.setString(2, rota.getDestino());
            stmt.setDouble(3, rota.getDistancia());
            stmt.setString(4, rota.getCarga());
            stmt.setDouble(5, rota.getValorFrete());

            if (rota.getMotorista() != null && rota.getMotorista().getId() != null) {
                stmt.setInt(6, rota.getMotorista().getId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            if (rota.getVeiculo() != null && rota.getVeiculo().getId() != null) {
                stmt.setInt(7, rota.getVeiculo().getId());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao cadastrar rota no banco de dados.", e);
        }
    }

    // Retorna as rotas cadastradas com os dados do motorista e veículo associados
    public List<Rota> listar() throws LogisticaException {
        String sql = "SELECT r.id AS r_id, r.origem, r.destino, r.distancia, r.carga, r.valor_frete, " +
                "m.id AS m_id, m.nome, m.cpf, m.telefone, m.cnh, m.tipo_cnh, " +
                "v.id AS v_id, v.marca, v.modelo, v.placa " +
                "FROM rotas r " +
                "LEFT JOIN motoristas m ON r.id_motorista = m.id " +
                "LEFT JOIN veiculos v ON r.id_veiculo = v.id " +
                "ORDER BY r.id";
        List<Rota> lista = new ArrayList<>();
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

                Veiculo v = null;
                if (rs.getObject("v_id") != null) {
                    v = new Veiculo(
                            rs.getInt("v_id"), rs.getString("marca"), rs.getString("modelo"),
                            rs.getString("placa"), m
                    );
                }

                Rota r = new Rota(
                        rs.getInt("r_id"), rs.getString("origem"), rs.getString("destino"),
                        rs.getDouble("distancia"), rs.getString("carga"), rs.getDouble("valor_frete"),
                        m, v
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao listar rotas.", e);
        }
        return lista;
    }

    // Consulta rota por ID com os seus relacionamentos
    public Rota buscarPorId(int id) throws LogisticaException {
        String sql = "SELECT r.id AS r_id, r.origem, r.destino, r.distancia, r.carga, r.valor_frete, " +
                "m.id AS m_id, m.nome, m.cpf, m.telefone, m.cnh, m.tipo_cnh, " +
                "v.id AS v_id, v.marca, v.modelo, v.placa " +
                "FROM rotas r " +
                "LEFT JOIN motoristas m ON r.id_motorista = m.id " +
                "LEFT JOIN veiculos v ON r.id_veiculo = v.id " +
                "WHERE r.id = ?";
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

                    Veiculo v = null;
                    if (rs.getObject("v_id") != null) {
                        v = new Veiculo(
                                rs.getInt("v_id"), rs.getString("marca"), rs.getString("modelo"),
                                rs.getString("placa"), m
                        );
                    }

                    return new Rota(
                            rs.getInt("r_id"), rs.getString("origem"), rs.getString("destino"),
                            rs.getDouble("distancia"), rs.getString("carga"), rs.getDouble("valor_frete"),
                            m, v
                    );
                }
            }
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao buscar rota por ID.", e);
        }
        return null;
    }

    // Atualiza as informações da rota no banco
    public boolean alterar(Rota rota) throws LogisticaException {
        String sql = "UPDATE rotas SET origem = ?, destino = ?, distancia = ?, carga = ?, valor_frete = ?, " +
                "id_motorista = ?, id_veiculo = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rota.getOrigem());
            stmt.setString(2, rota.getDestino());
            stmt.setDouble(3, rota.getDistancia());
            stmt.setString(4, rota.getCarga());
            stmt.setDouble(5, rota.getValorFrete());

            if (rota.getMotorista() != null && rota.getMotorista().getId() != null) {
                stmt.setInt(6, rota.getMotorista().getId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            if (rota.getVeiculo() != null && rota.getVeiculo().getId() != null) {
                stmt.setInt(7, rota.getVeiculo().getId());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            stmt.setInt(8, rota.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao alterar rota no banco de dados.", e);
        }
    }

    // Remove a rota informada pelo ID
    public boolean excluir(int id) throws LogisticaException {
        String sql = "DELETE FROM rotas WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao excluir rota do banco de dados.", e);
        }
    }
}