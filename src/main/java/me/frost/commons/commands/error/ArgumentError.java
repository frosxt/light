package me.frost.commons.commands.error;

public class ArgumentError extends Error {
    private final Throwable exception;
    private final String formattedMessage;

    public ArgumentError(final String cause, final String formattedMessage, final Throwable exception) {
        super(cause, null, false, false);
        this.formattedMessage = formattedMessage;
        this.exception = exception;
    }

    public ArgumentError(final String cause, final String formattedMessage) {
        super(cause, null, false, false);
        this.formattedMessage = formattedMessage;
    }

    public Throwable getException() {
        return this.exception;
    }

    public String getFormattedMessage() {
        return this.formattedMessage;
    }
}