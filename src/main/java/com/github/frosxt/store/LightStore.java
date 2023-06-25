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

package com.github.frosxt.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

/**
 *
 * @param <F>
 *
 * Every store must have its own file, and the file will be taken and written to
 * A SparkStore can only be used once for each file, and the file must be a .json file
 */
public class LightStore<F extends File> {
    private final Gson gson;
    private final F file;

    public LightStore(final F file) throws FileNotFoundException, UnsupportedEncodingException {
        this.gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
        this.file = file;

        if (!file.exists()) {
            final PrintWriter printWriter = new PrintWriter(file, "UTF-8");

            printWriter.print("{");
            printWriter.print("}");
            printWriter.flush();
            printWriter.close();
        }
    }

    public Gson getGson() {
        return gson;
    }

    public F getFile() {
        return file;
    }

    /**
     *
     * @param file The file you want to retrieve data from
     * @return A JsonObject of the data
     */
    public JsonObject getObject(final File file) throws FileNotFoundException, UnsupportedEncodingException {
        return new JsonParser().parse(new InputStreamReader(new FileInputStream(file), "UTF-8")).getAsJsonObject();
    }

    /**
     * @param object The object you want to save
     *
     * Saves an object to a JSON File
     */
    public void saveObject(final JsonObject object) {
        try (final FileWriter writer = new FileWriter(getFile())) {
            writer.write(getGson().toJson(object));
            writer.flush();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
}
