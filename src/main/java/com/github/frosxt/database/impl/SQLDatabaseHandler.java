package com.github.frosxt.database.impl;

import com.github.frosxt.database.IDatabase;
import com.github.frosxt.handler.IHandler;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Used to connect to a MySQL Database
 * <p>
 * The database can be retrieved using the getConnection() method
 * <p>
 * Methods for queries must be created yourself
 * <p>
 * This class should be registered as a handler
 * @see com.github.frosxt.handler.HandlerManager
 */
public abstract class SQLDatabaseHandler implements IDatabase, IHandler {
    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private final String databaseName;

    @Getter
    private MysqlDataSource dataSource = null;

    /**
     * Creates a new MySQL database instance
     * @param hostname The database host
     * @param port The database port
     * @param username The database username
     * @param password The database password
     * @param databaseName The database name
     */
    public SQLDatabaseHandler(final String hostname, final int port, final String username, final String password, final String databaseName) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    @Override
    public void load() {
        // Connects the database and initialises the connection variable
        connect();
    }

    /**
     * Connects to the database
     * This is handled automagically when the handler is initialised
     */
    @Override
    public void connect() {
        dataSource = new MysqlDataSource();

        dataSource.setServerName(hostname);
        dataSource.setPortNumber(port);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        try {
            try (final Connection connection = dataSource.getConnection()) {
                if (!connection.isValid(3)) {
                    throw new SQLException("Error whilst connecting to MySQL Database!");
                }
            }
        } catch (final SQLException exception) {
            Bukkit.getLogger().severe("Error whilst connecting to MySQL Database!");
            exception.printStackTrace();
        }

        /**try {
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + databaseName, username, password);
        } catch (final SQLException exception) {
            Bukkit.getLogger().severe("Error whilst connecting to MySQL Database!");
            exception.printStackTrace();
        }**/
    }

    @Override
    public void disconnect() {
        // This method is empty as it is not needed
    }

    /**
     * Checks if the database is initialised
     * @return true if the database field is not null
     */
    @Override
    public boolean isConnected() {
        return dataSource != null;
    }
}