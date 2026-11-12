package controllers;

import java.util.List;
import exceptions.LogisticaException;
import models.Motorista;
import models.Rota;
import models.Veiculo;
import services.MotoristaService;
import services.RotaService;
import services.VeiculoService;
import views.RotaView;

// Controlador das ações e fluxos do módulo de rotas
public class RotaController {

    private final RotaView rotaView = new RotaView();
    private final RotaService rotaService = new RotaService();
    private final MotoristaService motoristaService = new MotoristaService();
    private final VeiculoService veiculoService = new VeiculoService();

    // Menu principal do módulo de rotas
    public void iniciar() throws LogisticaException {
        int opcao;
        do {
            opcao = rotaView.menu();
            switch (opcao) {
                case 0: this.listar(); break;
                case 1: this.cadastrar(); break;
                case 2: this.alterar(); break;
                case 3: this.excluir(); break;
            }
        } while (opcao >= 0);
    }

    // Exibe listagem de rotas cadastradas
    private void listar() {
        try {
            List<Rota> lista = rotaService.listar();
            if (lista.isEmpty()) {
                rotaView.exibir("LISTAGEM DE ROTAS", "Nenhuma rota cadastrada.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Rota r : lista) {
                    sb.append(r.toString()).append("\n");
                }
                rotaView.exibir("LISTAGEM DE ROTAS", sb.toString());
            }
        } catch (Exception e) {
            rotaView.alertaErro("LISTAGEM DE ROTAS", e.getMessage());
        }
    }

    // Cadastra rota associando motorista e veículo
    private void cadastrar() {
        try {
            Rota rota = rotaView.informarDados("CADASTRO DE ROTA", new Rota());
            if (rota == null) return;

            List<Motorista> motoristas = motoristaService.listar();
            Integer idMotorista = rotaView.selecionarMotorista("CADASTRO DE ROTA", motoristas);
            if (idMotorista == null) return;

            Motorista motorista = motoristaService.buscarPorId(idMotorista);
            if (motorista == null) {
                rotaView.alertaErro("CADASTRO DE ROTA", "Motorista não encontrado.");
                return;
            }

            List<Veiculo> veiculos = veiculoService.listar();
            Integer idVeiculo = rotaView.selecionarVeiculo("CADASTRO DE ROTA", veiculos);
            if (idVeiculo == null) return;

            Veiculo veiculo = veiculoService.buscarPorId(idVeiculo);
            if (veiculo == null) {
                rotaView.alertaErro("CADASTRO DE ROTA", "Veículo não encontrado.");
                return;
            }

            rota.setMotorista(motorista);
            rota.setVeiculo(veiculo);

            if (rotaService.cadastrar(rota)) {
                rotaView.alertaSucesso("CADASTRO DE ROTA", "Rota cadastrada com sucesso!");
            } else {
                rotaView.alertaErro("CADASTRO DE ROTA", "Erro ao cadastrar rota.");
            }
        } catch (Exception e) {
            rotaView.alertaErro("CADASTRO DE ROTA", e.getMessage());
        }
    }

    // Altera os dados da rota e refaz as associações
    private void alterar() {
        try {
            List<Rota> rotas = rotaService.listar();
            Integer id = rotaView.selecionarRota("ALTERAÇÃO DE ROTA", rotas);
            if (id == null) return;

            Rota rotaExistente = rotaService.buscarPorId(id);
            if (rotaExistente == null) {
                rotaView.alertaErro("ALTERAÇÃO DE ROTA", "Rota não encontrada.");
                return;
            }

            Rota rotaAlterada = rotaView.informarDados("ALTERAÇÃO DE ROTA", rotaExistente);
            if (rotaAlterada == null) return;

            List<Motorista> motoristas = motoristaService.listar();
            Integer idMotorista = rotaView.selecionarMotorista("ALTERAÇÃO DE ROTA", motoristas);
            if (idMotorista != null) {
                Motorista motorista = motoristaService.buscarPorId(idMotorista);
                if (motorista != null) {
                    rotaAlterada.setMotorista(motorista);
                }
            }

            List<Veiculo> veiculos = veiculoService.listar();
            Integer idVeiculo = rotaView.selecionarVeiculo("ALTERAÇÃO DE ROTA", veiculos);
            if (idVeiculo != null) {
                Veiculo veiculo = veiculoService.buscarPorId(idVeiculo);
                if (veiculo != null) {
                    rotaAlterada.setVeiculo(veiculo);
                }
            }

            if (rotaService.alterar(rotaAlterada)) {
                rotaView.alertaSucesso("ALTERAÇÃO DE ROTA", "Rota alterada com sucesso!");
            } else {
                rotaView.alertaErro("ALTERAÇÃO DE ROTA", "Erro ao alterar rota.");
            }
        } catch (Exception e) {
            rotaView.alertaErro("ALTERAÇÃO DE ROTA", e.getMessage());
        }
    }

    // Confirma e remove a rota selecionada
    private void excluir() {
        try {
            List<Rota> rotas = rotaService.listar();
            Integer id = rotaView.selecionarRota("EXCLUSÃO DE ROTA", rotas);
            if (id == null) return;

            Rota rota = rotaService.buscarPorId(id);
            if (rota == null) {
                rotaView.alertaErro("EXCLUSÃO DE ROTA", "Rota não encontrada.");
                return;
            }

            int confirma = rotaView.alertaConfirmacao(
                    "EXCLUSÃO DE ROTA",
                    "Deseja realmente excluir a rota de " + rota.getOrigem() + " para " + rota.getDestino() + " (ID: " + rota.getId() + ")?"
            );

            if (confirma == 0) {
                if (rotaService.excluir(id)) {
                    rotaView.alertaSucesso("EXCLUSÃO DE ROTA", "Rota excluída com sucesso!");
                } else {
                    rotaView.alertaErro("EXCLUSÃO DE ROTA", "Erro ao excluir rota.");
                }
            }
        } catch (Exception e) {
            rotaView.alertaErro("EXCLUSÃO DE ROTA", e.getMessage());
        }
    }
}