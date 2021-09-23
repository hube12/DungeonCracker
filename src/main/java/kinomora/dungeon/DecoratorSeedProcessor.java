package kinomora.dungeon;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.seedutils.lcg.LCG;
import mjtb49.hashreversals.ChunkRandomReverser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static kinomora.gui.dungeondatatab.SpawnerDataPanel.UNKNOWN;

public class DecoratorSeedProcessor {
    public static final LCG FAILED_DUNGEON = LCG.JAVA.combine(-5);
    public static final LCG PREVIOUS = LCG.JAVA.invert();
    private final int posX;
    private final int posZ;
    private final Biome biome;
    private final MCVersion version;
    private final Set<Long> structureSeeds = new HashSet<>();
    private final Set<Long> decoratorSeeds;

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
                    structureSeeds.addAll(reverseSeedForSalt(decSeed));
                    decSeed = FAILED_DUNGEON.nextSeed(decSeed);
                }
            } else {                                    //1.12-Legacy
                for (int i = 0; i < 2000; i++) {
                    structureSeeds.addAll(ChunkRandomReverser.reversePopulationSeed(decSeed ^ LCG.JAVA.multiplier, posX >> 4, posZ >> 4, version));
                    decSeed = PREVIOUS.nextSeed(decSeed);
                }
            }
        }
        //System.out.println("pop2: " + structureSeeds);
        return structureSeeds;
    }

    private List<Long> reverseSeedForSalt(long decoratorSeed) {

        List<Long> popSeed = new ArrayList<>();
        if (version.isNewerThan(MCVersion.v1_15)) {
            if (biome == Biomes.DESERT || biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS) {
                popSeed.addAll(ChunkRandomReverser.reversePopulationSeed((decoratorSeed ^ LCG.JAVA.multiplier) - 30003L, posX & -16, posZ & -16, version));
            } else {
                popSeed.addAll(ChunkRandomReverser.reversePopulationSeed((decoratorSeed ^ LCG.JAVA.multiplier) - 30002L, posX & -16, posZ & -16, version));
            }
            // if unknown we need to add 3 as well as the 2
            if (biome == UNKNOWN) {
                popSeed.addAll(ChunkRandomReverser.reversePopulationSeed((decoratorSeed ^ LCG.JAVA.multiplier) - 30003L, posX & -16, posZ & -16, version));
            }
        } else {
            popSeed.addAll(ChunkRandomReverser.reversePopulationSeed((decoratorSeed ^ LCG.JAVA.multiplier) - 20003L, posX & -16, posZ & -16, version));
        }
        return popSeed;
    }
}
