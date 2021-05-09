package neil;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.mcutils.rand.seed.WorldSeed;
import kaptainwutax.mcutils.version.MCVersion;

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

		public Biome getBiome(OverworldBiomeSource source) {
			return source.getBiome(x, 0, z);
		}
	}

	public void crackBiomes() {
		long[] seeds = new long[] {
				9053361151027974540L,
				-3453416489156603508L,
				};
		for (int i = 0; i < seeds.length; i++) {
			seeds[i] = WorldSeed.toStructureSeed(seeds[i]);
		}
		ArrayList<BiomeData> biomeDatas = new ArrayList<>();
		biomeDatas.add(new BiomeData(-279, 25, Biomes.MOUNTAINS));
		biomeDatas.add(new BiomeData(-144, 100, Biomes.JUNGLE));
		biomeDatas.add(new BiomeData(-191, -1470, Biomes.DESERT));
		biomeDatas.add(new BiomeData(-393, -1508, Biomes.OCEAN));
		biomeDatas.add(new BiomeData(64, -1528, Biomes.FOREST));
		for (long seed : seeds) {

//            List<Long> randomSeeds = StructureSeed.toRandomWorldSeeds(seed);
//            for (long randomSeed : randomSeeds) {
			for (long i = 0; i < (1L << 16); i++) {
				long cur = (i << 48) | seed;
				boolean good = true;
				double score = 0.0;
				OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_8, cur);
				for (BiomeData data : biomeDatas) {
					double s = data.check(source);
					if (s == 0) {
						good = false;
						break;
					}
					score += s;
				}
				if (score > 1) {
					System.out.println(cur + " " + score);
				}
//                }
			}


		}

	}

	public static void main(String[] args) {
		ReverseBiomes main = new ReverseBiomes();
		main.crackBiomes();
	}

}
