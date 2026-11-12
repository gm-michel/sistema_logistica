package controllers;

import java.util.List;
import exceptions.LogisticaException;
import models.Motorista;
import services.MotoristaService;
import views.MotoristaView;

// Controlador responsável por integrar as telas e as regras de negócio dos motoristas
public class MotoristaController {

    private final MotoristaView motoristaView = new MotoristaView();
    private final MotoristaService motoristaService = new MotoristaService();

    // Inicia e gerencia o menu de motoristas
    public void iniciar() throws LogisticaException {
        int opcao;
        do {
            opcao = motoristaView.menu();
            switch (opcao) {
                case 0: this.listar(); break;
                case 1: this.cadastrar(); break;
                case 2: this.alterar(); break;
                case 3: this.excluir(); break;
            }
        } while (opcao >= 0);
    }

    // Busca a lista de motoristas e solicita a exibição na view
    private void listar() {
        try {
            List<Motorista> lista = motoristaService.listar();
            if (lista.isEmpty()) {
                motoristaView.exibir("LISTAGEM DE MOTORISTAS", "Nenhum motorista cadastrado.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Motorista m : lista) {
                    sb.append(m.toString()).append("\n");
                }
                motoristaView.exibir("LISTAGEM DE MOTORISTAS", sb.toString());
            }
        } catch (Exception e) {
            motoristaView.alertaErro("LISTAGEM DE MOTORISTAS", e.getMessage());
        }
    }

    // Processa a inclusão de um novo motorista
    private void cadastrar() {
        try {
            Motorista motorista = motoristaView.informarDados("CADASTRO DE MOTORISTA", new Motorista());
            if (motorista == null) return;

            if (motoristaService.cadastrar(motorista)) {
                motoristaView.alertaSucesso("CADASTRO DE MOTORISTA", "Motorista cadastrado com sucesso!");
            } else {
                motoristaView.alertaErro("CADASTRO DE MOTORISTA", "Erro ao cadastrar motorista.");
            }
        } catch (Exception e) {
            motoristaView.alertaErro("CADASTRO DE MOTORISTA", e.getMessage());
        }
    }

    // Seleciona um motorista existente via lista e envia as alterações
    private void alterar() {
        try {
            List<Motorista> lista = motoristaService.listar();
            Integer id = motoristaView.selecionarMotorista("ALTERAÇÃO DE MOTORISTA", lista);
            if (id == null) return;

            Motorista motoristaExistente = motoristaService.buscarPorId(id);
            if (motoristaExistente == null) {
                motoristaView.alertaErro("ALTERAÇÃO DE MOTORISTA", "Motorista não encontrado.");
                return;
            }

            Motorista motoristaAlterado = motoristaView.informarDados("ALTERAÇÃO DE MOTORISTA", motoristaExistente);
            if (motoristaAlterado == null) return;

            if (motoristaService.alterar(motoristaAlterado)) {
                motoristaView.alertaSucesso("ALTERAÇÃO DE MOTORISTA", "Motorista alterado com sucesso!");
            } else {
                motoristaView.alertaErro("ALTERAÇÃO DE MOTORISTA", "Erro ao alterar motorista.");
            }
        } catch (Exception e) {
            motoristaView.alertaErro("ALTERAÇÃO DE MOTORISTA", e.getMessage());
        }
    }

    // Solicita confirmação e executa a exclusão
    private void excluir() {
        try {
            List<Motorista> lista = motoristaService.listar();
            Integer id = motoristaView.selecionarMotorista("EXCLUSÃO DE MOTORISTA", lista);
            if (id == null) return;

            Motorista motorista = motoristaService.buscarPorId(id);
            if (motorista == null) {
                motoristaView.alertaErro("EXCLUSÃO DE MOTORISTA", "Motorista não encontrado.");
                return;
            }

            int confirma = motoristaView.alertaConfirmacao(
                    "EXCLUSÃO DE MOTORISTA",
                    "Deseja realmente excluir o motorista " + motorista.getNome() + " (ID: " + motorista.getId() + ")?"
            );

            if (confirma == 0) {
                if (motoristaService.excluir(id)) {
                    motoristaView.alertaSucesso("EXCLUSÃO DE MOTORISTA", "Motorista excluído com sucesso!");
                } else {
                    motoristaView.alertaErro("EXCLUSÃO DE MOTORISTA", "Erro ao excluir motorista.");
                }
            }
        } catch (Exception e) {
            motoristaView.alertaErro("EXCLUSÃO DE MOTORISTA", e.getMessage());
        }
    }
}