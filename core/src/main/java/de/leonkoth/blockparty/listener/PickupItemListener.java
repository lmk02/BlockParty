package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.version.Version;

import static de.leonkoth.blockparty.version.Version.v1_12;

public class PickupItemListener {

    public PickupItemListener(BlockParty blockParty) {
        if (Version.CURRENT_VERSION.isGreaterOrEquals(v1_12)) {
            new EntityPickupItemListener(blockParty);
        } else {
            new PlayerPickupItemListener(blockParty);
        }
    }

}
