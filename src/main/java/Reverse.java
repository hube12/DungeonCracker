import dungeons.kaptainwutax.magic.RandomSeed;

import java.util.Random;

public class Reverse {
    public static void main(String[] args) {
        long[] seeds=new long[]{
                27594263
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
