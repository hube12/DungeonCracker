package neil;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import neil.gui.MCVersion;

import java.util.*;

import static neil.gui.MCVersion.*;

public class Main {
    public static final boolean PARTIAL_OVERRIDE = true;

    public static void main(String[] args) {
        //Program data
        Scanner userInput = new Scanner(System.in);
        String input;
        boolean validInput = false;
        boolean dungeonSeedMode = false;
        boolean doubleSpawnerMode = false;
        MCVersion version = vUnknown;

        //Dungeon data
        long dungeon1Seed = 0L;
        int dungeon1x = 0;
        int dungeon1y = 0;
        int dungeon1z = 0;
        String dungeon1Sequence = "";
        Biome dungeon1Biome = Biomes.THE_VOID;

        long dungeon2Seed = 0L;
        int dungeon2x = 0;
        int dungeon2y = 0;
        int dungeon2z = 0;
        String dungeon2Sequence = "";
        Biome dungeon2Biome = Biomes.THE_VOID;
        ;

        //testing methods
        if (false) {
            test16_17();
        }
        if (false) {
            test15();
        }
        if (false) {
            test13_14();
        }
        if (false) {
            test8_12();
        }
        if (false) {
            testLegacy();
        }
        if (false) {
            crackSingleDungeon(v1_16, -6799, -1473, Biomes.DESERT, 66991252199345L);
        }
        if (false) {
            crackDoubleDungeon(vLegacy, 140, -35, Biomes.THE_VOID, 240428811966007L, 171, -34, Biomes.THE_VOID, 82449899703950L);
        }

        //Ask the user for the MCVersion they want to use
        System.out.print("Please provide the version number that the dungeon was created in, as listed below:\n16    Releases 1.16.x and 1.17.x\n15    Release  1.15.x\n13    Releases 1.13.x and 1.14.x\n8     Releases 1.8.x through 1.12.x\n1     Releases 1.7.x and earlier; including beta, alpha, and infdev\nType the corresponding number on the left: ");
        //valid version starts off false and is inverted, then set to true to after a valid input is received
        while (!validInput) {
            input = userInput.nextLine();
            if (getIntFromInputString(input) == -1) {
                System.out.println("Please provide a supported version number like \"16\".");
                System.out.print("Version number: ");
            } else {
                version = getMCVersionFromInt(getIntFromInputString(input));
                validInput = true;
            }
        }
        validInput = false;

        //Ask the user if they are using 1 or 2 dungeons
        System.out.print("\nDo you have 1 dungeon or 2: ");
        while (!validInput) {
            input = userInput.nextLine();
            if (getIntFromInputString(input) == 1) {
                System.out.println("Using 1 dungeon..\n");
                doubleSpawnerMode = false;
                validInput = true;
            } else if (getIntFromInputString(input) == 2) {
                System.out.println("Using 2 dungeons..\n");
                doubleSpawnerMode = true;
                validInput = true;
            } else {
                System.out.println("Please enter a valid system mode by typing 1 or 2.\nApplication mode: ");
            }
        }
        validInput = false;

        if (version.isOlderThan(v1_13) && !doubleSpawnerMode) {
            //Ask the user if they want to input dungeon data or seeds
            System.out.println("Since you only have 1 dungeon and have selected a version older than 1.13, Dungeon Data mode has automatically been selected.");
            System.out.println("Entering Dungeon Data mode..\n");
            dungeonSeedMode = false;
        } else {
            //Ask the user if they want to input dungeon data or seeds
            System.out.print("Do you have dungeon data or do you already have dungeon seeds?\n1     Dungeon Data  (Co-ords, Floor Pattern, etc)\n2     Dungeon Seeds (137229083672372L)\nType the corresponding number on the left: ");
            while (!validInput) {
                input = userInput.nextLine();
                if (getIntFromInputString(input) == 1) {
                    System.out.println("Entering Dungeon Data mode..\n");
                    dungeonSeedMode = false;
                    validInput = true;
                } else if (getIntFromInputString(input) == 2) {
                    System.out.println("Entering Dungeon Seed only mode..\n");
                    dungeonSeedMode = true;
                    validInput = true;
                } else {
                    System.out.println("Please enter a valid system mode by typing 1 or 2.\nApplication mode: ");
                }
            }
        }
        validInput = false;

        //If we are running in Dungeon Data mode we will need to gather more input data
        if (!dungeonSeedMode) {
            if (!doubleSpawnerMode) {
                System.out.println("Please input your dungeon data:");
                dungeon1x = getDungeonX(0);
                dungeon1y = getDungeonY(0);
                dungeon1z = getDungeonZ(0);
                dungeon1Sequence = getSequence();
                dungeon1Biome = getDungeonBiome();
            } else {
                System.out.println("Please provide data for the first dungeon:");
                dungeon1x = getDungeonX(1);
                dungeon1y = getDungeonY(1);
                dungeon1z = getDungeonZ(1);
                dungeon1Sequence = getSequence();
                dungeon1Biome = getDungeonBiome();
                System.out.println("\nNow enter the data for the second dungeon:");
                dungeon2x = getDungeonX(2);
                dungeon2y = getDungeonY(2);
                dungeon2z = getDungeonZ(2);
                dungeon2Sequence = getSequence();
                dungeon2Biome = getDungeonBiome();
            }
        } else {
            if (!doubleSpawnerMode) {
                System.out.println("Please input your dungeon data:");
                dungeon1x = getDungeonX(0);
                dungeon1z = getDungeonZ(0);
                dungeon1Seed = getDungeonSeed(0);
                dungeon1Biome = getDungeonBiome();
            } else {
                System.out.println("Please provide data for the first dungeon:");
                dungeon1x = getDungeonX(1);
                dungeon1z = getDungeonZ(1);
                dungeon1Seed = getDungeonSeed(1);
                dungeon1Biome = getDungeonBiome();
                System.out.println("\nNow enter the data for the second dungeon:");
                dungeon2x = getDungeonX(2);
                dungeon2z = getDungeonZ(2);
                dungeon2Seed = getDungeonSeed(2);
                dungeon2Biome = getDungeonBiome();
            }
        }


        if (!dungeonSeedMode) {
            if (!doubleSpawnerMode) {
                //Dungeon Data mode with 1 dungeon
                if (version.isBetween(vLegacy, v1_17)) {
                    crackSingle(version, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon1Biome);
                } else {
                    System.out.println("Unknown supported version error..");
                    System.exit(0);
                }
            } else {
                //Dungeon Data mode with 2 dungeons
                if (version.isBetween(vLegacy, v1_17)) {
                    crackDouble(version, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon1Biome, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence, dungeon2Biome);
                } else {
                    System.out.println("Unknown supported version error..");
                    System.exit(0);
                }
            }
        } else {
            //Dungeon Seed mode with 1 dungeon
            if (!doubleSpawnerMode) {
                if (version.isNewerThan(v1_13)) {
                    crackSingleDungeon(version, dungeon1x, dungeon1z, dungeon1Biome, dungeon1Seed);
                } else {
                    System.out.println("You can't run Dungeon Seed mode with only 1 dungeon on versions below 1.13!");
                    System.exit(0);
                }
            } else {
                //Dungeon Seed mode with 2 dungeons
                if (version.isBetween(vLegacy, v1_17)) {
                    crackDoubleDungeon(version, dungeon1x, dungeon1z, dungeon1Biome, dungeon1Seed, dungeon2x, dungeon2z, dungeon2Biome, dungeon2Seed);
                } else {
                    System.out.println("Unknown supported version error..");
                    System.exit(0);
                }

            }
        }

    }

