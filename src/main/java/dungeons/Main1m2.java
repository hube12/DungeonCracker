package dungeons;


import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main1m2 {

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
        System.out.println("Enter posZ of spawner ");
        int posZ = in.nextInt();
        System.out.println("Enter the sequence Read it from the image with the supplied script");
        String stringPattern = in.nextLine();
        stringPattern = in.nextLine();
        int THREAD_COUNT = 1 << THREAD_BITS;
        System.out.println("Running on " + THREAD_COUNT + " threads");
        ExecutorService SERVICE = Executors.newFixedThreadPool(THREAD_COUNT);
        //int posX = 137 ;
        //int posY = 29 ;
        //int posZ = -67;
        //String stringPattern = "011110110111111101011011101111011111010111001101111000111111101";
        // 1 mossy
        // 0 cobble
        //============================================================ END INPUT

        int offsetX = (posX - 8) & 15;
        int offsetZ = (posZ - 8) & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        LCG back = Rand.JAVA_LCG.combine(-1);
        LCG skipFloorSize = Rand.JAVA_LCG.combine(3);

        List<Long> decoratorSeeds = new ArrayList<>();
        System.out.format("Seed space is in the range [%d, %d). \n", (long) posY << 41, (long) (posY + 1) << 41);
        AtomicInteger completedThreads = new AtomicInteger(0);

        for (int threadId = 0; threadId < THREAD_COUNT; threadId++) {
            // nextInt(128)==posY means
            // posY== (128*next(31))>>31
            // so (posY<<31)/128 == next(31) but 128==2^7
            // so (posY<<24) == this.seed>>17
            // so posY << 41 == this.seed
            long seedsPerThread = (1L << 41) >> THREAD_BITS;
            long lower = ((long) posY << 41) + seedsPerThread * threadId;
            long upper = lower + seedsPerThread;
            System.out.format("Thread %d starting with bounds [%d, %d). \n", threadId, lower, upper);
            SERVICE.submit(() -> {
                for (long seed = lower; seed < upper; seed++) {

                    /*-
                    int k2 = i + rand.nextInt(16) + 8; <- back (-1)
                    int k3 = rand.nextInt(128); <- seed
                    int i4 = j + rand.nextInt(16) + 8; <-  next(1)
                    int i = par2Random.nextInt(2) + 2;  <- skip(1)
                    int j = par2Random.nextInt(2) + 2;  <- skip(1)

                     */
                    long seedCopy = seed;
                    long temp = back.nextSeed(seed);
                    if (temp >>> (48 - 4) != offsetX) continue;
                    temp = Rand.JAVA_LCG.nextSeed(seed); // back(-1)

                    if (temp >>> (48 - 4) != offsetZ) continue;
                    seedCopy = skipFloorSize.nextSeed(seedCopy); // next(1)

                    boolean floorMatches = true;
                    int count = 0;
                    for (int block : pattern) {
                        seedCopy = Rand.JAVA_LCG.nextSeed(seedCopy); // next(1)+skip(2)
                        int nextInt4 = (int) (seedCopy >>> (48 - 2));
                        // goes from -x to +x
                        // and -z to +z
                        // if mossy and result equals at 0, wrong branch
                        if ((block == 1 && nextInt4 == 0) || (block == 0 && nextInt4 != 0)) {
                            floorMatches = false;
                            break;
                        }
                        count++;
                    }
                    if (floorMatches) {
                        long decoratorSeed = Rand.JAVA_LCG.combine(-2).nextSeed(seed);
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

        System.out.format("Finished dungeon search and looking for structure seeds.\n");
        //decoratorSeeds.add(42704106289324L);
        for (long seed : decoratorSeeds) {
            long decoratorSeed = seed;
            for (int i = 0; i < 100000; i++) {
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, (posX-8)>>4, (posZ-8)>>4).forEach(el-> System.out.println(el+","));
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
    }
}


