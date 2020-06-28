import dungeons.VersionCrack;
import gui.MCVersion;
import kaptainwutax.biomeutils.Biome;

public class Main {
    public static void main(String[] args) {
        System.out.println(new VersionCrack(MCVersion.vLegacy,-433,17,-1162,
                "110111122"+
                        "101100112"+
                        "211011112"+
                        "211110112"+
                        "001101100"+
                        "111111111"+
                        "111110111").run().toString());
    }
}
