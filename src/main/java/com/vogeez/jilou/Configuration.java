package com.vogeez.jilou;

import org.lwjgl.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    /**
     * Function for calling {@link #fetchFromArguments(String[], boolean)}with override set to false.
     * Call this for add configuration at runtime state. For initialization state use {@link #setFromArguments(String[])}.
     * @param args list of arguments which will set in configuration.
     */
    public static void addFromArguments(String[] args) {
        fetchFromArguments(args, false);
    }

    /**
     * Function for calling {@link #fetchFromArguments(String[], boolean)}with override set to true.
     * Call this for initialize your program. For and configurations in runtime use {@link #addFromArguments(String[])}.
     * @param args list of arguments which will set in configuration.
     */
    public static void setFromArguments(String[] args) {
        fetchFromArguments(args, true);
    }

    /**
     * Function for fetch {@link String} arrays which store program configurations.
     * The configurations ar stored in the jilou.toml file and can be set as properties in
     * arguments.
     * @param args list of arguments which will be searched at the configuration to change his values.
     * @param override force override and ignore system configurations.
     */
    public static void fetchFromArguments(String[] args, boolean override) {

    }

    /**
     * Function to print overall information about the current library.
     * Can be disabled in the jilou.toml file.
     */
    public static void printVersion() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("gradle.properties"));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        LOG.info("JILOU Version {}", properties.getProperty("version"));
        LOG.info("LWJGL Version {}", Version.getVersion().substring(0, 5));
    }

}
