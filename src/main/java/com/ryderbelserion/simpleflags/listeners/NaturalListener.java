package com.ryderbelserion.simpleflags.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.flags.builders.StateFlagBuilder;
import com.ryderbelserion.simpleflags.flags.FlagManager;
import com.ryderbelserion.simpleflags.flags.enums.CustomFlags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NaturalListener implements Listener {

    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private final FlagManager flagManager = this.plugin.getFlagManager();

    @EventHandler(ignoreCancelled = true)
    public void onSpawnEvent(PreCreatureSpawnEvent event) {
        final String flagName = CustomFlags.NATURAL_FLAG.getName();

        if (!this.flagManager.hasFlag(flagName)) return;

        final StateFlagBuilder flag = (StateFlagBuilder) this.flagManager.getFlag(flagName);

        if (!flag.isEnabled()) return;

        final StateFlag stateFlag = flag.getFlag();

        if (stateFlag == null) return;

        if (!flag.preventSpawning(event.getSpawnLocation(), event.getReason(), CreatureSpawnEvent.SpawnReason.NATURAL, stateFlag)) {
            event.setCancelled(true);
        }
    }
}