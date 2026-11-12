package services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.Motorista;
import exceptions.LogisticaException;

// Classe de testes unitários para validar a regra do MotoristaService
public class MotoristaServiceTest {

    private MotoristaService service;

    @BeforeEach
    public void setUp() {
        service = new MotoristaService();
    }

    // Teste unitário para validar o cadastro e consulta de motoristas
    @Test
    public void testCadastrarEBuscarMotorista() throws LogisticaException {
        String cpfTeste = "999." + System.currentTimeMillis() % 1000000 + "-00";
        Motorista novo = new Motorista(null, "Motorista Teste", cpfTeste, "(47) 99999-9999", "Possui", "B");

        boolean cadastrou = service.cadastrar(novo);
        assertTrue(cadastrou, "O cadastro do motorista deve retornar verdadeiro.");

        boolean encontrado = service.listar().stream()
                .anyMatch(m -> m.getCpf().equals(cpfTeste));
        assertTrue(encontrado, "O motorista cadastrado deve ser encontrado na listagem.");
    }

    // Teste unitário para verificar busca por ID inexistente
    @Test
    public void testBuscarPorIdInexistente() throws LogisticaException {
        Motorista resultado = service.buscarPorId(-999);
        assertNull(resultado, "A busca por ID inexistente deve retornar null.");
    }
}