package dungeons;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private final List<Long> dungeonSeeds = new ArrayList<>();
    private final List<Long> structureSeeds = new ArrayList<>();
    private final List<Long> worldSeeds = new ArrayList<>();

    public Result() {

    }

    public void addDungeonSeed(long seed) {
        dungeonSeeds.add(seed);
    }

    public void addStructureSeed(long seed) {
        structureSeeds.add(seed);
    }

    public void addWorldSeed(long seed) {
        worldSeeds.add(seed);
    }

    public List<Long> getDungeonSeeds() {
        return dungeonSeeds;
    }

    public List<Long> getStructureSeeds() {
        return structureSeeds;
    }

    public List<Long> getWorldSeeds() {
        return worldSeeds;
    }

    @Override
    public String toString() {
        return "Result{" +
                "dungeonSeeds=" + dungeonSeeds.toString() +
                ", structureSeeds=" + structureSeeds.toString() +
                ", worldSeeds=" + worldSeeds.toString() +
                '}';
    }
}
