package br.com.crdd.common.exception;

public class InfrastructureException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InfrastructureException() {
    }

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfrastructureException(Throwable cause) {
        super(cause);
    }

    public String getNameException() {
        return InfrastructureException.class.getSimpleName();
    }
}
