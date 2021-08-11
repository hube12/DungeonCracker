package neil;

import neil.dungeons.kaptainwutax.magic.RandomSeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StructureSeedProcessor {
    public Set<Long> worldSeeds = new HashSet<>();
    public Set<Long> structureSeeds;

    public StructureSeedProcessor(Set<Long> structureSeedsIn) {
        this.structureSeeds = structureSeedsIn;
    }

    public Set<Long> getWorldSeedsFromStructureSeeds() {
        for (long structureSeed : structureSeeds) {
            for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                long worldSeed = (upperBits << 48) | structureSeed;
                if (!RandomSeed.isRandomSeed(worldSeed)) {
                    continue;
                }
                worldSeeds.add(worldSeed);
            }
        }
        return worldSeeds;
    }
}
