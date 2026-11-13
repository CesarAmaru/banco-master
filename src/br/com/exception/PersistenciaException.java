package br.com.exception;

/**
 * Erro de acesso ao banco de dados. Embrulha o {@code SQLException} para que
 * as camadas de cima (service, controle e telas) não dependam de JDBC.
 *
 * <b>Camada:</b> Exception
 */
public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String mensagem) {
        super(mensagem);
    }

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
