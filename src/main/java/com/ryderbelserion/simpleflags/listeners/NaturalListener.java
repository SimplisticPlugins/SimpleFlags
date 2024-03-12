package com.ryderbelserion.simpleflags.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.flags.FlagBuilder;
import com.ryderbelserion.simpleflags.flags.FlagManager;
import com.ryderbelserion.simpleflags.flags.enums.CustomFlags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NaturalListener implements Listener {

    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private final FlagManager flagManager = this.plugin.getFlagManager();

    @EventHandler(ignoreCancelled = true)
    public void onSpawnEvent(PreCreatureSpawnEvent event) {
        FlagBuilder flag = this.flagManager.getFlag(CustomFlags.NATURAL_FLAG.getName());

        if (!flag.preventSpawning(event.getSpawnLocation(), event.getReason(), CreatureSpawnEvent.SpawnReason.NATURAL)) {
            event.setCancelled(true);
        }
    }
}