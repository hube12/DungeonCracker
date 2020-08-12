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

        dataList.add(new Data(248794591925024L, -577 , -848 ));
        dataList.add(new Data(21447089443958L, -553 ,-973 ));
        dataList.add(new Data(148135802758948L, -656 , -1133 ));
        dataList.add(new Data(35670729128800L, -583 ,-1200 ));
        dataList.add(new Data(162925418310042L, -448-262+10 ,-1144+65+10 ));
        dataList.add(new Data(236341059760407L,  -448-257 ,-1144-240));
    normal(dataList);


    }
    public static void normal(List<Data> dataList){
            List<Long> res = crack(dataList);
            if (res.isEmpty()) {
                System.out.println("You failed !");
                return;
            }
            System.out.println("If the seed was randomly generated: (else use biomes)");
            for (long seed : res) {
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
        for (int i = 0; i < 10000; i++) {
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
    public static List<Long> generateList(Data data) {
        List<Long> res = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            res.addAll(PopReversal2TheHalvening.getSeedFromChunkseedPre13(
                    data.getPrevious() ^ Rand.JAVA_LCG.multiplier,
                    data.posX >> 4, data.posZ >> 4));

        }
        return res;
    }
}
