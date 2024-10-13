package com.ryderbelserion.simpleflags.config.impl;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class Config implements SettingsHolder {

    @Comment("The prefix that appears in front of messages.")
    public static final Property<String> command_prefix = newProperty("root.prefix", "<gold>[<red>SimpleFlags<gold>]: ");

    @Comment("The flag prevent_drowning as you guessed it, it prevents drowning if enabled.")
    public static final Property<Boolean> drowning_flag = newProperty("flags.drowning-flag", false);

    @Comment("The flag prevent_natural_spawning prevents natural mob spawns in regions.")
    public static final Property<Boolean> natural_spawning_flag = newProperty("flags.natural-spawning-flag", false);

}