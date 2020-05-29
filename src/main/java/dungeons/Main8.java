package dungeons;


import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.util.Rand;
import randomreverser.RandomReverser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main8 {

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
        //Scanner in = new Scanner(System.in);
        //System.out.println("Enter posX of spawner");
        //int posX = in.nextInt();
        //System.out.println("Enter posY of spawner");
        //int posY = in.nextInt();
        //System.out.println("Enter posZ of spawner ");
        //int posZ = in.nextInt();
        //System.out.println("Enter the sequence Read it from the image with the supplied script");
        //String stringPattern = in.nextLine();
        //stringPattern = in.nextLine();
        int posX = -120;
        int posY = 17;
        int posZ = -385;
        String stringPattern = "111111111111101111110111111110011111101001111101011111111010111";
        // 1 mossy
        // 0 cobble
        //============================================================ END INPUT
        posX -= 8;
        posZ -= 8;
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        RandomReverser device = new RandomReverser();
        device.setVerbose(true);
        device.addNextIntCall(16, offsetX, offsetX); // X offset
        device.addNextIntCall(256, posY, posY); // Y position
        device.addNextIntCall(16, offsetZ, offsetZ); // Y offset
        device.consumeNextIntCalls(2, 2); //skip 2 nextInt(2)
        for (Integer stone : pattern) { //for each stone
            if (stone == 1) {
                //mossy
                device.addNextIntCall(4, 1, 3);
            } else if (stone == 0) {
                //cobble
                device.addNextIntCall(4, 0, 0);
            } else {
                //unknown
                device.consumeNextIntCalls(1, 4);
            }
        }
        device.findAllValidSeeds().forEach(System.out::println);
        List<Long> decoratorSeeds = new ArrayList<>();
                decoratorSeeds.forEach(s -> System.out.println("Found Dungeon seed: " + s));
        System.out.format("Finished dungeon search and looking for structure seeds.\n");
        for (long seed : decoratorSeeds) {
            long decoratorSeed = seed;
            for (int i = 0; i < 10000; i++) {
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, posX >> 4, posZ >> 4).forEach(System.out::println);
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
    }
}


