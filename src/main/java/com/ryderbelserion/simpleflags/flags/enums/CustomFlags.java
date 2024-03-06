package com.ryderbelserion.simpleflags.flags.enums;

public enum CustomFlags {

    DROWN_FLAG("prevent_drowning");

    private final String name;

    CustomFlags(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}