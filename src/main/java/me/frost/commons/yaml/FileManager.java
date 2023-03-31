package me.frost.commons.yaml;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class FileManager {
    private final JavaPlugin plugin;
    private final Reflections reflections;

    // plugin.getClass().getPackage().getName()

    /**
     * This constructor will create a new instance of the FileManager class
     * @param plugin The JavaPlugin instance retrieved from the main class (ClassName.getPlugin(ClassName.class))
     */
    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.reflections = new Reflections(plugin.getClass().getPackage().getName());
    }

    /**
     * This method will scan the package for any classes that extend SparkConfig and load them
     *
     * @see SparkConfig
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