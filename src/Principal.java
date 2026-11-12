import javax.swing.JOptionPane;
import controllers.MotoristaController;
import controllers.VeiculoController;
import controllers.RotaController;

public class Principal {

    public static void main(String[] args) {
        MotoristaController motoristaController = new MotoristaController();
        VeiculoController veiculoController = new VeiculoController();
        RotaController rotaController = new RotaController();

        int opcao;
        // Loop que mantém o menu ativo até fechar a janela
        do {
            String[] menu = { "Cadastro de Motoristas", "Cadastro de Veículos", "Gestão de Rotas" };

            opcao = JOptionPane.showOptionDialog(
                    null,
                    "Menu de opções:",
                    "SISTEMA DE LOGÍSTICA",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    menu,
                    menu[0]
            );

            // Captura erros para impedir que a aplicação encerre inesperadamente
            try {
                switch (opcao) {
                    case 0 -> motoristaController.iniciar();
                    case 1 -> veiculoController.iniciar();
                    case 2 -> rotaController.iniciar();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro na aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } while (opcao >= 0);
    }
}