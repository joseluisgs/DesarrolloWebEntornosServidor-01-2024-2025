package dev.joseluisgs.repository;

import dev.joseluisgs.exceptions.UserNotFoundException;
import dev.joseluisgs.mapper.UserMapper;
import dev.joseluisgs.model.User;
import dev.joseluisgs.rest.UserApiRest;
import io.reactivex.rxjava3.core.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class UserRemoteRepository {
    private final UserApiRest userApiRest;
    private final Logger logger = LoggerFactory.getLogger(UserRemoteRepository.class);

    public UserRemoteRepository(UserApiRest userApiRest) {
        this.userApiRest = userApiRest;
    }

    // Lo bueno de esta es que ya puedo mapear los errores y lanzarla como callable desde el servicio
    public List<User> getAll() {
        var call = userApiRest.getAllSync();
        try {
            var response = call.execute();
            if (!response.isSuccessful()) {
                throw new Exception("Error: " + response.code()); // Aquí deberíamos lanzar una excepción
            }
            return response.body().getData().stream()
                    .map(UserMapper::toUserFromCreate)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<User> getAllCompletableFuture() {
        var call = userApiRest.getAllCompletableFuture();
        try {
            var response = call.get();
            return response.getData().stream()
                    .map(UserMapper::toUserFromCreate)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<User> getAllWithPage(int page) {
        var call = userApiRest.getAllWithPage(page);
        try {
            var response = call.get();
            return response.getData().stream()
                    .map(UserMapper::toUserFromCreate)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public User getByIdSync(int id) {
        var call = userApiRest.getByIdSync(id);
        try {
            var response = call.execute();
            if (!response.isSuccessful()) {
                // con el codigo podemos saber que ha pasado
                // throw new Exception("Error: " + response.code()); // Aquí deberíamos lanzar una excepción
                if (response.code() == 404) {
                    throw new UserNotFoundException("User not found with id: " + id);
                } else {
                    throw new Exception("Error: " + response.code());
                }
            }
            return UserMapper.toUserFromCreate(response.body().getData());
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public User getById(int id) {
        var call = userApiRest.getById(id);
        try {
            var response = call.get();
            return UserMapper.toUserFromCreate(response.getData());
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public User createUser(User user) {
        var callSync = userApiRest.createUser(UserMapper.toRequest(user));
        try {
            var response = callSync.get();
            return UserMapper.toUserFromCreate(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User updateUser(int id, User user) {
        var callSync = userApiRest.updateUser(id, UserMapper.toRequest(user));
        try {
            var response = callSync.get();
            return UserMapper.toUserFromUpdate(response, id);
        } catch (Exception e) {
            if (e.getCause().getMessage().contains("404")) {
                throw new UserNotFoundException("User not found with id: " + id);
            } else {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void deleteUser(int id) {
        var callSync = userApiRest.deleteUser(id);
        try {
            callSync.get();
        } catch (Exception e) {
            if (e.getCause().getMessage().contains("404")) {
                throw new UserNotFoundException("User not found with id: " + id);
            } else {
                e.printStackTrace();
            }
        }
    }

    public Flux<User> getAllWithReactor() {
        return userApiRest.getAllWithReactor()
                .flatMapMany(response -> Flux.fromIterable(response.getData())
                        .map(UserMapper::toUserFromCreate));
    }

    public Observable<User> getAllWithRxJava() {
        return userApiRest.getAllWithRxJava()
                .flatMapObservable(response -> Observable.fromIterable(response.getData())
                        .map(UserMapper::toUserFromCreate));
    }

    public User createUserWithToken(String token, User user) {
        var callSync = userApiRest.createUserWithToken(token, UserMapper.toRequest(user));
        try {
            var response = callSync.get();
            return UserMapper.toUserFromCreate(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
