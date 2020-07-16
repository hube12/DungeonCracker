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
    }


    public static void main(String[] args) {
        Data data1 = new Data(111939615203864L, 2882, 7327);
        Data data2 = new Data(121627667492461L, 2760, 7209);
        List<Data> dataList = new ArrayList<>(Arrays.asList(data1, data2));
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
                System.out.format("WorldSeed: %d", worldSeed);
            }
        }
    }

    public static List<Long> crack(List<Data> dataList) {
        List<Long> res = new ArrayList<>();
        Map<Long, Boolean> map = new HashMap<>();
        for (int i = 0; i < 500; i++) {
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
