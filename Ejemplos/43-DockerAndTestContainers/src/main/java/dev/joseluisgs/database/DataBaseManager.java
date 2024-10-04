package dev.joseluisgs.database;

import dev.joseluisgs.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager implements AutoCloseable {
    private static DataBaseManager instance = null;
    private final Logger logger = LoggerFactory.getLogger(DataBaseManager.class);
    private String DB_URL = "jdbc:postgresql://localhost:5432/personas";
    private String DB_USER = "admin"; // reemplaza con tu usuario
    private String DB_PASSWORD = "adminPassword123"; // reemplaza con tu contraseña
    private Connection connection = null;

    protected DataBaseManager() {
    }

    // Cambiamos el constructor para que reciba la configuración
    private DataBaseManager(ConfigProperties config) {
        DB_URL = config.getProperty("database.url", DB_URL);
        DB_USER = config.getProperty("database.user", DB_USER);
        DB_PASSWORD = config.getProperty("database.password", DB_PASSWORD);
    }

    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public static DataBaseManager getInstance(ConfigProperties config) {
        if (instance == null) {
            instance = new DataBaseManager(config);
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            logger.error("Error al conectar a la base de datos", e);
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Desconectado de la base de datos...");
                logger.info("Desconectado de la base de datos...");
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Error al desconectar de la base de datos", e);
                throw new RuntimeException("Error al desconectar de la base de datos", e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        disconnect();
    }
}
