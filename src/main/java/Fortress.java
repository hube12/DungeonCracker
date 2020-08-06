import java.util.Random;

public class Fortress {
    public static boolean test(long worldseed, int chunkX, int chunkZ){
        int shiftX = chunkX >> 4;
        int shiftZ = chunkZ >> 4;
        Random random= new Random((long)(shiftX ^ shiftZ << 4) ^ worldseed);
        random.nextInt();
        if (random.nextInt(3) != 0) {
            return false;
        }
        if (chunkX != (shiftX << 4) + 4 + random.nextInt(8)) {
            return false;
        }
        return chunkZ == (shiftZ << 4) + 4 + random.nextInt(8);
    }
    public static void main(String[] args) {
        System.out.println(test(17179738002L, -5,7));
    }
}
