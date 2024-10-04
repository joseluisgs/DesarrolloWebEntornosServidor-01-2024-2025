package personas.services

import dev.joseluisgs.personas.exceptions.PersonaException
import dev.joseluisgs.personas.model.Persona
import dev.joseluisgs.personas.repository.PersonasRepository
import dev.joseluisgs.personas.services.PersonasServiceImpl
import dev.joseluisgs.personas.services.cache.CachePersonas
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class PersonasServiceImplTest {

    @MockK
    private lateinit var cache: CachePersonas

    @MockK
    private lateinit var repository: PersonasRepository

    @InjectMockKs
    private lateinit var personasService: PersonasServiceImpl

    private val persona1 = Persona(UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"), "Test1", 18)
    private val persona2 = Persona(UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03"), "Test2", 19)
    private val personas = listOf(persona1, persona2)

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun getAll() {
        // Arrange
        every { repository.getAll() } returns personas

        // Act
        val result = personasService.getAll()

        // Assert
        assertAll(
            { assert(result.isNotEmpty()) },
            { assert(result.size == 2) },
            { assert(result.contains(persona1)) },
            { assert(result.contains(persona2)) }
        )

        // Verify
        verify(atLeast = 1) { repository.getAll() }
    }

    @Test
    fun findByName() {
        // Arrange
        every { repository.findByName("Test1") } returns listOf(persona1)

        // Act
        val result = personasService.findByName("Test1")

        // Assert
        assertAll(
            { assert(result.isNotEmpty()) },
            { assert(result.size == 1) },
            { assert(result.contains(persona1)) }
        )

        // Verify
        verify(atLeast = 1) { repository.findByName("Test1") }
    }

    @Test
    fun getByIdInCache() {
        // Arrange
        every { cache.get(persona1.ID) } returns persona1

        // Act
        val result = personasService.getById(persona1.ID)

        // Assert
        assertAll(
            { assert(result == persona1) },
            { assert(result.ID == persona1.ID) },
            { assert(result.nombre == persona1.nombre) },
            { assert(result.edad == persona1.edad) }
        )

        // Verify
        verify(atLeast = 0) { repository.getById(persona1.ID) }
        verify(atLeast = 1) { cache.get(persona1.ID) }
    }

    @Test
    fun getByIdNotFoundInCacheButInRepository() {
        // Arrange
        every { cache.get(persona1.ID) } returns null
        every { repository.getById(persona1.ID) } returns persona1
        every { cache.put(persona1.ID, persona1) } returns Unit

        // Act
        val result = personasService.getById(persona1.ID)

        // Assert
        assertAll(
            { assert(result == persona1) },
            { assert(result.ID == persona1.ID) },
            { assert(result.nombre == persona1.nombre) },
            { assert(result.edad == persona1.edad) }
        )

        // Verify
        verify(atLeast = 1) { repository.getById(persona1.ID) }
        verify(atLeast = 1) { cache.get(persona1.ID) }
    }

    @Test
    fun getByIdNotFoundInCacheAndRepository() {
        // Arrange
        every { cache.get(persona1.ID) } returns null
        every { repository.getById(persona1.ID) } returns null

        // Act
        val message = assertThrows<PersonaException.PersonaNotFoundException> {
            personasService.getById(persona1.ID)
        }

        // Assert
        assertAll(
            { assert(message.message == "Persona no encontrada con ID: ${persona1.ID}") }
        )

        // Verify
        verify(atLeast = 1) { repository.getById(persona1.ID) }
        verify(atLeast = 1) { cache.get(persona1.ID) }
    }

    @Test
    fun save() {
        // Arrange
        every { repository.save(persona1) } returns persona1

        // Act
        val result = personasService.save(persona1)

        // Assert
        assertAll(
            { assert(result.ID == persona1.ID) },
            { assert(result.nombre == persona1.nombre) },
            { assert(result.edad == persona1.edad) }
        )

        // Verify
        verify(atLeast = 1) { repository.save(persona1) }
        verify(atLeast = 0) { cache.put(persona1.ID, persona1) }
    }

    @Test
    fun update() {
        // Arrange
        every { repository.update(persona1.ID, persona1) } returns persona1
        every { cache.remove(persona1.ID) } returns Unit

        // Act
        val result = personasService.update(persona1.ID, persona1)

        // Assert
        assertAll(
            { assert(result.ID == persona1.ID) },
            { assert(result.nombre == persona1.nombre) },
            { assert(result.edad == persona1.edad) }
        )

        // Verify
        verify(atLeast = 1) { repository.update(persona1.ID, persona1) }
        verify(atLeast = 1) { cache.remove(persona1.ID) }
    }

    @Test
    fun updateNotFound() {
        // Arrange
        every { repository.update(persona1.ID, persona1) } returns null
        every { cache.remove(persona1.ID) } returns Unit

        // Act
        val message = assertThrows<PersonaException.PersonaNotUpdatedException> {
            personasService.update(persona1.ID, persona1)
        }

        // Assert
        assertAll(
            { assert(message.message == "Persona no se ha podido actualizar con ID: ${persona1.ID}") }
        )

        // Verify
        verify(atLeast = 1) { repository.update(persona1.ID, persona1) }
        verify(atLeast = 1) { cache.remove(persona1.ID) }
    }

    @Test
    fun delete() {
        // Arrange
        every { repository.delete(persona1.ID) } returns persona1
        every { cache.remove(persona1.ID) } returns Unit

        // Act
        val result = personasService.delete(persona1.ID)

        // Assert
        assertAll(
            { assert(result.ID == persona1.ID) },
            { assert(result.nombre == persona1.nombre) },
            { assert(result.edad == persona1.edad) }
        )

        // Verify
        verify(atLeast = 1) { repository.delete(persona1.ID) }
        verify(atLeast = 1) { cache.remove(persona1.ID) }
    }

    @Test
    fun deleteNotFound() {
        // Arrange
        every { repository.delete(persona1.ID) } returns null
        every { cache.remove(persona1.ID) } returns Unit

        // Act
        val message = assertThrows<PersonaException.PersonaNotDeletedException> {
            personasService.delete(persona1.ID)
        }

        // Assert
        assertAll(
            { assert(message.message == "Persona no se ha podido eliminar con ID: ${persona1.ID}") }
        )

        // Verify
        verify(atLeast = 1) { repository.delete(persona1.ID) }
        verify(atLeast = 1) { cache.remove(persona1.ID) }
    }
}