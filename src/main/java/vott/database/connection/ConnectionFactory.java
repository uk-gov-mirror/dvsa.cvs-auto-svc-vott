package vott.database.connection;

import lombok.RequiredArgsConstructor;
import vott.config.VottConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class ConnectionFactory {

    private final DatabaseConfiguration configuration;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            configuration.toJdbcUrl(),
            configuration.getUsername(),
            configuration.getPassword()
        );
    }
}
