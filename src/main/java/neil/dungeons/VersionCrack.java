package neil.dungeons;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import neil.Main;
import neil.dungeons.kaptainwutax.magic.PopReversal2TheHalvening;
import neil.dungeons.kaptainwutax.magic.PopulationReversal;
import neil.dungeons.kaptainwutax.magic.RandomSeed;
import neil.dungeons.kaptainwutax.util.LCG;
import neil.dungeons.kaptainwutax.util.Rand;
import neil.gui.MCVersion;
import randomreverser.ReverserDevice;
import randomreverser.call.FilteredSkip;
import randomreverser.call.NextInt;

import java.util.*;
import java.util.stream.Collectors;

public class VersionCrack {
    private int pos1X;
    private int pos2X;
    private final int pos1Y;
    private final int pos2Y;
    private int pos1Z;
    private int pos2Z;
    private final Biome biome;
    String sequence1, sequence2;
    MCVersion version;
    List<Long> dungeonSeeds = new ArrayList<>();
    List<Long> structureSeeds = new ArrayList<>();
    List<Long> worldSeeds = new ArrayList<>();
    Set<Long> decoratorSeeds;

    public VersionCrack(MCVersion version, int posX, int posY, int posZ, String sequence1, Biome biome) {
        this.pos1X = posX;
        this.pos1Y = posY;
        this.pos1Z = posZ;
        this.sequence1 = sequence1;
        this.pos2X = 0;
        this.pos2Y = 0;
        this.pos2Z = 0;
        this.sequence2 = "";
        this.version = version;
        this.biome = biome;
    }

    public VersionCrack(MCVersion version, int posX, int posY, int posZ, String sequence1) {
        this.pos1X = posX;
        this.pos1Y = posY;
        this.pos1Z = posZ;
        this.sequence1 = sequence1;
        this.pos2X = 0;
        this.pos2Y = 0;
        this.pos2Z = 0;
        this.sequence2 = "";
        this.version = version;
        this.biome = Biomes.PLAINS;
    }

    public VersionCrack(MCVersion version, int pos1X, int pos1Y, int pos1Z, String sequence1, int pos2X, int pos2Y, int pos2Z, String sequence2) {
        this.pos1X = pos1X;
        this.pos1Y = pos1Y;
        this.pos1Z = pos1Z;
        this.sequence1 = sequence1;
        this.pos2X = pos2X;
        this.pos2Y = pos2Y;
        this.pos2Z = pos2Z;
        this.sequence2 = sequence2;
        this.version = version;
        this.biome = Biomes.PLAINS;
    }

    /*public Result run() {
        return getSeed(version);
    }*/

    public void runTest(){
        getSeedTest(version);
    }

    private int getOrdinalBiome(Biome biome) {
        // there is a mineshaft (so 2 because 2 loops) in all 68 biomes ( 46 in defaultLand, 10 in defaultOcean,
        // 6 in DefaultFeature, 6 in defaultMesa) and
        // 3 have fossils
        List<Biome> fossilBiomes = new ArrayList<>(Arrays.asList(Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.DESERT));
        return 2 + (fossilBiomes.contains(biome) ? 1 : 0);
    }

    //MCVersion.v1_16 MCVersion.v1_17
    public Result crack1_16(Biome biome) {
        Result result = new Result();
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }
        decoratorSeeds.forEach(s -> {
            result.addDungeonSeed(s);
            System.out.println("Found Dungeon seed: " + s);
        });

