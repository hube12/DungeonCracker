import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.ArrayList;

public class ReverseBiomes {
    public static class BiomeData {
        public Biome biome;
        public int x;
        public int z;

        BiomeData(int x, int z, Biome biome) {
            this.x = x;
            this.z = z;
            this.biome = biome;
        }

        public double check(OverworldBiomeSource source) {
            Biome biome = source.getBiome(x, 0, z);

            if (biome.getCategory() == this.biome.getCategory()) {
                return biome == this.biome ? 1 : 0.5;
            }
            return 0;

        }
    }

    public void crackBiomes() {
        long seed = 27594263L;
        ArrayList<BiomeData> biomeDatas = new ArrayList<>();
        biomeDatas.add(new BiomeData(-9070, -4555, Biome.FLOWER_FOREST));
        biomeDatas.add(new BiomeData(1909, -1279, Biome.SAVANNA));
        biomeDatas.add(new BiomeData(1922, -1223, Biome.JUNGLE_EDGE));
        for (long i = 0; i < (1L << 16); i++) {
            long cur = (i << 48) | seed;
            boolean good = true;
            double score = 0.0;
            OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_12, cur);
            for (BiomeData data : biomeDatas) {
                double s = data.check(source);
                if (s == 0) {
                    good = false;
                    break;
                }
                score += s;
            }
            if (good && score > 2) System.out.println(cur);
        }
    }

    public static void main(String[] args) {
        ReverseBiomes main = new ReverseBiomes();
        main.crackBiomes();
    }

}
