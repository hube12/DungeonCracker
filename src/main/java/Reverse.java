import dungeons.kaptainwutax.magic.RandomSeed;

import java.util.Random;

public class Reverse {
    public static void main(String[] args) {
        long[] seeds=new long[]{
                196264606179333L,
                                43939131515690L,
                                190749578490196L,
                                168668688955000L,
                                212200906373897L
        };
        for (long structureSeed : seeds) {
            for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                long worldSeed = (upperBits << 48) | structureSeed;
                if (!RandomSeed.isRandomSeed(worldSeed)) continue;
                System.out.format("\t With nextLong() worldseed equivalent %d.\n", worldSeed);
            }
        }

    }
}
