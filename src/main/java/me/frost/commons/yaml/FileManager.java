package me.frost.commons.yaml;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class FileManager {
    private final JavaPlugin plugin;
    private final Reflections reflections;

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.reflections = new Reflections(plugin.getClass().getPackage().getName());
    }

    /**
     * This method will scan the package for any classes that extend SparkConfig and load them
     */
    public void loadConfigs() {
        reflections.getSubTypesOf(SparkConfig.class).forEach(clazz -> {
            try {
                SparkConfig config = clazz.newInstance();
                config.loadFile(plugin);
                config.write();
            } catch (InstantiationException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }
}