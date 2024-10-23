package com.ryderbelserion.simpleflags.flags.builders;

import com.ryderbelserion.simpleflags.flags.FlagBuilder;
import com.sk89q.worldguard.protection.flags.IntegerFlag;

public abstract class IntegerFlagBuilder extends FlagBuilder<IntegerFlag> {

    @Override
    public abstract IntegerFlag getFlag();

}