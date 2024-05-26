package com.vogeez.jilou;

public final class Configuration {

    public static void addFromArguments(String[] args) {
        fetchFromArguments(args, false);
    }

    public static void setFromArguments(String[] args) {
        fetchFromArguments(args, true);
    }

    public static void fetchFromArguments(String[] args, boolean override) {

    }

}
