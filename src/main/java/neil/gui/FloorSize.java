package neil.gui;

import java.util.function.BiPredicate;

public enum FloorSize {
	_9x9((x, z) -> true, "9 by 9"),
	_7x9((x, z) -> x != 0 && x != 8, "7 by 9"),
	_9x7((x, z) -> z != 0 && z != 8, "9 by 7"),
	_7x7((x, z) -> x != 0 && x != 8 && z != 0 && z != 8, "7 by 7");

	private final BiPredicate<Integer, Integer> isValidBlock;
	private String displayString;

	FloorSize(BiPredicate<Integer, Integer> isValidBlock, String displayString) {
		this.isValidBlock = isValidBlock;
		this.displayString = displayString;
	}

	public boolean test(int x, int z) {
		return this.isValidBlock.test(x, z);
	}

	public FloorSize next() {
		return values()[(this.ordinal() + 1) % values().length];
	}

	@Override
	public String toString() {
		return this.displayString;
	}
	
}
