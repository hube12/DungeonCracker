import dungeons.VersionCrack;
import gui.MCVersion;
import kaptainwutax.biomeutils.Biome;

public class Main {
    public static void main(String[] args) {
        test1_16();
    }

    public static void test1_16() {
        // -720350949281663006
        //System.out.println(new VersionCrack(MCVersion.v1_16,-1840,23,-1275,"1001011111001010111011100110111110101001011101011",Biome.OCEAN).run().toString());
        //System.out.println(new VersionCrack(MCVersion.v1_16,-2420,40,-1693,"1111101100100111101111000011110101110101100111111",Biome.JUNGLE_EDGE).run().toString());
//
        //System.out.println(new VersionCrack(MCVersion.v1_16,-5620,34,-2062,"111110111101111111100101111111110110011111110111101111111001211011101111001101011",Biome.SWAMP).run().toString());
        //System.out.println(new VersionCrack(MCVersion.v1_16,-6799,61,-1473,"011010011111011111011110011100111011110111011110001011110111011111000011111101011",Biome.DESERT).run().toString());

        // -8158687655945187724
        System.out.println(new VersionCrack(MCVersion.v1_16, 247, 26, -2403, "111101010101011011111011010101110101110011111001011100100110100111010011101011010", Biome.BEACH).run().toString());

    }
}
