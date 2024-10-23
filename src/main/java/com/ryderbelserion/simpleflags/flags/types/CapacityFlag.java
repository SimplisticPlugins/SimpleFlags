package com.ryderbelserion.simpleflags.flags.types;

import com.ryderbelserion.simpleflags.config.impl.Config;
import com.ryderbelserion.simpleflags.flags.builders.IntegerFlagBuilder;
import com.ryderbelserion.simpleflags.flags.enums.CustomFlags;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.IntegerFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import java.util.logging.Level;

public class CapacityFlag extends IntegerFlagBuilder {

    private static final Number[] values = {0, 5, 10, 20};

    private IntegerFlag flag;

    @Override
    public void register() {
        if (!isEnabled()) return;

        try {
            this.flag = new IntegerFlag("max-players");

            this.flag.setSuggestedValues(values); //todo() might break in the future

            getRegistry().register(this.flag);
        } catch (FlagConflictException exception) {
            Flag<?> existingFlag = getRegistry().get("max-players");

            if (existingFlag instanceof IntegerFlag integerFlag) {
                this.flag = integerFlag;

                return;
            }

            this.plugin.getLogger().log(Level.WARNING, "An error has occurred registering " + getName() + " flag.", exception);
        }
    }

    @Override
    public String getName() {
        return CustomFlags.CAPACITY_FLAG.getName();
    }

    @Override
    public IntegerFlag getFlag() {
        return this.flag;
    }

    @Override
    public boolean isEnabled() {
        return this.config.getProperty(Config.max_players_flag);
    }
}