        if (!decoratorSeeds.isEmpty()) {
            System.out.format("Finished dungeon search and looking for world seeds.\n");
        }

        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                // they changed and added lake in the enum so 3 * 10000 + pos in the UNDERGROUND_STRUCTURES so 2 (yeah it changed)
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 2, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                    //System.out.format("Structure seed %d... \n", structureSeed);
                    result.addStructureSeed(structureSeed);
                    for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                        long worldSeed = (upperBits << 48) | structureSeed;
                        if (!RandomSeed.isRandomSeed(worldSeed)) {
                            continue;
                        }
                        //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                        result.addWorldSeed(worldSeed);
                    }
                });
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 3, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                    //System.out.format("Structure seed %d... \n", structureSeed);
                    result.addStructureSeed(structureSeed);
                    for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                        long worldSeed = (upperBits << 48) | structureSeed;
                        if (!RandomSeed.isRandomSeed(worldSeed)) {
                            continue;
                        }
                        //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                        result.addWorldSeed(worldSeed);
                    }
                });

                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
        return result;
    }

    //MCVersion.1_15
    public Result crack1_15() {
        Result result = new Result();
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }
        decoratorSeeds.forEach(s -> {
            result.addDungeonSeed(s);
            System.out.println("Found Dungeon seed: " + s);
        });

        if (!decoratorSeeds.isEmpty()) {
            System.out.format("Finished dungeon search and looking for world seeds.\n");
        }

        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                    //System.out.format("Structure seed %d... \n", structureSeed);
                    result.addStructureSeed(structureSeed);
                    for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                        long worldSeed = (upperBits << 48) | structureSeed;
                        if (!RandomSeed.isRandomSeed(worldSeed)) {
                            continue;
                        }
                        //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                    }
                });

                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
        return result;
    }

    //MCVersion.v1_13, MCVersion.v1_14
    public Result crack1_14() {
        Result result = new Result();
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }
        decoratorSeeds.forEach(s -> {
            result.addDungeonSeed(s);
            System.out.println("Found Dungeon seed: " + s);
        });

        if (!decoratorSeeds.isEmpty()) {
            System.out.format("Finished dungeon search and looking for world seeds.\n");
        }

        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                    //System.out.format("Structure seed %d... \n", structureSeed);
                    result.addStructureSeed(structureSeed);
                    for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                        long worldSeed = (upperBits << 48) | structureSeed;
                        if (!RandomSeed.isRandomSeed(worldSeed)) {
                            continue;
                        }
                        //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                        result.addWorldSeed(worldSeed);
                    }
                });

                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
        return result;
    }

    //MCVersion.v1_7
    public Result crackBetween1_7To1_12() {
        Result result = new Result();
        pos1X -= 8; //different from 1.13+
        pos1Z -= 8; //different from 1.13+
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }
        decoratorSeeds.forEach(s -> {
            result.addDungeonSeed(s);
            System.out.println("Found Dungeon seed: " + s);
        });
        if (!decoratorSeeds.isEmpty()) {
            System.out.format("Finished dungeon search and looking for world seeds.\n");
        }
		/*for (long seed : decoratorSeeds) {
			long decoratorSeed = seed;
			for (int i = 0; i < 2000; i++) {
				PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, posX >> 4, posZ >> 4).forEach(s -> {
					System.out.println(s);
					result.addStructureSeed(s);
				});
				decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
			}
		}*/
        return result;
    }

    //MCVersion.vLegacy
    public Result crackBefore1_7() {
        Result result = new Result();
        pos1X -= 8; //different from 1.7+
        pos1Z -= 8; //different from 1.7+
        //int offsetX = pos1X & 15;
        //int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.consume(16, 1)); //different from 1.7+
        device.addCall(NextInt.consume(16, 1)); //different from 1.7+
        device.addCall(NextInt.withValue(128, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }

        decoratorSeeds.forEach(s -> {
            result.addDungeonSeed(s);
            System.out.println("Found Dungeon seed: " + s);
        });

        for (long seed : decoratorSeeds) {
            long decoratorSeed = seed;
            for (int i = 0; i < 2000; i++) {
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, pos1X >> 4, pos1Z >> 4).forEach(s -> {
                    System.out.println(s);
                    result.addStructureSeed(s);
                });
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
        return result;
    }

    /***
     * @return Returns an array list of DungeonSeeds (also known as DecoratorSeeds)
     */
    public ArrayList<Long> getDungeonSeedsLegacy() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        //Result result = new Result();
        pos1X -= 8; //different from 1.7+
        pos1Z -= 8; //different from 1.7+
        //int offsetX = pos1X & 15;
        //int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.consume(16, 1)); //different from 1.7+
        device.addCall(NextInt.consume(16, 1)); //different from 1.7+
        device.addCall(NextInt.withValue(128, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        return dungeonSeeds;
    }

    public ArrayList<Long> getDungeonSeeds812() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        //Result result = new Result();
        pos1X -= 8; //different from 1.13+
        pos1Z -= 8; //different from 1.13+
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        return dungeonSeeds;
    }

    public ArrayList<Long> getDungeonSeeds1314() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        //Result result = new Result();
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        return dungeonSeeds;
    }

    public ArrayList<Long> getDungeonSeeds15() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        //Result result = new Result();
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        return dungeonSeeds;
    }

    public ArrayList<Long> getDungeonSeeds1617() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        //Result result = new Result();
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, pos1Y));
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().sequential().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().sequential().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        return dungeonSeeds;
    }

    /***
     * @param decoratorSeeds Pass in a set of decorator seeds
     * @return Returns an array list of structure seeds
     */
    public ArrayList<Long> getStructureSeedsLegacy(ArrayList<Long> decoratorSeeds) {
        ArrayList<Long> structureSeeds = new ArrayList<>();
        //System.out.print("Debug 2\nRunning Legacy and 1.8-1.12 code..\n");
        for (long seed : decoratorSeeds) {
            long decoratorSeed = seed;
            for (int i = 0; i < 2000; i++) {
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, pos1X >> 4, pos1Z >> 4).forEach(structureSeeds::add);
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
        return structureSeeds;
    }

    public ArrayList<Long> getStructureSeeds812(ArrayList<Long> decoratorSeeds) {
        return getStructureSeedsLegacy(decoratorSeeds);
    }

    public ArrayList<Long> getStructureSeeds1314(ArrayList<Long> decoratorSeeds) {
        ArrayList<Long> structureSeeds = new ArrayList<>();
        //System.out.print("Debug 3\nRunning 1.13 and 1.14 code..\n");
        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                structureSeeds.addAll(PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, pos1X & -16, pos1Z & -16));

                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
        return structureSeeds;
    }

    public ArrayList<Long> getStructureSeeds15(ArrayList<Long> decoratorSeeds) {
        ArrayList<Long> structureSeeds = new ArrayList<>();
        //System.out.print("Debug 4\nRunning 1.15 code..\n");
        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                structureSeeds.addAll(PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, pos1X & -16, pos1Z & -16));

                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
        return structureSeeds;
    }

    public ArrayList<Long> getStructureSeeds1617(ArrayList<Long> decoratorSeeds) {
        ArrayList<Long> structureSeeds = new ArrayList<>();
        //System.out.print("Debug 5\nRunning 1.16 code..\n");
        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);
            for (int i = 0; i < 8; i++) {
                structureSeeds.addAll(PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 2, pos1X & -16, pos1Z & -16));
                structureSeeds.addAll(PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 3, pos1X & -16, pos1Z & -16));
                decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
            }
        }
        return structureSeeds;
    }

    public void getWorldSeedsUniversal(ArrayList<Long> structureSeeds) {
        for (long structureSeed : structureSeeds) {
            for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                long worldSeed = (upperBits << 48) | structureSeed;
                if (!RandomSeed.isRandomSeed(worldSeed)) {
                    continue;
                }
                worldSeeds.add(worldSeed);
            }
        }
        System.out.println("Your world may be one of these seeds: " + worldSeeds);
    }

    public void getWorldSeedUnknown(){
        /*case v1_17, v1_16 -> {
            if (FOSSIL_BIOMES.contains(biome)) {
                30003L;
            } else {
                30002L;
            }
        }*/
    }

    public Long fossilBiomeSalt(){
        if(biome == Biomes.DESERT || biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS){
            return 30003L;
        } else {
            return 30002L;
        }
    }

    public void getSeedTest(MCVersion version) {
        switch (version) {
            case v1_16: getWorldSeedsUniversal(getStructureSeeds1617(getDungeonSeeds1617())); break;
            case v1_15: getWorldSeedsUniversal(getStructureSeeds15(getDungeonSeeds15())); break;
            case v1_13: getWorldSeedsUniversal(getStructureSeeds1314(getDungeonSeeds1314())); break;
            case v1_8: getWorldSeedsUniversal(getStructureSeeds812(getDungeonSeeds812())); break;
            case vLegacy: getWorldSeedsUniversal(getStructureSeedsLegacy(getDungeonSeedsLegacy())); break;
            case vUnknown: getWorldSeedUnknown(); break;
        }
    }

    public Result getSeed(MCVersion version) {
        Result result = new Result();
        if (version == MCVersion.v1_8 || version == MCVersion.vLegacy) {
            pos1X -= 8; //different from 1.13+
            pos1Z -= 8; //different from 1.13+
        }
        int offsetX = pos1X & 15;
        int offsetZ = pos1Z & 15;
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        if (version == MCVersion.vLegacy) {
            device.addCall(NextInt.consume(16, 1)); //different from 1.7+
            device.addCall(NextInt.consume(16, 1)); //different from 1.7+
            device.addCall(NextInt.withValue(128, pos1Y));
        } else if (version == MCVersion.v1_13) {
            //13 and 14
            device.addCall(NextInt.withValue(16, offsetX));
            device.addCall(NextInt.withValue(256, pos1Y));
            device.addCall(NextInt.withValue(16, offsetZ));
        } else {
            //15 and 16
            device.addCall(NextInt.withValue(16, offsetX));
            device.addCall(NextInt.withValue(16, offsetZ));
            device.addCall(NextInt.withValue(256, pos1Y));
        }
        device.addCall(NextInt.consume(2, 2)); //Skip size.

        for (Integer integer : pattern) {
            if (integer == 0) {
                device.addCall(NextInt.withValue(4, 0));
            } else if (integer == 1) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
            } else {
                device.addCall(NextInt.consume(4, 1));
            }
        }

        Set<Long> decoratorSeeds;
        if (Main.PARTIAL_OVERRIDE) {
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().parallel().limit(1).collect(Collectors.toSet());
        }
        decoratorSeeds.forEach(s -> {
            result.addDungeonSeed(s);
            dungeonSeeds.add(s);
            //System.out.println("Found Dungeon seed: " + s);
        });
        //System.out.print("Debug 1\nDungeon seeds after finished searching: " + dungeonSeeds + "\n");

        //Begin testing for world seeds
        if (version == MCVersion.vLegacy || version == MCVersion.v1_8) {
            //System.out.print("Debug 2\nRunning Legacy and 1.8-1.12 code..\n");
            for (long seed : decoratorSeeds) {
                long decoratorSeed = seed;
                for (int i = 0; i < 2000; i++) {
                    PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, pos1X >> 4, pos1Z >> 4).forEach(s -> {
                        //System.out.println(s);
                        result.addStructureSeed(s);
                    });
                    decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
                }
            }
        } else if (version == MCVersion.v1_13) {
            //System.out.print("Debug 3\nRunning 1.13 and 1.14 code..\n");
            for (long decoratorSeed : decoratorSeeds) {
                LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

                for (int i = 0; i < 8; i++) {
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                        //System.out.format("Structure seed %d... \n", structureSeed);
                        result.addStructureSeed(structureSeed);
                        for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                            long worldSeed = (upperBits << 48) | structureSeed;
                            if (!RandomSeed.isRandomSeed(worldSeed)) {
                                continue;
                            }
                            //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                            result.addWorldSeed(worldSeed);
                        }
                    });

                    decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
                }
            }
        } else if (version == MCVersion.v1_15) {
            //System.out.print("Debug 4\nRunning 1.15 code..\n");
            for (long decoratorSeed : decoratorSeeds) {
                LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

                for (int i = 0; i < 8; i++) {
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                        //System.out.format("Structure seed %d... \n", structureSeed);
                        result.addStructureSeed(structureSeed);
                        for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                            long worldSeed = (upperBits << 48) | structureSeed;
                            if (!RandomSeed.isRandomSeed(worldSeed)) {
                                continue;
                            }
                            //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                            result.addWorldSeed(worldSeed);
                        }
                    });

                    decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
                }
            }
        } else if (version == MCVersion.v1_16) {
            //System.out.print("Debug 5\nRunning 1.16 code..\n");
            for (long decoratorSeed : decoratorSeeds) {
                LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

                for (int i = 0; i < 8; i++) {
                    // they changed and added lake in the enum so 3 * 10000 + pos in the UNDERGROUND_STRUCTURES so 2 (yeah it changed)
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 2, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                        //System.out.format("Structure seed %d... \n", structureSeed);
                        result.addStructureSeed(structureSeed);
                        for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                            long worldSeed = (upperBits << 48) | structureSeed;
                            if (!RandomSeed.isRandomSeed(worldSeed)) {
                                continue;
                            }
                            //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                            result.addWorldSeed(worldSeed);
                        }
                    });
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 3, pos1X & -16, pos1Z & -16).forEach(structureSeed -> {
                        //System.out.format("Structure seed %d... \n", structureSeed);
                        result.addStructureSeed(structureSeed);
                        for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                            long worldSeed = (upperBits << 48) | structureSeed;
                            if (!RandomSeed.isRandomSeed(worldSeed)) {
                                continue;
                            }
                            //System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                            result.addWorldSeed(worldSeed);
                        }
                    });

                    decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
                }
            }
        } else if (version == MCVersion.vUnknown) {
            //System.out.print("Debug 6\nRunning unholy code..\n");
            //place the unholy amalgam of all version code squashed together here
        } else {
            //placeholder for 1.17 (lol)
        }
        return result;
    }

    //From SeedCandy/Dungeon.java, you will need to research this and use it for reference *not code theft :)*
