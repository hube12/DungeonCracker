package neil;

import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;
import kaptainwutax.seedutils.mc.seed.WorldSeed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Slime {
    public static void main(String[] args) {
        ChunkRand rand = new ChunkRand();
        List<CPos> slimeChunks = generateSlimeChunks(rand);

        lower:
        for(long l = 0; l < 1L << 18; l++) {
            for(CPos slimeChunk: slimeChunks) {
                rand.setSlimeSeed(l, slimeChunk.getX(), slimeChunk.getZ(), MCVersion.v1_16_2);
                if(rand.next(31) % 2 != 0)continue lower;
            }

            System.out.println("Found lower bits " + l + ":");

            upper:
            for(long u = 0; u < 1L << (48 - 18); u++) {
                for(CPos slimeChunk: slimeChunks) {
                    rand.setSlimeSeed(l | (u << 18), slimeChunk.getX(), slimeChunk.getZ(), MCVersion.v1_16_2);
                    if(rand.nextInt(10) != 0)continue upper;
                }

                System.out.println("Found seed " + (l | (u << 18)) + ".");
            }
        }
    }

    private static List<CPos> generateSlimeChunks(ChunkRand rand) {
        List<CPos> slimeChunks = new ArrayList<>();
        long worldSeed = new Random().nextLong();
        System.out.println("Seed is " + WorldSeed.toStructureSeed(worldSeed));

        while(slimeChunks.size() < 30) {
            CPos pos = new CPos(rand.nextInt(100001) - 5000, rand.nextInt(100001) - 5000);
            rand.setSlimeSeed(worldSeed, pos.getX(), pos.getZ(), MCVersion.v1_16_2);
            if(rand.nextInt(10) == 0)slimeChunks.add(pos);
        }

        System.out.println("Chunks are " + slimeChunks);
        return slimeChunks;
    }
}
