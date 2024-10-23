package com.ryderbelserion.simpleflags.flags;

import com.ryderbelserion.simpleflags.SimpleFlags;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FlagBuilder<T extends Flag> {

protected SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    public abstract void register();

    public abstract String getName();

    public abstract T getFlag();

    /**
     * @return A query of regions.
     */
    protected RegionQuery getQuery() {
        return this.plugin.getRegions().createQuery();
    }

    /**
     * @return The flag registry.
     */
    protected FlagRegistry getRegistry() {
        return this.plugin.getWorldGuard().getFlagRegistry();
    }

}