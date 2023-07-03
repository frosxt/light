package com.github.frosxt.database;

public interface IDatabase {
    void connect();

    void disconnect();

    boolean isConnected();
}