package java132;

import kaptainwutax.seedutils.mc.seed.WorldSeed;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static class BiomeData {
        private int x;
        private int z;
        private BiomeGenBase biome;

        public BiomeData(int x, int z, BiomeGenBase biome) {
            this.x = x;
            this.z = z;
            this.biome = biome;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public BiomeGenBase getBiome() {
            return biome;
        }

        public void setBiome(BiomeGenBase biome) {
            this.biome = biome;
        }
    }

    public static void main(String[] args) {
        ArrayList<Long> seeds = new ArrayList<>();
        int posX = 18;
        int posY = 36;
        int posZ = 162;
//        long dungeonSeed = 74295097218760L;
        BiomeData[] biomeDatas = new BiomeData[]{
                new BiomeData(-279,25, BiomeGenBase.extremeHills),
                new BiomeData(-144,100, BiomeGenBase.jungle),
                new BiomeData( -97,-107, BiomeGenBase.forest),
                new BiomeData(-191,-1470, BiomeGenBase.desert),
                new BiomeData(-393, -1508, BiomeGenBase.ocean),
                new BiomeData( 64, -1528,BiomeGenBase.forest),
                new BiomeData(  360,-1536,BiomeGenBase.desert),
                new BiomeData(  335,-1137,BiomeGenBase.plains),
        };
//count:3 -*\d*
//        for (int i = 0; i < 1000; i++) {
//            LCG failed = Rand.JAVA_LCG.combine(-1);
//            dungeonSeed = failed.nextSeed(dungeonSeed);
//            seeds.addAll(PopReversal2TheHalvening.getSeedFromChunkseedPre13(dungeonSeed ^ Rand.JAVA_LCG.multiplier, (posX - 8) >> 4, (posZ - 8) >> 4));
//        }
        seeds.add(WorldSeed.toStructureSeed(9053361151027974540L));
        seeds.add(WorldSeed.toStructureSeed(-3453416489156603508L));
        for (Long seed : seeds) {
            for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                long worldSeed = (upperBits << 48) | seed;
                //if (!RandomSeed.isRandomSeed(worldSeed)) continue;
                GenLayer voronoi = GenLayer.initializeAllBiomeGenerators(worldSeed, WorldType.DEFAULT)[1];
                int count = 0;
                int index = 0;
                long[] biomes = new long[biomeDatas.length];
                for (BiomeData biomeData : biomeDatas) {
                    int biome = voronoi.getInts(biomeData.x, biomeData.z, 1, 1)[0];
                    biomes[index++] = biome;
                    if (biome == biomeData.biome.biomeID) {
                        count++;
                    }
                }
                if (count > 5) {
                    System.out.println("biomes:" + Arrays.toString(biomes));
                    System.out.println("count:" + count + " " + worldSeed);
                    System.out.println("----");
                }


                IntCache132.resetIntCache();
            }
        }
    }
}
