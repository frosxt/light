package com.github.frosxt.sparkcommons.store;

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
public class SparkStore<F extends File> {
    private final Gson gson;
    private final F file;

    public SparkStore(final F file) throws FileNotFoundException, UnsupportedEncodingException {
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
