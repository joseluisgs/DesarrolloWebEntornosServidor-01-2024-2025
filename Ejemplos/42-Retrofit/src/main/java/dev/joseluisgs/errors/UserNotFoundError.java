package dev.joseluisgs.errors;

public class UserNotFoundError extends UserError {
    public UserNotFoundError(long id) {
        super("User not found with id:" + id, 404);
    }
}
