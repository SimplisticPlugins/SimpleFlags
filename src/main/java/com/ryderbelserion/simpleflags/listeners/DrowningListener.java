package com.ryderbelserion.simpleflags.listeners;

import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.flags.FlagBuilder;
import com.ryderbelserion.simpleflags.flags.FlagManager;
import com.ryderbelserion.simpleflags.flags.enums.CustomFlags;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DrowningListener implements Listener {

    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private final FlagManager flagManager = this.plugin.getFlagManager();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDrowning(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player player)) return;

        FlagBuilder flag = this.flagManager.getFlag(CustomFlags.DROWN_FLAG.getName());

        if (!flag.preventDamage(player, event.getDamageSource(), DamageType.DROWN)) {
            event.setCancelled(true);
        }
    }
}