package neil;

import kaptainwutax.biomeutils.biome.Biomes;
import neil.dungeons.Result;
import neil.dungeons.VersionCrack;
import neil.gui.MCVersion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static final boolean PARTIAL_OVERRIDE = true;

	public static void main(String[] args) {
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222221110022011112211111221111022101112211001221111122222222").run();
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222222211111012211011112210111112210111102211110102222222222").run();
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222221111122100112211101220111122111112211110220011122222222").run();
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222222201101112201111012211111012211110112210111112222222222").run();
//        String stringPattern="0222222211111221111122111112211111221111122222222";
//        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);
//        ReverserDevice device = new ReverserDevice();
//        for (Integer integer : pattern) {
//            if (integer == 0) {
//                device.addCall(NextInt.withValue(4, 0));
//            } else if (integer == 1) {
//                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
//            } else {
//                device.addCall(NextInt.consume(4, 1));
//            }
//        }
//        device.streamSeeds().parallel().forEach(System.out::println);
		Result r = new VersionCrack(MCVersion.v1_16, -121, 20, 64, "100100101011100010111111101011000110101110111011111101101111110").run();
		System.out.println(r);
		//


//      Result r=new VersionCrack(MCVersion.vLegacy, 142,27,-934,  "222222222011101122111101012111021012121000101211111210222222222").run();
//        System.out.println(r);
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
		System.out.println(new VersionCrack(MCVersion.v1_16, -1840, 23, -1275, "1001011111001010111011100110111110101001011101011", Biomes.OCEAN).run().toString());
		System.out.println(new VersionCrack(MCVersion.v1_16, -2420, 40, -1693, "1111101100100111101111000011110101110101100111111", Biomes.JUNGLE_EDGE).run().toString());

		System.out.println(new VersionCrack(MCVersion.v1_16, -5620, 34, -2062, "111110111101111111100101111111110110011111110111101111111001211011101111001101011", Biomes.SWAMP).run().toString());
		System.out.println(new VersionCrack(MCVersion.v1_16, -6799, 61, -1473, "011010011111011111011110011100111011110111011110001011110111011111000011111101011", Biomes.DESERT).run().toString());

	}
}
