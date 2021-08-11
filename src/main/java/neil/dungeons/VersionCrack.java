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
    List<Long> worldSeeds = new ArrayList<>();
    Set<Long> worldSeedsSet = new HashSet<>();
    Set<Long> worldSeedsSet2 = new HashSet<>();

    //used for 1.16/1.17
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
        if (biome.getName().equals("plains")) {
            System.out.print("Using the following dungeon data:\nCoords: [" + posX + " " + posY + " " + posZ + "]; Sequence: [" + sequence1 + "]; Biome: other\n");
        } else {
            System.out.print("Using the following dungeon data:\nCoords: [" + posX + " " + posY + " " + posZ + "]; Sequence: [" + sequence1 + "]; Biome: [" + biome.getName() + "]\n");
        }
    }

    //used for 1.13-1.15
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
        System.out.print("Using the following dungeon data:\nCoords: [" + posX + " " + posY + " " + posZ + "]; Sequence: [" + sequence1 + "]\n");
    }

    //used for pre1.12
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
        System.out.print("Using the following dungeon data:\nCoords 1: [" + pos1X + " " + pos1Y + " " + pos1Z + "]; Sequence: [" + sequence1 + "]\nCoords 2: [" + pos2X + " " + pos2Y + " " + pos2Z + "]; Sequence: [" + sequence2 + "]\n");
    }

    /***
     * @return Returns an array list of DungeonSeeds (also known as DecoratorSeeds for this code)
     */
    public ArrayList<Long> getDungeonSeedsLegacy_1() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        pos1X -= 8; //different from 1.7+
        pos1Z -= 8; //different from 1.7+
        Integer[] pattern = sequence1.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.consume(16, 1)); //Skip size. //different from 1.7+
        device.addCall(NextInt.withValue(128, pos1Y));
        device.addCall(NextInt.consume(16, 1)); //Skip size. //different from 1.7+
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


        dungeonSeeds.addAll(decoratorSeeds);
        this.dungeonSeeds.addAll(dungeonSeeds);
        return dungeonSeeds;
    }

    public ArrayList<Long> getDungeonSeedsLegacy_2() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        pos2X -= 8; //different from 1.7+
        pos2Z -= 8; //different from 1.7+
        Integer[] pattern = sequence2.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.consume(16, 1)); //Skip size. //different from 1.7+
        device.addCall(NextInt.withValue(128, pos2Y));
        device.addCall(NextInt.consume(16, 1)); //Skip size. //different from 1.7+
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

        dungeonSeeds.addAll(decoratorSeeds);
        this.dungeonSeeds.addAll(dungeonSeeds);
        return dungeonSeeds;
    }

    public ArrayList<Long> getDungeonSeeds812_1() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        //Result result = new Result();
        pos1X -= 8; //different from 1.13+
        pos1Z -= 8; //different from 1.13+
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
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().parallel().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        this.dungeonSeeds.addAll(dungeonSeeds);
        return dungeonSeeds;
    }

    public ArrayList<Long> getDungeonSeeds812_2() {
        ArrayList<Long> dungeonSeeds = new ArrayList<>();
        pos2X -= 8; //different from 1.13+
        pos2Z -= 8; //different from 1.13+
        int offsetX = pos2X & 15;
        int offsetZ = pos2Z & 15;
        Integer[] pattern = sequence2.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);

        ReverserDevice device = new ReverserDevice();
        device.addCall(NextInt.withValue(16, offsetX));
        device.addCall(NextInt.withValue(256, pos2Y));
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
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().parallel().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        this.dungeonSeeds.addAll(dungeonSeeds);
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
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().parallel().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        this.dungeonSeeds.addAll(dungeonSeeds);
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
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().parallel().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        this.dungeonSeeds.addAll(dungeonSeeds);
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
            decoratorSeeds = device.streamSeeds().parallel().collect(Collectors.toSet());
        } else {
            decoratorSeeds = device.streamSeeds().parallel().limit(1).collect(Collectors.toSet());
        }

        dungeonSeeds.addAll(decoratorSeeds);
        this.dungeonSeeds.addAll(dungeonSeeds);
        return dungeonSeeds;
    }

    /***
     * @param decoratorSeeds Pass in a set of decorator seeds
     * @return Returns an array list of structure seeds
     */
    public ArrayList<Long> getStructureSeedsLegacy_1(ArrayList<Long> decoratorSeeds) {
        ArrayList<Long> structureSeeds = new ArrayList<>();
        //System.out.print("Debug 2\nRunning Legacy and 1.8-1.12 code..\n");
        for (long seed : decoratorSeeds) {
            long decoratorSeed = seed;

            for (int i = 0; i < 2000; i++) {
                structureSeeds.addAll(PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, pos1X >> 4, pos1Z >> 4));
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
        return structureSeeds;
    }

    public ArrayList<Long> getStructureSeedsLegacy_2(ArrayList<Long> decoratorSeeds) {
        ArrayList<Long> structureSeeds = new ArrayList<>();
        //System.out.print("Debug 2\nRunning Legacy and 1.8-1.12 code..\n");
        for (long seed : decoratorSeeds) {
            long decoratorSeed = seed;

            for (int i = 0; i < 2000; i++) {
                structureSeeds.addAll(PopReversal2TheHalvening.getSeedFromChunkseedPre13(decoratorSeed ^ Rand.JAVA_LCG.multiplier, pos2X >> 4, pos2Z >> 4));
                decoratorSeed = Rand.JAVA_LCG.combine(-1).nextSeed(decoratorSeed);
            }
        }
        return structureSeeds;
    }

    public ArrayList<Long> getStructureSeeds812_1(ArrayList<Long> decoratorSeeds) {
        return getStructureSeedsLegacy_1(decoratorSeeds);
    }

    public ArrayList<Long> getStructureSeeds812_2(ArrayList<Long> decoratorSeeds) {
        return getStructureSeedsLegacy_2(decoratorSeeds);
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
        return getStructureSeeds1314(decoratorSeeds);
    }

    public ArrayList<Long> getStructureSeeds1617(ArrayList<Long> decoratorSeeds) {
        ArrayList<Long> structureSeeds = new ArrayList<>();
        //System.out.print("Debug 5\nRunning 1.16 code..\n");
        for (long decoratorSeed : decoratorSeeds) {
            LCG failedDungeon = Rand.JAVA_LCG.combine(-5);

            for (int i = 0; i < 8; i++) {
                structureSeeds.addAll(PopulationReversal.getWorldSeeds((decoratorSeed ^ Rand.JAVA_LCG.multiplier) - fossilBiomeSalt(), pos1X & -16, pos1Z & -16));
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
                worldSeedsSet.add(worldSeed);
            }
        }
    }

    public void getWorldSeedsUniversal_2(ArrayList<Long> structureSeeds) {
        for (long structureSeed : structureSeeds) {
            for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
                long worldSeed = (upperBits << 48) | structureSeed;
                if (!RandomSeed.isRandomSeed(worldSeed)) {
                    continue;
                }
                worldSeedsSet2.add(worldSeed);
            }
        }
    }

    public Long fossilBiomeSalt() {
        if (biome == Biomes.DESERT || biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS) {
            return 30003L;
        } else {
            return 30002L;
        }
    }

    public void getSeedBranched() {
        switch (version) {
            case v1_16: //System.out.println("Debug: 1.16/1.17");
                getWorldSeedsUniversal(getStructureSeeds1617(getDungeonSeeds1617()));
                break;
            case v1_15: //System.out.println("Debug: 1.15");
                getWorldSeedsUniversal(getStructureSeeds15(getDungeonSeeds15()));
                break;
            case v1_13: //System.out.println("Debug: 1.13/1.14");
                getWorldSeedsUniversal(getStructureSeeds1314(getDungeonSeeds1314()));
                break;
            case v1_8: //System.out.println("Debug: 1.8-1.12");
                getWorldSeedsUniversal(getStructureSeeds812_1(getDungeonSeeds812_1()));
                getWorldSeedsUniversal_2(getStructureSeeds812_2(getDungeonSeeds812_2()));
                worldSeedsSet.retainAll(worldSeedsSet2);
                break;
            case vLegacy: //System.out.println("Debug: Legacy");
                getWorldSeedsUniversal(getStructureSeedsLegacy_1(getDungeonSeedsLegacy_1()));
                getWorldSeedsUniversal_2(getStructureSeedsLegacy_2(getDungeonSeedsLegacy_2()));
                worldSeedsSet.retainAll(worldSeedsSet2);
                break;
            case vUnknown: //System.out.println("Debug: Oh Lawd");
                break;
        }
        if (version.isNewerThan(MCVersion.v1_12)) {
            if (worldSeedsSet.isEmpty()) {
                System.out.println("Your data may be wrong, as no unique world seed was found with the data you provided.");
            } else {
                System.out.println("Your dungeon seed: " + dungeonSeeds);
                System.out.println("Your would may be one of these seeds: " + worldSeedsSet);
            }
        } else {
            if (worldSeedsSet.isEmpty()) {
                System.out.println("Your data may be wrong, as no unique world seed was found with the data you provided.");
                System.out.println("Your dungeon seeds: " + dungeonSeeds);
            } else {
                System.out.println("Your dungeons: " + dungeonSeeds);
                System.out.println("Your world seed: " + worldSeedsSet);
            }
        }
    }
}
