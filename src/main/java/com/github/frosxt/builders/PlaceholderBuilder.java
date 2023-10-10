/*
 *     Light
 *     Copyright (C) 2023 frosxt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License in LICENSE.MD
 *     Please refer to that file for details.
 */

package com.github.frosxt.builders;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderBuilder {
    @Getter
    private final Map<String, String> placeholders;

    public PlaceholderBuilder() {
        placeholders = new HashMap<>();
    }

    public PlaceholderBuilder addPlaceholder(final String placeholder, final String replacement) {
        placeholders.put(placeholder, replacement);
        return this;
    }

    public PlaceholderBuilder addPlaceholders(final Map<String, String> placeholders) {
        placeholders.forEach(this::addPlaceholder);
        return this;
    }

    public String parse(String args) {
        for (final Map.Entry<String, String> entry : placeholders.entrySet()) {
            args = args.replace(entry.getKey(), entry.getValue());
        }

        return args;
    }
}