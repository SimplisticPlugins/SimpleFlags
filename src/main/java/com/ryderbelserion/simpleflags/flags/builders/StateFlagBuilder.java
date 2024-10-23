package com.ryderbelserion.simpleflags.flags.builders;

import com.ryderbelserion.simpleflags.flags.FlagBuilder;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

public abstract class StateFlagBuilder extends FlagBuilder<StateFlag> {

    @Override
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
        return source.getDamageType() == type && getQuery().testState(BukkitAdapter.adapt(player.getLocation()), WorldGuardPlugin.inst().wrapPlayer(player), getFlag());
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
        return receivingReason != reason || getQuery().testState(BukkitAdapter.adapt(location), null, getFlag());
    }
}