package dev.joseluisgs.repository;

import dev.joseluisgs.exceptions.UserNotFoundException;
import dev.joseluisgs.rest.UserApiRest;
import dev.joseluisgs.rest.responses.getall.ResponseGetAll;
import dev.joseluisgs.rest.responses.getall.UserGetAll;
import dev.joseluisgs.rest.responses.getbyid.ResponseGetById;
import dev.joseluisgs.rest.responses.getbyid.UserGetById;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRemoteRepositoryTest {

    @Mock
    private UserApiRest userApiRest;

    @InjectMocks
    private UserRemoteRepository userRemoteRepository;

    @Test
    void getAllCompletableFuture() {
        // Arrange
        var response = ResponseGetAll.builder().data(
                List.of(
                        UserGetAll.builder().id(1).firstName("Pepe").lastName("Perez").email("email").avatar("avatar").build(),
                        UserGetAll.builder().id(2).firstName("Juan").lastName("Garcia").email("email").avatar("avatar").build()
                )
        ).build();

        when(userApiRest.getAllCompletableFuture()).thenReturn(CompletableFuture.completedFuture(response));

        // Act
        var res = userRemoteRepository.getAllCompletableFuture();

        // Assert
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(2, res.size()),
                () -> assertEquals("Pepe", res.getFirst().getFirstName()),
                () -> assertEquals("Perez", res.getFirst().getLastName()),
                () -> assertEquals("Juan", res.getLast().getFirstName()),
                () -> assertEquals("Garcia", res.getLast().getLastName())
        );
        // Verify at least one call
        verify(userApiRest, times(1)).getAllCompletableFuture();

    }

    @Test
    void getById() {
        // Arrange
        var response = ResponseGetById.builder()
                .data(
                        UserGetById.builder().id(1).firstName("Pepe").lastName("Perez").email("email").avatar("avatar").build()
                ).build();


        when(userApiRest.getById(1)).thenReturn(CompletableFuture.completedFuture(response));

        // Act
        var res = userRemoteRepository.getById(1);

        // Assert
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.getId()),
                () -> assertEquals("Pepe", res.getFirstName()),
                () -> assertEquals("Perez", res.getLastName())
        );

        // Verify at least one call
        verify(userApiRest, times(1)).getById(1);
    }

    @Test
    void getByIdNotFound() {
        // Arrange
        var response = ResponseGetById.builder()
                .data(null)
                .build();

        when(userApiRest.getById(1)).thenReturn(CompletableFuture.completedFuture(response));

        // Act
        var res = assertThrows(UserNotFoundException.class, () -> userRemoteRepository.getById(1));

        // Assert
        assertEquals("User not found with id: 1", res.getMessage());

        // Verify at least one call
        verify(userApiRest, times(1)).getById(1);
    }

    @Test
    void getAllCompletableFutureReactor() {
        // Arrange
        var response = ResponseGetAll.builder().data(
                List.of(
                        UserGetAll.builder().id(1).firstName("Pepe").lastName("Perez").email("email").avatar("avatar").build(),
                        UserGetAll.builder().id(2).firstName("Juan").lastName("Garcia").email("email").avatar("avatar").build()
                )
        ).build();

        when(userApiRest.getAllWithReactor()).thenReturn(Mono.just(response));

        // Act
        var res = userRemoteRepository.getAllWithReactor().collectList().block();

        // Assert
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(2, res.size()),
                () -> assertEquals("Pepe", res.getFirst().getFirstName()),
                () -> assertEquals("Perez", res.getFirst().getLastName()),
                () -> assertEquals("Juan", res.getLast().getFirstName()),
                () -> assertEquals("Garcia", res.getLast().getLastName())
        );
        // Verify at least one call
        verify(userApiRest, times(1)).getAllWithReactor();

    }

    @Test
    void getAllCompletableFutureRxJava() {
        // Arrange
        var response = ResponseGetAll.builder().data(
                List.of(
                        UserGetAll.builder().id(1).firstName("Pepe").lastName("Perez").email("email").avatar("avatar").build(),
                        UserGetAll.builder().id(2).firstName("Juan").lastName("Garcia").email("email").avatar("avatar").build()
                )
        ).build();

        when(userApiRest.getAllWithRxJava()).thenReturn(Single.just(response));

        // Act
        var res = userRemoteRepository.getAllWithRxJava().toList().blockingGet();

        // Assert
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(2, res.size()),
                () -> assertEquals("Pepe", res.getFirst().getFirstName()),
                () -> assertEquals("Perez", res.getFirst().getLastName()),
                () -> assertEquals("Juan", res.getLast().getFirstName()),
                () -> assertEquals("Garcia", res.getLast().getLastName())
        );
        // Verify at least one call
        verify(userApiRest, times(1)).getAllWithRxJava();

    }
}