    private static void crackSingle(MCVersion v, int x, int y, int z, String seq, Biome b) {
        if (v.isOlderThan(v1_13)) {
            Set<Long> DungeonSeed = new DungeonDataProcessor(v, x, y, z, seq).dungeonDataToDecoratorSeed();
            System.out.print("Your dungeon seed is:\n" + DungeonSeed + "\n");
        } else {

            Set<Long> WorldSeeds = new StructureSeedProcessor(new DecoratorSeedProcessor(v, x, z, b, new DungeonDataProcessor(v, x, y, z, seq).dungeonDataToDecoratorSeed()).decoratorSeedsToStructureSeeds()).getWorldSeedsFromStructureSeeds();
            if (WorldSeeds.isEmpty()) {
                System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no dungeon seed found.");
            } else {
                System.out.println("If the data you entered was valid, your world will be one of these seeds. To narrow this down, use 2 dungeons:\n" + WorldSeeds + "\n");
            }
        }
    }

    private static void crackDouble(MCVersion v, int x1, int y1, int z1, String seq1, Biome b1, int x2, int y2, int z2, String seq2, Biome b2) {
        Set<Long> StructureSeeds1 = new DecoratorSeedProcessor(v, x1, z1, b1, new DungeonDataProcessor(v, x1, y1, z1, seq1).dungeonDataToDecoratorSeed()).decoratorSeedsToStructureSeeds();
        Set<Long> StructureSeeds2 = new DecoratorSeedProcessor(v, x2, z2, b2, new DungeonDataProcessor(v, x2, y2, z2, seq2).dungeonDataToDecoratorSeed()).decoratorSeedsToStructureSeeds();

        StructureSeeds1.retainAll(StructureSeeds2);
        if (StructureSeeds1.isEmpty()) {
            System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no world seeds were found.");
        } else {
            System.out.println("If the data you entered was valid, your world seed is: " + new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds());
        }
    }

