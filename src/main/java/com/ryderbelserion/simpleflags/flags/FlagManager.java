package com.ryderbelserion.simpleflags.flags;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FlagManager {

    private final Map<String, FlagBuilder> flags = new HashMap<>();

    public void addFlag(FlagBuilder builder) {
        // Register the flag.
        builder.register();

        // Add to hashmap.
        this.flags.putIfAbsent(builder.getName(), builder);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasFlag(String name) {
        return this.flags.containsKey(name);
    }

    public FlagBuilder getFlag(String name) {
        return this.flags.get(name);
    }

    public void removeFlag(String name) {
        this.flags.remove(name);
    }

    public Map<String, FlagBuilder> getFlags() {
        return Collections.unmodifiableMap(this.flags);
    }
}