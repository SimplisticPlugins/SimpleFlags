package com.ryderbelserion.simpleflags.listeners;

import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.flags.builders.IntegerFlagBuilder;
import com.ryderbelserion.simpleflags.flags.FlagManager;
import com.ryderbelserion.simpleflags.flags.enums.CustomFlags;
import com.sk89q.worldguard.protection.flags.IntegerFlag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CapacityListener implements Listener {

    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private final FlagManager flagManager = this.plugin.getFlagManager();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Location location = event.getTo();

        if (location == event.getFrom()) return; // don't run if they didn't move.

        final String flagName = CustomFlags.CAPACITY_FLAG.getName();

        if (!this.flagManager.hasFlag(flagName)) return;

        final IntegerFlagBuilder flag = (IntegerFlagBuilder) this.flagManager.getFlag(flagName);

        if (!flag.isEnabled()) return;

        final IntegerFlag integerFlag = flag.getFlag();

        if (integerFlag == null) return;

        final Player player = event.getPlayer();

        final String query = flag.getQuery().queryValue(location, player, integerFlag);
    }
}