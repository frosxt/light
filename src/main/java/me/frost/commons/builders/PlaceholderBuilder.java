package me.frost.commons.builders;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderBuilder {
    private final Map<String, String> placeholders;

    public PlaceholderBuilder() {
        placeholders = new HashMap<>();
    }

    public PlaceholderBuilder addPlaceholder(String replace, String replacement) {
        placeholders.put(replace, replacement);
        return this;
    }

    public String parse(String args) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            args = args.replace(entry.getKey(), entry.getValue());
        }
        return args;
    }
}