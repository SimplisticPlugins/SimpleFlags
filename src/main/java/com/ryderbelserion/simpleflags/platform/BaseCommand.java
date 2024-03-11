package com.ryderbelserion.simpleflags.platform;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.platform.impl.Config;
import com.ryderbelserion.simpleflags.platform.impl.Locale;
import com.ryderbelserion.simpleflags.platform.impl.Metrics;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Command("simpleflags")
public class BaseCommand {

    @NotNull
    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    @NotNull
    private final Metrics metrics = this.plugin.getMetrics();

    @NotNull
    private final SettingsManager config = this.plugin.getOptions();

    @NotNull
    private final SettingsManager locale = this.plugin.getLocale();

    @NotNull
    private final MiniMessage message = MiniMessage.miniMessage();

    @Command
    @Permission(value = "simpleflags.help", def = PermissionDefault.TRUE)
    public void help(CommandSender sender) {
        this.locale.getProperty(Locale.help).forEach(line -> sender.sendMessage(this.message.deserialize(line)));
    }

    @Command("reload")
    @Permission(value = "simpleflags.reload", def = PermissionDefault.OP)
    public void reload(CommandSender sender) {
        // Reload the config files.
        this.config.reload();
        this.locale.reload();

        // Check if metrics needs to be enabled or not.
        if (!this.config.getProperty(Config.toggle_metrics)) {
            this.metrics.stop();
        } else {
            this.metrics.start();
        }

        // Send the reload message.
        sender.sendMessage(this.message.deserialize(this.locale.getProperty(Locale.reload_plugin).replaceAll("\\{prefix}", this.config.getProperty(Config.command_prefix))));
    }
}