package neil;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import neil.dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import neil.dungeons.kaptainwutax.magic.PopulationReversal;
import neil.dungeons.kaptainwutax.util.LCG;
import neil.dungeons.kaptainwutax.util.Rand;
import neil.gui.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class DecoratorSeedProcessor {
    private int posX;
    private int posZ;
    private final Biome biome;
    private final MCVersion version;
    private final Set<Long> structureSeeds = new HashSet<>();
    private final Set<Long> decoratorSeeds;

    public DecoratorSeedProcessor(MCVersion version, int posX, int posZ, Biome biome, Set<Long> decoratorSeeds) {
        this.posX = posX;
        this.posZ = posZ;
        this.biome = biome;
        this.version = version;
        this.decoratorSeeds = decoratorSeeds;
    }

    public Set<Long> decoratorSeedsToStructureSeeds() {
        if(version.isNewerThan(MCVersion.v1_12)){
            posX -=8;
            posZ -=8;
        }

        for (long decSeed : decoratorSeeds) {
            if (version.isNewerThan(MCVersion.v1_12)) { //1.13-1.17
                LCG failedDungeon = Rand.JAVA_LCG.combine(-5);
                for (int i = 0; i < 8; i++) {
                    structureSeeds.addAll(PopReversal2TheHalvening.getSeedFromChunkseed13Plus((decSeed ^ Rand.JAVA_LCG.multiplier) - getSalt(), posX & -16, posZ & -16));
                    decSeed = failedDungeon.nextSeed(decSeed);
                }
            } else {                                    //1.12-Legacy
                for (int i = 0; i < 2000; i++) {
                    structureSeeds.addAll(PopReversal2TheHalvening.getSeedFromChunkseedPre13(decSeed ^ Rand.JAVA_LCG.multiplier, posX >> 4, posZ >> 4));
                    decSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decSeed);
                }
            }
        }
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
