package personas.services.cache

import dev.joseluisgs.personas.model.Persona
import dev.joseluisgs.personas.services.CachePersonasImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CachePersonasImplTest {

    private var cache = CachePersonasImpl()

    @BeforeEach
    fun setUp() {
        cache.clear()
    }

    @Test
    fun get() {
        // arrange
        val persona = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "John", 25)

        // act
        cache.put(persona.ID, persona)
        val result = cache.get(persona.ID)

        // Assert
        assertAll(
            { assertEquals(cache.size(), 1) },
            { assertNotNull(result) },
            { assertEquals(persona, result) }
        )
    }

    @Test
    fun getNotFound() {
        // arrange
        val persona = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "John", 25)

        // act
        cache.put(persona.ID, persona)
        val result = cache.get(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af17"))

        // Assert
        assertAll(
            { assertEquals(cache.size(), 1) },
            { assertNull(result) }
        )
    }

    @Test
    fun put() {
        // arrange
        val persona = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af17"), "John", 25)

        // act
        cache.put(persona.ID, persona)
        val result = cache.get(persona.ID)


        // Assert
        assertAll(
            { assertEquals(cache.size(), 1) },
            { assertNotNull(result) },
            { assertEquals(persona, result) }
        )
    }

    @Test
    fun putOverwrite() {
        // arrange
        val persona = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "John", 25)
        val persona2 = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "John", 25)

        // act
        cache.put(persona.ID, persona)
        cache.put(persona2.ID, persona2)
        val result = cache.get(persona.ID)

        // Assert
        assertAll(
            { assertEquals(cache.size(), 1) },
            { assertNotNull(result) },
            { assertEquals(persona, result) }
        )
    }

    @Test
    fun remove() {
        // arrange
        val persona = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "John", 25)

        // act
        cache.put(persona.ID, persona)
        cache.remove(persona.ID)
        val result = cache.get(persona.ID)

        // Assert
        assertAll(
            { assertEquals(cache.size(), 0) },
            { assertNull(result) },
        )
    }

    @Test
    fun clear() {
        // arrange
        val persona = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "John", 25)

        // act
        cache.put(persona.ID, persona)
        cache.clear()

        // Assert
        assertAll(
            { assertEquals(cache.size(), 0) }
        )
    }

    @Test
    fun size() {
        // arrange
        val persona = Persona(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af16"), "John", 25)

        // act
        cache.put(persona.ID, persona)

        // Assert
        assertAll(
            { assertEquals(cache.size(), 1) }
        )
    }
}