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
        if(false)test16_17();
        if(false)test15();
        if(true)test13_14();
        if(true)test8_12();
        if(true)testLegacy();

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
        if(version == 0){
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
                case "true": biome = Biomes.DESERT; break;
                case "n":
                case "no":
                case "f":
                case "false": biome = Biomes.PLAINS; break;
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

    private static void test16_17(){
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
    private static void test15(){
        // 1.15                       Dungeon Seed: [54954658892082];  Coords: [161 16 -716];    Sequence: [1111111101101110111110111011101111111101101100101];                                 World Seed: [7298916735143357077]
        Set<Long> DungeonData;
        Set<Long> StructureSeeds;
        Set<Long> WorldSeeds;

        DungeonData = new DungeonDataProcessor(v1_15, 161,16, -716, "1111111101101110111110111011101111111101101100101").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.15 dungeon data: " + DungeonData + " | Expected data: [54954658892082]");
        StructureSeeds = new DecoratorSeedProcessor(v1_15,161,-716, Biomes.PLAINS, DungeonData).decoratorSeedsToStructureSeeds();
        System.out.println("Here is the 1.15 structure data: " + StructureSeeds + " | Expected data: [?]");
        WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
        System.out.println("Here are the 1.15 world seeds: " + WorldSeeds + " | Expected data: [7298916735143357077]");

        DungeonData.clear();
        StructureSeeds.clear();
        WorldSeeds.clear();
    }
    private static void test13_14(){
        // 1.14, 1.13                 Dungeon Seed: [82836126371671];  Coords: [693 30 -74];     Sequence: [111011100101011111011011001111110110111011101111111011101111011011111110111111110]; World Seed: [1724951870366438529]
        //                            Dungeon Seed: [19957636759997];  Coords: [280 29 674];     Sequence: [111011111101101111111111101111101111110100111110111101111111110111111101111110011]; World Seed: [1724951870366438529]
        Set<Long> DungeonData1;
        Set<Long> StructureSeeds1;
        Set<Long> DungeonData2;
        Set<Long> StructureSeeds2;
        Set<Long> WorldSeeds;

        DungeonData1 = new DungeonDataProcessor(v1_13,693,30,-74,"111011100101011111011011001111110110111011101111111011101111011011111110111111110").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.13 dungeon1 data: " + DungeonData1 + " | Expected data: [82836126371671]");
        StructureSeeds1 = new DecoratorSeedProcessor(v1_13,693,-74, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the 1.13 structure1 data: " + StructureSeeds1 + " | Expected data: [?]");
        DungeonData2 = new DungeonDataProcessor(v1_13,280,29,674,"111011111101101111111111101111101111110100111110111101111111110111111101111110011").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.13 dungeon2 data: " + DungeonData2 + " | Expected data: [19957636759997]");
        StructureSeeds2 = new DecoratorSeedProcessor(v1_13,280,674, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
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
    private static void test8_12(){
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
        DungeonData2 = new DungeonDataProcessor(v1_8, 61,59,668, "111111110111111111001101101110111111110111111001111111001111111").dungeonDataToDecoratorSeed();
        System.out.println("Here is the 1.8 dungeon2 data: " + DungeonData2 + " | Expected data: [226023998267313]");
        StructureSeeds2 = new DecoratorSeedProcessor(v1_8, 61,668, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
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
    private static void testLegacy(){
        // alpha testing              Dungeon Seed: [240428811966007]; Coords: [140 81 -35];     Sequence: [001011101111111111001111111011011101110111111110000110110111111];                   World Seed: [3257840388504953787];  Version: [a1.2.2]
        //                            Dungeon Seed: [82449899703950];  Coords: [171 13 -34];     Sequence: [101111111111110111011101111100011111111100101011111011001111010];                   World Seed: [3257840388504953787];  Version: [a1.2.2]
        Set<Long> DungeonData1;
        Set<Long> StructureSeeds1;
        Set<Long> DungeonData2;
        Set<Long> StructureSeeds2;
        Set<Long> WorldSeeds;

        DungeonData1 = new DungeonDataProcessor(vLegacy,140,81,-35,"001011101111111111001111111011011101110111111110000110110111111").dungeonDataToDecoratorSeed();
        System.out.println("Here is the legacy dungeon1 data: " + DungeonData1 + " | Expected data: [240428811966007]");
        StructureSeeds1 = new DecoratorSeedProcessor(vLegacy,140,-35, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
        //System.out.println("Here is the legacy structure1 data: " + StructureSeeds1 + " | Expected data: [?]");
        DungeonData2 = new DungeonDataProcessor(vLegacy,171,13,-34,"101111111111110111011101111100011111111100101011111011001111010").dungeonDataToDecoratorSeed();
        System.out.println("Here is the legacy dungeon2 data: " + DungeonData2 + " | Expected data: [82449899703950]");
        StructureSeeds2 = new DecoratorSeedProcessor(vLegacy,171,-34, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
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

    public static void theWorldIsCursedAndGodHasForsakenUs(){
        long list1[] = new long[]{244870534898570L, 250754830617750L, 70115114343864L, 226803739140623L, 173304973621495L, 86662730757010L, 91627518194418L, 213645417841023L, 236608941152476L, 226934722459121L, 113036137889799L, 73431706792988L, 15184249443866L, 47884948803279L, 225848798521378L, 19582345383766L, 195410886718947L, 135715633559910L, 59742168410311L, 106699911426934L, 161468143970729L, 93692748021742L, 113108956974556L, 223645927672644L, 133891226841844L, 260612702503600L, 187275730140471L, 133954672658758L, 230734718991633L, 131511969629156L, 221198641736473L, 199029798431021L, 33448141860528L, 29523186666899L, 158559579195160L, 28122517225852L, 193658763583911L, 212718775681588L, 178535496313594L, 1336172657535L, 120951823557667L, 276487455984560L, 274421690109615L, 37744744599597L, 88233300593367L, 243254721407515L, 229222536013771L, 73572538663814L, 124251441973856L, 223362296712462L, 130346857842903L, 3283444690437L, 3388463342415L, 139485693429141L, 117620958738701L, 232574841536479L, 183203989747368L, 140589279920453L, 147082651044961L, 41911568243375L, 93289992560787L, 261681647090937L, 153499151271267L, 236627452242065L, 184130985357842L, 225197844294093L, 177583572903136L, 91068558913388L, 86016040981772L, 248028065597824L, 37601802385548L, 214763176522021L, 38262404311911L, 145681655464860L, 178245021504927L, 259238044681445L, 19823067093103L, 18711001632545L, 47632616435395L, 183318863796676L, 241811284158620L, 146963884959553L, 55616629531426L, 118157446203179L, 132638722515867L, 166410785439827L, 58348045149018L, 10370150610049L, 255153871493916L, 62806871839896L, 170692837617183L, 224119974020211L, 85760550965770L, 4695068001091L, 241570565082348L, 251098102116757L, 142783331345541L, 92648564303981L, 185520792470715L, 43101772533259L, 4091558310070L, 17723007272316L, 121690125369070L, 148847231239627L, 254788930727163L, 49618574480477L, 233571536922862L, 125446880576043L, 7945147464072L, 31915093558238L, 5068294838398L, 14844204966664L, 206088635565468L, 279628704833232L, 269711889581327L, 180531189302340L, 199286075069355L, 39372813910138L, 188755705697856L, 49336246042781L, 279966298399817L, 252712904333855L, 45955121805903L, 16994236643637L, 276960487602604L, 203623995031526L, 171329753164786L, 54565945241436L, 195340110715255L, 276447649874338L, 183693497118605L, 265782395959753L, 76086041705743L, 167152463724152L, 202873381001734L, 162289376920187L, 172055874038901L, 54055407980802L, 249494933599046L, 267694007086100L, 191544862928100L, 190816048180762L, 143047328644725L, 196336916558816L, 212845107924419L, 116890194580887L, 246106625079165L, 148952119126079L, 46020536653057L, 142732534506163L, 186291927834664L, 110123431237954L, 207488879020339L, 33119346859413L, 253462231347625L, 139292659197607L, 69359142490472L, 5153289458210L, 160893784482808L, 19025430021062L, 17572239500699L, 186656271234054L, 205651991285653L, 147073892973467L, 64710288642659L, 268688380987040L, 255070470872652L, 234656210697470L, 183447254090550L, 56453678573483L, 247773825353225L, 70948156327592L, 204001454674494L, 120261347195407L, 205492335100628L, 86162709519993L, 173881452564874L, 34613792086059L, 98995318435672L, 248565170627900L, 188876932189220L, 16563623299667L, 73781450704790L, 47991275019493L, 193617350126815L, 239944458946464L, 243868204306899L, 120578410591570L, 167112565009219L, 265191226643522L, 34274299205102L, 9963173647314L, 95776964626782L, 24447439674235L, 128171998993246L, 252138115866161L, 10227868963975L, 5675644203613L, 216692256918621L, 86820698454946L, 20441458800328L, 229745220262771L, 221246959983347L, 176166329314045L, 169322032677794L, 205929365671996L, 98909418945784L, 26758170982106L, 130615077246536L, 126065489062854L, 32576123675703L, 153444923548788L, 188544516979291L, 157560533165317L, 204799607698504L, 148097027772882L, 243596651073422L, 161038944153253L, 10143737912578L, 143651330452931L, 177030137576086L, 245557903165734L, 211204611880495L, 118560041923357L, 272035611901056L, 191606377875897L, 112995381984947L, 111986131712434L, 260476104547515L, 44193449369370L, 270655734494516L, 168985028267228L, 59318249664314L, 218005648855300L, 67043470612669L, 192096246380600L, 198033671223473L, 192218794295350L, 243624453735040L, 224128990458130L, 149682362682935L, 112272617694588L, 70995658368113L, 179544450785886L, 278137588714234L, 251053401217721L, 160352906875384L, 136932806171116L, 58375468920358L, 148518082050737L, 138834737674068L, 154165036869208L, 150079345326174L, 177092079138137L, 22093056067297L, 153571916383775L, 134380934739937L, 203255303240384L, 249084907301341L, 127733247561929L, 57950772924853L, 265697146315678L, 187321286015658L, 20927033210156L, 182371120656966L, 67876864225170L, 36196591976352L, 160809087772803L, 264155618056105L, 124413466167040L, 138384866194254L, 127557096498486L, 41315557396694L, 173048734696279L, 96949532448823L, 14822788008978L, 161844398122119L, 259153377636544L, 30064568134345L, 171416171952530L, 20546494257837L, 178082711695286L, 268020981386788L, 84830496394297L, 158225838165888L, 156528598211499L, 25070559081880L, 245605851872383L, 1836281457118L, 173487430012925L, 181221585217136L, 80936333246048L, 109495519660160L, 227936989715102L, 99322997319439L, 148266262718870L, 71460086688590L, 160582648311583L, 154052833568478L, 45913692849713L, 232218897132864L, 64668554127949L, 182657603632390L, 158560871794422L, 17607465077763L, 124540211598563L, 10898244496033L, 110380568071255L, 220883125020265L, 133654607870046L, 226532812454762L, 204680025505967L, 148706484205788L, 31995166686046L, 212583246141255L, 188730645720684L, 155589892097902L, 220736218082760L, 224188386165807L, 125223149640303L, 139026601364374L, 176883034437736L, 270564774146443L, 223019247767303L, 209445352249207L, 108664864969376L, 82573152351917L, 101723751048815L, 242785621922031L, 67646346480404L, 171350025773150L, 166835662287165L, 164322603024881L, 111866651061409L, 240312983171467L, 189705250049127L, 55900290620616L, 41579080250741L, 126593217594890L, 122197701768020L, 4239411184046L, 81709275231471L, 188219052101757L, 237517175970835L, 122462147288408L, 42514319292093L, 15156664021162L, 14186565263167L, 147236168839317L, 224267878585907L, 50550050272971L, 244810564897436L, 116304397611376L, 220913086094826L, 216267078630876L, 263060572337086L, 250753437205128L, 196819062817368L, 104741845823691L, 216770965445193L, 134430927522529L, 12364516146855L, 133119378165560L, 212752610021847L, 170162212385772L, 229236906533640L, 202798401033678L, 244280698197256L, 37993605726923L, 234291361198935L, 250540549797049L, 52172637245420L, 8570074273007L, 174782934060902L, 53361241911922L, 228168994617212L, 54022123930507L, 55844233576021L, 113501314273685L, 221914460632672L, 229443264425944L, 74561796916618L, 39004795110074L, 52027890893714L, 202757767541728L, 22075968114962L, 41783290956553L, 83777761605812L, 257983480003657L, 71308192332011L, 41558608173200L, 24996073275384L, 152936787019415L, 63775846564348L, 150613258242665L, 155927818156211L, 60786692019507L, 102376439066883L, 200479824453044L, 226114731425477L, 140950345317420L, 260090963737197L, 66154748593408L, 136214340311006L, 279483872601509L, 227375014552410L, 246988308787619L, 258634431606076L, 153478498203552L, 200649301748554L, 122223668864539L, 77350531422169L, 226428449105360L, 119661754885065L, 268889532956393L, 38087915067775L, 113260531982662L, 125091079764760L, 3327575802493L, 84703955829688L, 61726157776405L, 39062692130274L, 281395721482336L, 71779513871020L, 209672076343454L, 150420136452492L, 115475568949965L, 52690726267000L, 3699453179992L, 187388572824745L, 247313073204379L, 90264907972167L, 139327447085014L, 231159721099443L, 20521804274645L, 177932843492755L, 241876721793091L, 82058664975518L, 29592735093040L, 89424900462996L, 211329279954229L, 1972410793635L, 180695367453910L, 239307782681970L, 212262558656723L, 280842005807909L, 278749857541121L, 28178154792689L, 191361958735905L, 161438750949902L, 90453418988049L, 239133759488122L, 113231577866964L, 126077083695802L, 72173184516991L, 121398436612868L, 158878516992563L, 75081746781007L, 166002871851793L, 170166241350527L, 192868065186830L, 200006309265159L, 144760502849044L, 42378222524297L, 139799429753745L, 13700681940930L, 209498998518811L, 18318180842484L, 90794557247536L, 220092231183515L, 83091370360822L, 8030641128247L, 30085939016980L, 235717156699910L, 184538532932196L, 228447985019704L, 268016842010478L, 158384777273448L, 231772428210050L, 279801634135926L, 120796798122991L, 236532533479057L, 236013185108156L, 180514847372130L, 90591776140496L, 269657776678566L, 169354648833463L, 198557547333786L, 95630587025906L, 44446795525948L, 245893529716722L, 114387731731512L, 50888912000508L, 70898391479609L, 232711290631745L, 169992652759298L, 45103743133092L, 280085679504874L, 270060286979806L, 154624480147882L, 114256970180043L, 73988167262567L, 180391006313864L, 107420040898705L, 21784887035879L, 227967105294658L, 79436140127894L, 57565659819178L, 236124611451165L, 113779662858066L, 38266561021504L, 264574341900101L, 178547308748506L, 72968873309641L, 94599330807261L, 229394054484951L, 44479900083337L, 98870905409671L, 140545889187909L, 71672104555302L, 46032545805321L, 188923099265461L, 58743803210376L, 243569280142733L, 31290786077733L, 247879814484313L, 267640592464255L, 27999964058360L, 194857755127319L, 266871728536648L, 226060361842277L, 222412556229452L, 179517240941128L, 34908041667238L, 171514907091604L, 142893433994716L, 268065306633946L, 58796780609593L, 180557965937958L, 97275351831240L, 197175040058134L, 96974503394913L, 7314012188478L, 142123273618172L, 254202812356576L, 123639254914341L, 209380876235300L, 112360125187043L, 207561926250152L, 63357032073332L, 72396904006391L, 160025521368385L, 233929724580465L, 34832853859549L, 186450486472197L, 23007763794415L, 65536770062566L, 166682737584734L, 86317312625374L, 72520757557397L, 154177884757653L, 211513729221711L, 147919307495396L, 90653044213921L, 265167987535414L, 124531143577441L, 98002427509365L, 16915487865456L, 237433332838972L, 167314349642998L, 246067528746197L, 1515829762977L, 79253957247790L, 106127587988847L, 240575461144815L, 184695455948854L, 198474384882559L, 48108609959830L, 228999858027451L, 87031011231595L, 71829461281821L, 186015039962527L, 117150609784359L, 94397183954832L, 221692724207972L, 29868077207L, 91104871135088L, 73908462239811L, 110220350430259L, 92173972528110L, 259087878420367L, 196199171251067L, 11638492856904L, 4113601987219L, 191365146493303L, 60293007252859L, 117869804346376L, 12613891692827L, 113209792345967L, 137363146015108L, 111363302693799L, 15951644816284L, 55355118578039L, 208353464552005L, 6887269401211L, 174760818743166L, 162479861775548L, 279707120253156L, 154513163554736L, 71421493917985L, 112971543179039L, 101699137123734L, 27168709740775L, 50119317219502L, 32351912791053L, 224214416454249L, 238485641449008L, 98535863863198L, 631007194955L, 108503593669032L, 110769390339000L, 226736989346477L, 235409597850177L, 74775039273618L, 96190544394611L, 212547101593093L, 48388326244806L, 145265579259418L, 134630818021723L, 195753023179356L, 252660652926206L, 181710666768318L, 135985040519904L, 126757342300252L, 100158043730627L, 146421016759509L, 44043284962564L, 157842938668694L, 168115407746717L, 261153055508427L, 81029789317930L, 204185140884006L, 175310832017908L, 115788411460400L, 91365584167797L, 79714144155681L, 26907764670168L, 71461034615816L, 247912893768685L, 27517194851578L, 197636346084075L, 37274372176184L, 227050892508195L, 37370596159884L, 148385050412454L, 208879813023643L, 73397919051600L, 181647622423115L, 129131881986820L, 278971049552499L, 217041438471481L, 215912492079787L, 157610669518129L, 109980801795851L, 50033149343748L, 215823620664289L, 49398293358272L, 16665411984709L, 136762809457491L, 121784131217596L, 18304732295722L, 14360089464984L, 214939511031787L, 114373968199192L, 15702328922200L, 24836078742165L, 206177789439946L, 183969723054852L, 20599293321882L, 126845335510491L, 41870232055786L, 170311098051537L, 34513859459202L, 191835085319641L, 7662233576435L, 53255137679422L, 91006760517287L, 219940379283942L, 2395744463510L, 272398471484505L, 100945251093468L, 69515936762436L, 95345073173066L, 161793735896267L, 69264531733755L, 249088202365585L, 159531882085877L, 191507153190967L, 132971769240109L, 249411408918893L, 277643947441550L, 274144410657973L, 14894589895393L, 242366446525508L, 212791567450520L, 224622751454936L, 8349015308896L, 34356524067516L, 213340289736652L, 55330053312402L, 151782484742896L, 82662409746770L, 258664798534080L, 153709305918126L, 78686261735624L, 51820391602911L, 108655035891705L, 74543876841339L, 146516391764416L, 472529961323L, 142187064694312L, 201105553631731L, 235550915287192L, 183862078683353L, 234301187996681L, 111828465710153L, 24892531355352L, 127922723321380L, 152199224973530L, 51430672226872L, 119139056382825L, 38336210513146L, 233686349051208L, 74683119276340L, 41508874234465L, 213197661345070L, 73307475099320L, 279007173066918L, 166180440221487L, 152307503904311L, 132216167073674L, 114417712872752L, 48167908010715L, 81063099143836L, 6787008026939L, 249894001196622L, 206750597952990L, 231441175448916L, 212622498316093L, 248557417876671L, 165324584419665L, 149261066145084L, 100760728851174L, 265594936730724L, 184157564548990L, 278132767571471L, 101037606239090L, 127051380081114L, 130670463870580L, 69705679451930L, 232506083266507L, 44903797359628L, 121484247669073L, 264514078055802L, 276071591997552L, 221098956800922L, 244451847829909L, 22387843555687L, 38925184416993L, 108001916851082L, 255041089843259L, 81860701200936L, 22994148531333L, 30054133311687L, 109599427309353L, 43562421947227L, 48876185933233L, 251330266472870L, 196232266806914L, 163669542967894L, 276951975064962L, 215910305073966L, 276391008569086L, 118155621901013L, 6805971669961L, 151359556479925L, 21754234120232L, 15535763708677L, 278313646061966L, 109183474034678L, 197341977114513L, 184903861626486L, 122194790996126L, 17876991256916L, 183477290099405L, 72459162403296L, 247792361791563L, 28188290430476L, 87942081824208L, 234311848233414L, 249632565393857L, 127245766525862L, 240417289695013L, 234196318369782L, 157100566391614L, 115694405020798L, 16501883575647L, 182452588445439L, 126236343976968L, 930636490798L, 96758140857437L, 184861451147200L, 151206820756368L, 14415274343057L, 150614735824876L, 6185338134922L, 201671102851031L, 169872070099146L, 174187246688330L, 95802637007828L, 218963159151781L, 144048580368805L, 182833380251188L, 152889045949599L, 77547933702490L, 81305776920278L, 144879576302258L, 68566639456949L, 14401055123971L, 185135873340233L, 195215306239383L, 63226711073474L, 31030574294150L, 156485609483679L, 249477817053345L, 139499499911699L, 142457843694147L, 135678679644700L, 140577771546872L, 259522782752846L, 162998565186733L, 189831039496202L, 109874816822658L, 263983347031732L, 186626386872778L, 263819207043812L, 67169419322236L, 79411262189349L, 109493510260050L, 113133150903472L, 202819113569874L, 146888719381372L, 234654634149999L, 108243605944518L, 70669732533015L, 268631102193190L, 87669931739010L, 248079704447019L, 198542649369506L, 59937864621701L, 47167700777926L, 274369930519705L, 131159998701893L, 144639797118726L, 150433402773096L, 80356795775075L, 33709174271681L, 99224830253519L, 203446284896694L, 216381890074185L, 236047001466938L, 140936328961222L, 100171968583355L, 69655336586625L, 147451213510760L, 159924832944360L, 18440650918902L, 263302207277746L, 209484907690055L, 261882454174659L, 135452443395774L, 68388646027656L, 110147424768323L, 117520005422976L, 212417657041027L, 70276445407784L, 186226415376731L, 274432647060155L, 123745819231812L, 237194338831522L, 103773930223876L, 160503294624634L, 220079883413518L, 56680412035900L, 91740383878391L, 38489884502915L, 85238844718575L, 253290201979314L, 249027299568298L, 244514688490026L, 140031760410493L, 43498126815896L, 133618793775695L, 255689997363341L, 55165289647658L, 96309088068626L, 52313010604670L, 227149284612473L, 202996802547543L, 247448449516132L, 186030248006767L, 175912955159873L, 124256369489482L, 59048874070294L, 280292237129306L, 275121179175312L, 277367657991020L, 154945065668330L, 136814836458573L, 122018416363782L, 151166979944702L, 205988965038054L, 161589974355033L, 163277602993322L, 172446686262274L, 220256945528605L, 68609501973807L, 226226622920452L, 1657085965375L, 164360346956504L, 271146720977575L, 82551416206081L, 1107663897388L, 135181692640761L, 100510589398364L, 189984536785869L, 190695284968963L, 259738736552142L, 27298788991381L, 263933712850146L, 147302230883072L, 151856068739786L, 190465406936118L, 21297315921484L, 7531183250619L, 12628116507384L, 231682129028690L, 83148869965474L, 263124108821423L, 188657402221845L, 188774693274827L, 265600017548495L, 10187549517813L, 184229142954214L, 28588824631106L, 199001587320337L, 100831352300376L, 208235004712983L, 194167585943913L, 81630945455534L, 10612106847086L, 2263510596004L, 17677215511587L, 188850357982555L, 74971576164892L, 54047401250074L, 24308489977070L, 3090580270623L, 176976828983762L, 32117164857636L, 64584108374601L, 20522713119622L, 250078381822529L, 164811185133041L, 62173406334838L, 31301720210346L, 80320756204395L, 223760376894976L, 275204381630817L, 118993983084133L, 6547050739483L, 235430587992768L, 177212171363812L, 109256665999307L, 107683624613270L, 125141435312971L, 147056726393367L, 51479455098926L, 81047182894435L, 71353325118755L, 205586077417351L, 69775124573846L, 195063052775153L, 278888228846607L, 238648588256560L, 133521396833898L, 14940250696109L, 194177101859268L, 234964966524570L, 112600102903615L, 23361223104559L, 190540313053854L, 44597200320701L, 220165246543631L, 186140517209693L, 62871884513542L, 94979077895380L, 71414836221830L, 177026822507867L, 60983709151149L, 150329255829975L, 141036437078645L, 90209104165662L, 101437146651078L, 67707271685860L, 113781445882182L, 150741161982180L, 128083000273246L, 70294771335572L, 43799324916857L, 70869275136275L, 227180100930313L, 42636791744692L, 484471710741L, 36767390415590L, 108878466446846L, 24517591481522L, 146599026676502L, 270641615652820L, 94142745972191L, 65218600845430L, 173840398514584L, 249095139176816L, 41480750575718L, 134412153407021L, 204143266770934L, 248327164646670L, 264614476133323L, 41909278723338L, 47082540777486L, 89773155477613L, 35165526760324L, 29997624634946L, 23877657517622L, 183993170395146L, 65075049661656L, 146961904873818L, 133591019783186L, 42125682825435L, 109842011995428L, 199811836738796L, 147504742846774L, 76850659585350L, 88439783437678L, 99864291222957L, 274810954860621L, 131883175964366L, 17054487468495L, 265711648295212L, 189601872073915L, 119362377350244L, 125754145034633L, 33737440619124L, 46246589611376L, 132162626880162L, 253215472286967L, 5315765780604L, 105922325929933L, 244321470390597L, 132623461474262L, 170601084021538L, 13177957534093L, 254199264558818L, 77624699189013L, 20680210913146L, 280360793301658L, 167938225101797L, 233467476815690L, 211286650140810L, 244332621396030L, 78904317470988L, 69544527937152L, 275473489062109L, 253570610965899L, 64678353152440L, 33275474889654L, 154219192271173L, 137026983993656L, 163577461818729L, 48379119341847L, 32082925340482L, 184847035624648L, 135390009840489L, 209549366420515L, 202241827964575L, 115459107879713L, 92679075514967L, 257910481622390L, 264308901158666L, 208493232150993L, 243712075450176L, 13971152743524L, 96254236889434L, 58014653381447L, 130559146398846L, 109648432863507L, 54763835812475L, 46896187927914L, 2054229307667L, 228126538772323L, 60282328546637L, 146403988466116L, 67642495007292L, 52681216523141L, 110017808505099L, 18937164709539L, 217491307702411L, 115828104107315L, 154348300523157L, 145223266398286L, 187608005576520L, 224067535125358L, 81550366207938L, 22310673270526L, 257955681636137L, 228388205478676L, 78514980655000L, 258778364922534L, 183969224811110L, 29539717845952L, 6579652218192L, 84314622222900L, 108632690339751L, 55741395141950L, 245931008868420L, 30785892697154L, 3584389199080L, 69032348188619L, 92361669848336L, 117477950166067L, 77051154981180L, 44052886900011L, 224450313526415L, 24313336659022L, 253513473104777L, 49361899049738L, 260824080925213L, 64172652597226L, 82354224375055L, 268032810756634L, 38245017745367L, 241675779925345L, 17972781728941L, 101939440801071L, 219667387953883L, 192506902791976L, 102731419549971L, 145897707681798L, 100089810410335L, 71101911829850L, 186589102983494L, 1092195119824L, 139275311724570L, 166016043249786L, 69309284095389L, 229853140357660L, 272712524727543L, 105167078271019L, 213452740201784L, 202635104889049L, 250464932537212L, 174678037594452L, 196437617760564L, 175680540395009L, 79954855874950L, 161779884509615L, 277119369766800L, 261431417256197L, 22285271904247L, 107733229765401L, 201765223194109L, 76846084845695L, 80531645198132L, 176302948016349L, 95085019019430L, 53340307765391L, 3825019760030L, 247125832572332L, 36012122354457L, 211412636351822L, 219262007625575L, 79187850570129L, 5545851830700L, 114360288794179L, 141941662102882L, 77690333764660L, 233090099486025L, 184438275644528L, 138081900586269L, 85313203101837L, 195540451116230L, 76797858999025L, 237835005818239L, 233320678775333L, 16803893175070L, 51111445445555L, 103762528812220L, 122958939131515L, 115928047905084L, 239634161626805L, 177033368850497L, 23796354648755L, 198147067595762L, 60836856873483L, 50224757410591L, 133821498371337L, 231543794763089L, 166437992468563L, 26742470821186L, 3371302053944L, 109554222443851L, 233688011011079L, 78674904014932L, 111025950474433L, 279829167422188L, 247292364072731L, 158293000541073L, 71819066988255L, 45060769764782L, 234554493970017L, 63778181235628L, 66785118034431L, 218276582303452L, 197573910809588L, 9633871502653L, 214269536394237L, 180397613263155L, 141970747807848L, 95114380969710L, 266689122279008L, 219208922832936L, 128692696884733L, 29664131287157L, 13420250321913L, 35414472935859L, 31043549353239L, 140207704773486L, 115762514357337L, 32537591933075L, 82974976954321L, 168065529490519L, 178122393346393L, 14183019719577L, 60130176899522L, 41298770379182L, 31109446413666L, 51991426373477L, 61864323967970L, 64481034934566L, 49797066112919L, 90301199186547L, 183888302872945L, 24359729327518L, 4852685087809L, 91874749475066L, 134929105903975L, 205219135909289L, 153900587500768L, 17268182366865L, 244148599439938L, 230135602173274L, 59854021842207L, 23196532576967L, 23901620619501L, 20641490710704L, 233628480946219L, 239398655833277L, 17302821410281L, 122348431835970L, 189677459148743L, 106449759723034L, 208681144785554L, 246807958973978L, 83471048183581L, 224936702197362L, 159930233385748L, 13009982803538L, 68119367594409L, 102027098193619L, 179778395730434L, 127775999894985L, 50608760730076L, 264947277324865L, 831799718161L, 42287698219962L, 23450628544972L, 180175175631987L, 40799839067146L, 48034011376509L, 117368584286343L, 1264271968163L, 200925236981980L, 23722337462253L, 220044637098318L, 149960053131479L, 48125788366920L, 44344603355912L, 268980182748048L, 104937549834525L, 158121375372457L, 58556218257607L, 23137719522708L, 258708584143133L, 120161824492672L, 47653904992858L, 25506860096323L, 196306593311084L, 134996960529411L, 105674779572362L, 166027683985669L, 265848537179518L, 22775936385028L, 240061583818762L, 147368937557009L, 44110749562533L, 23238723187148L, 228316991591856L, 279850656373580L, 133867992449244L, 197569742029497L, 150382364261607L, 210912071622528L, 33824758023459L, 220993663904637L, 127465178129426L, 242960618593011L, 270704219804082L, 15183801005953L, 176629582937162L, 88801208000478L, 192909737388125L, 239530022580037L, 62937935057674L, 91841361687374L, 208283054328687L, 127682795464946L, 137502291319117L, 42493018390683L, 79595182354831L, 166470748271052L, 98360622813171L, 221187983009138L, 145918331346300L, 276146980542990L, 51914270871315L, 18981353257498L, 38489147788147L, 33090447695347L, 124465003470569L, 171499790698605L, 243289852318706L, 108171488877274L, 79229242916222L, 180241802189035L, 160696428635197L, 91320702130836L, 5739019880092L, 135705887165893L, 211165032446764L, 68603554404230L, 250301336074755L, 223681750669242L, 220412862127500L, 245292940132896L, 165278029637574L, 157977638137090L, 16252644337776L, 217712097923568L, 939671667616L, 65727850100061L, 179479629009670L, 23624347571735L, 270510223187057L, 144695796438433L, 145325839126677L, 181198636816146L, 136060804276118L, 101201060000499L, 94685786606965L, 229870217100923L, 205848426668707L, 45067161351945L, 239343219256811L, 239442030510908L, 196468107514551L, 129766393526803L, 142805200369408L, 118698674421882L, 258349769252625L, 145686726613750L, 233911050057299L, 159494945625471L, 259172886025117L, 212393285734550L, 235268817327188L, 7199500766917L, 98555330793777L, 42805462049115L, 235242266633637L, 263984338517826L, 97566635869183L, 146615483481464L, 255924521672967L, 264849658304221L, 105823873638824L, 50483021482122L, 264121753831583L, 76908885205185L, 204856982604288L, 261134193151148L, 171713837980034L, 113818584907735L, 6777826451416L, 253442967896209L, 207355761477139L, 219030181784340L, 136057515310190L, 226021866381457L, 136941880341693L, 32391939577705L, 119568492287276L, 138052175726457L, 267278098917153L, 102655177352239L, 247726772010857L, 75021333920727L, 275922064317000L, 255730779218633L, 146416172630722L, 280919327585240L, 205481170642671L, 219057205330423L, 34436575637678L, 89333690582013L, 112796454736423L, 155627212286225L, 142253933620863L, 137990901658398L, 86772518162375L, 66031734527522L, 159265887114816L, 49393406532774L, 117809233981077L, 253087291023365L, 263590773571985L, 213931030785483L, 43001473291475L, 240472454806979L, 247627850831720L, 147132787133471L, 214906875029704L, 72389288963164L, 222662641748344L, 254704866222820L, 201181599270761L, 155159130377618L, 272229781722273L, 171601447097093L, 212742997318633L, 100568411852643L, 75407877311310L, 118232197084148L, 182820425140318L, 100105125457169L, 56913311245824L, 46782152632167L, 56766099160305L, 58957880826474L, 75071221647801L, 122506162910136L, 180932342788104L, 61722247962636L, 223035710286356L, 116771168394939L, 21029754831039L, 138456902710261L, 18134790003027L, 29233574309573L, 67471989053964L, 16880440752981L, 52643404495912L, 170781161529728L, 190621331356997L, 42882193277507L, 215478228422675L, 94094050382670L, 279502625936446L, 112556692545346L, 105348452301426L, 20081669307954L, 118008555521518L, 156024608718387L, 192549382719012L, 137854229778878L, 256071387324495L, 224941711565447L, 191714175430091L, 197264483883218L, 183279259725149L, 230745784503698L, 273222468973696L, 128396625912653L, 222167537111038L, 172459065737559L, 110594577905350L, 133077138025045L, 114744974918141L, 258679723820693L, 62836652444065L, 198949000553646L, 222221926134795L, 145043296352556L, 98029869059663L, 235889222162210L, 113055738957087L, 77515395718967L, 61870408873745L, 8425925643020L, 156516655815964L, 270899281534460L, 100675931997770L, 60379251423693L, 275285689915242L, 102987603286736L, 2880059960980L, 120982100518357L, 229407307092311L, 241905743331611L, 273381786569754L, 214699397445398L, 236573066518454L, 120592559612937L, 113623313843993L, 144571246445478L, 38048830865544L, 5648701929693L, 215757982578928L, 44589934830224L, 112538780545398L, 24406063546795L, 79259750489212L, 129716765401452L, 120624982163890L, 252484469950386L, 24477641887312L, 21308367107066L, 111273807252429L, 66658503067130L, 16234511325124L, 229626984206154L, 114676498646778L, 112953908337259L, 140159558558338L, 83382590968616L, 36531738724666L, 6326778640873L, 139848807810302L, 97030735601654L, 191024182397051L, 60531186948538L, 229878510698169L, 242776897546020L, 165599555075438L, 49425022249294L, 123910775206996L, 29864332141210L, 32439477614423L, 242364589905531L, 71494698558533L, 228921656719211L, 134258827216624L, 243174968792553L, 81920803961743L, 81584107512041L, 123281396056744L, 154417761976015L, 173621194212632L, 121862245974107L, 35214054700112L, 268806843064688L, 46547891954101L, 137331767593647L, 25630513572755L, 104938763265968L, 75025855064122L, 77161889680466L, 76205322807043L, 121818893736108L, 249836795574222L, 142780322684469L, 124131411887574L, 21978554583432L, 187945950733512L, 28728227133549L, 98989818723191L, 8665961937400L, 218487379850643L, 52413149757706L, 39232892293306L, 84955252100939L, 155233533665055L, 37368576975023L, 34271810917796L, 986575702285L, 22740752967606L, 178760788177482L, 67843109930075L, 126930428741068L, 94157300526193L, 113158381114965L, 136592240134765L, 20729044407339L, 242671112991855L, 241771531408500L, 269444240156831L, 57749526983997L, 119154436373552L, 275562553581368L, 108208218398648L, 215685617414414L, 98421402886397L, 238903480056216L, 154041277957759L, 136498938385245L, 137482430222246L, 118488045732837L, 150162862842179L, 160003397152234L, 201229524774041L, 51510871010360L, 207818381675544L, 261873269984475L, 45795912979798L, 18287106359562L, 44883212098286L, 207454544680715L, 49151253191574L, 151784679147572L, 28687553908920L, 130911027323788L, 201583242377990L, 168935509641548L, 61266595788797L, 153267750062915L, 190934592343236L, 278309972805600L, 138136272476964L, 132340878014126L, 228089620361801L, 118992604552744L, 220654425676280L, 208816486159251L, 129796359035344L, 188817934605398L, 178079981155834L, 87642859405374L, 172918068966236L, 193733116432297L, 276540044835500L, 81304793319599L, 154410657376799L, 261932843662898L, 146467858403214L, 153140200934934L, 177680933412709L, 182137301737115L, 54934981620734L, 165514536838076L, 7811075158364L, 197022184042599L, 105999671142137L, 5484904740627L, 258114400961993L, 119218927055755L, 47062519221315L, 273699599958453L, 128819931058473L, 83725535812787L, 6547830231720L, 127350677089865L, 24447176672562L, 24711520674137L, 276370218238399L, 272485510722444L, 262285418927609L, 206790010498883L, 128504687410863L, 204615153785284L, 212856822958224L, 139199651148039L, 210370011182275L, 189222585586311L, 175623591185438L, 101200580168392L, 93705009000087L, 48863335977943L, 235957131072529L, 94601425929242L, 163199083767155L, 13613796706195L, 190739745750472L, 219246745421183L, 1370286613555L, 51981549847572L, 67585319578889L, 234126994192948L, 152192599305520L, 3736554063379L, 280817277796349L, 243549054160343L, 186284538591299L, 57769570286765L, 181084153825929L, 116834224427680L, 103572925457353L, 254293476751752L, 113057905811482L, 151959464314185L, 263610238524354L, 231212467820009L, 167640341043246L, 119931254442084L, 134961533799007L, 52827183376348L, 135212164922719L, 220815017096922L, 8716985744788L, 174744466632066L, 258800210275137L, 45809880159927L, 249134948879370L, 25896236320455L, 64218783370514L, 257614322046676L, 141754359290903L, 173247021769824L, 147118955144715L, 215965786791584L, 166612036274814L, 207396128509664L, 147926552776185L, 227359621163375L, 132027397800168L, 123049331615366L, 281081891535625L, 143870980247184L, 241841309398081L, 88980950803448L, 281206585955215L, 188042204027174L, 195035608824747L, 134736180884051L, 193808807251892L, 179262125640374L, 130612403964372L, 35087820186632L, 175002348370185L, 156719501408845L, 110082038895160L, 221910602469770L, 244508214256088L, 68222808468661L, 149229374444191L, 227462164871574L, 96392768510445L, 184589637707477L, 125108284941347L, 168357251583036L, 88598870501299L, 169104625896433L, 165728926002287L, 246941372095298L, 152391688573432L, 33022907416501L, 42466447250436L, 77917721291737L, 167472340100633L, 53932096550246L, 275183020502856L, 76986671690260L, 98975887633340L, 185741328839211L, 41179553449726L, 216903700524651L, 243431713894047L, 206892621464112L, 96354644576461L, 231994025618608L, 264201760029564L, 239269277368046L, 155090712116479L, 2676984522214L, 133791339311057L, 200232460458481L, 197689600958204L, 163204745833419L, 149458941167541L, 67427782562583L, 32985297829455L, 99897532481901L, 254513427607116L, 104986279302617L, 11997848887851L, 163204173444588L, 249055680202390L, 119195461666816L, 226723366151381L, 84422255400212L, 219359899376696L, 57268109188608L, 251003578619919L, 177284798069558L, 120679968205831L, 17270176323179L, 65500597587759L, 166907552725445L, 145929839758225L, 27169781129952L, 30616611817703L, 103529356677076L, 64520666547900L, 244913122801261L, 52717755555736L, 224429537833345L, 256438897357607L, 53768019618003L, 75471759333442L, 223857884622251L, 8958997736000L, 207285078508153L, 205635673664965L, 209117777799014L, 82421513147513L, 237235390178148L, 151330030477976L, 215571905930623L, 64909372227723L, 39650064997268L, 26176689099806L, 29434735594402L, 148745619206416L, 111303298153250L, 212253696119993L, 180531242327869L, 123503635067320L, 210205906718952L, 121578749494044L, 230827651944063L, 252292074019236L, 236987461024078L, 57007887221421L, 87968683020727L, 255720047923617L, 87523848461265L, 66398237427863L, 245032691929083L, 151423609268347L, 54150415564491L, 201992647486475L, 63618643372226L, 132140772936762L, 30891946628185L, 255139006765587L, 18474418775587L, 252782972073312L, 232039033479403L, 117726600366312L, 248116584241853L, 81417340270178L, 148276695651024L, 71242467988196L, 168902741097902L, 165835215658015L, 155534316938513L, 163434820233495L, 198592478339433L, 168886789464922L, 259416984808726L, 173308184925839L, 23097441026074L, 125563790730486L, 147770598932362L, 37316479800726L, 162496859751353L, 198021572159106L, 255726796996511L, 10496689362196L, 216137248867469L, 109346537891085L, 174281916996273L, 218490110113829L, 246615575946866L, 260335181742391L, 58025445892393L, 79297632787677L, 89582941006306L, 3303738993356L, 156448956210626L, 239243945251895L};
        long list2[] = new long[]{104519829802348L, 132342071580683L, 242840327380711L, 190748275147575L, 114077253295225L, 33744152924934L, 77822512223857L, 230873890541009L, 243746855780406L, 198269489651522L, 74317410176730L, 258619313652574L, 259592202640231L, 17089357171064L, 221230655307390L, 163007631995003L, 183855006241871L, 190390618283611L, 76356401752691L, 277100698615708L, 251882630222009L, 266410015579356L, 18944224097831L, 262973055602673L, 73951920783818L, 217300689405356L, 274508573294007L, 68100312101244L, 78452842472363L, 91756834376152L, 280908254218947L, 49008055821243L, 198097108614209L, 220906554011269L, 250236992912601L, 98474566880598L, 193936817267138L, 248213560903426L, 110176634093290L, 55660815792915L, 175913483069229L, 131933100157158L, 88125282132519L, 26116105681582L, 56170980698382L, 268570832525811L, 115608162351788L, 84908698444379L, 158532551060331L, 219565943593509L, 72186556800914L, 261093470474937L, 21552626949579L, 226871164866850L, 38078025057335L, 117329607132650L, 149465490339764L, 189871796901690L, 166148940977055L, 136044737301564L, 45075647012812L, 1402506108968L, 84551551612563L, 10301160895686L, 52780721562411L, 161629157044146L, 260561150813609L, 272460321684380L, 92791787412030L, 213362949712137L, 170386520636347L, 110065039801313L, 137016914992891L, 162711282476184L, 195630686006439L, 169208778214324L, 192417664448194L, 197770219624458L, 157854194362313L, 245628531936518L, 70666417716923L, 229643009297578L, 71350300501970L, 74892346602127L, 73463984512779L, 2366651135273L, 160739012692044L, 198101159589705L, 12565592462545L, 239838157877940L, 196418733514460L, 1642770749901L, 13527126551853L, 59225058603958L, 196646998164336L, 244918296442340L, 240413695767939L, 131916570217003L, 183987331786103L, 107849272765192L, 258496174614742L, 12411214350693L, 133020487528986L, 98701801985814L, 28298478328076L, 95586606895070L, 232071191189998L, 79717839038901L, 55739904733470L, 179432592946681L, 213753857819956L, 191101102933824L, 46785536357696L, 244862529514133L, 179067139542257L, 16653356229883L, 211404317299930L, 175252471000432L, 244830764776462L, 237265899029751L, 281319344844141L, 238829455803739L, 21290726548381L, 88000072834841L, 193759176958310L, 20012302379216L, 27190136964887L, 78900896054589L, 219816396125233L, 110699126831849L, 161482259356484L, 144457244632057L, 198160909402725L, 253721797102660L, 279302866530736L, 192410543922212L, 11218322231858L, 17541284003923L, 156690146644299L, 121330163309541L, 3671193175734L, 9250150385466L, 196328060873376L, 10757468401714L, 88465280874432L, 194994643376010L, 61597479226725L, 69101261929025L, 71223683200146L, 178345663789088L, 133189164758679L, 164410709919685L, 201761420865841L, 60260481219460L, 183264963000297L, 202325296238618L, 87716662722125L, 146480758535320L, 142523989702642L, 242291491000558L, 180114093637917L, 53085800204343L, 152110417417316L, 27133773988190L, 134745822980304L, 149379341283166L, 241095734612846L, 16830834823657L, 260230731049183L, 23211729036340L, 52866208114315L, 84207415307008L, 60859818215131L, 96943917712828L, 96329493285211L, 237698666025618L, 277171428360558L, 91373111211284L, 113428126320777L, 281168849817313L, 42486773920696L, 155597460914790L, 99164272089933L, 181657937976394L, 75254827793938L, 67326094361744L, 13316662845461L, 250337151322531L, 279069862064127L, 60762451710736L, 122187342070098L, 174772216804505L, 123805163251925L, 76151804676762L, 90614515035968L, 130116110447300L, 106766316297513L, 158495118701144L, 205679536202029L, 142007650708097L, 269501880348614L, 160545965345443L, 123290985668381L, 45932379397544L, 3641375019831L, 197672109182243L, 158227011077008L, 258919734015684L, 266199545640921L, 41571634585760L, 172345496301430L, 126553068098471L, 4224393100615L, 60289708737418L, 181691606194088L, 70893228503424L, 47014513659328L, 188349797354877L, 132707112571709L, 164519600957635L, 177352386455180L, 8360876829120L, 139169136420759L, 179650245030744L, 35254326675695L, 264223935351325L, 85296120219848L, 156077340315555L, 225455853754079L, 209897544286902L, 130091218394041L, 188870449097871L, 150045288508821L, 61940926061802L, 192328101720685L, 277006204874850L, 46110669320698L, 232312886114729L, 149980622370466L, 39120230666196L, 129421826246099L, 167274212750153L, 268176077639768L, 159180916958483L, 235516784529575L, 91724403321944L, 150265916690971L, 42614789745277L, 219666263753417L, 257301138351546L, 199519017184279L, 118133778129785L, 196951023789763L, 225694677921011L, 249085615404194L, 252974402535519L, 219834862648315L, 29850669550580L, 148788900233115L, 136543297132109L, 273507651641938L, 51809355885276L, 261647117325124L, 244352853824752L, 239821694063426L, 252975696837012L, 47248003949403L, 26227449862997L, 150514018454345L, 194887392814697L, 31985773992836L, 260204797215738L, 21896099270091L, 26114149235563L, 164034203081731L, 271623352583181L, 278352946040794L, 63562906190120L, 239442715660700L, 70714124287809L, 219530397907519L, 8974536169264L, 246163650250114L, 19543988652194L, 199463837712023L, 36062650963550L, 164772211309674L, 189481615493495L, 226555229404315L, 5662464730635L, 154731327364274L, 278416800691179L, 126532070781875L, 214083240773397L, 167238615112032L, 10694372374540L, 177935790929735L, 134798622313137L, 104817274836949L, 120291884738042L, 154911206879817L, 17939334750029L, 67106295457060L, 2185972697642L, 77018914033009L, 180277600654849L, 176257720033491L, 210969128814624L, 134392381066199L, 104518769074187L, 36939187140147L, 78738019063484L, 11065199838886L, 269932884405176L, 239566540122657L, 274593127673734L, 167040725441962L, 225998335217972L, 240399397123293L, 272574638005570L, 206765234983404L, 84093893900080L, 205465315776393L, 195813160877542L, 48338467061310L, 22095979908185L, 21810256908270L, 168246813269359L, 237328823061067L, 154503317330145L, 109443488001237L, 56570832759229L, 155292191230707L, 265445043541687L, 250654303212658L, 235943714064142L, 128072321518909L, 104374460866625L, 227443884683438L, 41442092094647L, 171806931812125L, 34849459262695L, 72264866229158L, 18257696810797L, 207818167188007L, 72090660515514L, 66299462454277L, 100007322146312L, 216200819041008L, 182843931391863L, 229058868250780L, 29073647226682L, 29229490518591L, 266863882058621L, 136816447020944L, 171896764878422L, 270461571955696L, 128722678994895L, 259554723501286L, 146195503064316L, 206552802862395L, 85839512486005L, 102988461353863L, 230141973671141L, 209217906285589L, 101651789545848L, 188079825056323L, 205318080409943L, 106385663380305L, 38779130822262L, 243441972759812L, 64241379158336L, 227822492445620L, 195367000637495L, 274467608958116L, 37200001296025L, 276229011779785L, 94321573113114L, 153418729420056L, 64517653725813L, 101979943300711L, 211168623243823L, 209230814143914L, 68854436402757L, 191604777507737L, 249716994017440L, 55213388294918L, 79180322761008L, 171753806281774L, 38918993595227L, 6756688477606L, 134067687178460L, 70103620916267L, 91516711815008L, 225635623486140L, 251220497002272L, 70162456679207L, 2552339147391L, 11691588850715L, 236169305005605L, 126046781741551L, 50568815074416L, 138504704002240L, 89463081205238L, 107594778544041L, 203819668285208L, 208341049893328L, 113380911639888L, 73741599151256L, 117015907514620L, 208314052746336L, 187402496056061L, 257624636964367L, 85467387738770L, 159840142094628L, 60236242221542L, 58657357477327L, 149308842886465L, 270562108167189L, 246723310734642L, 57618427950662L, 127881728662626L, 216101716322747L, 241354449441574L, 217938050644621L, 91376940415702L, 164254362612725L, 75206393709376L, 8285098090957L, 275526860441907L, 242881748252383L, 71631093947423L, 273431075805277L, 55802207005717L, 245656250022276L, 110625406664023L, 247491055213705L, 168829317441039L, 209179733613236L, 176527601101162L, 236142143815109L, 170849375319165L, 37332736861821L, 46865221519520L, 281059281441106L, 281009345069028L, 98617576290025L, 212325204459021L, 108136627772779L, 135235614690833L, 184819766675454L, 51170597960964L, 162035610910482L, 20675817273337L, 212703225663612L, 158378609520563L, 62494305839649L, 77336880999085L, 104238406697708L, 160973794135522L, 159518497619824L, 5144199246060L, 25647022056436L, 160408102445339L, 171417956675226L, 153881816061690L, 184523456059597L, 8087232885275L, 138623349666374L, 122154411572919L, 218749972598723L, 224429355550011L, 98422624225408L, 64959830981462L, 8324444002647L, 163367015010170L, 6747433959671L, 194124889832936L, 182496053792914L, 164470999337156L, 81332531269399L, 27343208993143L, 42807304953952L, 179701426490155L, 269236143004139L, 72838054096853L, 196458682285731L, 84612773560314L, 190895334434577L, 53995060253455L, 215818263746732L, 130634208735842L, 241753292084141L, 205868267534213L, 45905267096634L, 69309429320457L, 37764800639289L, 222470506920118L, 213515963072186L, 263021973958667L, 23557648806923L, 55066678041465L, 130681236965708L, 118298702317137L, 40983166945876L, 210231391840567L, 110308779996660L, 61615446374318L, 241623350331260L, 168327639850120L, 161933275524005L, 724036813867L, 272715892240537L, 92108086753835L, 126131267157999L, 134818907435786L, 274095074282413L, 218401776329671L, 181671374310356L, 251522318703359L, 183918210861977L, 104046611489916L, 176784565037628L, 91343974219560L, 212558381630259L, 142054718052790L, 15703304210096L, 244508353978438L, 200865305793210L, 273225977098835L, 51216336232514L, 71268428436287L, 20197623213716L, 239519227988118L, 99322758848698L, 278003140535507L, 52713216442053L, 205065976513866L, 127626182236186L, 12842831919618L, 122025279068206L, 273487138248623L, 242858252518670L, 126062463135857L, 273385837505664L, 110385515290282L, 64735233423078L, 181026591494336L, 87359583069143L, 225138387434857L, 50538750263469L, 138200393442637L, 175793280101515L, 32003572046837L, 55692747303825L, 214752531817577L, 262634834251896L, 212700583859301L, 243909339974213L, 233451815453680L, 197069857462472L, 197030219547484L, 222602497967038L, 151522526417610L, 43299529418403L, 229273741916128L, 43163769889862L, 29724000921341L, 380189493403L, 26631803155449L, 24642661145662L, 126457993540748L, 20267406618051L, 188644431828690L, 175715113926580L, 200427390489718L, 217472982422838L, 50449703091367L, 86403252726805L, 206924243356352L, 60787165735426L, 45834037007845L, 245165337459677L, 56693930220470L, 39403556775721L, 92883978370481L, 238731261115839L, 223599247330631L, 52565343547985L, 44920576729564L, 180848938446506L, 71712590501305L, 15811976832375L, 225648859583747L, 112422252695137L, 196110673504167L, 212940050714523L, 161286684108475L, 108072556953903L, 54051241286473L, 134282651770587L, 124974124149085L, 80400991785776L, 236096060926221L, 98119323664630L, 49655868876988L, 180161156256127L, 76393946470917L, 84064235375220L, 208989811061169L, 63350565419197L, 148139801764381L, 122297850411559L, 120457163593959L, 272542969813511L, 126186001194734L, 44277760274037L, 96591936594277L, 109362209619548L, 170125402432597L, 81725999273767L, 25755266665544L, 98879256706558L, 28621993737598L, 2286888313705L, 9195306378231L, 11715571086059L, 171113470582261L, 218685092968199L, 188755752472750L, 113219198837224L, 266913291654520L, 217639324954544L, 10136181658743L, 236447733448957L, 251681243287560L, 93722047802774L, 60048593216301L, 78406035713680L, 80537154976958L, 145152356517335L, 215495393373402L, 78763163056946L, 97938420095409L, 153066261428247L, 1824212948698L, 70063793219567L, 34804478240523L, 278171894497244L, 56217596552318L, 153192717694619L, 59687599344148L, 231852169365309L, 228757576300091L, 171568443837567L, 268735709301685L, 204207247293876L, 43918519458067L, 264040390708993L, 244660927297072L, 187273994931015L, 149398115284062L, 101532670522188L, 109051370163109L, 149762446130641L, 245544577092118L, 135950411262665L, 140692378483091L, 242302683747071L, 275063808447084L, 69286223136429L, 197630459915402L, 190372344902850L, 221037760371803L, 42941291656416L, 61097901717673L, 229469068485627L, 16868768837056L, 37230981624582L, 131217824578648L, 40185892473662L, 227707274111520L, 129151785826672L, 261668982612600L, 157984365267316L, 243379790213048L, 266661504945253L, 162802120314535L, 121506186606622L, 95137214130985L, 257465722698431L, 44532095531734L, 70083538247306L, 79764245636508L, 111730629829741L, 216638138184961L, 114618557980195L, 183025685975946L, 97684331148960L, 168251046689502L, 203799016029593L, 56665415084291L, 193943542428867L, 104469051891992L, 75080933971325L, 266715414838113L, 201511702213116L, 114376047320666L, 236530734010087L, 245951329738105L, 276601592967690L, 255607119418269L, 88586714772133L, 27419926375078L, 129788890766360L, 11971613201738L, 234163513911556L, 53265057992981L, 150810117715349L, 178247030004525L, 33272085770454L, 214020819482743L, 16187920120371L, 42855888483653L, 172798095830835L, 231612203852019L, 146680083804214L, 136615816956032L, 98305082044525L, 55823982252583L, 131831529433632L, 21539878317074L, 184738276876101L, 168199680293703L, 34470721358337L, 242442111998731L, 125560720465210L, 146249066534719L, 69202651616642L, 11027359980606L, 49621904618759L, 71791326681736L, 75803125210584L, 116973357483233L, 119469827615945L, 155824004700640L, 202676156726533L, 256576074731737L, 252117501712843L, 79666027216583L, 155490074510195L, 140859190516390L, 258442429430858L, 3106956346148L, 256440573828748L, 156657943043022L, 9249995868599L, 7869588763415L, 270232916739331L, 44110089979546L, 197265965643242L, 38169356666818L, 9806704632651L, 200595662313904L, 17763145707764L, 236200800983531L, 271708408397881L, 62856271989550L, 76461149158189L, 144408024485055L, 136224563440281L, 39991368208285L, 42385645247778L, 82538937468962L, 218269639208322L, 178493691085029L, 201599998770447L, 243814678661942L, 200661825519619L, 190445845802847L, 153794730887364L, 201894208966418L, 211304808848629L, 5943296977615L, 172542739081959L, 58970752756970L, 245595699763790L, 15832492493120L, 204867575061452L, 100121317740635L, 264246864329091L, 39568161963569L, 40165719401714L, 60765087623130L, 4019374594295L, 33605341292023L, 211937601103685L, 56614215565336L, 274048005343207L, 89064216022880L, 33584871646010L, 163884290647879L, 153390860733565L, 111269682489397L, 87726787172815L, 102723974588803L, 109434063855650L, 162680222410767L, 143059224383772L, 141150397635004L, 120573481485754L, 119688085670914L, 83504985734703L, 106416897663655L, 185598558142765L, 228947348048188L, 120427049274672L, 41579781198705L, 50098772420839L, 118516996864895L, 230718733611477L, 92307847204570L, 146004314296354L, 53856217283125L, 218398860084263L, 195523921333204L, 93766005060603L, 179982905908600L, 25304191225486L, 227426083799081L, 99828405274845L, 127612590694331L, 80501364658889L, 226204711354544L, 47233371240199L, 159081009257597L, 90233275933671L, 124320142996994L, 262039715959698L, 36323314396167L, 234902964952447L, 271136509419462L, 123878021897287L, 167464166012446L, 235528284397626L, 138049710089815L, 181920439016187L, 266710948924844L, 191872727378628L, 128046527797425L, 29453823299882L, 119647033878958L, 251768203349020L, 105512946165268L, 62366505758829L, 28654175985322L, 243480368191150L, 168047927435336L, 26890303516205L, 243949006272551L, 7758489897529L, 111798812261434L, 258566549590828L, 272784256578337L, 269793610283479L, 191148148387216L, 279189200095426L, 271798953643885L, 32050703426478L, 211906105185918L, 64721465965774L, 38781171671313L, 153832353853516L, 115799700205028L, 275402922102563L, 222380525479105L, 12620237016430L, 205834273978124L, 177589882325079L, 228167638585815L, 123528306694410L, 238405196474015L, 201734286228352L, 193336924011610L, 49153266068920L, 198510747732771L, 73134085723287L, 206195172165113L, 249818641279140L, 261489633175898L, 102323423958185L, 10843050265925L, 31331373558494L, 166606242131538L, 55694623862173L, 138079300594357L, 195778468274980L, 163901979872588L, 29749842790105L, 42489856559814L, 6951213448284L, 8003355811377L, 126040547692156L, 42944369113597L, 75648687261855L, 34363151559038L, 96728697880934L, 198742929739292L, 151255407170829L, 97997658468856L, 170270805523938L, 165797797064495L, 205103572519058L, 74132854654966L, 135456122887617L, 13358911566242L, 148901786552540L, 12556380700648L, 76123014847921L, 254516867765658L, 46213314780052L, 35682383442172L, 45786456411368L, 266797179034711L, 250596567818436L, 132156014290634L, 6360748136914L, 108467808637L, 129971845034708L, 251811255715526L, 37122993465546L, 41882381619317L, 36962407013009L, 144852970993576L, 195700310117624L, 13916296191981L, 153499221919724L, 124355210263355L, 1057974250967L, 116461594705081L, 276499305603117L, 276511055158678L, 68108652534434L, 235526491763656L, 224409507962590L, 181649729119454L, 148273730617070L, 86105890657305L, 280983799364572L, 119464495067816L, 11662613955355L, 87357997469581L};

        Set<Long> aList1 = Arrays.stream(list1).boxed().collect(Collectors.toSet());
        Set<Long> aList2 = Arrays.stream(list2).boxed().collect(Collectors.toSet());

        aList1.retainAll(aList2);
        System.out.print("List1 size: " + list1.length + "\nList2 size: " + list2.length + "\nCommon seed: " + aList1 + "\n");
    }
}
