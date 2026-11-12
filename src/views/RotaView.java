package views;

import java.util.List;
import javax.swing.JOptionPane;
import models.Motorista;
import models.Rota;
import models.Veiculo;
import utils.Utilidade;

public class RotaView {

    // Exibe o menu de opções do módulo de rotas
    public int menu() {
        String[] opcoes = { "Lista", "Cadastro", "Alteração", "Exclusão" };
        return JOptionPane.showOptionDialog(
                null, "Menu de opções:", "CADASTRO DE ROTAS",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]
        );
    }

    // Coleta dados da rota e efetua o cálculo automático do frete
    public Rota informarDados(String titulo, Rota rota) {
        String origem = JOptionPane.showInputDialog(titulo + "\nOrigem da rota:", rota.getOrigem());
        if (origem == null || origem.trim().isEmpty()) return null;

        String destino = JOptionPane.showInputDialog(titulo + "\nDestino da rota:", rota.getDestino());
        String distanciaStr = JOptionPane.showInputDialog(titulo + "\nDistância da rota (em km):", rota.getDistancia());

        // Valida e converte a distância digitada
        Double distancia = Utilidade.converterDouble(distanciaStr);
        if (distancia == null || distancia <= 0) {
            alertaErro(titulo, "Distância inválida.");
            return null;
        }

        String[] cargas = { "Até 3.500 kg", "Até 7.000 kg", "Até 13.000 kg", "Até 50.500 kg" };
        int escolhaCarga = JOptionPane.showOptionDialog(
                null, "Escolha o peso da carga:", "Carga",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, cargas, cargas[0]
        );

        if (escolhaCarga < 0) return null;

        // Define a taxa por km com base no peso da carga selecionada
        double valorKm = switch (escolhaCarga) {
            case 0 -> 4.50;
            case 1 -> 5.50;
            case 2 -> 7.50;
            case 3 -> 9.50;
            default -> 0.0;
        };

        // Regra de negócio: multiplica a distância pela taxa da carga
        double valorFrete = distancia * valorKm;

        rota.setOrigem(origem);
        rota.setDestino(destino);
        rota.setDistancia(distancia);
        rota.setCarga(cargas[escolhaCarga]);
        rota.setValorFrete(valorFrete);

        return rota;
    }

    // Menu drop-down para seleção de rota por ID
    public Integer selecionarRota(String titulo, List<Rota> rotas) {
        if (rotas == null || rotas.isEmpty()) {
            alertaErro(titulo, "Nenhuma rota cadastrada.");
            return null;
        }

        String[] opcoes = new String[rotas.size()];
        for (int i = 0; i < rotas.size(); i++) {
            Rota r = rotas.get(i);
            opcoes[i] = "ID: " + r.getId() + " - " + r.getOrigem() + " -> " + r.getDestino();
        }

        String selecionado = (String) JOptionPane.showInputDialog(
                null,
                "Selecione a Rota:",
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (selecionado == null) return null;

        // Extrai o ID numérico da rota
        String idStr = selecionado.split(" - ")[0].replace("ID: ", "").trim();
        return Integer.parseInt(idStr);
    }

    // Menu drop-down para selecionar motorista responsável pela rota
    public Integer selecionarMotorista(String titulo, List<Motorista> motoristas) {
        if (motoristas == null || motoristas.isEmpty()) {
            alertaErro(titulo, "Nenhum motorista cadastrado.");
            return null;
        }

        String[] opcoes = new String[motoristas.size()];
        for (int i = 0; i < motoristas.size(); i++) {
            Motorista m = motoristas.get(i);
            opcoes[i] = "ID: " + m.getId() + " - " + m.getNome();
        }

        String selecionado = (String) JOptionPane.showInputDialog(
                null,
                "Selecione o Motorista:",
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (selecionado == null) return null;

        // Extrai o ID do motorista
        String idStr = selecionado.split(" - ")[0].replace("ID: ", "").trim();
        return Integer.parseInt(idStr);
    }

    // Menu drop-down para selecionar veículo alocado para a rota
    public Integer selecionarVeiculo(String titulo, List<Veiculo> veiculos) {
        if (veiculos == null || veiculos.isEmpty()) {
            alertaErro(titulo, "Nenhum veículo cadastrado.");
            return null;
        }

        String[] opcoes = new String[veiculos.size()];
        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo v = veiculos.get(i);
            opcoes[i] = "ID: " + v.getId() + " - " + v.getMarca() + " " + v.getModelo() + " (" + v.getPlaca() + ")";
        }

        String selecionado = (String) JOptionPane.showInputDialog(
                null,
                "Selecione o Veículo:",
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (selecionado == null) return null;

        // Extrai o ID do veículo
        String idStr = selecionado.split(" - ")[0].replace("ID: ", "").trim();
        return Integer.parseInt(idStr);
    }

    public void exibir(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void alertaSucesso(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void alertaErro(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    // Diálogo de confirmação de exclusão ou alteração
    public int alertaConfirmacao(String titulo, String mensagem) {
        String[] opcoes = { "Sim", "Não" };
        return JOptionPane.showOptionDialog(
                null,
                mensagem,
                titulo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }
}