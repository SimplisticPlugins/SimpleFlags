package com.ryderbelserion.simpleflags.flags;

import com.ryderbelserion.simpleflags.SimpleFlags;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FlagBuilder {

    protected SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    public abstract void register();

    public abstract String getName();

    public abstract StateFlag getFlag();

    /**
     * Prevent any type of damage in any region.
     *
     * @param player the player to prevent damage to.
     * @param source the source of the damage.
     * @param type the type of the damage to check.
     * @return true or false
     */
    public boolean preventDamage(Player player, DamageSource source, DamageType type) {
        return source.getDamageType() == type && getQuery().testState(BukkitAdapter.adapt(player.getLocation()),  WorldGuardPlugin.inst().wrapPlayer(player), getFlag());
    }

    /**
     * Prevent spawning based on a supplied spawn reason.
     *
     * @param location the location to check.
     * @param receivingReason the reason from the event.
     * @param reason your reason.
     * @return true or false
     */
    public boolean preventSpawning(org.bukkit.Location location, CreatureSpawnEvent.SpawnReason receivingReason, CreatureSpawnEvent.SpawnReason reason) {
        if (receivingReason != reason) {
            return false;
        }

        return getQuery().testState(BukkitAdapter.adapt(location), null, getFlag());
    }

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