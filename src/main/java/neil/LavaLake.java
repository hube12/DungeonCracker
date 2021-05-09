package neil;

import neil.dungeons.kaptainwutax.util.Rand;
import randomreverser.util.LCG;

import static randomreverser.util.Mth.mask;

public class LavaLake {
	public static void main(String[] args) {
		long baseSeed = 4626824210191504603L;
		Rand rand = new Rand(baseSeed);
		int x = 16;

		int z = 16;
		long i = rand.nextLong() | 1L;
		long j = rand.nextLong() | 1L;
		long k = (long) x * i + (long) z * j ^ baseSeed;
		System.out.println(k ^ LCG.JAVA.multiplier);
		long ll = 26130958638411111L;
		long seed = 4626824210191504603L;
		System.out.println((ll ^ seed) & mask(48)); // correctness
		System.out.println((ll ^ (seed & mask(48))) & mask(48)); // you want to use efficient
	}
}
