package kinomora.gui.util;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;

public class BiomeNameToBiome {
    public static Biome getBiomeFromString(String biome){
        switch(biome){
            case "DESERT": return Biomes.DESERT;
            case "SWAMP": return Biomes.SWAMP;
            case "SWAMP_HILLS": return Biomes.SWAMP_HILLS;
            default: return Biomes.THE_VOID;
        }
    }
}