    private static void crackSingleDungeon(MCVersion v, int x, int z, Biome b, long dSeed) {
        Set<Long> WorldSeeds = new StructureSeedProcessor(new DecoratorSeedProcessor(v, x, z, b, Collections.singleton(dSeed)).decoratorSeedsToStructureSeeds()).getWorldSeedsFromStructureSeeds();

        if (WorldSeeds.isEmpty()) {
            System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no dungeon seed found.");
        } else {
            System.out.println("If the data you entered was valid, your world will be one of these seeds. To narrow this down, use 2 dungeons:\n" + WorldSeeds + "\n");
        }
    }

    private static void crackDoubleDungeon(MCVersion v, int x1, int z1, Biome b1, long dSeed1, int x2, int z2, Biome b2, long dSeed2) {
        Set<Long> StructureSeeds1 = new DecoratorSeedProcessor(v, x1, z1, b1, Collections.singleton(dSeed1)).decoratorSeedsToStructureSeeds();
        Set<Long> StructureSeeds2 = new DecoratorSeedProcessor(v, x2, z2, b2, Collections.singleton(dSeed2)).decoratorSeedsToStructureSeeds();

        StructureSeeds1.retainAll(StructureSeeds2);
        if (StructureSeeds1.isEmpty()) {
            System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no world seeds were found.");
        } else {
            System.out.println("If the data you entered was valid, your world seed is: " + new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds());
        }

    }


    public static MCVersion getMCVersionFromInt(int input) {
        switch (input) {
            case 17: return v1_17;
            case 16: return v1_16;
            case 15: return v1_15;
            case 14: return v1_14;
            case 13: return v1_13;
            case 12: return v1_12;
            case 11: return v1_11;
            case 10: return v1_10;
            case 9: return v1_9;
            case 8: return v1_8;
            case 7: return v1_7;
            case 6:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
            case 0: return vLegacy;
            default: return vUnknown;
        }
    }

    public static int getIntFromInputString(String input) {
        switch (input) {
            case "17": return 17;
            case "16": return 16;
            case "15": return 15;
            case "14": return 14;
            case "13": return 13;
            case "12": return 12;
            case "11": return 11;
            case "10": return 10;
            case "9": return 9;
            case "8": return 8;
            case "7": return 7;
            case "6": return 6;
            case "5": return 5;
            case "4": return 4;
            case "3": return 3;
            case "2": return 2;
            case "1": return 1;
            case "0": return 0;
            default: return -1;
        }
    }

