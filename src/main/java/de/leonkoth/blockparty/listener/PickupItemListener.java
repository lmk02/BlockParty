package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.pauhull.utils.misc.MinecraftVersion;

import static de.pauhull.utils.misc.MinecraftVersion.v1_12;

public class PickupItemListener {

    public PickupItemListener(BlockParty blockParty) {
        if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_12)) {
            new EntityPickupItemListener(blockParty);
        } else {
            new PlayerPickupItemListener(blockParty);
        }
    }

}
