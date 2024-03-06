package com.ryderbelserion.simpleflags;

import com.ryderbelserion.simpleflags.flags.FlagManager;
import com.ryderbelserion.simpleflags.flags.types.DrownFlag;
import com.ryderbelserion.simpleflags.listeners.DrowningListener;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class SimpleFlags extends JavaPlugin {

    private WorldGuard worldGuard;
    private RegionContainer regions;

    private FlagManager flagManager;

    @Override
    public void onLoad() {
        this.worldGuard = WorldGuard.getInstance();
        this.regions = this.worldGuard.getPlatform().getRegionContainer();

        this.flagManager = new FlagManager();

        List.of(
                new DrownFlag()
        ).forEach(this.flagManager::addFlag);
    }

    @Override
    public void onEnable() {
        // Register the listener.
        getServer().getPluginManager().registerEvents(new DrowningListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public WorldGuard getWorldGuard() {
        return this.worldGuard;
    }

    public RegionContainer getRegions() {
        return this.regions;
    }

    public FlagManager getFlagManager() {
        return this.flagManager;
    }
}