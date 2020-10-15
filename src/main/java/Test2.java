import dungeons.kaptainwutax.magic.PopulationReversal;
import dungeons.kaptainwutax.magic.RandomSeed;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;
import randomreverser.ReverserDevice;
import randomreverser.call.FilteredSkip;
import randomreverser.call.NextInt;

public class Test2 {
    public static void main(String[] args) {
        int posX = 276;
        int posY = 85;
        int posZ = -653;
        posX -= 8;
        posZ -= 8;
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        System.out.println("OFFSET: " + offsetX + " " + offsetZ);
        long[] seeds = new long[]{268415171656839L};
        for (long decoratorSeed : seeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                // they changed and added lake in the enum so 3 * 10000 + pos in the UNDERGROUND_STRUCTURES so 2 (yeah it changed)
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 2, posX & -16, posZ & -16).forEach(structureSeed -> {
                    //System.out.format("Structure seed %d... \n", structureSeed);

                    for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                        long worldSeed = (upperBits << 48) | structureSeed;
                        if (!RandomSeed.getRandomSeed(worldSeed)) continue;
                        System.out.format("%d\n", worldSeed);
                    }
                });

                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }

    }
    public static void dream(){
        ReverserDevice device = new ReverserDevice();
        device.setVerbose(true);
        for (int i = 1; i <=3; ++i) {
            for (int j = 1; j <= 6; ++j) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(3) != 0));
                device.addCall(NextInt.withValue(4,0));
            }
        }
        device.streamSeeds().forEach(System.out::println);
    }
}
