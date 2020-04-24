package dungeons;


import dungeons.kaptainwutax.magic.PopulationReversal;
import dungeons.kaptainwutax.magic.RandomSeed;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main15 {


    static int highestPowerof2(int n) {
        int res = 0;
        for (int i = n; i >= 1; i--) {
            // If i is a power of 2
            if ((i & (i - 1)) == 0) {
                res = i;
                break;
            }
        }
        return (int) (Math.log(res) / Math.log(2) + 1e-10);
    }

    public static void main(String[] args) throws InterruptedException {
        //============================================================ START INPUT
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number of threads");
        int threads = in.nextInt();
        int THREAD_BITS = highestPowerof2(threads);
        System.out.println("Enter posX of spawner");
        int posX = in.nextInt();
        System.out.println("Enter posY of spawner");
        int posY = in.nextInt();
        System.out.println("Enter posZ of spawner, read it ");
        int posZ = in.nextInt();
        System.out.println("Enter the sequence, Read it from the image with the supplied script");
        String stringPattern = in.nextLine();
        stringPattern = in.nextLine();

        int THREAD_COUNT = 1 << THREAD_BITS;
        System.out.println("Running on " + THREAD_COUNT + " threads");
        ExecutorService SERVICE = Executors.newFixedThreadPool(THREAD_COUNT);
        // int posX = 106;
        //int posY = 56;
        //int posZ = -269;
        //String stringPattern = "101110101111110111111101001011111111111111011111111110101101101";
        //============================================================ END INPUT

        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        LCG back = Rand.JAVA_LCG.combine(-2);
        LCG skipFloorSize = Rand.JAVA_LCG.combine(2);

        List<Long> decoratorSeeds = new ArrayList<>();
        System.out.format("Seed space is in the range [%d, %d). \n", (long) posY << 40, (long) (posY + 1) << 40);
        AtomicInteger completedThreads = new AtomicInteger(0);

        for (int threadId = 0; threadId < THREAD_COUNT; threadId++) {
            long seedsPerThread = (1L << 40) >> THREAD_BITS;
            long lower = ((long) posY << 40) + seedsPerThread * threadId;
            long upper = lower + seedsPerThread;

            System.out.format("Thread %d starting with bounds [%d, %d). \n", threadId, lower, upper);

            SERVICE.submit(() -> {
                for (long seed = lower; seed < upper; seed++) {
                    long seedCopy = seed;
                    long temp = back.nextSeed(seed);

                    if (temp >>> (48 - 4) != offsetX) continue;
                    temp = Rand.JAVA_LCG.nextSeed(temp);
                    if (temp >>> (48 - 4) != offsetZ) continue;

                    seedCopy = skipFloorSize.nextSeed(seedCopy);
                    boolean floorMatches = true;

                    for (int block : pattern) {
                        seedCopy = Rand.JAVA_LCG.nextSeed(seedCopy);
                        int nextInt4 = (int) (seedCopy >>> (48 - 2));

                        if ((block == 1 && nextInt4 == 0) || (block == 0 && nextInt4 != 0)) {
                            floorMatches = false;
                            break;
                        }
                    }

                    if (floorMatches) {
                        long decoratorSeed = Rand.JAVA_LCG.combine(-3).nextSeed(seed);
                        decoratorSeeds.add(decoratorSeed);
                        System.out.format("Found seed %d.\n", decoratorSeed);
                    }
                }

                completedThreads.getAndIncrement();
            });
        }

        while (completedThreads.get() != THREAD_COUNT) {
            Thread.sleep(50);
        }

        System.out.format("Finished dungeon search and looking for world seeds.\n");

        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, posX & -16, posZ & -16).forEach(structureSeed -> {
                    System.out.format("Structure seed %d... \n", structureSeed);

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

}


