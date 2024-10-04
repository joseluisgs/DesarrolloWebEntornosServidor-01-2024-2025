package dev.joseluisgs.repository;

import dev.joseluisgs.database.DataBaseManager;
import dev.joseluisgs.model.Persona;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers // Necesario para TestContainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonasRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:12-alpine")
            .withDatabaseName("testdb") // Nombre de la base de datos
            .withUsername("test") // Usuario
            .withPassword("test") // Contraseña
            .withInitScript("init-personas-db.sql"); // Script de inicialización en resources

    private static DataBaseManager dataBaseManager;
    private static PersonasRepositoryImpl personasRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        // Configurar el DataBaseManager para que use el contenedor de PostgreSQL
        dataBaseManager = new DataBaseManager() {
            @Override
            public Connection connect() throws SQLException {
                return DriverManager.getConnection(
                        postgresContainer.getJdbcUrl(),
                        postgresContainer.getUsername(),
                        postgresContainer.getPassword()
                );
            }
        };

        personasRepository = new PersonasRepositoryImpl(dataBaseManager);
    }

    @AfterAll
    public static void tearDown() {
        if (postgresContainer != null) {
            postgresContainer.stop(); // Paramos el contenedor
        }
    }


    @Test
    @Order(1)
    public void testGetAll() {
        List<Persona> personas = personasRepository.getAll();

        assertAll(() -> {
            assertNotNull(personas);
            assertEquals(1, personas.size());
            assertEquals("Test 01", personas.getFirst().getNombre());
        });

    }

    @Test
    @Order(2)
    public void testGetById() {
        var persona = personasRepository.getById(1L);

        assertAll(() -> {
            assertTrue(persona.isPresent());
            assertEquals("Test 01", persona.get().getNombre());
        });
    }

    @Test
    @Order(3)
    public void testGetByIdNotFound() {
        var persona = personasRepository.getById(2L);

        assertAll(() -> {
            assertTrue(persona.isEmpty());
        });
    }

    @Test
    @Order(4)
    public void testCreate() {
        Persona persona = Persona.builder()
                .nombre("Test 02")
                .build();

        var personaCreada = personasRepository.create(persona);

        assertAll(() -> {
            assertNotNull(personaCreada);
            assertEquals("Test 02", personaCreada.getNombre());
        });
    }

    @Test
    @Order(5)
    public void testUpdate() {
        Persona persona = Persona.builder()
                .id(1L)
                .nombre("Test 03")
                .build();

        var personaActualizada = personasRepository.update(1, persona);

        assertAll(() -> {
            assertNotNull(personaActualizada);
            assertEquals("Test 03", personaActualizada.getNombre());
        });
    }

    @Test
    @Order(6)
    public void testUpdateNotFound() {
        Persona persona = Persona.builder()
                .id(2L)
                .nombre("Test 03")
                .build();

        var personaActualizada = personasRepository.update(99, persona);

        assertAll(() -> {
            assertNull(personaActualizada);
        });
    }

    @Test
    @Order(7)
    public void testDelete() {
        var persona = personasRepository.delete(1);

        assertAll(() -> {
            assertTrue(persona);
        });
    }

    @Test
    @Order(8)
    public void testDeleteNotFound() {
        var persona = personasRepository.delete(99);

        assertAll(() -> {
            assertFalse(persona);
        });
    }

}