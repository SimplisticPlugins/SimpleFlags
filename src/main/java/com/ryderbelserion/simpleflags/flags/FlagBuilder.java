package com.ryderbelserion.simpleflags.flags;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.config.ConfigManager;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FlagBuilder<T extends Flag> {

    protected SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    protected SettingsManager config = ConfigManager.getConfig();

    public abstract void register();

    public abstract String getName();

    public abstract T getFlag();

    public abstract boolean isEnabled();

    /**
     * @return A query of regions.
     */
    public RegionQuery getQuery() {
        return this.plugin.getRegions().createQuery();
    }

    /**
     * @return The flag registry.
     */
    protected FlagRegistry getRegistry() {
        return this.plugin.getWorldGuard().getFlagRegistry();
    }
}