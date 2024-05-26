package com.vogeez.jilou;

import org.lwjgl.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    public static void addFromArguments(String[] args) {
        fetchFromArguments(args, false);
    }

    public static void setFromArguments(String[] args) {
        fetchFromArguments(args, true);
    }

    public static void fetchFromArguments(String[] args, boolean override) {

    }

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
