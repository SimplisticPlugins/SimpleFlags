package com.ryderbelserion.simpleflags.platform;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.platform.impl.Config;
import com.ryderbelserion.simpleflags.platform.impl.Locale;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Command("simpleflags")
@Permission("simpleflags.access")
public class BaseCommand {

    private final @NotNull SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private final @NotNull SettingsManager config = this.plugin.getOptions();

    private final @NotNull SettingsManager locale = this.plugin.getLocale();

    @Command
    @Permission(value = "simpleflags.help", def = PermissionDefault.TRUE)
    public void help(CommandSender sender) {
        this.locale.getProperty(Locale.help).forEach(sender::sendRichMessage);
    }

    @Command("reload")
    @Permission(value = "simpleflags.reload", def = PermissionDefault.OP)
    public void reload(CommandSender sender) {
        // Reload the config files.
        this.config.reload();
        this.locale.reload();

        sender.sendRichMessage(this.locale.getProperty(Locale.reload_plugin).replaceAll("\\{prefix}", this.config.getProperty(Config.command_prefix)));
    }
}