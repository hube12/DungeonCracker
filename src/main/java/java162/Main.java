package java162;

import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.magic.RandomSeed;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;

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
        long dungeonSeed = 74295097218760L;
        BiomeData[] biomeDatas = new BiomeData[]{
                new BiomeData(74, 164, BiomeGenBase.desert),
                new BiomeData(94, 164, BiomeGenBase.icePlains),
                new BiomeData(94, 94, BiomeGenBase.jungle)
        };
//count:3 -*\d*
        for (int i = 0; i < 1000; i++) {
            LCG failed = Rand.JAVA_LCG.combine(-1);
            dungeonSeed = failed.nextSeed(dungeonSeed);
            seeds.addAll(PopReversal2TheHalvening.getSeedFromChunkseedPre13(dungeonSeed ^ Rand.JAVA_LCG.multiplier, (posX - 8) >> 4, (posZ - 8) >> 4));
        }
        for (Long seed : seeds) {
            for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                long worldSeed = (upperBits << 48) | seed;
                //if (!RandomSeed.isRandomSeed(worldSeed)) continue;
                GenLayer[] var4 = GenLayer.initializeAllBiomeGenerators(worldSeed);
                int count = 0;
                int index=0;
                long[]biomes=new long[3];
                for (BiomeData biomeData : biomeDatas) {
                    int biome = var4[1].getInts(biomeData.x, biomeData.z, 1, 1)[0];
                    biomes[index++]=biome;
                    if (biome == biomeData.biome.biomeID) {
                        count++;
                    }
                }
                if (count>1){
                    System.out.println("biomes:"+ Arrays.toString(biomes));
                    System.out.println("count:" + count + " " + worldSeed);
                    System.out.println("----");
                }


                IntCache.resetIntCache();
            }
        }
    }
}
