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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VersionCrack {
    private int posX;
    private final int posY;
    private int posZ;
    private final Biome biome;
    String stringPattern;
    MCVersion version;

    public VersionCrack(MCVersion version, int posX, int posY, int posZ, String stringPattern, Biome biome) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.stringPattern = stringPattern;
        this.version = version;
        this.biome = biome;
    }

    public VersionCrack(MCVersion version, int posX, int posY, int posZ, String stringPattern) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.stringPattern = stringPattern;
        this.version = version;
        this.biome = Biomes.PLAINS;
    }

    /*public Result run() {
        switch (version) {
            case v1_16:
                return crack1_16(biome);
            case v1_15:
                return crack1_15();
            case v1_13: //1.13 and 1.14
                return crack1_14();
            case v1_7: //1.8, 1.9, 1.10, 1.11, 1.12
                return crackBetween1_7To1_12();
            default: //1.7 and older
                return crackBefore1_7();
        }
    }*/
    public Result run(){
        return getDungeonSeedVersionless(version);
    }

    private int getOrdinalBiome(Biome biome) {
        // there is a mineshaft (so 2 because 2 loops) in all 68 biomes ( 46 in defaultLand, 10 in defaultOcean,
        // 6 in DefaultFeature, 6 in defaultMesa) and
        // 3 have fossils
        List<Biome> fossilBiomes = new ArrayList<>(Arrays.asList(Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.DESERT));
        return 2 + (fossilBiomes.contains(biome) ? 1 : 0);
    }

    //MCVersion.v1_16
    public Result crack1_16(Biome biome) {
        Result result = new Result();
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, posY));
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
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 2, posX & -16, posZ & -16).forEach(structureSeed -> {
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
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 3, posX & -16, posZ & -16).forEach(structureSeed -> {
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
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, posY));
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
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, posX & -16, posZ & -16).forEach(structureSeed -> {
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

    //MCVersion.v1_13
    public Result crack1_14() {
        Result result = new Result();
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(256, posY));
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
                PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, posX & -16, posZ & -16).forEach(structureSeed -> {
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
        posX -= 8; //different from 1.13+
        posZ -= 8; //different from 1.13+
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(16, offsetZ));
        device.addCall(NextInt.withValue(256, posY));
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
        posX -= 8; //different from 1.7+
        posZ -= 8; //different from 1.7+
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.consume(16, 1)); //different from 1.7+
        device.addCall(NextInt.consume(16, 1)); //different from 1.7+
        device.addCall(NextInt.withValue(128, posY));
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
                PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, posX >> 4, posZ >> 4).forEach(s -> {
                    System.out.println(s);
                    result.addStructureSeed(s);
                });
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
        return result;
    }

    public Result getDungeonSeedVersionless(MCVersion version) {
        Result result = new Result();
        if (version == MCVersion.v1_7 || version == MCVersion.vLegacy) {
            posX -= 8; //different from 1.13+
            posZ -= 8; //different from 1.13+
        }
        int offsetX = posX & 15;
        int offsetZ = posZ & 15;
        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        if (version == MCVersion.vLegacy) {
            device.addCall(NextInt.consume(16, 1)); //different from 1.7+
            device.addCall(NextInt.consume(16, 1)); //different from 1.7+
            device.addCall(NextInt.withValue(128, posY));
            device.addCall(NextInt.consume(2, 2)); //Skip size.
        } else {
            device.addCall(NextInt.withValue(16, offsetX));
            device.addCall(NextInt.withValue(16, offsetZ));
            device.addCall(NextInt.withValue(256, posY));
            device.addCall(NextInt.consume(2, 2)); //Skip size.
        }

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

        if (version == MCVersion.vLegacy || version == MCVersion.v1_7) {
            for (long seed : decoratorSeeds) {
                long decoratorSeed = seed;
                for (int i = 0; i < 2000; i++) {
                    PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, posX >> 4, posZ >> 4).forEach(s -> {
                        System.out.println(s);
                        result.addStructureSeed(s);
                    });
                    decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
                }
            }
        } else if (version == MCVersion.v1_13) {
            System.out.println("Debug: For some reason this code isn't producing any results");
            for (long decoratorSeed : decoratorSeeds) {
                LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

                for (int i = 0; i < 8; i++) {
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, posX & -16, posZ & -16).forEach(structureSeed -> {
                        System.out.format("Structure seed %d... \n", structureSeed);
                        result.addStructureSeed(structureSeed);
                        for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                            long worldSeed = (upperBits << 48) | structureSeed;
                            if (!RandomSeed.isRandomSeed(worldSeed)) {
                                continue;
                            }
                            System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                            result.addWorldSeed(worldSeed);
                        }
                    });

                    decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
                }
            }
        } else if (version == MCVersion.v1_15) {
            for (long decoratorSeed : decoratorSeeds) {
                LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

                for (int i = 0; i < 8; i++) {
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 20003L, posX & -16, posZ & -16).forEach(structureSeed -> {
                        System.out.format("Structure seed %d... \n", structureSeed);
                        result.addStructureSeed(structureSeed);
                        for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                            long worldSeed = (upperBits << 48) | structureSeed;
                            if (!RandomSeed.isRandomSeed(worldSeed)) {
                                continue;
                            }
                            System.out.format("\t With nextLong() equivalent %d.\n", worldSeed);
                            result.addWorldSeed(worldSeed);
                        }
                    });

                    decoratorSeed = failedDungeon.nextSeed(decoratorSeed);
                }
            }
        } else if (version == MCVersion.v1_16) {
            for (long decoratorSeed : decoratorSeeds) {
                LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

                for (int i = 0; i < 8; i++) {
                    // they changed and added lake in the enum so 3 * 10000 + pos in the UNDERGROUND_STRUCTURES so 2 (yeah it changed)
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 2, posX & -16, posZ & -16).forEach(structureSeed -> {
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
                    PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - 30000L - 3, posX & -16, posZ & -16).forEach(structureSeed -> {
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
        } else {
            //placeholder for 1.17 (lol)
        }
        return result;
    }

}
