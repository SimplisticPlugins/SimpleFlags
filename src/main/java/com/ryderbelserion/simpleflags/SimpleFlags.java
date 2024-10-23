package com.ryderbelserion.simpleflags;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.ryderbelserion.simpleflags.commands.BaseCommand;
import com.ryderbelserion.simpleflags.commands.types.CommandHelp;
import com.ryderbelserion.simpleflags.commands.types.CommandReload;
import com.ryderbelserion.simpleflags.config.ConfigManager;
import com.ryderbelserion.simpleflags.flags.FlagManager;
import com.ryderbelserion.simpleflags.flags.types.CapacityFlag;
import com.ryderbelserion.simpleflags.flags.types.DrownFlag;
import com.ryderbelserion.simpleflags.flags.types.NaturalFlag;
import com.ryderbelserion.simpleflags.listeners.DrowningListener;
import com.ryderbelserion.simpleflags.listeners.NaturalListener;
import com.ryderbelserion.vital.paper.Vital;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class SimpleFlags extends Vital {

    private WorldGuard worldGuard;

    private FlagManager flagManager;

    @Override
    public void onLoad() {
        ConfigManager.load(getDataFolder());

        this.worldGuard = WorldGuard.getInstance();

        this.flagManager = new FlagManager();

        List.of(
                new DrownFlag(),
                new NaturalFlag(),

                new CapacityFlag()
        ).forEach(this.flagManager::addFlag);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new NaturalListener(), this);
        getServer().getPluginManager().registerEvents(new DrowningListener(), this);

        // Register commands.
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            LiteralArgumentBuilder<CommandSourceStack> root = new BaseCommand().registerPermission().literal().createBuilder();

            List.of(
                    new CommandReload(),
                    new CommandHelp()
            ).forEach(command -> root.then(command.registerPermission().literal()));

            event.registrar().register(root.build(), "the base command for RedstonePvP");
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public @NotNull WorldGuard getWorldGuard() {
        return this.worldGuard;
    }

    public @NotNull RegionContainer getRegions() {
        return this.worldGuard.getPlatform().getRegionContainer();
    }

    public @NotNull FlagManager getFlagManager() {
        return this.flagManager;
    }
}