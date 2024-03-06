package com.ryderbelserion.simpleflags.flags;

import com.ryderbelserion.simpleflags.SimpleFlags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FlagBuilder {

    protected SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    public abstract void register();

    public abstract String getName();

    public abstract StateFlag getFlag();

    public abstract boolean checkFlag(Player player, DamageSource source);

    protected RegionQuery getQuery() {
        return this.plugin.getRegions().createQuery();
    }

    protected FlagRegistry getRegistry() {
        return this.plugin.getWorldGuard().getFlagRegistry();
    }
}