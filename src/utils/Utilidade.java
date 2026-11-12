package utils;

// Classe utilitária para conversão e tratamento seguro de tipos de dados
public class Utilidade {

    // Converte texto para Integer tratando nulos e erros de digitação
    public static Integer converterInteger(String dado) {
        try {
            if (dado == null) return null;
            return Integer.parseInt(dado.trim());
        } catch (Exception e) {
            return null; // Retorna null caso o texto seja inválido
        }
    }

    // Converte texto para Double tratando vírgula como ponto decimal
    public static Double converterDouble(String dado) {
        try {
            if (dado == null) return null;
            return Double.parseDouble(dado.trim().replace(",", "."));
        } catch (Exception e) {
            return null; // Retorna null caso a conversão falhe
        }
    }
}