import dungeons.kaptainwutax.magic.PopulationReversal;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;
import kaptainwutax.seedutils.mc.seed.WorldSeed;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindWorldSeedDungeonSaltMissing {
    public static void main(String[] args) {
      //  System.out.println(-7379792620528906219L-(-5163168295138872166L));
       getSalt(186509673867635L, -534171, -1274087, -5163168295138872166L & 0xFFFF_FFFF_FFFFL,29368845010725931L);
    }


    public static void getSalt(long seed, int x, int z, long actualSeed,long hashSeed) {
        LCG failedDungeon = Rand.JAVA_LCG.combine(-5);
        for (int salt = 0; salt < 0xffffff; salt++) {
            long decoratorSeed = seed;
            for (int i = 0; i < 8; i++) {
                List<Long> seeds = PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - salt, x & -16, z & -16);
                if (seeds.contains(actualSeed)) {
                    System.out.println("Salt : "+salt);
                }
                for (Long aLong : seeds) {
                    WorldSeed.fromHash(aLong, hashSeed).forEach(e-> System.out.println("Seed : "+e));
                }
                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
    }


    public static void old() {
        long dungeon1 = 239658245455776L;
        int posX1 = -2038;
        int posZ1 = 7682;

        long dungeon2 = 98088806449158L;
        int posX2 = -9062;
        int posZ2 = 2486;

        long dungeon3 = 121034749032699L;
        int posX3 = -4218;
        int posZ3 = 4140;

        List<Long> res1 = getStructureFifteen(dungeon1, posX1, posZ1);
        System.out.println("done 1");
        List<Long> res2 = getStructureFifteen(dungeon2, posX2, posZ2);
        System.out.println("done 2");
        List<Long> res3 = getStructureFifteen(dungeon3, posX3, posZ3);
        System.out.println("done 3");
        Set<Long> result = res1.stream()
                .distinct()
                .filter(res2::contains)
                .collect(Collectors.toSet());
        System.out.println(result);
        for (Long aLong : result) {
            if (res3.contains(aLong)) {
                System.out.println(aLong + " final");
            }
        }
    }

    public static List<Long> getStructureFifteen(long seed, int x, int z) {
        List<Long> res = new ArrayList<>();
        LCG failedDungeon = Rand.JAVA_LCG.combine(-5);
        for (int salt = 0; salt < 39999; salt++) {
            long decoratorSeed = seed;
            for (int i = 0; i < 8; i++) {
                res.addAll(PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - salt, x & -16, z & -16));
                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
        return res;
    }
}
