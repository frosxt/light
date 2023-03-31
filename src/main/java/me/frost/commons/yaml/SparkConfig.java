package me.frost.commons.yaml;

import me.frost.commons.SparkCommons;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * This class will be used to load a new config file
 */
public abstract class SparkConfig {
    private final String fileName;
    private final String path;
    private static FileConfiguration config;
    private final Consumer<FileConfiguration> writer;

    // Hashmap to store every config
    protected Map<String, FileConfiguration> loadedConfigs = new HashMap<>();

    /**
     * @param fileName This is the name of the config file
     * @param path This is the path for the config file to load to
     * @param writer This is what will be written to the file
     */
    protected SparkConfig(String fileName, String path, Consumer<FileConfiguration> writer) {
        this.fileName = fileName;
        this.path = path;
        this.writer = writer;
    }

    /**
     * @param plugin This is the JavaPlugin parameter
     *
     * This method will load a new config file
     */
    public void loadFile(JavaPlugin plugin) {
        File file = new File(path, fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

        config = YamlConfiguration.loadConfiguration(file);
        loadedConfigs.put(fileName, config);
    }

    public void write() {
        writer.accept(loadedConfigs.get(fileName));
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public FileConfiguration getFileConfiguration() {
        return loadedConfigs.get(fileName);
    }

    public Consumer<FileConfiguration> getWriter() {
        return writer;
    }

    protected Map<String, FileConfiguration> getLoadedConfigs() {
        return loadedConfigs;
    }
}
