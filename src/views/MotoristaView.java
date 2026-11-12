package views;

import java.util.List;
import javax.swing.JOptionPane;
import models.Motorista;

public class MotoristaView {

    // Exibe o menu de opções do módulo de motoristas
    public int menu() {
        String[] opcoes = { "Lista", "Cadastro", "Alteração", "Exclusão" };
        return JOptionPane.showOptionDialog(
                null, "Menu de Opções:", "CADASTRO DE MOTORISTAS",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]
        );
    }

    // Coleta dados pessoais do motorista e categoria da CNH
    public Motorista informarDados(String titulo, Motorista motorista) {
        String nome = JOptionPane.showInputDialog(titulo + "\nNome:", motorista.getNome());
        if (nome == null || nome.trim().isEmpty()) return null;

        String cpf = JOptionPane.showInputDialog(titulo + "\nCPF:", motorista.getCpf());
        String telefone = JOptionPane.showInputDialog(titulo + "\nTelefone:", motorista.getTelefone());

        String[] opcoes = { "Sim", "Não" };

        // Pergunta se o motorista possui CNH (Sim/Não)
        int resposta = JOptionPane.showOptionDialog(
                null,
                "Possui CNH?",
                "CNH",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        String cnh = (resposta == 0) ? "Possui" : "Não possui";
        String tipoCnh = "Nenhuma";

        // Exibe seletor da categoria da CNH caso possua habilitação
        if (resposta == 0) {
            String[] tipos = { "A", "B", "C", "D", "E" };
            tipoCnh = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecione o tipo da CNH:",
                    titulo,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tipos,
                    tipos[0]
            );

            if (tipoCnh == null) {
                tipoCnh = "Não informado";
            }
        }

        motorista.setNome(nome);
        motorista.setCpf(cpf);
        motorista.setTelefone(telefone);
        motorista.setCnh(cnh);
        motorista.setTipoCnh(tipoCnh);

        return motorista;
    }

    // Menu drop-down para selecionar o motorista por ID
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

        // Extrai o ID numérico do motorista selecionado
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

    // Diálogo de confirmação para ações do sistema
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