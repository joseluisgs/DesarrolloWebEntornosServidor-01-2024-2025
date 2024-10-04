package dev.joseluisgs.services;

import dev.joseluisgs.errors.UserError;
import dev.joseluisgs.errors.UserNotFoundError;
import dev.joseluisgs.exceptions.UserNotFoundException;
import dev.joseluisgs.model.User;
import dev.joseluisgs.repository.UserRemoteRepository;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class UserService {
    private final UserRemoteRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRemoteRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllSync() {
        return userRepository.getAll();
    }

    public List<User> getAllAsync() {
        // Es el Sync pero lanzado con un CompletableFuture
        CompletableFuture<List<User>> completableFuture = CompletableFuture.supplyAsync(userRepository::getAll);
        // Se debe hacer el try catch y las cosas necesarias
        try {
            return completableFuture.get(1000, MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

    }

    public List<User> getAllCompletableFuture() {
        return userRepository.getAllCompletableFuture();
    }

    // Ahora repetimos con el getbyID
    public User getByIdSync(int id) {
        return userRepository.getById(id);
    }

    public User getByIdAsync(int id) {
        CompletableFuture<User> completableFuture = CompletableFuture.supplyAsync(() -> userRepository.getByIdSync(id));
        try {
            return completableFuture.get(1000, MILLISECONDS);
        } catch (UserNotFoundException e) {
            logger.error("User not found", e);
            // O vomitar la excepción
            return null;
            // O lanzar una nueva excepción
            // throw new UserNotFoundException("User not found", e);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Ahora podemos repetir con los métodos con Mono, o lo que sea

    // Tambien podemos coger estas ideas y hacer aquí el Either

    public Either<UserError, List<User>> getAllSyncEither() {
        try {
            return Either.right(userRepository.getAll());
        } catch (Exception e) {
            return Either.left(new UserError("Error getting users", 500));
        }
    }

    public Either<UserError, List<User>> getAllAsyncEither() {
        // Es el Sync pero lanzado con un CompletableFuture
        CompletableFuture<List<User>> completableFuture = CompletableFuture.supplyAsync(userRepository::getAll);
        // Se debe hacer el try catch y las cosas necesarias
        try {
            return Either.right(completableFuture.get(1000, MILLISECONDS));
        } catch (Exception e) {
            return Either.left(new UserError("Error getting users", 500));
        }

    }

    public Either<UserError, List<User>> getAllCompletableFutureEither() {
        try {
            return Either.right(userRepository.getAllCompletableFuture());
        } catch (Exception e) {
            return Either.left(new UserError("Error getting users", 500));
        }
    }

    // Ahora repetimos con el getbyID
    public Either<UserError, User> getByIdSyncEither(int id) {
        try {
            return Either.right(userRepository.getById(id));
        } catch (UserNotFoundException e) {
            return Either.left(new UserNotFoundError(id));
        } catch (Exception e) {
            return Either.left(new UserError("Error getting user", 500));
        }
    }

    public Either<UserError, User> getByIdAsyncEither(int id) {
        CompletableFuture<User> completableFuture = CompletableFuture.supplyAsync(() -> userRepository.getByIdSync(id));
        try {
            return Either.right(completableFuture.get(1000, MILLISECONDS));
        } catch (UserNotFoundException e) {
            return Either.left(new UserNotFoundError(id));
        } catch (Exception e) {
            return Either.left(new UserError("Error getting user", 500));
        }
    }
}
