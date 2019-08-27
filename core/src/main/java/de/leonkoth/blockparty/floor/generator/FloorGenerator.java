package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.version.BlockInfo;

import java.util.Set;

public interface FloorGenerator {

    void generateFloor(Floor floor);

    Set<BlockInfo> getBlocks(Floor floor);

}
