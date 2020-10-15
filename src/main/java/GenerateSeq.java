import dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import dungeons.kaptainwutax.util.LCG;
import dungeons.kaptainwutax.util.Rand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GenerateSeq {

    public static class Data {
        static final LCG nextSeed = Rand.JAVA_LCG.combine(-1);
        public long dungeonSeed;
        public int posX;
        public int posZ;

        public Data(long dungeonSeed, int posX, int posZ) {
            this.dungeonSeed = dungeonSeed;
            this.posX = posX - 8;
            this.posZ = posZ - 8;
        }

        public long getPrevious() {
            return dungeonSeed = nextSeed.nextSeed(dungeonSeed);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "dungeonSeed=" + dungeonSeed +
                    ", posX=" + posX +
                    ", posZ=" + posZ +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<FindStructureSeedFromDungeons.Data> dataList = new ArrayList<>();
        dataList.add(new FindStructureSeedFromDungeons.Data(254065726851662L, 1018, 349));
        dataList.add(new FindStructureSeedFromDungeons.Data(65629121126901L, 783, 463));
        dataList.add(new FindStructureSeedFromDungeons.Data(109457728398433L, 801, 458));
        String[] res= new String[1];
        for (int i = 0; i < 10000; i++) {
            for (FindStructureSeedFromDungeons.Data data : dataList) {
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(
                        data.getPrevious() ^ Rand.JAVA_LCG.multiplier,
                        data.posX >> 4, data.posZ >> 4).forEach(e-> res[0]+=e+",");
            }
        }
        System.out.println(res[0]);
    }
}
