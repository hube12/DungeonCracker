package kinomora.dungeon;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.seedutils.lcg.LCG;
import mjtb49.hashreversals.ChunkRandomReverser;

import java.util.HashSet;
import java.util.Set;

public class DecoratorSeedProcessor {
	private final int posX;
	private final int posZ;
	private final Biome biome;
	private final MCVersion version;
	private final Set<Long> structureSeeds = new HashSet<>();
	private final Set<Long> decoratorSeeds;
	public static final LCG FAILED_DUNGEON = LCG.JAVA.combine(-5);
	public static final LCG PREVIOUS = LCG.JAVA.invert();

	public DecoratorSeedProcessor(MCVersion version, int posX, int posZ, Biome biome, Set<Long> decoratorSeeds) {
		this.posX = (version.isOlderThan(MCVersion.v1_13) ? posX - 8 : posX);
		this.posZ = (version.isOlderThan(MCVersion.v1_13) ? posZ - 8 : posZ);
		this.biome = biome;
		this.version = version;
		this.decoratorSeeds = decoratorSeeds;
	}

	public Set<Long> decoratorSeedsToStructureSeeds() {

		for (long decSeed : decoratorSeeds) {
			if (version.isNewerOrEqualTo(MCVersion.v1_13)) { //1.13-1.17
				for (int i = 0; i < 8; i++) {

					structureSeeds.addAll(ChunkRandomReverser.reversePopulationSeed(
							(decSeed ^ LCG.JAVA.multiplier) - getSalt(),
							posX & -16, posZ & -16, version)
					);
					decSeed = FAILED_DUNGEON.nextSeed(decSeed);
				}
			} else {                                    //1.12-Legacy
				for (int i = 0; i < 2000; i++) {
					structureSeeds.addAll(ChunkRandomReverser.reversePopulationSeed(decSeed ^ LCG.JAVA.multiplier, posX >> 4, posZ >> 4,version));
					decSeed = PREVIOUS.nextSeed(decSeed);
				}
			}
		}
		//System.out.println("pop2: " + structureSeeds);
		return structureSeeds;
	}

	private Long getSalt() {
		if (version.isNewerThan(MCVersion.v1_15)) {
			if (biome == Biomes.DESERT) {
				return 30003L;
			} else {
				return 30002L;
			}
		} else {
			return 20003L;
		}
	}
}
