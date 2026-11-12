package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exceptions.LogisticaException;

// Gerenciador de conexão com o banco de dados PostgreSQL
public class ConexaoBD {

    // Parâmetros de conexão JDBC com a base de dados sistema_logistica
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_logistica";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "root";

    // Abre e retorna uma conexão ativa com o banco PostgreSQL
    public static Connection getConexao() throws LogisticaException {
        try {
            // Registra o driver JDBC na aplicação
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new LogisticaException("Driver JDBC do PostgreSQL não foi encontrado nas bibliotecas do projeto.", e);
        } catch (SQLException e) {
            // Trata falhas de autenticação ou rede encapsulando na LogisticaException
            throw new LogisticaException("Erro ao conectar com o banco de dados PostgreSQL: " + e.getMessage(), e);
        }
    }

    // Encerra a conexão com o banco para liberar os recursos do servidor
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}