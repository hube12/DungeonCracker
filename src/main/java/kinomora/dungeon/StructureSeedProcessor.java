package kinomora.dungeon;

import kaptainwutax.mcutils.rand.seed.StructureSeed;

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
            worldSeeds.addAll(StructureSeed.toRandomWorldSeeds(structureSeed));
        }
        return worldSeeds;
    }
}
