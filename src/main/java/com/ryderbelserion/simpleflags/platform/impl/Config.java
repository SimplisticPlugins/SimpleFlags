package com.ryderbelserion.simpleflags.platform.impl;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class Config implements SettingsHolder {

    @Comment("The prefix that appears in front of messages.")
    public static final Property<String> command_prefix = newProperty("root.prefix", "<gold>[<red>SimpleFlags<gold>]: ");
}