package com.ryderbelserion.simpleflags;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.simpleflags.flags.FlagManager;
import com.ryderbelserion.simpleflags.flags.types.DrownFlag;
import com.ryderbelserion.simpleflags.flags.types.NaturalFlag;
import com.ryderbelserion.simpleflags.listeners.DrowningListener;
import com.ryderbelserion.simpleflags.listeners.NaturalListener;
import com.ryderbelserion.simpleflags.platform.BaseCommand;
import com.ryderbelserion.simpleflags.platform.impl.Config;
import com.ryderbelserion.simpleflags.platform.impl.Locale;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.List;

public class SimpleFlags extends JavaPlugin {


    private WorldGuard worldGuard;

    private FlagManager flagManager;

    @Override
    public void onLoad() {
        ConfigManager.load(getDataFolder());

        this.worldGuard = WorldGuard.getInstance();

        this.flagManager = new FlagManager();

        List.of(
                new DrownFlag(),
                new NaturalFlag()
        ).forEach(this.flagManager::addFlag);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new NaturalListener(), this);
        // Register the listener.
        getServer().getPluginManager().registerEvents(new DrowningListener(), this);
        getServer().getPluginManager().registerEvents(new NaturalListener(), this);

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