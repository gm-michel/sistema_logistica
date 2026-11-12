package controllers;

import java.util.List;
import exceptions.LogisticaException;
import models.Motorista;
import models.Veiculo;
import services.MotoristaService;
import services.VeiculoService;
import views.VeiculoView;

// Controlador das ações e fluxos de veículos
public class VeiculoController {

    private final VeiculoView veiculoView = new VeiculoView();
    private final VeiculoService veiculoService = new VeiculoService();
    private final MotoristaService motoristaService = new MotoristaService();

    // Menu principal do módulo de veículos
    public void iniciar() throws LogisticaException {
        int opcao;
        do {
            opcao = veiculoView.menu();
            switch (opcao) {
                case 0: this.listar(); break;
                case 1: this.cadastrar(); break;
                case 2: this.alterar(); break;
                case 3: this.excluir(); break;
            }
        } while (opcao >= 0);
    }

    // Exibe a listagem de veículos
    private void listar() {
        try {
            List<Veiculo> lista = veiculoService.listar();
            if (lista.isEmpty()) {
                veiculoView.exibir("LISTAGEM DE VEÍCULOS", "Nenhum veículo cadastrado.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Veiculo v : lista) {
                    sb.append(v.toString()).append("\n");
                }
                veiculoView.exibir("LISTAGEM DE VEÍCULOS", sb.toString());
            }
        } catch (Exception e) {
            veiculoView.alertaErro("LISTAGEM DE VEÍCULOS", e.getMessage());
        }
    }

    // Cadastra veículo e vincula ao motorista selecionado
    private void cadastrar() {
        try {
            Veiculo veiculo = veiculoView.informarDados("CADASTRO DE VEÍCULO", new Veiculo());
            if (veiculo == null) return;

            List<Motorista> motoristas = motoristaService.listar();
            Integer idMotorista = veiculoView.selecionarMotorista("CADASTRO DE VEÍCULO", motoristas);
            if (idMotorista == null) return;

            Motorista motorista = motoristaService.buscarPorId(idMotorista);
            if (motorista == null) {
                veiculoView.alertaErro("CADASTRO DE VEÍCULO", "Motorista não encontrado.");
                return;
            }

            veiculo.setMotorista(motorista);

            if (veiculoService.cadastrar(veiculo)) {
                veiculoView.alertaSucesso("CADASTRO DE VEÍCULO", "Veículo cadastrado com sucesso!");
            } else {
                veiculoView.alertaErro("CADASTRO DE VEÍCULO", "Erro ao cadastrar veículo.");
            }
        } catch (Exception e) {
            veiculoView.alertaErro("CADASTRO DE VEÍCULO", e.getMessage());
        }
    }

    // Altera veículo e seu relacionamento com o motorista
    private void alterar() {
        try {
            List<Veiculo> veiculos = veiculoService.listar();
            Integer id = veiculoView.selecionarVeiculo("ALTERAÇÃO DE VEÍCULO", veiculos);
            if (id == null) return;

            Veiculo veiculoExistente = veiculoService.buscarPorId(id);
            if (veiculoExistente == null) {
                veiculoView.alertaErro("ALTERAÇÃO DE VEÍCULO", "Veículo não encontrado.");
                return;
            }

            Veiculo veiculoAlterado = veiculoView.informarDados("ALTERAÇÃO DE VEÍCULO", veiculoExistente);
            if (veiculoAlterado == null) return;

            List<Motorista> motoristas = motoristaService.listar();
            Integer idMotorista = veiculoView.selecionarMotorista("ALTERAÇÃO DE VEÍCULO", motoristas);
            if (idMotorista != null) {
                Motorista motorista = motoristaService.buscarPorId(idMotorista);
                if (motorista != null) {
                    veiculoAlterado.setMotorista(motorista);
                }
            }

            if (veiculoService.alterar(veiculoAlterado)) {
                veiculoView.alertaSucesso("ALTERAÇÃO DE VEÍCULO", "Veículo alterado com sucesso!");
            } else {
                veiculoView.alertaErro("ALTERAÇÃO DE VEÍCULO", "Erro ao alterar veículo.");
            }
        } catch (Exception e) {
            veiculoView.alertaErro("ALTERAÇÃO DE VEÍCULO", e.getMessage());
        }
    }

    // Confirma e remove o veículo
    private void excluir() {
        try {
            List<Veiculo> veiculos = veiculoService.listar();
            Integer id = veiculoView.selecionarVeiculo("EXCLUSÃO DE VEÍCULO", veiculos);
            if (id == null) return;

            Veiculo veiculo = veiculoService.buscarPorId(id);
            if (veiculo == null) {
                veiculoView.alertaErro("EXCLUSÃO DE VEÍCULO", "Veículo não encontrado.");
                return;
            }

            int confirma = veiculoView.alertaConfirmacao(
                    "EXCLUSÃO DE VEÍCULO",
                    "Deseja realmente excluir o veículo " + veiculo.getMarca() + " - " + veiculo.getPlaca() + " (ID: " + veiculo.getId() + ")?"
            );

            if (confirma == 0) {
                if (veiculoService.excluir(id)) {
                    veiculoView.alertaSucesso("EXCLUSÃO DE VEÍCULO", "Veículo excluído com sucesso!");
                } else {
                    veiculoView.alertaErro("EXCLUSÃO DE VEÍCULO", "Erro ao excluir veículo.");
                }
            }
        } catch (Exception e) {
            veiculoView.alertaErro("EXCLUSÃO DE VEÍCULO", e.getMessage());
        }
    }
}