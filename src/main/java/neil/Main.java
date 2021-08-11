package neil;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import neil.dungeons.VersionCrack;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static neil.gui.MCVersion.*;

public class Main {
    public static final boolean PARTIAL_OVERRIDE = true;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        boolean validVersion = false;
        int version = 0;
        Biome dungeonBiome = Biomes.DESERT;
        int dungeon1x, dungeon1y, dungeon1z;
        String dungeon1Sequence = "";
        int dungeon2x = 0;
        int dungeon2y = 0;
        int dungeon2z = 0;
        String dungeon2Sequence = "";

        //theWorldIsCursedAndGodHasForsakenUs();

        //testing methods
        if (true)
            test16_17();
        if (true)
            test15();
        if (true)
            test13_14();
        if (true)
            test8_12();
        if (true)
            testLegacy();

        System.exit(0);

        //Ask the user for the version they want to use
        System.out.print("Please provide the version number the dungeon was created in as listed below:\n16    Release 1.16.x and 1.17.x\n15    Release 1.15.x\n13    Releases 1.13.x and 1.14.x\n8     Releases 1.8.x through 1.12.x\n7     Releases 1.7.x and earlier\n0     I don't know\nType the corresponding number on the left: ");
        //valid version starts off false and is inverted, then set to true to after a valid input is received
        while (!validVersion) {
            String input = userInput.nextLine();
            version = validateUserVersionInput(input);
            if (version == -1) {
                System.out.println("Please provide a supported version number like \"16\". If you do not know the version, put 0");
                System.out.print("Version number: ");
            } else {
                validVersion = true;
            }
        }

        //Remove this after you get "unknown version" figured out
        if (version == 0) {
            System.out.print("Unknown version not yet support, sorry.\nPress enter to exit.");
            String input = userInput.nextLine();
            System.exit(0);
        }

        if (version > 5) {
            dungeon1x = 0;
            dungeon1y = 0;
            dungeon1z = 0;
            dungeon1Sequence = "";
            dungeonBiome = Biomes.PLAINS;
        } else if (version == 5) {
            System.out.println("Please input your dungeon data:");
            dungeon1x = getDungeonX(0);
            dungeon1y = getDungeonY(0);
            dungeon1z = getDungeonZ(0);
            dungeon1Sequence = getSequence();
            dungeonBiome = getDungeonBiome();
        } else if (version >= 3) {
            System.out.println("Please input your dungeon data:");
            dungeon1x = getDungeonX(0);
            dungeon1y = getDungeonY(0);
            dungeon1z = getDungeonZ(0);
            dungeon1Sequence = getSequence();
        } else {
            System.out.println("Please provide data from two different dungeons:");
            dungeon1x = getDungeonX(1);
            dungeon1y = getDungeonY(1);
            dungeon1z = getDungeonZ(1);
            dungeon1Sequence = getSequence();
            System.out.println("Now enter the data for the second dungeon:");
            dungeon2x = getDungeonX(2);
            dungeon2y = getDungeonY(2);
            dungeon2z = getDungeonZ(2);
            dungeon2Sequence = getSequence();
        }

        switch (version) {
            //Unknown
            case 0: new VersionCrack(vUnknown, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence).getSeedBranched();
                break;
            //Legacy(1.0-1.7)
            case 1: new VersionCrack(vLegacy, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence).getSeedBranched();
                break;
            //1.8-1.12
            case 2: new VersionCrack(v1_8, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence).getSeedBranched();
                break;
            //1.13-1.14
            case 3: new VersionCrack(v1_13, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence).getSeedBranched();
                break;
            //1.15
            case 4: new VersionCrack(v1_15, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence).getSeedBranched();
                break;
            //1.16-1.17
            case 5: new VersionCrack(v1_16, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeonBiome).getSeedBranched();
                break;
            //test cases (testL, test8, test13, test15, test16)
            case 6: new VersionCrack(vLegacy, 240, 25, 170, "001101111011110110111111100011101111111101000111110010101101011", 256, 28, 129, "111110110111111111111111111111110111111111101111100101110111011").getSeedBranched();
                break;
            case 7: new VersionCrack(v1_8, 137, 27, -147, "111110101111111110110110111110011111111111111111111111101111011", 61, 59, 668, "111111110111111111001101101110111111110111111001111111001111111").getSeedBranched();
                break;
            case 8: new VersionCrack(v1_13, 280, 29, 674, "111011111101101111111111101111101111110100111110111101111111110111111101111110011").getSeedBranched();
                break;
            case 9: new VersionCrack(v1_15, 161, 16, -716, "1111111101101110111110111011101111111101101100101").getSeedBranched();
                break;
            case 10: new VersionCrack(v1_16, 25, 54, 88, "0111010110011110110100010101110110101110111111111", Biomes.PLAINS).getSeedBranched();
                break;
            default: System.out.println("Something went wrong..");
        }
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

    public static int validateUserVersionInput(String input) {
        switch (input) {
            case "17":
            case "16": System.out.println("Running version for 1.16 and 1.17..");
                return 5;
            case "15": System.out.println("Running version for 1.15..");
                return 4;
            case "14":
            case "13": System.out.println("Running version for 1.13 and 1.14..");
                return 3;
            case "12":
            case "11":
            case "10":
            case "9":
            case "8": System.out.println("Running version for 1.8 through 1.12..");
                return 2;
            case "7":
            case "6":
            case "5":
            case "4":
            case "3":
            case "2":
            case "1": System.out.println("Running version for 1.7 and older..");
                return 1;
            case "0": return 0;
            case "test16": return 10;
            case "test15": return 9;
            case "test13": return 8;
            case "test8": return 7;
            case "testL": return 6;
            default: return -1;
        }
    }

    public static int getIntFromString(String input) {
        return Integer.parseInt(input.trim());
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.print("Please input a number.\n");
            return false;
        }
        return true;
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
        while (!isInteger(input)) {
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
        while (!isInteger(input)) {
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
        while (!isInteger(input)) {
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
        System.out.print("Is the EXACT biome that the spawner block in a SWAMP, SWAMP_HILLS, or DESERT? Type \"yes\" or \"no\": ");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        Biome biome = Biomes.FOREST;
        while (biome == Biomes.FOREST) {
            switch (input.toLowerCase()) {
                case "y":
                case "ye":
                case "yes":
                case "t":
                case "true": biome = Biomes.DESERT;
                    break;
                case "n":
                case "no":
                case "f":
                case "false": biome = Biomes.PLAINS;
                    break;
                default: System.out.print("Please type yes or no: ");
                    input = userInput.nextLine();
            }
        }
        return biome;
    }

    private static boolean isSequenceValidLength(String input) {
        if (input.length() == 49 || input.length() == 63 || input.length() == 81) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isSequenceValidChars(String input) {
        for (char ch : input.toCharArray()) {
            if (!(ch == '0' || ch == '1' || ch == '2')) {
                return false;
            }
        }
        return true;
    }

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
}
