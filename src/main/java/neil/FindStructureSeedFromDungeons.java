package neil;

import neil.dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import neil.dungeons.kaptainwutax.magic.RandomSeed;
import neil.dungeons.kaptainwutax.util.LCG;
import neil.dungeons.kaptainwutax.util.Rand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindStructureSeedFromDungeons {
	/*
	neil.Reverse any number of dungeon seed to their common structure seed
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
		List<Data> dataList = new ArrayList<>();
		dataList.add(new Data(37913390166981L, 1091, -285));  //test data
		dataList.add(new Data(251966589995722L, 1067, -307)); //test data
		//dataList.add( new Data());

		List<Long> res = crack(dataList);
		if (res.isEmpty()) {
			System.out.println("You failed!");
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
		for (int i = 0; i < 10; i++) {
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
