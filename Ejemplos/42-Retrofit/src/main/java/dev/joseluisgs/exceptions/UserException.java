package dev.joseluisgs.exceptions;

abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
