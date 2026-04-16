package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;

public class PickupItemListener {

    public PickupItemListener(BlockParty blockParty) {
        new EntityPickupItemListener(blockParty);
    }

}
