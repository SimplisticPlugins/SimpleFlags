package com.ryderbelserion.simpleflags.platform.impl;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;

import java.util.List;

import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class Locale implements SettingsHolder {

    public static final Property<String> reload_plugin = newProperty("root.reload-plugin", "{prefix}<red>You have reloaded the plugin.");

    public static final Property<List<String>> help = newListProperty("root.help-menu", List.of(
            "<red>SimpleFlags Help Menu",
            "",
            " <red>/simpleflags reload <gray>- <gold>Reloads the plugin."
    ));
}