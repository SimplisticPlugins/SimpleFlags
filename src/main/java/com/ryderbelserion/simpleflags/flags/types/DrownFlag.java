package com.ryderbelserion.simpleflags.flags.types;

import com.ryderbelserion.simpleflags.flags.FlagBuilder;
import com.ryderbelserion.simpleflags.flags.enums.CustomFlags;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import java.util.logging.Level;

public class DrownFlag extends FlagBuilder {

    private StateFlag flag;

    @Override
    public void register() {
        try {
            getRegistry().register(this.flag = new StateFlag("prevent-drowning", true));
        } catch (FlagConflictException exception) {
            Flag<?> existingFlag = getRegistry().get("prevent-drowning");

            if (existingFlag instanceof StateFlag stateFlag) {
                this.flag = stateFlag;

                return;
            }

            this.plugin.getLogger().log(Level.WARNING, "An error has occurred registering " + getName() + " flag.", exception);
        }
    }

    @Override
    public String getName() {
        return CustomFlags.DROWN_FLAG.getName();
    }

    @Override
    public StateFlag getFlag() {
        return this.flag;
    }

    @Override
    public boolean checkFlag(Player player, DamageSource source) {
        if (source.getDamageType() != DamageType.DROWN) {
            return false;
        }

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

        Location location = BukkitAdapter.adapt(player.getLocation());

        return getQuery().testState(location, localPlayer, getFlag());
    }
}