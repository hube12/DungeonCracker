package dungeons;


import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;
import randomreverser.ReverserDevice;
import randomreverser.call.FilteredSkip;
import randomreverser.call.NextInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main1m2 {

    public static void main(String[] args) throws InterruptedException {
        //============================================================ START INPUT
        Scanner in = new Scanner(System.in);
        System.out.println("Enter posX of spawner");
        int posX = in.nextInt();
        System.out.println("Enter posY of spawner");
        int posY = in.nextInt();
        System.out.println("Enter posZ of spawner ");
        int posZ = in.nextInt();
        System.out.println("Enter the sequence Read it from the image with the supplied script");
        String stringPattern = in.nextLine();
        stringPattern = in.nextLine();
        //int posX = 137 ;
        //int posY = 29 ;
        //int posZ = -67;
        //String stringPattern = "011110110111111101011011101111011111010111001101111000111111101";
        // 1 mossy
        // 0 cobble
        //============================================================ END INPUT

        posX -= 8;
        posZ -= 8;
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(128, posY));

        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        decoratorSeeds.forEach(s -> System.out.println("Found Dungeon seed: " + decoratorSeeds));
        System.out.format("Finished dungeon search and looking for structure seeds, you need other structure or dungeon to get the correct one, outputting 1000.\n");
        //decoratorSeeds.add(42704106289324L);
        for (long seed : decoratorSeeds) {
            long decoratorSeed = seed;
            for (int i = 0; i < 100000; i++) {
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, (posX)>>4, (posZ)>>4).forEach(el-> System.out.println(el+","));
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
    }
}


