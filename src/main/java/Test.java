import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.util.Rand;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int posX = -65;
        int posY = 62;
        int posZ = 62;
        //92655165313152
        long decoratorSeed = 16733948376765L;
        PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, (posX - 8) >> 4, (posZ - 8) >> 4).forEach(System.out::println);
        /*
        [18:22:46] [Server thread/INFO]: [STDOUT]: WORLDSEED: -9161480349131746006
        [18:22:46] [Server thread/INFO]: [STDOUT]: STRUCTURE SEED: 248667823396138
        [18:22:46] [Server thread/INFO]: [STDOUT]: INTERNAL ORIGINAL SEED 248681352344391
        [18:22:46] [Server thread/INFO]: [STDOUT]: INTERNAL ORIGINAL SEED 150725636568281
        [18:22:46] [Server thread/INFO]: [STDOUT]: INTERNAL ORIGINAL SEED 115842438041019
        [18:22:46] [Server thread/INFO]: [STDOUT]: INTERNAL ORIGINAL SEED 78989798059410
        [18:22:46] [Server thread/INFO]: [STDOUT]: Original Coords  BlockPos{x=-80, y=0, z=128}


         */
        //posX = -80;
        //posZ = 128;
        //long worldSeed=-9161480349131746006L;
        //Rand rand = new Rand(0);
        //rand.setSeed(worldSeed, true);
        //System.out.println(rand.getSeed());
        //long k = rand.nextLong() / 2L * 2L + 1L;
        //System.out.println(rand.getSeed());
        //long l = rand.nextLong() / 2L * 2L + 1L;
        //System.out.println(rand.getSeed());
        //rand.setSeed((long) (posX>>4) * k + (long) (posZ>>4) * l ^ worldSeed, true);
        //System.out.println(rand.getSeed());
        posX =  136  ;
        posY = 37;
        posZ = 194;
        String stringPattern = "222222221111122001002201101220111122011011221110120112012222210";
        // 1 mossy
        // 0 cobble
        //============================================================ END INPUT
        posX -= 8;
        posZ -= 8;
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        System.out.println("OFFSET: " + offsetX + " " + offsetZ);
        long[] seeds = new long[]{197732478141861L};
        for (long seed : seeds) {
            System.out.println(seed);
            check(seed, offsetX, posY, offsetZ, stringPattern);
        }
    }

    private static void check(long seed, int offsetX, int posY, int offsetZ, String pattern) {
        Random random = new Random(seed ^ Rand.JAVA_LCG.multiplier);
        System.out.print(random.nextInt(16) == offsetX);
        System.out.print(" ");
        System.out.print(random.nextInt(128) == posY);
        System.out.print(" ");
        System.out.print(random.nextInt(16) == offsetZ);
        System.out.print(" ");
        random.nextInt(2);
        random.nextInt(2);
        Integer[] patterns = pattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        for (Integer s : patterns) {
            switch (s) {
                case 0:
                    System.out.print(random.nextInt(4) == 0);
                    System.out.print(" ");
                    break;
                case 1:
                    System.out.print(random.nextInt(4) != 0);
                    System.out.print(" ");
                    break;
                default:
                    random.nextInt(4);
                    break;

            }
        }

    }

    private static void check12(long seed, int offsetX, int posY, int offsetZ, String pattern) {
        System.out.println(Rand.JAVA_LCG.combine(-1).nextSeed(seed));
        Random random = new Random(seed ^ Rand.JAVA_LCG.multiplier);
        System.out.println(random.nextInt(16));
        //System.out.print(random.nextInt(16) == offsetX);
        System.out.print(" ");
        System.out.println(random.nextInt(256));
        //System.out.print(random.nextInt(256) == posY);
        System.out.print(" ");
        System.out.println(random.nextInt(16));
        //System.out.print(random.nextInt(16) == offsetZ);
        System.out.print(" ");
        random.nextInt(2);
        random.nextInt(2);
        Integer[] patterns = pattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        for (Integer s : patterns) {
            switch (s) {
                case 0:
                    System.out.print(random.nextInt(4) == 0);
                    System.out.print(" ");
                    break;
                case 1:
                    System.out.print(random.nextInt(4) != 0);
                    System.out.print(" ");
                    break;
                default:
                    random.nextInt(4);
                    break;

            }
        }

    }
}



