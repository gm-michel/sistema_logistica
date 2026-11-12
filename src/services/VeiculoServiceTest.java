package services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.Veiculo;
import exceptions.LogisticaException;

// Classe de testes unitários para o VeiculoService
public class VeiculoServiceTest {

    private VeiculoService service;

    @BeforeEach
    public void setUp() {
        service = new VeiculoService();
    }

    // Teste de cadastro e listagem de veículos
    @Test
    public void testCadastrarEBuscarVeiculo() throws LogisticaException {
        // Placa dinâmica para evitar duplicidade no banco
        String placaTeste = "TST" + (System.currentTimeMillis() % 8999 + 1000);
        Veiculo novo = new Veiculo(null, "Volvo", "FH 540", placaTeste, null);

        boolean cadastrou = service.cadastrar(novo);
        assertTrue(cadastrou, "O cadastro do veículo deve retornar verdadeiro.");

        boolean encontrado = service.listar().stream()
                .anyMatch(v -> v.getPlaca().equals(placaTeste));
        assertTrue(encontrado, "O veículo cadastrado deve ser encontrado na listagem.");
    }

    // Teste de busca por ID inexistente
    @Test
    public void testBuscarPorIdInexistente() throws LogisticaException {
        Veiculo resultado = service.buscarPorId(-999);
        assertNull(resultado, "A busca por ID inexistente deve retornar null.");
    }
}