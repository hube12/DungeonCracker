import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.magic.PopulationReversal;
import dungeons.kaptainwutax.magic.RandomSeed;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;

public class Find {
    public static void main(String[] args) {

        sixteen();
    }
    private static void twelve(){
        int posX = 1673  ;
        int posY = 20  ;
        int posZ = -292;
        long dungeonSeed = 255455040733081L;
        for (int i = 0; i < 10000; i++) {
            PopReversal2TheHalvening.getSeedFromChunkseedPre13(dungeonSeed ^ Rand.JAVA_LCG.multiplier, (posX - 8) >> 4, (posZ - 8) >> 4).forEach(System.out::println);
            dungeonSeed=Rand.JAVA_LCG.combine(-1).nextSeed(dungeonSeed);
        }
    }
    private static void fifteen(){
        int posX = 701300 ;
        int posZ = -599977;
        long decoratorSeed = 201550308667634L;
        LCG failedDungeon = Rand.JAVA_LCG.combine(-5);
        for (int i = 0; i < 8; i++) {
            PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, posX & -16, posZ & -16).forEach(structureSeed -> {
                //System.out.format("Structure seed %d... \n", structureSeed);
                for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                    long worldSeed = (upperBits << 48) | structureSeed;
                    if (!RandomSeed.isRandomSeed(worldSeed)) continue;
                    System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                }
            });
            decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
        }

    }
    private static void sixteen(){

        int posX = -1840;
        int posZ = -1275;
        long decoratorSeed = 56031235379053L;
        LCG failedDungeon = Rand.JAVA_LCG.combine(-5);
        for (int i = 0; i < 8; i++) {
            PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30002L, posX & -16, posZ & -16).forEach(structureSeed -> {
                //System.out.format("Structure seed %d... \n", structureSeed);
                for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                    long worldSeed = (upperBits << 48) | structureSeed;
                    if (!RandomSeed.isRandomSeed(worldSeed)) continue;
                    System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                }
            });
            decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
        }

    }
}
