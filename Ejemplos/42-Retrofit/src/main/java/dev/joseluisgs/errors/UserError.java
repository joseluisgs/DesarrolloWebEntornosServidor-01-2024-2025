package dev.joseluisgs.errors;

public class UserError {
    private final String message;
    private final int code;

    public UserError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
