package me.frost.commons.commands.error;

public class InstanceError extends Error {
    private final String formattedMessage;

    public InstanceError(final String cause, final String formattedMessage) {
        super(cause, null, false, false);
        this.formattedMessage = formattedMessage;
    }

    public String getFormattedMessage() {
        return this.formattedMessage;
    }
}