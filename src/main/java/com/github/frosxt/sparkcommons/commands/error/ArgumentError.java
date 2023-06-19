package com.github.frosxt.sparkcommons.commands.error;

public class ArgumentError extends Error {
    private final Throwable exception;
    private final String formattedMessage;

    public ArgumentError(final String cause, final String formattedMessage, final Throwable exception) {
        super(cause, null, false, false);
        this.formattedMessage = formattedMessage;
        this.exception = exception;
    }

    public Throwable getException() {
        return this.exception;
    }

    public String getFormattedMessage() {
        return this.formattedMessage;
    }
}