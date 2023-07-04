package com.github.frosxt.database.impl;

import com.github.frosxt.database.IDatabase;
import com.github.frosxt.handler.IHandler;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
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
    private final String authenticationDatabase;

    @Getter
    private Connection connection = null;

    /**
     * Creates a new MySQL database instance
     * @param hostname The database host
     * @param port The database port
     * @param username The database username
     * @param password The database password
     * @param authenticationDatabase The database name
     */
    public SQLDatabaseHandler(final String hostname, final int port, final String username, final String password, final String authenticationDatabase) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.authenticationDatabase = authenticationDatabase;
    }

    @Override
    public void load() {
        // Connects the database and initialises the connection variable
        connect();
    }

    @Override
    public void unload() {
        // Disconnects the database
        disconnect();
    }

    /**
     * Connects to the database and initialises the connection variable
     * This is handled automagically when the handler is initialised
     */
    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + authenticationDatabase, username, password);
        } catch (final SQLException exception) {
            Bukkit.getLogger().severe("Error whilst connecting to MySQL Database!");
            exception.printStackTrace();
        }
    }

    /**
     * Disconnects the database
     * This is handled automagically when the handler is unregistered
     */
    @Override
    public void disconnect() {
        if (!isConnected()) {
            return;
        }

        try {
            connection.close();
        } catch (final SQLException exception) {
            Bukkit.getLogger().severe("Error whilst disconnecting from MySQL Database!");
            exception.printStackTrace();
        }
    }

    /**
     * Checks if the database is initialised
     * @return true if the database field is not null
     */
    @Override
    public boolean isConnected() {
        return connection != null;
    }
}