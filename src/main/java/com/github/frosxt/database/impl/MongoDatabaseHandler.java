package com.github.frosxt.database.impl;

import com.github.frosxt.database.IDatabase;
import com.github.frosxt.handler.IHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * Used to connect to a MongoDB
 * <p>
 * The database can be retrieved using the getDatabase() method
 * <p>
 * Methods for queries must be created yourself
 * <p>
 * MongoClient handles connection pooling itself which means we do not need to disconnect the database ourselves
 * <p>
 * This class should be registered as a handler
 * @see com.github.frosxt.handler.HandlerManager
 */
public abstract class MongoDatabaseHandler implements IDatabase, IHandler {
    private final String hostname;
    private final int port;

    private final String username;
    private String password;
    private final String databaseName;
    private boolean authenticate = false;
    private boolean sslEnabled;

    private MongoClient mongoClient = null;
    @Getter
    private MongoDatabase database = null;

    /**
     * Creates a new MongoDB instance
     * @param hostname The database host
     * @param port The database port
     * @param username The database username
     * @param password The database password
     * @param databaseName The database name
     * @param sslEnabled Whether SSL is enabled or not
     */
    public MongoDatabaseHandler(
            final String hostname,
            final int port,
            final String username,
            final String password,
            final String databaseName,
            final boolean sslEnabled) {

        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.databaseName = databaseName;

        if (!password.isEmpty()) {
            this.password = password;
            this.authenticate = true;
            this.sslEnabled = sslEnabled;
        }
    }

    @Override
    public void load() {
        // Connects the database and initialises the database variable
        connect();
    }

    /**
     * Connects to the database and initialises the database variable
     * This is handled automagically when the handler is initialised
     */
    @Override
    public void connect() {
        if (authenticate) {
            try {
                this.mongoClient = new MongoClient(
                        new ServerAddress(hostname, port),
                        MongoCredential.createCredential(username, databaseName, password.toCharArray()),
                        MongoClientOptions.builder().sslEnabled(sslEnabled).build()
                );

                this.database = mongoClient.getDatabase(databaseName);
            } catch (final Exception exception) {
                Bukkit.getLogger().severe("Error whilst connecting to MongoDB!");
                exception.printStackTrace();
            }


            return;
        }

        try {
            this.mongoClient = new MongoClient(hostname, port);
            this.database = mongoClient.getDatabase(databaseName);
        } catch (final Exception exception) {
            Bukkit.getLogger().severe("Error whilst connecting to MongoDB!");
            exception.printStackTrace();
        }
    }

    /**
     * Closes the MongoClient instance
     */
    @Override
    public void disconnect() {
        mongoClient.close();
    }

    /**
     * Checks if the database is initialised
     * @return true if the database field is not null
     */
    @Override
    public boolean isConnected() {
        return database != null && mongoClient != null;
    }
}