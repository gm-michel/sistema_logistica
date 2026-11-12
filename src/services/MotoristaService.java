package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exceptions.LogisticaException;
import models.Motorista;
import utils.ConexaoBD;

// Serviço responsável pelas operações de persistência do Motorista no banco
public class MotoristaService {

    // Salva um novo motorista no banco de dados
    public boolean cadastrar(Motorista m) throws LogisticaException {
        String sql = "INSERT INTO motoristas (nome, cpf, telefone, cnh, tipo_cnh) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getCpf());
            stmt.setString(3, m.getTelefone());
            stmt.setString(4, m.getCnh());
            stmt.setString(5, m.getTipoCnh());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao salvar motorista no banco.", e);
        }
    }

    // Retorna a lista de todos os motoristas cadastrados
    public List<Motorista> listar() throws LogisticaException {
        String sql = "SELECT * FROM motoristas ORDER BY id";
        List<Motorista> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Motorista(
                        rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("telefone"), rs.getString("cnh"), rs.getString("tipo_cnh")
                ));
            }
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao listar motoristas.", e);
        }
        return lista;
    }

    // Consulta motorista por ID
    public Motorista buscarPorId(int id) throws LogisticaException {
        String sql = "SELECT * FROM motoristas WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Motorista(
                            rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"),
                            rs.getString("telefone"), rs.getString("cnh"), rs.getString("tipo_cnh")
                    );
                }
            }
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao buscar motorista.", e);
        }
        return null;
    }

    // Atualiza os dados de um motorista existente
    public boolean alterar(Motorista m) throws LogisticaException {
        String sql = "UPDATE motoristas SET nome = ?, cpf = ?, telefone = ?, cnh = ?, tipo_cnh = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getCpf());
            stmt.setString(3, m.getTelefone());
            stmt.setString(4, m.getCnh());
            stmt.setString(5, m.getTipoCnh());
            stmt.setInt(6, m.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao alterar motorista.", e);
        }
    }

    // Remove o registro do motorista pelo ID
    public boolean excluir(int id) throws LogisticaException {
        String sql = "DELETE FROM motoristas WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new LogisticaException("Erro ao excluir motorista.", e);
        }
    }
}