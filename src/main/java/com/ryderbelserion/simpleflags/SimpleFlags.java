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
import com.ryderbelserion.simpleflags.platform.impl.Metrics;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.List;

public class SimpleFlags extends JavaPlugin {

    private SettingsManager config;
    private SettingsManager locale;

    private WorldGuard worldGuard;

    private FlagManager flagManager;

    private Metrics metrics;

    @Override
    public void onLoad() {
        this.worldGuard = WorldGuard.getInstance();

        this.flagManager = new FlagManager();

        List.of(
                new DrownFlag(),
                new NaturalFlag()
        ).forEach(this.flagManager::addFlag);
    }

    @Override
    public void onEnable() {
        // Create config files
        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        this.config = SettingsManagerBuilder
                .withYamlFile(new File(getDataFolder(), "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(Config.class)
                .create();

        if (this.config.getProperty(Config.toggle_metrics)) {
            this.metrics = new Metrics();
            this.metrics.start();
        }

        this.locale = SettingsManagerBuilder
                .withYamlFile(new File(getDataFolder(), "messages.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(Locale.class)
                .create();

        // Register the listener.
        getServer().getPluginManager().registerEvents(new DrowningListener(), this);
        getServer().getPluginManager().registerEvents(new NaturalListener(), this);

        // Register the commands.
        BukkitCommandManager<CommandSender> command = BukkitCommandManager.create(this);

        command.registerCommand(new BaseCommand());
    }

    @Override
    public void onDisable() {
        if (this.metrics != null) this.metrics.stop();

        if (this.config != null) this.config.save();

        if (this.locale != null) this.locale.save();
    }

    public SettingsManager getOptions() {
        return this.config;
    }

    public Metrics getMetrics() {
        return this.metrics;
    }

    public SettingsManager getLocale() {
        return this.locale;
    }

    public WorldGuard getWorldGuard() {
        return this.worldGuard;
    }

    public RegionContainer getRegions() {
        return this.worldGuard.getPlatform().getRegionContainer();
    }

    public FlagManager getFlagManager() {
        return this.flagManager;
    }
}