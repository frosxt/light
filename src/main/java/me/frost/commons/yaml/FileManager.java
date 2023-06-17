package me.frost.commons.yaml;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
    @Getter
    private final Map<String, SparkConfig> loadedConfigs = new HashMap<>();

    /**
     * This method will load any configs and store them in a HashMap to be referenced from
     *
     * @param sparkConfig An instance of the config class to be loaded
     *
     * @see SparkConfig
     */
    public void loadConfig(final SparkConfig sparkConfig) {
        sparkConfig.loadFile();

        loadedConfigs.put(sparkConfig.getFileName(), sparkConfig);
    }
}