package com.github.frosxt.database.impl;

import com.github.frosxt.database.IDatabase;
import com.github.frosxt.handler.IHandler;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

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
    private final String databaseName;

    private com.mongodb.client.MongoClient mongoClient = null;

    @Getter
    private MongoDatabase database = null;

    private final String connectionString;

    /**
     * Creates a new MongoDB instance
     * @param hostname The database host
     * @param username The database username
     * @param password The database password
     * @param databaseName The database name
     */
    public MongoDatabaseHandler(
            final String hostname,
            final String username,
            final String password,
            final String databaseName) {

        this.databaseName = databaseName;

        this.connectionString = "mongodb+srv://" + username + ":" + password + "@" + hostname + "/?retryWrites=true&w=majority";
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
        final MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        this.mongoClient = MongoClients.create(settings);

        this.database = mongoClient.getDatabase(databaseName);
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