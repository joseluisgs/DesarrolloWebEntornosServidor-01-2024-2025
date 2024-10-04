package personas.repository

import dev.joseluisgs.personas.model.Persona
import dev.joseluisgs.personas.repository.PersonasRepositoryImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*

class PersonasRepositoryImplTest {

    private var repository = PersonasRepositoryImpl()

    @BeforeEach
    fun setUp() {
        // Initialize repository for testing
        repository = PersonasRepositoryImpl()
        // Seed the repository with some data for testing
        repository.save(Persona(UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"), "Test1", 18))
        repository.save(Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "Test2", 19))
    }

    @AfterEach
    fun tearDown() {
        // Clean up the repository after testing
        repository = PersonasRepositoryImpl()
    }

    @Test
    fun findByName() {
        // Arrange

        // Act
        val result = repository.findByName("Test1")

        // Assert
        assertAll(
            { assert(result.size == 1) },
            { assert(result[0].nombre == "Test1") },
            { assert(result[0].edad == 18) }
        )
    }

    @Test
    fun getAll() {
        // Arrange

        // Act
        val result = repository.getAll()

        // Assert
        assertAll(
            { assert(result.size == 2) },
            { assert(result[0].nombre == "Test1") },
            { assert(result[0].edad == 18) },
            { assert(result[1].nombre == "Test2") },
            { assert(result[1].edad == 19) }
        )
    }

    @Test
    fun getById() {

        // Arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02")

        // Act
        val result = repository.getById(id)!!

        // Assert
        assertAll(
            { assert(result.nombre == "Test1") },
            { assert(result.edad == 18) }
        )
    }

    @Test
    fun getByIdNotFound() {

        // Arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03")

        // Act
        val result = repository.getById(id)

        // Assert
        assert(result == null)
    }

    @Test
    fun save() {
        // Arrange
        val persona = Persona(UUID.randomUUID(), "Test3", 20)

        // Act
        val result = repository.save(persona)

        // Assert
        assertAll(
            { assert(result.nombre == "Test3") },
            { assert(result.edad == 20) }
        )
    }

    @Test
    fun update() {
        // Arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02")
        val persona = Persona(id, "Test3", 20)

        // Act
        val result = repository.update(id, persona)!!

        // Assert
        assertAll(
            { assert(result.nombre == "Test3") },
            { assert(result.edad == 20) }
        )
    }

    @Test
    fun updateNotFound() {
        // Arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03")
        val persona = Persona(id, "Test3", 20)

        // Act
        val result = repository.update(id, persona)

        // Assert
        assert(result == null)
    }

    @Test
    fun delete() {
        // Arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02")

        // Act
        val result = repository.delete(id)!!

        // Assert
        assertAll(
            { assert(result.nombre == "Test1") },
            { assert(result.edad == 18) }
        )
    }

    @Test
    fun deleteNotFound() {
        // Arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03")

        // Act
        val result = repository.delete(id)

        // Assert
        assert(result == null)
    }
}