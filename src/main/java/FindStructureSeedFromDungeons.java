import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.magic.RandomSeed;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;
import kaptainwutax.seedutils.mc.seed.WorldSeed;

import java.util.*;

public class FindStructureSeedFromDungeons {
    /*
    Reverse any number of dungeon seed to their common structure seed
     */
    public static class Data {
        static final LCG nextSeed = Rand.JAVA_LCG.combine(-1);
        public long dungeonSeed;
        public int posX;
        public int posZ;

        public Data(long dungeonSeed, int posX, int posZ) {
            this.dungeonSeed = dungeonSeed;
            this.posX = posX - 8;
            this.posZ = posZ - 8;
        }

        public long getPrevious() {
            return dungeonSeed = nextSeed.nextSeed(dungeonSeed);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "dungeonSeed=" + dungeonSeed +
                    ", posX=" + posX +
                    ", posZ=" + posZ +
                    '}';
        }
    }


    public static void main(String[] args) {
        List<Data> dataList = new ArrayList<>();
        while (true){
            Scanner in = new Scanner(System.in);
            System.out.println("Enter posX of spawner");
            int posX = in.nextInt();
            System.out.println("Enter posZ of spawner");
            int posZ = in.nextInt();
            System.out.println("Enter the dungeon seed obtained before");
            long seed = in.nextLong();
            System.out.println("More dungeon seeds? (Y/N)");
            String stringPattern = in.nextLine();
            stringPattern = in.nextLine();
            dataList.add( new Data(seed,  posX, posZ));
            if (!stringPattern.toLowerCase().equals("y")){
                break;
            }

        }

        //System.out.println(Arrays.toString(dataList.toArray()));
        //dataList.add( new Data(254892590318259L,  -367, -964));
        //dataList.add( new Data(72416206423802L, -271,  -1239));
        //dataList.add( new Data(263445769234434L, 1325,  -3919));
        //dataList.add( new Data(83106984196729L, -414,  -1769));

        List<Long> res = crack(dataList);
        if (res.isEmpty()){
            System.out.println("You failed !");
            return;
        }
        System.out.println("If the seed was randomly generated: (else use biomes)");
        for (long seed:res) {
            for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                long worldSeed = (upperBits << 48) | seed;
                if (!RandomSeed.isRandomSeed(worldSeed)) continue;
                System.out.format("WorldSeed: %d\n", worldSeed);
            }
        }
    }

    public static List<Long> crack(List<Data> dataList) {
        List<Long> res = new ArrayList<>();
        Map<Long, Boolean> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            for (Data data : dataList) {
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(
                        data.getPrevious() ^ Rand.JAVA_LCG.multiplier,
                        data.posX >> 4, data.posZ >> 4).forEach(e -> {
                    if (map.containsKey(e)) {
                        System.out.println("Found structure seed: " + e);
                        res.add(e);
                        map.remove(e);
                    } else {
                        map.put(e, true);
                    }
                });
            }
        }
        return res;
    }
}
