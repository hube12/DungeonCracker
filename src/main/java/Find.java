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
        int posX = 2394  ;
        int posY = 20  ;
        int posZ = 15510;
        long dungeonSeed = 227076706119835L;
        for (int i = 0; i < 10000; i++) {
            PopReversal2TheHalvening.getSeedFromChunkseedPre13(dungeonSeed ^ Rand.JAVA_LCG.multiplier, (posX - 8) >> 4, (posZ - 8) >> 4).forEach(System.out::println);
            dungeonSeed=Rand.JAVA_LCG.combine(-1).nextSeed(dungeonSeed);
        }
    }
    private static void fifteen(){
        int posX = 5749   ;
        int posZ = -7617;
        long decoratorSeed = 239658245455776L;
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

        /*
        Structure seed 198292443665051...
Structure seed 232211695270555...
Structure seed 108332548843494...
Structure seed 158283023404932...
Structure seed 264964616996740...
Structure seed 18256993836932...
Structure seed 135967568045807...
Structure seed 220919009856239...
Structure seed 249557224506058...
Structure seed 99967220858405...
Structure seed 122401275890213...
Structure seed 41743401148965...
Structure seed 83611965361736...

         */
        long dungeon1=239658245455776L;
        int posX1=-2038;
        int posZ1=7682;
        int posX = -2038;
        int posZ = 7682;
        long decoratorSeed = 239658245455776L;
        LCG failedDungeon = Rand.JAVA_LCG.combine(-5);
        for (int i = 0; i < 8; i++) {
            PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30002L, posX & -16, posZ & -16).forEach(structureSeed -> {
                System.out.format("Structure seed %d... \n", structureSeed);
                for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                    long worldSeed = (upperBits << 48) | structureSeed;
                    if (!RandomSeed.isRandomSeed(worldSeed)) continue;
                    //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                }
            });
            decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
        }

    }
}
