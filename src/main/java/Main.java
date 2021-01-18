import dungeons.Result;
import dungeons.VersionCrack;
import gui.MCVersion;
import kaptainwutax.biomeutils.Biome;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Result r=new VersionCrack(MCVersion.v1_16, 201,15,-96,  "0011100001111110111121011101010011111110100111011").run();
        System.out.println(r);
        if (false) {
            for (MCVersion version : new MCVersion[] {MCVersion.v1_14, MCVersion.v1_15, MCVersion.v1_16}) {
                Result r1 = new VersionCrack(version, -8642, 37, 13766, "022222222100101222111011222210111222201011022222101122220111222211011222222222222").run();
                Result r2 = new VersionCrack(version, -330, 34, 639, "111111101110010111111100101110110111100111101111010101001111111").run();
                Result r3 = new VersionCrack(version, -527, 19, 899, "011100011111110101111111010111111101011011111100010001111111111").run();
                Set<Long> set1 = new HashSet<>(r1.getStructureSeeds());
                Set<Long> set2 = new HashSet<>(r2.getStructureSeeds());
                Set<Long> set3 = new HashSet<>(r3.getStructureSeeds());

                Set<Long> copy1 = new HashSet<>(set1);
                set1.retainAll(set2);
                copy1.retainAll(set3);
                set2.retainAll(set3);
                System.out.println(Arrays.toString(set1.toArray()));
                System.out.println(Arrays.toString(copy1.toArray()));
                System.out.println(Arrays.toString(set2.toArray()));
            }
        }



    }

    public static void test1_16() {
        // -720350949281663006
        System.out.println(new VersionCrack(MCVersion.v1_16, -1840, 23, -1275, "1001011111001010111011100110111110101001011101011", Biome.OCEAN).run().toString());
        System.out.println(new VersionCrack(MCVersion.v1_16, -2420, 40, -1693, "1111101100100111101111000011110101110101100111111", Biome.JUNGLE_EDGE).run().toString());

        System.out.println(new VersionCrack(MCVersion.v1_16, -5620, 34, -2062, "111110111101111111100101111111110110011111110111101111111001211011101111001101011", Biome.SWAMP).run().toString());
        System.out.println(new VersionCrack(MCVersion.v1_16, -6799, 61, -1473, "011010011111011111011110011100111011110111011110001011110111011111000011111101011", Biome.DESERT).run().toString());

    }
}
