package me.frost.commons.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    private final JsonObject loaded;
    private final JsonObject loader;

    public SparkStore(F file) {
        this.gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        this.file = file;
        this.loaded = new JsonObject();
        this.loader = new JsonObject();
    }

    public Gson getGson() {
        return gson;
    }

    public F getFile() {
        return file;
    }

    public JsonObject getLoaded() {
        return loaded;
    }

    public JsonObject getLoader() {
        return loader;
    }

    /**
     * @param savedAs The name of the object you want to get
     * @return The object you want to get
     */
    public JsonElement getObject(String savedAs) {
        return getLoaded().get(savedAs);
    }

    /**
     * @param object The object you want to save
     * @param saveAs The name that you want the object to save as
     *
     * Saves an object to a JSON File
     */
    public void saveObject(JsonObject object, String saveAs) {
        getLoader().add(saveAs, object);
        getLoaded().add("spark-store", getLoader());

        try (FileWriter writer = new FileWriter(getFile())) {
            getFile().delete();
            getFile().createNewFile();

            writer.write(getGson().toJson(getLoaded()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
