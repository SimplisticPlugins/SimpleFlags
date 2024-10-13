package com.ryderbelserion.simpleflags.commands.types;

import ch.jalu.configme.SettingsManager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.config.ConfigManager;
import com.ryderbelserion.simpleflags.config.impl.Config;
import com.ryderbelserion.simpleflags.config.impl.Locale;
import com.ryderbelserion.vital.paper.commands.PaperCommand;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends PaperCommand {

    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private final SettingsManager config = ConfigManager.getConfig();

    private final SettingsManager locale = ConfigManager.getLocale();

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        this.config.reload();

        this.locale.reload();

        sender.sendRichMessage(this.locale.getProperty(Locale.reload_plugin).replaceAll("\\{prefix}", this.config.getProperty(Config.command_prefix)));
    }

    @Override
    public @NotNull final String getPermission() {
        return "simpleflags.reload";
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("reload").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
            execute(new PaperCommandInfo(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        }).build();
    }

    @Override
    public @NotNull final PaperCommand registerPermission() {
        final PluginManager pluginManager = this.plugin.getServer().getPluginManager();

        final Permission permission = pluginManager.getPermission(getPermission());

        if (permission == null) {
            pluginManager.addPermission(new Permission(getPermission(), PermissionDefault.OP));
        }

        return this;
    }
}