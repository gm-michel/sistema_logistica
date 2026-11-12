package exceptions;

// Exceção personalizada para tratamento unificado de erros no sistema de logística
public class LogisticaException extends Exception {

    public LogisticaException(String mensagem) {
        super(mensagem);
    }

    public LogisticaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}