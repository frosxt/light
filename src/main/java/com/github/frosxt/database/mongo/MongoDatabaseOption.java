package com.github.frosxt.database.mongo;

import com.github.frosxt.database.DatabaseOption;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Collections;

public abstract class MongoDatabaseOption implements DatabaseOption {
    private final String hostname;
    private final int port;

    private final String username;
    private String password;
    private final String authenticationDatabase;
    private boolean authenticate;
    private boolean sslEnabled;

    @Getter
    private MongoDatabase database;

    public MongoDatabaseOption(final String hostname,
                               final int port,
                               final String username,
                               final String password,
                               final String authenticationDatabase,
                               final boolean authenticate,
                               final boolean sslEnabled) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.authenticationDatabase = authenticationDatabase;

        if (!password.isEmpty()) {
            this.password = password;
            this.authenticate = true;
            this.sslEnabled = sslEnabled;
        }

        setupDatabase();
    }

    @Override
    public void setupDatabase() {
        try {
            final MongoClient mongoClient = !authenticate ? new MongoClient(hostname, port) :
                    new MongoClient(
                            new ServerAddress(hostname, port),
                            Collections.singletonList(MongoCredential.createCredential(username, authenticationDatabase, password.toCharArray())),
                            MongoClientOptions.builder().sslEnabled(sslEnabled).build()
                    );

            this.database = mongoClient.getDatabase(authenticationDatabase);
        } catch (final Exception exception) {
            Bukkit.getLogger().info("Error whilst setting up MongoDB!");
            exception.printStackTrace();
        }
    }
}