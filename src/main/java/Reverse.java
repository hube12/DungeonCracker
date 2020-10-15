import static dungeons.kaptainwutax.magic.RandomSeed.getRandomSeed;

public class Reverse {
    public static void main(String[] args) {
        long yourSeed = -537155707690643L;
        long structureSeed = yourSeed & 0xFFFF_FFFF_FFFFL;
        for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
            long worldSeed = (upperBits << 48) | structureSeed;
            if (!getRandomSeed(worldSeed)) continue;
            System.out.format("Valid random seed : %d\n", worldSeed);
        }
    }
}
