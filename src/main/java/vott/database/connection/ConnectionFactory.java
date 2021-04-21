package vott.database.connection;

import lombok.RequiredArgsConstructor;
import vott.config.VottConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class ConnectionFactory {

    private final VottConfiguration configuration;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            configuration.getDatabaseProperties().toJdbcUrl(),
            configuration.getDatabaseProperties().getUsername(),
            configuration.getDatabaseProperties().getPassword()
        );
    }
}
