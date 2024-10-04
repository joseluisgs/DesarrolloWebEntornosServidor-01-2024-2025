package dev.joseluisgs.services;

import dev.joseluisgs.errors.PersonaError;
import dev.joseluisgs.errors.PersonaNotFound;
import dev.joseluisgs.model.Persona;
import dev.joseluisgs.repository.PersonasRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonasServiceImplTest {

    @Mock
    private PersonasRepository personasRepository;

    @InjectMocks
    private PersonasServiceImpl personasService;


    @Test
    void getAllSuccess() {
        List<Persona> personas = List.of(Persona.builder().id(1).nombre("John Doe").build());
        when(personasRepository.getAll()).thenReturn(personas);

        Either<PersonaError, List<Persona>> result = personasService.getAll();

        assertAll(() -> {
            assertTrue(result.isRight());
            assertEquals(personas, result.get());

        });

        verify(personasRepository, times(1)).getAll();
    }

    @Test
    void getByIdSuccess() {
        Persona persona = Persona.builder().id(1).nombre("John Doe").build();
        when(personasRepository.getById(1)).thenReturn(Optional.of(persona));

        Either<PersonaError, Persona> result = personasService.getById(1);

        assertAll(() -> {
            assertTrue(result.isRight());
            assertEquals(persona, result.get());
        });
        verify(personasRepository, times(1)).getById(1);
    }

    @Test
    void getByIdNotFound() {
        when(personasRepository.getById(1)).thenReturn(Optional.empty());

        Either<PersonaError, Persona> result = personasService.getById(1);

        assertAll(() -> {
            assertTrue(result.isLeft());
            assertInstanceOf(PersonaNotFound.class, result.getLeft());
            assertEquals("Persona no encontrada con id: 1", result.getLeft().getMessage());
        });

        verify(personasRepository, times(1)).getById(1);
    }

    @Test
    void createSuccess() {
        Persona persona = Persona.builder().id(1).nombre("John Doe").build();
        when(personasRepository.create(persona)).thenReturn(persona);

        Either<PersonaError, Persona> result = personasService.create(persona);

        assertAll(() -> {
            assertTrue(result.isRight());
            assertEquals(persona, result.get());
        });

        verify(personasRepository, times(1)).create(persona);
    }

    @Test
    void createError() {
        Persona persona = Persona.builder().id(1).nombre("John Doe").build();
        when(personasRepository.create(persona)).thenThrow(new RuntimeException());

        Either<PersonaError, Persona> result = personasService.create(persona);

        assertTrue(result.isLeft());
        assertInstanceOf(PersonaError.class, result.getLeft());
        verify(personasRepository, times(1)).create(persona);
    }

    @Test
    void updateSuccess() {
        Persona persona = Persona.builder().id(1).nombre("John Doe").build();
        when(personasRepository.getById(1)).thenReturn(Optional.of(persona));
        when(personasRepository.update(1, persona)).thenReturn(persona);

        Either<PersonaError, Persona> result = personasService.update(1, persona);

        assertAll(() -> {
            assertTrue(result.isRight());
            assertEquals(persona, result.get());
        });

        assertTrue(result.isRight());
        assertEquals(persona, result.get());


        verify(personasRepository, times(1)).getById(1);
        verify(personasRepository, times(1)).update(1, persona);
    }

    @Test
    void updateNotFound() {
        Persona persona = Persona.builder().id(1).nombre("John Doe").build();
        when(personasRepository.getById(1)).thenReturn(Optional.empty());

        Either<PersonaError, Persona> result = personasService.update(1, persona);

        assertAll(() -> {
            assertTrue(result.isLeft());
            assertInstanceOf(PersonaNotFound.class, result.getLeft());
            assertEquals("Persona no encontrada con id: 1", result.getLeft().getMessage());
        });

        verify(personasRepository, times(1)).getById(1);
        verify(personasRepository, never()).update(1, persona);
    }

    @Test
    void deleteSuccess() {
        Persona persona = Persona.builder().id(1).nombre("John Doe").build();
        when(personasRepository.getById(1)).thenReturn(Optional.of(persona));

        Either<PersonaError, Persona> result = personasService.delete(1);

        assertAll(() -> {
            assertTrue(result.isRight());
            assertEquals(persona, result.get());
        });

        verify(personasRepository, times(1)).getById(1);
        verify(personasRepository, times(1)).delete(1);
    }

    @Test
    void deleteNotFound() {
        when(personasRepository.getById(1)).thenReturn(Optional.empty());

        Either<PersonaError, Persona> result = personasService.delete(1);

        assertAll(() -> {
            assertTrue(result.isLeft());
            assertInstanceOf(PersonaNotFound.class, result.getLeft());
            assertEquals("Persona no encontrada con id: 1", result.getLeft().getMessage());
        });
        
        verify(personasRepository, times(1)).getById(1);
        verify(personasRepository, never()).delete(1);
    }
}