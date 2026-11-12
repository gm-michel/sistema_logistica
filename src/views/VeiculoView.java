package views;

import java.util.List;
import javax.swing.JOptionPane;
import models.Motorista;
import models.Veiculo;

public class VeiculoView {

    // Exibe o menu de opções do módulo de veículos
    public int menu() {
        String[] opcoes = { "Lista", "Cadastro", "Alteração", "Exclusão" };
        return JOptionPane.showOptionDialog(
                null, "Menu de opções:", "CADASTRO DE VEÍCULOS",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]
        );
    }

    // Coleta os dados do veículo digitados pelo usuário
    public Veiculo informarDados(String titulo, Veiculo veiculo) {
        String marca = JOptionPane.showInputDialog(titulo + "\nMarca do veículo:", veiculo.getMarca());
        if (marca == null || marca.trim().isEmpty()) return null;

        String modelo = JOptionPane.showInputDialog(titulo + "\nModelo do veículo:", veiculo.getModelo());
        String placa = JOptionPane.showInputDialog(titulo + "\nPlaca do veículo:", veiculo.getPlaca());

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        return veiculo;
    }

    // Exibe caixas de seleção em menu drop-down para selecionar o veículo por ID
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

        // Extrai o ID numérico a partir do texto selecionado no menu
        String idStr = selecionado.split(" - ")[0].replace("ID: ", "").trim();
        return Integer.parseInt(idStr);
    }

    // Exibe lista de motoristas para vincular ao veículo
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
                "Selecione o Motorista responsável:",
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (selecionado == null) return null;

        // Extrai o ID do motorista selecionado
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

    // Caixa de diálogo de confirmação (Sim/Não)
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