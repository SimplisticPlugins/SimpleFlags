package com.ryderbelserion.simpleflags.platform.impl;

import com.ryderbelserion.simpleflags.SimpleFlags;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Metrics {

    @NotNull
    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private org.bstats.bukkit.Metrics metrics;

    public void start() {
        if (this.metrics != null) {
            this.plugin.getLogger().warning("Metrics is already enabled.");

            return;
        }

        this.metrics = new org.bstats.bukkit.Metrics(this.plugin, 21261);

        this.plugin.getLogger().fine("Metrics has been enabled.");
    }

    public void stop() {
        if (this.metrics == null) {
            this.plugin.getLogger().warning("Metrics isn't enabled so we do nothing.");

            return;
        }

        this.metrics.shutdown();
        this.metrics = null;

        this.plugin.getLogger().fine("Metrics has been turned off.");
    }
}