package dungeons.kaptainwutax.magic;


import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;

import java.util.Random;

public class RandomSeed {

    private static final LCG INVERSE_LCG = Rand.JAVA_LCG.combine(-1);
    private static final LCG INVERSE_LCG_CLEAR = new LCG(246154705703781L, 107048004364969L, 281474976710656L);

    public static long nextSeed(long seed, LCG lcg) {
        return (seed * lcg.multiplier + lcg.addend) & (lcg.modulo - 1);
    }

    /**
     * Source: https://twitter.com/Geosquare_/status/1169623192153010176
     */
    public static boolean getRandomSeed(long worldSeed) {
        long upperBits = worldSeed >>> 32;
        long lowerBits = worldSeed & 0xFFFF_FFFFL;
        long a = (24667315 * upperBits + 18218081 * lowerBits + 67552711) >> 32;
        long b = (-4824621 * upperBits + 7847617 * lowerBits + 7847617) >> 32;
        long seed = nextSeed(7847617 * a - 18218081 * b, INVERSE_LCG);
        return new Random(seed^0x5DEECE66DL).nextLong() == worldSeed;
    }

    public static void main(String[] args) {
        System.out.println(INVERSE_LCG);
    }
}
