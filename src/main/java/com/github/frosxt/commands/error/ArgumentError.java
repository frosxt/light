/*
 *     SparkCommons
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

package com.github.frosxt.commands.error;

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