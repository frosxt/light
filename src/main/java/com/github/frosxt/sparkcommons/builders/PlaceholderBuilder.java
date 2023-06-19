package com.github.frosxt.sparkcommons.builders;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderBuilder {
    @Getter
    private final Map<String, String> placeholders;

    public PlaceholderBuilder() {
        placeholders = new HashMap<>();
    }

    public void addPlaceholder(final String replace, final String replacement) {
        placeholders.put(replace, replacement);
    }

    public void addPlaceholders(final Map<String, String> placeholders) {
        placeholders.forEach(this::addPlaceholder);
    }

    public String parse(String args) {
        for (final Map.Entry<String, String> entry : placeholders.entrySet()) {
            args = args.replace(entry.getKey(), entry.getValue());
        }

        return args;
    }
}