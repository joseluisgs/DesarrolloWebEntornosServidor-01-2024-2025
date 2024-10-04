package dev.joseluisgs.services;

import dev.joseluisgs.exceptions.UserNotFoundException;
import dev.joseluisgs.model.User;
import dev.joseluisgs.repository.UserRemoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRemoteRepository userRemoteRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllSync() {
        // Cuando se llame a getAll() devolverá una lista de usuarios
        var users = List.of(
                User.builder().id(1L).firstName("John Doe").build(),
                User.builder().id(2L).firstName("Jane Doe").build());
        when(userRemoteRepository.getAll()).thenReturn(users);
        var res = userService.getAllSync();

        // Comprobamos que la lista devuelta es la misma que la que hemos creado
        assertAll(
                () -> assertEquals(2, res.size()),
                () -> assertEquals("John Doe", res.get(0).getFirstName()),
                () -> assertEquals("Jane Doe", res.get(1).getFirstName())
        );

        verify(userRemoteRepository, times(1)).getAll();
    }

    @Test
    void getAllAsync() {
        // Cuando se llame a getAll() devolverá una lista de usuarios
        var users = List.of(
                User.builder().id(1L).firstName("John Doe").build(),
                User.builder().id(2L).firstName("Jane Doe").build());
        when(userRemoteRepository.getAll()).thenReturn(users);

        var res = userService.getAllAsync();

        // Comprobamos que la lista devuelta es la misma que la que hemos creado
        assertAll(
                () -> assertEquals(2, res.size()),
                () -> assertEquals("John Doe", res.get(0).getFirstName()),
                () -> assertEquals("Jane Doe", res.get(1).getFirstName())
        );

        verify(userRemoteRepository, times(1)).getAll();
    }

    @Test
    void getAllAsyncTimeOut() {
        // Cuando se llame a getAll() devolverá una lista de usuarios

        when(userRemoteRepository.getAll()).thenAnswer(invocation -> {
            Thread.sleep(2000);
            return List.of(
                    User.builder().id(1L).firstName("John Doe").build(),
                    User.builder().id(2L).firstName("Jane Doe").build());
        });

        try {
            var res = userService.getAllAsync();
        } catch (Exception e) {
            assertEquals("java.util.concurrent.TimeoutException", e.getClass().getName());
        }
    }

    @Test
    void getAllSyncEither() {
        // Cuando se llame a getAll() devolverá una lista de usuarios
        var users = List.of(
                User.builder().id(1L).firstName("John Doe").build(),
                User.builder().id(2L).firstName("Jane Doe").build());
        when(userRemoteRepository.getAll()).thenReturn(users);
        var res = userService.getAllSyncEither();

        // Comprobamos que la lista devuelta es la misma que la que hemos creado
        assertAll(
                () -> assertTrue(res.isRight()),
                () -> assertEquals(2, res.get().size()),
                () -> assertEquals("John Doe", res.get().getFirst().getFirstName()),
                () -> assertEquals("Jane Doe", res.get().get(1).getFirstName())
        );

        verify(userRemoteRepository, times(1)).getAll();
    }

    @Test
    void getAllAsyncEither() {
        // Cuando se llame a getAll() devolverá una lista de usuarios
        var users = List.of(
                User.builder().id(1L).firstName("John Doe").build(),
                User.builder().id(2L).firstName("Jane Doe").build());
        when(userRemoteRepository.getAll()).thenReturn(users);

        var res = userService.getAllAsyncEither();

        // Comprobamos que la lista devuelta es la misma que la que hemos creado
        assertAll(
                () -> assertTrue(res.isRight()),
                () -> assertEquals(2, res.get().size()),
                () -> assertEquals("John Doe", res.get().getFirst().getFirstName()),
                () -> assertEquals("Jane Doe", res.get().get(1).getFirstName())
        );

        verify(userRemoteRepository, times(1)).getAll();
    }

    // Repetir para el timeOut


    @Test
    void getByIdSyncEither() {
        // Cuando se llame a getById() devolverá un usuario
        var user = User.builder().id(1L).firstName("John Doe").build();
        when(userRemoteRepository.getById(1)).thenReturn(user);
        var res = userService.getByIdSyncEither(1);

        // Comprobamos que el usuario devuelto es el que hemos creado
        assertAll(
                () -> assertTrue(res.isRight()),
                () -> assertEquals(1L, res.get().getId()),
                () -> assertEquals("John Doe", res.get().getFirstName())
        );

        verify(userRemoteRepository, times(1)).getById(1);
    }

    @Test
    void getByIdSyncEitherTimeOut() {
        // Cuando se llame a getById() devolverá un usuario
        when(userRemoteRepository.getById(1)).thenAnswer(invocation -> {
            Thread.sleep(2000);
            return User.builder().id(1L).firstName("John Doe").build();
        });

        var res = userService.getByIdSyncEither(1);

        // Comprobamos que la excepción se ha lanzado correctamente
        assertAll(
                () -> assertTrue(res.isLeft()),
                () -> assertEquals("java.util.concurrent.TimeoutException", res.getLeft().getClass().getName())
        );

    }


    @Test
    void getByIdSyncEitherNotFound() {
        // Cuando se llame a getById() devolverá un usuario
        when(userRemoteRepository.getById(1)).thenThrow(new RuntimeException("User not found with id: 1"));
        var res = userService.getByIdSyncEither(1);

        // Comprobamos que el usuario devuelto es el que hemos creado
        assertAll(
                () -> assertTrue(res.isRight()),
                () -> assertEquals("User not found with id: 1", res.getLeft().getMessage())
        );

        verify(userRemoteRepository, times(1)).getById(1);
    }

    @Test
    void getByIdAsyncEither() {
        // Cuando se llame a getById() devolverá un usuario
        var user = User.builder().id(1L).firstName("John Doe").build();
        when(userRemoteRepository.getById(1)).thenReturn(user);

        var res = userService.getByIdAsyncEither(1);

        // Comprobamos que el usuario devuelto es el que hemos creado
        assertAll(
                () -> assertTrue(res.isRight()),
                () -> assertEquals(1L, res.get().getId()),
                () -> assertEquals("John Doe", res.get().getFirstName())
        );

        verify(userRemoteRepository, times(1)).getById(1);
    }

    @Test
    void getByIdAsyncEitherNotFound() {
        // Cuando se llame a getById() devolverá un usuario
        when(userRemoteRepository.getById(1)).thenThrow(new UserNotFoundException("User not found with id: 1"));

        var res = userService.getByIdAsyncEither(1);

        // Comprobamos que el usuario devuelto es el que hemos creado
        assertAll(
                () -> assertTrue(res.isLeft()),
                () -> assertEquals("User not found with id: 1", res.getLeft().getMessage())
        );

        verify(userRemoteRepository, times(1)).getById(1);
    }

    // Repetir para el timeOut
    @Test
    void getByIdAsyncEitherTimeOut() {
        // Cuando se llame a getById() devolverá un usuario
        when(userRemoteRepository.getById(1)).thenAnswer(invocation -> {
            Thread.sleep(2000);
            return User.builder().id(1L).firstName("John Doe").build();
        });

        var res = userService.getByIdAsyncEither(1);

        // Comprobamos que la excepción se ha lanzado correctamente
        assertAll(
                () -> assertTrue(res.isLeft()),
                () -> assertEquals("java.util.concurrent.TimeoutException", res.getLeft().getClass().getName())
        );
    }
}