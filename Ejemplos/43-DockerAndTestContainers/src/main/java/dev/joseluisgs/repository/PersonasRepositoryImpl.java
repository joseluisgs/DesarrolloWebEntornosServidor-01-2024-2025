package dev.joseluisgs.repository;

import dev.joseluisgs.database.DataBaseManager;
import dev.joseluisgs.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonasRepositoryImpl implements PersonasRepository {
    private final Logger logger = LoggerFactory.getLogger(PersonasRepositoryImpl.class);
    private final DataBaseManager dataBaseManager;

    public PersonasRepositoryImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public List<Persona> getAll() {
        logger.info("Obteniendo personas...");
        List<Persona> personas = new ArrayList<>();
        String query = "SELECT * FROM personas";

        // Esto es un try-with-resources, se cierra automáticamente
        try (Connection connection = dataBaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Persona persona = Persona.builder()
                        .id(resultSet.getLong("id"))
                        .uuid((java.util.UUID) resultSet.getObject("uuid"))
                        .nombre(resultSet.getString("nombre"))
                        .createdAt(resultSet.getObject("created_at", LocalDateTime.class))
                        .updatedAt(resultSet.getObject("updated_at", LocalDateTime.class))
                        .build();
                personas.add(persona);
            }
        } catch (SQLException e) {
            logger.error("Error al obtener personas", e);
        }

        return personas;
    }

    @Override
    public Optional<Persona> getById(long id) {
        logger.info("Obteniendo persona por id...");
        String query = "SELECT * FROM personas WHERE id = ?";

        // Esto es un try-with-resources, se cierra automáticamente
        try (Connection connection = dataBaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Sustituimos el ? por el id
            statement.setLong(1, id);
            // Ejecutamos la consulta como try with resources
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(Persona.builder()
                            .id(resultSet.getLong("id"))
                            .uuid((java.util.UUID) resultSet.getObject("uuid"))
                            .nombre(resultSet.getString("nombre"))
                            .createdAt(resultSet.getObject("created_at", LocalDateTime.class))
                            .updatedAt(resultSet.getObject("updated_at", LocalDateTime.class))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener persona por id", e);
        }
        return Optional.empty();
    }

    @Override
    public Persona create(Persona persona) {
        logger.info("Creando persona...");
        String query = "INSERT INTO personas (uuid, nombre, created_at, updated_at) VALUES (?, ?, ?, ?)";
        var uuid = java.util.UUID.randomUUID();
        var timeStamp = LocalDateTime.now();
        // Esto es un try-with-resources, se cierra automáticamente
        try (Connection connection = dataBaseManager.connect();
             // Este statement nos permite recuperar la clave generada
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Sustituimos los ? por los valores de la persona
            statement.setObject(1, uuid);
            statement.setString(2, persona.getNombre());
            statement.setObject(3, timeStamp);
            statement.setObject(4, timeStamp);

            // Ejecutamos la consulta
            statement.executeUpdate();

            // Recuperamos la clave generada
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    persona.setId(id); // Asumiendo que Persona tiene un método setId
                    persona.setUuid(uuid);
                    persona.setCreatedAt(timeStamp);
                    persona.setUpdatedAt(timeStamp);
                    return persona;
                } else {
                    throw new SQLException("No se pudo obtener la clave generada.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error al crear persona", e);
        }

        return persona;
    }

    @Override
    public Persona update(long id, Persona persona) {
        logger.info("Actualizando persona...");
        String query = "UPDATE personas SET nombre = ?, updated_at = ? WHERE id = ?";
        LocalDateTime timeStamp = LocalDateTime.now();
        // Esto es un try-with-resources, se cierra automáticamente
        try (Connection connection = dataBaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Sustituimos los ? por los valores de la persona
            statement.setString(1, persona.getNombre());
            statement.setObject(2, timeStamp);
            statement.setLong(3, id);

            // Ejecutamos la consulta
            int rows = statement.executeUpdate();
            if (rows > 0) {
                persona.setUpdatedAt(timeStamp);
                return persona;
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar persona", e);
        }

        return null;
    }

    @Override
    public boolean delete(long id) {
        logger.info("Borrando persona...");
        String query = "DELETE FROM personas WHERE id = ?";
        // Esto es un try-with-resources, se cierra automáticamente
        try (Connection connection = dataBaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Sustituimos el ? por el id
            statement.setLong(1, id);

            // Ejecutamos la consulta
            int rows = statement.executeUpdate();
            if (rows > 0) {
                return true;
            } else {
                logger.warn("No se ha borrado ninguna persona");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error al borrar persona", e);
        }

        return false;
    }

}
