package kinomora.dungeon;

import kaptainwutax.mcutils.version.MCVersion;
import randomreverser.RandomReverser;


import java.util.Set;
import java.util.stream.Collectors;

public class DungeonDataProcessor {
    private final int posX;
    private final int posY;
    private final int posZ;
    private final int floorSizeX;
    private final int floorSizeZ;
    private final String sequence;
    private final MCVersion version;

    public DungeonDataProcessor(MCVersion version, int posX, int posY, int posZ, String sequence,int floorSizeX,int floorSizeZ) {
        this.posX = (version.isOlderThan(MCVersion.v1_13)?posX-8:posX);
        this.posY = posY;
        this.posZ = (version.isOlderThan(MCVersion.v1_13)?posZ-8:posZ);
        this.sequence = sequence;
        this.version = version;
        this.floorSizeX=(floorSizeX-7)/2;
        this.floorSizeZ=(floorSizeZ-7)/2;
    }

    /***
     * Processes dungeon data containing the version, coords, a sequence, and a biome and converts it into a set of dungeon structure seeds
     * @return Set<Long> of dungeon structure seeds
     */
    public Set<Long> dungeonDataToDecoratorSeed() {
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;

        Integer[] pattern = sequence.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        RandomReverser device = new RandomReverser();
        if (version.isOlderThan(MCVersion.v1_8)) {                       //Legacy
            device.addNextIntCall(16,offsetX,offsetX);
            device.addNextIntCall(128,posY,posY);
            device.addNextIntCall(16,offsetZ,offsetZ);
        } else if (version.isBetween(MCVersion.v1_8, MCVersion.v1_14)) { //1.8 to 1.14
            device.addNextIntCall(16,offsetX,offsetX);
            device.addNextIntCall(256,posY,posY);
            device.addNextIntCall(16,offsetZ,offsetZ);
        } else if (version.isNewerThan(MCVersion.v1_14)) {               //1.15+
            device.addNextIntCall(16,offsetX,offsetX);
            device.addNextIntCall(16,offsetZ,offsetZ);
            device.addNextIntCall(256,posY,posY);
        }
        // TODO check those are right order
        device.addNextIntCall(2,floorSizeX);
        device.addNextIntCall(2,floorSizeZ);

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addNextIntCall(4,0,0);
            } else if (integer == 1) {
                device.addNextIntCall(4,1,3);
            } else {
                device.consumeNextIntCalls(1,4);
            }
        }

        return device.findAllValidSeeds().parallel().boxed().collect(Collectors.toSet());
    }
}
