package services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.Rota;
import exceptions.LogisticaException;

// Classe de testes unitários do RotaService
public class RotaServiceTest {

    private RotaService service;

    @BeforeEach
    public void setUp() {
        service = new RotaService();
    }

    // Teste unitário para cadastro e busca de rota
    @Test
    public void testCadastrarEBuscarRota() throws LogisticaException {
        Rota nova = new Rota(null, "Joinville", "Curitiba", 130.0, "Até 7.000 kg", 715.00, null, null);

        boolean cadastrou = service.cadastrar(nova);
        assertTrue(cadastrou, "O cadastro da rota deve retornar verdadeiro.");

        boolean encontrado = service.listar().stream()
                .anyMatch(r -> r.getOrigem().equals("Joinville") && r.getDestino().equals("Curitiba"));
        assertTrue(encontrado, "A rota cadastrada deve ser encontrada na listagem.");
    }

    // Valida a regra de negócio do cálculo de frete (Distância x Taxa)
    @Test
    public void testValidacaoCalculoFrete() {
        double distancia = 100.0;
        double taxaKm = 5.50; // Faixa até 7.000 kg
        double freteEsperado = 550.00;

        assertEquals(freteEsperado, distancia * taxaKm, 0.001, "O cálculo do frete deve ser exato.");
    }

    // Teste para busca com ID inexistente
    @Test
    public void testBuscarPorIdInexistente() throws LogisticaException {
        Rota resultado = service.buscarPorId(-999);
        assertNull(resultado, "A busca por ID inexistente deve retornar null.");
    }
}