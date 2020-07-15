import kaptainwutax.seedutils.mc.seed.WorldSeed;

import java.util.ArrayList;
import java.util.List;

public class WorldSeedData {
    public static void main(String[]args){
            //testSha(218416036492882L,5132745156180918389L);


        test();
    }

    public static void testSha(long seed,long hashSeed){
        long structrureSeed=seed&0xFFFF_FFFF_FFFFL;
        System.out.println("struct seed : "+structrureSeed);
        WorldSeed.fromHash(structrureSeed, hashSeed).forEach(System.out::println);

    }
    public static void test(){
        long hashedSeed=5132745156180918389L;
        long[] seeds=new long[]{
                53195183428427L, 200514244509323L, 192588359563158L, 50220109755457L, 77752070672129L, 194057261099092L, 213279943841876L, 228283162559295L, 144427693597759L, 273475514104607L, 274062357086645L

    };
        for (long seed : seeds) {
            WorldSeed.fromHash(seed, hashedSeed).forEach(System.out::println);
        }
    }

}
