package neil;

import neil.gui.MCVersion;
import randomreverser.ReverserDevice;
import randomreverser.call.FilteredSkip;
import randomreverser.call.NextInt;

import java.util.Set;
import java.util.stream.Collectors;

public class DungeonDataProcessor {
    private int posX;
    private final int posY;
    private int posZ;
    String sequence;
    MCVersion version;

    public DungeonDataProcessor(MCVersion version, int posX, int posY, int posZ, String sequence) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.sequence = sequence;
        this.version = version;
    }

    /***
     * Processes dungeon data containing the version, coords, a sequence, and a biome and converts it into a set of dungeon structure seeds
     * @return Set<Long> of dungeon structure seeds
     */
    public Set<Long> dungeonDataToDecoratorSeed() {
        if (version.isOlderThan(MCVersion.v1_13)) {
            posX -= 8;
            posZ -= 8;
        }
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;

        Integer[] pattern = sequence.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        if (version.isOlderThan(MCVersion.v1_8)) {                       //Legacy
            device.addCall(NextInt.consume(16, 1));
            device.addCall(NextInt.withValue(128, posY));
            device.addCall(NextInt.consume(16, 1));
        } else if (version.isBetween(MCVersion.v1_8, MCVersion.v1_14)) { //1.8 to 1.14
            device.addCall(NextInt.withValue(16, offsetX));
            device.addCall(NextInt.withValue(256, posY));
            device.addCall(NextInt.withValue(16, offsetZ));
        } else if (version.isNewerThan(MCVersion.v1_14)) {               //1.15+
            device.addCall(NextInt.withValue(16, offsetX));
            device.addCall(NextInt.withValue(16, offsetZ));
            device.addCall(NextInt.withValue(256, posY));
        }
        device.addCall(NextInt.consume(2, 2));

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        return device.streamSeeds().parallel().collect(Collectors.toSet());
    }
}
