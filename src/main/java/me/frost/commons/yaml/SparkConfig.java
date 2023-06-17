package me.frost.commons.yaml;

import com.google.common.io.Files;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * This class will be used to load a new config file
 */
public abstract class SparkConfig {
    @Getter
    private final JavaPlugin plugin;

    @Getter
    private final String fileName;

    @Getter
    private final String path;

    @Getter
    private FileConfiguration config;

    private final File file;

    /**
     * @param fileName This is the name of the config file
     * @param path This is the path for the config file to load to
     */
    public SparkConfig(final JavaPlugin plugin, final String fileName, final String path) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.path = path;
        this.file = new File(path + File.separator + fileName + ".yml");
    }

    /**
     * This method will load a new config file
     */
    public void loadFile() {
        if (!file.exists()) {
            plugin.saveResource(fileName + ".yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.loadFromString(Files.toString(file, StandardCharsets.UTF_8));
        } catch (final IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }
}