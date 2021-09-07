package kinomora.dungeon;

import com.seedfinding.latticg.reversal.DynamicProgram;
import com.seedfinding.latticg.reversal.calltype.java.JavaCalls;
import com.seedfinding.latticg.util.LCG;
import kaptainwutax.mcutils.version.MCVersion;
import one.util.streamex.StreamEx;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class DungeonDataProcessor {
    // TODO modify me in the gui
    public static final int CORES= Runtime.getRuntime().availableProcessors();
    private final int posX;
    private final int posY;
    private final int posZ;
    private final int floorSizeX;
    private final int floorSizeZ;
    private final String sequence;
    private final MCVersion version;

    public DungeonDataProcessor(MCVersion version, int posX, int posY, int posZ, String sequence, int floorSizeX, int floorSizeZ) {
        this.posX = (version.isOlderThan(MCVersion.v1_13) ? posX - 8 : posX);
        this.posY = posY;
        this.posZ = (version.isOlderThan(MCVersion.v1_13) ? posZ - 8 : posZ);
        this.sequence = sequence;
        this.version = version;
        this.floorSizeX = (floorSizeX - 7) / 2;
        this.floorSizeZ = (floorSizeZ - 7) / 2;
    }

    /***
     * Processes dungeon data containing the version, coords, a sequence, and a biome and converts it into a set of dungeon structure seeds
     * @return Set<Long> of dungeon structure seeds
     */
    public Set<Long> dungeonDataToDecoratorSeed() {
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;

        Integer[] pattern = sequence.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        DynamicProgram device = DynamicProgram.create(LCG.JAVA);
        if (version.isOlderThan(MCVersion.v1_8)) {                       //Legacy
            device.add(JavaCalls.nextInt(16).equalTo(offsetX));
            device.add(JavaCalls.nextInt(128).equalTo(posY));
            device.add(JavaCalls.nextInt(16).equalTo(offsetZ));
        } else if (version.isBetween(MCVersion.v1_8, MCVersion.v1_14)) { //1.8 to 1.14
            device.add(JavaCalls.nextInt(16).equalTo(offsetX));
            device.add(JavaCalls.nextInt(256).equalTo(posY));
            device.add(JavaCalls.nextInt(16).equalTo(offsetZ));
        } else if (version.isNewerThan(MCVersion.v1_14)) {               //1.15+
            device.add(JavaCalls.nextInt(16).equalTo(offsetX));
            device.add(JavaCalls.nextInt(16).equalTo(offsetZ));
            device.add(JavaCalls.nextInt(256).equalTo(posY));
        }
        device.add(JavaCalls.nextInt(2).equalTo(floorSizeX));
        device.add(JavaCalls.nextInt(2).equalTo(floorSizeZ));

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.add(JavaCalls.nextInt(4).equalTo(0));
            } else if (integer == 1) {
                device.filteredSkip(r -> r.nextInt(4) != 0, 1);
            } else {
                device.skip(1);
            }
        }
        ForkJoinPool forkJoinPool=new ForkJoinPool(Math.max(CORES - 2, 1));
        return StreamEx.of(device.reverse().boxed()).parallel(forkJoinPool).collect(Collectors.toSet());
    }
}
