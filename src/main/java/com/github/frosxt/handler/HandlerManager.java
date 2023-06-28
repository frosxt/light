package com.github.frosxt.handler;

import java.util.*;

/**
 * Stores and loads IHandler classes
 * A new instance of this class should be made on plugin startup
 */
public class HandlerManager {
    private final Map<Class<? extends IHandler>, IHandler> map = new HashMap<>();

    /**
     * Registers a handler and runs the load() method
     * @param handler The handler to register
     */
    public void registerHandler(final IHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Handler passed is null!");
        }

        map.put(handler.getClass(), handler);
        handler.load();
    }

    /**
     * Registers all handlers provided and runs the load() method for each of them
     * @param handlers The handlers to register
     */
    public void registerHandlers(final IHandler... handlers) {
        Arrays.stream(handlers).forEach(handler -> {
            if (handler == null) {
                throw new IllegalArgumentException("Handler passed is null!");
            }

            map.put(handler.getClass(), handler);
            handler.load();
        });
    }

    /**
     * Registers all handlers in the list provided and runs the load() method for each of them
     * @param handlers The list of handlers to register
     */
    public void registerHandlers(final List<IHandler> handlers) {
        handlers.forEach(handler -> {
            if (handler == null) {
                throw new IllegalArgumentException("Handler passed is null!");
            }

            map.put(handler.getClass(), handler);
            handler.load();
        });
    }

    /**
     * Unregisters the handler and runs the unload() method
     * @param handlerClass The handler class to unregister
     */
    public void unregisterHandler(final Class<? extends IHandler> handlerClass) {
        if (!map.containsKey(handlerClass)) {
            return;
        }

        map.get(handlerClass).unload();
        map.remove(handlerClass);
    }

    /**
     * Gets a registered handler
     * @param tClass The class to search for
     * @return The found handler
     * @param <T> The type of handler
     */
    public <T> T getHandler(final Class<T> tClass) {
        return (T) map.get(tClass);
    }

    public Collection<IHandler> getHandlers() {
        return map.values();
    }
}