/*
    public static final Set<Biome> FOSSIL_BIOMES = Set.of(Biomes.DESERT, Biomes.SWAMP, Biomes.SWAMP_HILLS);

    public static List<Long> crack(String dungeonString, int posX, int posY, int posZ, MCVersion version, Biome biome) {
        if(!dungeonString.matches("[0-2]+")) return Collections.emptyList();

        LCG failedDungeon = LCG.JAVA.combine(-5);
        JavaRandomDevice device = getDungeonRand(posX, posY, posZ, version);

        for(char c : dungeonString.toCharArray()) {
            switch(c) {
                case '0' -> device.addCall(NextInt.withValue(4, 0));
                case '1' -> device.addCall(FilteredSkip.filter(LCG.JAVA, r ->
                        r.nextInt(4) != 0, 1));
                case '2' -> device.addCall(NextInt.consume(4, 1));
            }
        }

        return device.streamSeeds(LCGReverserDevice.Process.EVERYTHING)
                .parallel()
                .mapToObj(decoratorSeed -> {
                    List<Long> structureSeeds = new ArrayList<>();
                    for(int i = 0; i < 8; i++) {
                        structureSeeds.addAll(ChunkRandomReverser.reversePopulationSeed(
                                (decoratorSeed ^ LCG.JAVA.multiplier) - getDungeonSalt(version, biome),
                                posX & -16, posZ & -16, version));
                        decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
                    }
                    return structureSeeds;
                })
                .flatMap(List::stream)
                .toList();
    }

    private static JavaRandomDevice getDungeonRand(int posX, int posY, int posZ, MCVersion version) {
        JavaRandomDevice device = new JavaRandomDevice();
        device.addCall(NextInt.withValue(16, posX & 15));
        if(version.isNewerOrEqualTo(MCVersion.v1_15)) {
            device.addCall(NextInt.withValue(16, posZ & 15));
            device.addCall(NextInt.withValue(256, posY));
        } else {
            device.addCall(NextInt.withValue(256, posY));
            device.addCall(NextInt.withValue(16, posZ & 15));
        }
        device.addCall(NextInt.consume(2, 2));
        return device;
    }

    private static long getDungeonSalt(MCVersion version, Biome biome) {
        return switch(version) {
            case v1_17, v1_16 -> FOSSIL_BIOMES.contains(biome) ? 30003L : 30002L;
            case v1_15, v1_14, v1_13 -> 20003L;
            default -> throw new IllegalArgumentException();
        };
    }

    public enum Size {
        _9x9(9, 9),
        _9x7(9, 7),
        _7x9(7, 9),
        _7x7(7, 7);

        public final int x, y;

        Size(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return this.x + "x" + this.y;
        }
    }*/

}
