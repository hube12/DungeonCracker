import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;
import kaptainwutax.mathutils.solver.Hensel;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.ChunkRand;

import java.util.*;
import java.util.function.LongUnaryOperator;

public class ReverseLift {

    public static class Data {
        static final LCG nextSeed = Rand.JAVA_LCG.combine(-1);
        public long dungeonSeed;
        public int chunkX;
        public int chunkZ;

        public Data(long dungeonSeed, int posX, int posZ) {
            this.dungeonSeed = dungeonSeed;
            this.chunkX = (posX - 8) >> 4;
            this.chunkZ = (posZ - 8) >> 4;
        }

        public long getPrevious() {
            return dungeonSeed = nextSeed.nextSeed(dungeonSeed);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "dungeonSeed=" + dungeonSeed +
                    ", chunkX=" + chunkX +
                    ", chunkZ=" + chunkZ +
                    '}';
        }
    }

    public static class Pair {
        long seed1;
        long seed2;
        Set<Long> liftedWorldSeed;

        public Pair(long seed1, long seed2, Set<Long> liftedWorldSeeds) {
            this.seed1 = seed1;
            this.seed2 = seed2;
            this.liftedWorldSeed = liftedWorldSeeds;
        }

        public Set<Long> getLiftedWorldSeed() {
            return liftedWorldSeed;
        }

        public long getSeed1() {
            return seed1;
        }

        public long getSeed2() {
            return seed2;
        }

        public boolean contains(long seed) {
            return seed1 == seed || seed2 == seed;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "seed1=" + seed1 +
                    ", seed2=" + seed2 +
                    '}';
        }
    }

    public static void main(String[] args) {
        ChunkRand rand = new ChunkRand();

        //=======================================

        int radius = 1;
        //int chunkX1 = -37, chunkZ1 = -54;
        //long populationSeed1 = 248794591925024L;

        //int chunkX2 = -35, chunkZ2 = -62;
        //long populationSeed2 = 21447089443958L;
        //int chunkX1 = -656>>4, chunkZ1 = -1133>>4;
        //long populationSeed1 = 148135802758948L;
        int chunkX1 = -37, chunkZ1 = -54;
        long populationSeed1 = 248794591925024L;

        int chunkX2 = -583 >> 4, chunkZ2 = -1200 >> 4;
        long populationSeed2 = 35670729128800L;
        //=======================================
        List<FindStructureSeedFromDungeons.Data> dataList = new ArrayList<>();

        dataList.add(new FindStructureSeedFromDungeons.Data(248794591925024L, -577, -848));
        dataList.add(new FindStructureSeedFromDungeons.Data(21447089443958L, -553, -973));
        dataList.add(new FindStructureSeedFromDungeons.Data(148135802758948L, -656, -1133));
        dataList.add(new FindStructureSeedFromDungeons.Data(35670729128800L, -583, -1200));
        dataList.add(new FindStructureSeedFromDungeons.Data(162925418310042L, -448 - 262 + 10, -1144 + 65 + 10));
        dataList.add(new FindStructureSeedFromDungeons.Data(236341059760407L, -448 - 257, -1144 - 240));
        List<Pair> pairs = new ArrayList<>();
        for (FindStructureSeedFromDungeons.Data data : dataList) {
            for (FindStructureSeedFromDungeons.Data data1 : dataList) {
                if (data != data1) {
                    Set<Long> result = new HashSet<>();
                    for (int rx1 = -radius; rx1 < radius; rx1++) {
                        for (int rx2 = -radius; rx2 < radius; rx2++) {
                            for (int rz1 = -radius; rz1 < radius; rz1++) {
                                for (int rz2 = -radius; rz2 < radius; rz2++) {
                                    result.addAll(crack(populationSeed1, populationSeed2, chunkX1 + rx1, chunkX2 + rx2, chunkZ1 + rz1, chunkZ2 + rz2));
                                }
                            }
                        }
                    }
                    pairs.add(new Pair(data.dungeonSeed, data1.dungeonSeed, result));
                }
            }
        }
        HashMap<Long,Pair> result=new HashMap<>();
        for (Pair pair : pairs) {
            for (Long aLong : pair.liftedWorldSeed) {
                if (result.containsKey(aLong)){
                    System.out.println(aLong);//+" "+pair+" "+result.get(aLong));
                }else{
                    result.put(aLong,pair);
                }
            }
        }


    }

    public static List<Long> crack(long seed1, long seed2, int chunkX1, int chunkX2, int chunkZ2, int chunkZ1) {
        int dChunkX = chunkX2 - chunkX1;
        int dChunkZ = chunkZ2 - chunkZ1;

        LongUnaryOperator hash = seed -> {
            JRand r = new JRand(seed);
            long e = (seed1 ^ seed) - (seed2 ^ seed);
            long f = (r.nextLong() / 2L * 2L + 1L) * dChunkX + (r.nextLong() / 2L * 2L + 1L) * dChunkZ;
            return f - e;
        };

        List<Long> result = new ArrayList<>();

        for (long c = 0; c < 1L << 16; c++) {
            Hensel.lift(c, 0, 0, 32, 16, hash, result);
        }

        //if (!result.isEmpty()) {
        //    System.out.println("#################");
        //    System.out.println(chunkX1 + " " + chunkX2 + " " + chunkZ2 + " " + chunkZ1);
        //    for (long seed : result) {
        //        System.out.println(seed);
        //    }
        //    System.out.println("#################");
        //}
        return result;
    }
}
