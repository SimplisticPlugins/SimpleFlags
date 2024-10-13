package com.ryderbelserion.simpleflags.commands.types;

import ch.jalu.configme.SettingsManager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.simpleflags.SimpleFlags;
import com.ryderbelserion.simpleflags.config.ConfigManager;
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

public class CommandHelp extends PaperCommand {

    private final SimpleFlags plugin = JavaPlugin.getPlugin(SimpleFlags.class);

    private final SettingsManager locale = ConfigManager.getLocale();

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        this.locale.getProperty(Locale.help).forEach(sender::sendRichMessage);
    }

    @Override
    public @NotNull final String getPermission() {
        return "simpleflags.help";
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("help").requires(source -> source.getSender().hasPermission(getPermission()));

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