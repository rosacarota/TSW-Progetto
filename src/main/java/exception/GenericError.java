package exception;

public class GenericError extends RuntimeException {
    public GenericError() {
        super("Errore generico");
    }

    public GenericError(String message) {
        super(message);
    }
}
