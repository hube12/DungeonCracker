import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.util.Rand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindStructureFrom2Dungeons {
    public static void main(String[] args) {
        long dungeon1=239658245455776L;
        int posX1=-2038;
        int posZ1=7682;

        long dungeon2=163147727237906L;
        int posX2=-1223;
        int posZ2=3046;

        List<Long> structure1=new ArrayList<>();
        List<Long> structure2=new ArrayList<>();
        posX1 -= 8;
        posZ1 -= 8;
        posX2 -= 8;
        posZ2 -= 8;
        for (int i = 0; i < 200; i++) {
            structure1.addAll(PopReversal2TheHalvening.getSeedFromChunkseedPre13(dungeon1 ^ Rand.JAVA_LCG.multiplier, posX1 >> 4, posZ1 >> 4));
            dungeon1 = Rand.JAVA_LCG.combine(-1).nextSeed(dungeon1);
            PopReversal2TheHalvening.getSeedFromChunkseedPre13(dungeon2 ^ Rand.JAVA_LCG.multiplier, posX2 >> 4, posZ2 >> 4).forEach(s -> {

                if (structure1.contains(s)){
                    System.out.println(s);
                }
                structure2.add(s);
            });
            dungeon1 = Rand.JAVA_LCG.combine(-1).nextSeed(dungeon1);
            dungeon2 = Rand.JAVA_LCG.combine(-1).nextSeed(dungeon2);
        }
        System.out.println(Arrays.toString(structure1.toArray()));
        System.out.println("----------");
        System.out.println(Arrays.toString(structure2.toArray()));
    }
}