    public static int getIntFromString(String input) {
        return Integer.parseInt(input.trim());
    }

    public static boolean isNotInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.print("Please input a number.\n");
            return true;
        }
        return false;
    }

    public static boolean isNotLong(String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.print("Please input a dungeon seed.\n");
            return true;
        }
        return false;
    }

    public static int getDungeonX(int type) {
        if (type == 0) {
            System.out.print("Spawner X: ");
        } else if (type == 1) {
            System.out.print("Spawner 1 X: ");
        } else {
            System.out.print("Spawner 2 X: ");
        }
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        while (isNotInteger(input)) {
            if (type == 0) {
                System.out.print("Spawner X: ");
            } else if (type == 1) {
                System.out.print("Spawner 1 X: ");
            } else {
                System.out.print("Spawner 2 X: ");
            }
            input = userInput.nextLine();
        }
        return getIntFromString(input);
    }

    public static int getDungeonY(int type) {
        if (type == 0) {
            System.out.print("Spawner Y: ");
        } else if (type == 1) {
            System.out.print("Spawner 1 Y: ");
        } else {
            System.out.print("Spawner 2 Y: ");
        }
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        while (isNotInteger(input)) {
            if (type == 0) {
                System.out.print("Spawner Y: ");
            } else if (type == 1) {
                System.out.print("Spawner 1 Y: ");
            } else {
                System.out.print("Spawner 2 Y: ");
            }
            input = userInput.nextLine();
        }
        return getIntFromString(input);
    }

    public static int getDungeonZ(int type) {
        if (type == 0) {
            System.out.print("Spawner Z: ");
        } else if (type == 1) {
            System.out.print("Spawner 1 Z: ");
        } else {
            System.out.print("Spawner 2 Z: ");
        }
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        while (isNotInteger(input)) {
            if (type == 0) {
                System.out.print("Spawner Z: ");
            } else if (type == 1) {
                System.out.print("Spawner 1 Z: ");
            } else {
                System.out.print("Spawner 2 Z: ");
            }
            input = userInput.nextLine();
        }
        return getIntFromString(input);
    }

    public static Long getDungeonSeed(int type) {
        if (type == 0) {
            System.out.print("Dungeon Seed: ");
        } else if (type == 1) {
            System.out.print("Dungeon 1 Seed: ");
        } else {
            System.out.print("Dungeon 2 Seed: ");
        }
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        while (isNotLong(input)) {
            if (type == 0) {
                System.out.print("Dungeon Seed: ");
            } else if (type == 1) {
                System.out.print("Dungeon 1 Seed: ");
            } else {
                System.out.print("Dungeon 2 Seed: ");
            }
            input = userInput.nextLine();
        }
        return Long.parseLong(input);
    }

    public static String getSequence() {
        System.out.print("Please input your dungeon sequence: ");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();

        while (!(isSequenceValidLength(input))) {
            System.out.print("Please input a valid sequence that is 49, 63, or 81 chars long.\nPlease input your dungeon sequence: ");
            input = userInput.nextLine();
            while (!(isSequenceValidChars(input))) {
                System.out.print("Please input a valid sequence containing only 0, 1, or 2\nPlease input your dungeon sequence: ");
                input = userInput.nextLine();
            }
        }
        return input;
    }

    public static Biome getDungeonBiome() {
        System.out.print("Is the EXACT name of biome that the spawner block is in called SWAMP, SWAMP_HILLS, or DESERT? Type \"yes\" or \"no\": ");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        Biome biome = Biomes.FOREST;
        while (biome == Biomes.FOREST) {
            switch (input.toLowerCase()) {
                case "1":
                case "y":
                case "ye":
                case "yes":
                case "t":
                case "true": biome = Biomes.DESERT;
                    break;
                case "0":
                case "n":
                case "no":
                case "f":
                case "false": biome = Biomes.THE_VOID;
                    break;
                default: System.out.print("Please type yes or no: ");
                    input = userInput.nextLine();
            }
        }
        return biome;
    }

    private static boolean isSequenceValidLength(String input) {
        return input.length() == 49 || input.length() == 63 || input.length() == 81;
    }

    private static boolean isSequenceValidChars(String input) {
        for (char ch : input.toCharArray()) {
            if (!(ch == '0' || ch == '1' || ch == '2')) {
                return false;
            }
        }
        return true;
    }


    //Test data, ignore unless you're messing with the code :)
    private static void test16_17() {
        // 1.17                       Dungeon Seed: [137229083672372]; Coords: [25 54 88];       Sequence: [0111010110011110110100010101110110101110111111111];                                 World Seed: [1488979889728021444]; Biome: Giant_Tree_Taiga
        Set<Long> DungeonData;
        Set<Long> StructureSeeds;
        Set<Long> WorldSeeds;

        DungeonData = new DungeonDataProcessor(v1_16, 25, 54, 88, "0111010110011110110100010101110110101110111111111").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.16 dungeon data: " + DungeonData + " | Expected data: [137229083672372]");
        StructureSeeds = new DecoratorSeedProcessor(v1_16, 25, 88, Biomes.PLAINS, DungeonData).decoratorSeedsToStructureSeeds();
        System.out.println("Here is the 1.16 structure data: " + StructureSeeds + " | Expected data: [258737905361860]");
        WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
        System.out.println("Here are the 1.16 world seeds: " + WorldSeeds + " | Expected data: [1488979889728021444]");

        DungeonData.clear();
        StructureSeeds.clear();
        WorldSeeds.clear();
    }

    private static void test15() {
        // 1.15                       Dungeon Seed: [54954658892082];  Coords: [161 16 -716];    Sequence: [1111111101101110111110111011101111111101101100101];                                 World Seed: [7298916735143357077]
        Set<Long> DungeonData;
        Set<Long> StructureSeeds;
        Set<Long> WorldSeeds;

        DungeonData = new DungeonDataProcessor(v1_15, 161, 16, -716, "1111111101101110111110111011101111111101101100101").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.15 dungeon data: " + DungeonData + " | Expected data: [54954658892082]");
        StructureSeeds = new DecoratorSeedProcessor(v1_15, 161, -716, Biomes.PLAINS, DungeonData).decoratorSeedsToStructureSeeds();
        System.out.println("Here is the 1.15 structure data: " + StructureSeeds + " | Expected data: [?]");
        WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
        System.out.println("Here are the 1.15 world seeds: " + WorldSeeds + " | Expected data: [7298916735143357077]");

        DungeonData.clear();
        StructureSeeds.clear();
        WorldSeeds.clear();
    }

    private static void test13_14() {
        // 1.14, 1.13
        // World Seed: [1724951870366438529]
        //
        // Dungeon Seed: [82836126371671];
        // Coords: [693 30 -74];
        // Sequence: [111011100101011111011011001111110110111011101111111011101111011011111110111111110];
        //
        // Dungeon Seed: [19957636759997];
        // Coords: [280 29 674];
        // Sequence: [111011111101101111111111101111101111110100111110111101111111110111111101111110011];
        Set<Long> DungeonData1;
        Set<Long> StructureSeeds1;
        Set<Long> DungeonData2;
        Set<Long> StructureSeeds2;
        Set<Long> WorldSeeds;

        DungeonData1 = new DungeonDataProcessor(v1_13, 693, 30, -74, "111011100101011111011011001111110110111011101111111011101111011011111110111111110").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.13 dungeon1 data: " + DungeonData1 + " | Expected data: [82836126371671]");
        StructureSeeds1 = new DecoratorSeedProcessor(v1_13, 693, -74, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the 1.13 structure1 data: " + StructureSeeds1 + " | Expected data: [?]");
        DungeonData2 = new DungeonDataProcessor(v1_13, 280, 29, 674, "111011111101101111111111101111101111110100111110111101111111110111111101111110011").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.13 dungeon2 data: " + DungeonData2 + " | Expected data: [19957636759997]");
        StructureSeeds2 = new DecoratorSeedProcessor(v1_13, 280, 674, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the 1.13 structure2 data: " + StructureSeeds2 + " | Expected data: [?]");
        //System.out.println("Pre-filtered list: " + StructureSeeds1);
        StructureSeeds1.retainAll(StructureSeeds2);
        System.out.println("Filtered list: " + StructureSeeds1);
        WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
        System.out.println("Here are the 1.13 world seeds: " + WorldSeeds + " | Expected data: [1724951870366438529]");

        DungeonData1.clear();
        StructureSeeds1.clear();
        DungeonData2.clear();
        StructureSeeds2.clear();
        WorldSeeds.clear();
    }

    private static void test8_12() {
        // 1.12, 1.11, 1.10, 1.9, 1.8 Dungeon Seed: [14581818956973];  Coords: [137 27 -147];    Sequence: [111110101111111110110110111110011111111111111111111111101111011];                   World Seed: [-1700538326672817507]; Version: [1.10.0]
        //                            Dungeon Seed: [226023998267313]; Coords: [61 59 668];      Sequence: [111111110111111111001101101110111111110111111001111111001111111];                   World Seed: [-1700538326672817507]; Version: [1.10.0]
        Set<Long> DungeonData1;
        Set<Long> StructureSeeds1;
        Set<Long> DungeonData2;
        Set<Long> StructureSeeds2;
        Set<Long> WorldSeeds;
        //Dungeon Seed: [14581818956973];  Coords: [137 27 -147];    Sequence: [111110101111111110110110111110011111111111111111111111101111011];                   World Seed: [-1700538326672817507]; Version: [1.10.0]
        DungeonData1 = new DungeonDataProcessor(v1_8, 137, 27, -147, "111110101111111110110110111110011111111111111111111111101111011").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.8 dungeon1 data: " + DungeonData1 + " | Expected data: [14581818956973]");
        StructureSeeds1 = new DecoratorSeedProcessor(v1_8, 137, -147, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the 1.8 structure1 data: " + StructureSeeds1 + " | Expected data: [?]");
        //Dungeon Seed: [226023998267313]; Coords: [61 59 668];      Sequence: [111111110111111111001101101110111111110111111001111111001111111];                   World Seed: [-1700538326672817507]; Version: [1.10.0]
        DungeonData2 = new DungeonDataProcessor(v1_8, 61, 59, 668, "111111110111111111001101101110111111110111111001111111001111111").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.8 dungeon2 data: " + DungeonData2 + " | Expected data: [226023998267313]");
        StructureSeeds2 = new DecoratorSeedProcessor(v1_8, 61, 668, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the 1.8 structure2 data: " + StructureSeeds2 + " | Expected data: [?]");
        //System.out.println("Pre-filtered list: " + StructureSeeds1);
        StructureSeeds1.retainAll(StructureSeeds2);
        System.out.println("Filtered list: " + StructureSeeds1);
        WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
        System.out.println("Here are the 1.8 world seeds: " + WorldSeeds + " | Expected data: [-1700538326672817507]");
        DungeonData1.clear();
        StructureSeeds1.clear();
        DungeonData2.clear();
        StructureSeeds2.clear();
        WorldSeeds.clear();
    }

    private static void testLegacy() {
        // alpha testing              Dungeon Seed: [240428811966007]; Coords: [140 81 -35];     Sequence: [001011101111111111001111111011011101110111111110000110110111111];                   World Seed: [3257840388504953787];  Version: [a1.2.2]
        //                            Dungeon Seed: [82449899703950];  Coords: [171 13 -34];     Sequence: [101111111111110111011101111100011111111100101011111011001111010];                   World Seed: [3257840388504953787];  Version: [a1.2.2]
        Set<Long> DungeonData1;
        Set<Long> StructureSeeds1;
        Set<Long> DungeonData2;
        Set<Long> StructureSeeds2;
        Set<Long> WorldSeeds;

        DungeonData1 = new DungeonDataProcessor(vLegacy, 140, 81, -35, "001011101111111111001111111011011101110111111110000110110111111").dungeonDataToDecoratorSeed();
        System.out.println("Here is the legacy dungeon1 data: " + DungeonData1 + " | Expected data: [240428811966007]");
        StructureSeeds1 = new DecoratorSeedProcessor(vLegacy, 140, -35, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the legacy structure1 data: " + StructureSeeds1 + " | Expected data: [?]");
        DungeonData2 = new DungeonDataProcessor(vLegacy, 171, 13, -34, "101111111111110111011101111100011111111100101011111011001111010").dungeonDataToDecoratorSeed();
        System.out.println("Here is the legacy dungeon2 data: " + DungeonData2 + " | Expected data: [82449899703950]");
        StructureSeeds2 = new DecoratorSeedProcessor(vLegacy, 171, -34, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the legacy structure2 data: " + StructureSeeds2 + " | Expected data: [?]");
        //System.out.println("Pre-filtered list: " + StructureSeeds1);
        StructureSeeds1.retainAll(StructureSeeds2);
        System.out.println("Filtered list: " + StructureSeeds1);
        WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
        System.out.println("Here are the legacy world seeds: " + WorldSeeds + " | Expected data: [3257840388504953787]");

        DungeonData1.clear();
        StructureSeeds1.clear();
        DungeonData2.clear();
        StructureSeeds2.clear();
        WorldSeeds.clear();
    }
    /* Cobble = 0; Moss = 1; Unknown = 2
    // 1.17                       Dungeon Seed: [137229083672372]; Coords: [25 54 88];       Sequence: [0111010110011110110100010101110110101110111111111];                                 World Seed: [1488979889728021444]; Biome: Giant_Tree_Taiga
    // 1.16                       Dungeon Seed: [66991252199345];  Coords: [-6799 61 -1473]; Sequence: [011010011111011111011110011100111011110111011110001011110111011111000011111101011]; World Seed: [-720350949281663006]; Biome: Desert
    // 1.15                       Dungeon Seed: [54954658892082];  Coords: [161 16 -716];    Sequence: [1111111101101110111110111011101111111101101100101];                                 World Seed: [7298916735143357077]
    // 1.14, 1.13                 Dungeon Seed: [82836126371671];  Coords: [693 30 -74];     Sequence: [111011100101011111011011001111110110111011101111111011101111011011111110111111110]; World Seed: [1724951870366438529]
                                  Dungeon Seed: [19957636759997];  Coords: [280 29 674];     Sequence: [111011111101101111111111101111101111110100111110111101111111110111111101111110011]; World Seed: [1724951870366438529]
    // 1.12, 1.11, 1.10, 1.9, 1.8 Dungeon Seed: [14581818956973];  Coords: [137 27 -147];    Sequence: [111110101111111110110110111110011111111111111111111111101111011];                   World Seed: [-1700538326672817507]; Version: [1.10.0]
                                  Dungeon Seed: [226023998267313]; Coords: [61 59 668];      Sequence: [111111110111111111001101101110111111110111111001111111001111111];                   World Seed: [-1700538326672817507]; Version: [1.10.0]
    // 1.7 and below              Dungeon Seed: [215824296572061]; Coords: [256 28 129];     Sequence: [111110110111111111111111111111110111111111101111100101110111011];                   World Seed: [4549957071420637180];  Version: [1.4.7]
                                  Dungeon Seed: [185122040393267]; Coords: [240 25 170];     Sequence: [001101111011110110111111100011101111111101000111110010101101011];                   World Seed: [4549957071420637180];  Version: [1.4.7]
    // alpha testing              Dungeon Seed: [240428811966007]; Coords: [140 81 -35];     Sequence: [001011101111111111001111111011011101110111111110000110110111111];                   World Seed: [3257840388504953787];  Version: [a1.2.2]
    //                            Dungeon Seed: [82449899703950];  Coords: [171 13 -34];     Sequence: [101111111111110111011101111100011111111100101011111011001111010];                   World Seed: [3257840388504953787];  Version: [a1.2.2]
    */
}
