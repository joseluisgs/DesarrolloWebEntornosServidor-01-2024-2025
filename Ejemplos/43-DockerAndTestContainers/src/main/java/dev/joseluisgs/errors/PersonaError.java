package dev.joseluisgs.errors;

public class PersonaError {
    private final String message;

    public PersonaError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
