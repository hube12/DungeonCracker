package neil;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import neil.dungeons.Result;
import neil.dungeons.VersionCrack;
import neil.gui.MCVersion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static neil.gui.MCVersion.*;

public class Main {
    public static final boolean PARTIAL_OVERRIDE = true;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Boolean validVersion = false;
        Boolean enoughSpaners = false;
        int version = 0;
        Biome dungeonBiome = Biomes.DESERT;
        int dungeon1x, dungeon1y, dungeon1z;
        String dungeon1Sequence = "";
        int dungeon2x = 0;
        int dungeon2y = 0;
        int dungeon2z = 0;
        String dungeon2Sequence = "";
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222221110022011112211111221111022101112211001221111122222222").run();
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222222211111012211011112210111112210111102211110102222222222").run();
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222221111122100112211101220111122111112211110220011122222222").run();
//        Result r=new VersionCrack(MCVersion.vLegacy, 1067 ,29, -306,  "222222222201101112201111012211111012211110112210111112222222222").run();
//        String stringPattern="0222222211111221111122111112211111221111122222222";
//        Integer[] pattern = stringPattern.chars().mapToObj(c -> c == '0' ? 0 : c == '1' ? 1 : 2).toArray(Integer[]::new);
//        ReverserDevice device = new ReverserDevice();
//        for (Integer integer : pattern) {
//            if (integer == 0) {
//                device.addCall(NextInt.withValue(4, 0));
//            } else if (integer == 1) {
//                device.addCall(FilteredSkip.filter(r -> r.nextInt(4) != 0));
//            } else {
//                device.addCall(NextInt.consume(4, 1));
//            }
//        }
//        device.streamSeeds().parallel().forEach(System.out::println);
		/*Result r = new VersionCrack(MCVersion.v1_16, -121, 20, 64, "100100101011100010111111101011000110101110111011111101101111110").run();
		System.out.println(r);*/
        //

        //Ask the user for the version they want to use
        System.out.print("Please provide the version number the dungeon was created in as listed below:\n16    Release 1.16.x and 1.17.x\n15    Release 1.15.x\n13    Releases 1.13.x and 1.14.x\n8     Releases 1.8.x through 1.12.x\n7     Releases 1.7.x and earlier\n0     I don't know\nType the corresponding number on the left: ");
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

        if(version >= 5){
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
            case 0: new VersionCrack(vUnknown, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence).runTest(); break;
            //Legacy(1.0-1.7)
            case 1: new VersionCrack(vLegacy, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence).runTest(); break;
            //1.8-1.12
            case 2: new VersionCrack(v1_8, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence).runTest(); break;
            //1.13-1.14
            case 3: new VersionCrack(v1_13, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence).runTest(); break;
            //1.15
            case 4: new VersionCrack(v1_15, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence).runTest(); break;
            //1.16-1.17
            case 5: new VersionCrack(v1_16, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeonBiome).runTest(); break;
            //test cases (testL, test8, test13, test15, test16)
            case 6: new VersionCrack(vLegacy, -296, 19, 261, "010111111111011110001110011110120111111111101110011101111111011", -178, 14, 219, "010111110110110100101101100111101111111101111110111101111011110").runTest(); break;
            case 7: new VersionCrack(v1_8, 544, 49, -229, "0110110110011001111111011111001110111011111111101", 774, 54, -129, "100110111101111011100101101110101101111111111100010111110111110101011100101111100").runTest(); break;
            case 8: new VersionCrack(v1_13, 280, 29, 674, "111011111101101111111111101111101111110100111110111101111111110111111101111110011").runTest(); break;
            case 9: new VersionCrack(v1_15, 161, 16, -716, "1111111101101110111110111011101111111101101100101").runTest(); break;
            case 10: new VersionCrack(v1_16, 25, 54, 88, "0111010110011110110100010101110110101110111111111", Biomes.PLAINS).runTest(); break;
            default: System.out.println("Something went wrong..");
        }
    }

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

    public static Biome getDungeonBiome(){
        System.out.print("Please provide the biome the spawner block is in.\n1     SWAMP\n2     SWAMP_HILLS\n3     DESERT\n4     OTHER\nType the corresponding number on the left: ");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        Biome biome = Biomes.FOREST;
        while (biome == Biomes.FOREST) {
            switch(input){
                case "1": biome = Biomes.SWAMP; break;
                case "2": biome = Biomes.SWAMP_HILLS; break;
                case "3": biome = Biomes.DESERT; break;
                case "4": biome = Biomes.PLAINS; break;
                default: System.out.print("Please input a number 1-4: "); input = userInput.nextLine();
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

    /*public static void test(MCVersion version) {
        // 1.17                       Dungeon Seed: [137229083672372]; Coords: [25 54 88]; World Seed: [1488979889728021444]; Biome: Plains(?)
        // 1.16                       Dungeon Seed: [66991252199345]; Coords: [-6799 61 -1473]; World Seed: [-720350949281663006]; Biome: Desert
        // 1.15                       Dungeon Seed: [54954658892082]; Coords: [161 16 -716]; World Seed: [7298916735143357077]
        // 1.14, 1.13                 Dungeon Seed: [82836126371671,19957636759997]; Coords: [693 30 -74, 280 29 674]; World Seed: [1724951870366438529]
        // 1.12, 1.11, 1.10, 1.9, 1.8 Dungeon Seed: [246636189820814,269259332384656]; Coords: [-296 19 26, -178 14 219];  World Seed: [-1700538326672817507]
        // 1.7 and below              Dungeon Seed: [41813458706666,190214760258714]; Coords: [544 49 -229, 774 54 -129]; World Seed: [-6812128122949736898]
        switch (version) {
            case v1_16: System.out.println(new VersionCrack(version, 25, 54, 88, "0111010110011110110100010101110110101110111111111", Biomes.DESERT).run().toString()); break;
            //case v1_16: System.out.println(new VersionCrack(version, -6799, 61, -1473, "011010011111011111011110011100111011110111011110001011110111011111000011111101011", Biomes.DESERT).run().toString()); break;
            case v1_15: System.out.println(new VersionCrack(version, 161, 16, -716, "1111111101101110111110111011101111111101101100101").run().toString()); break;
            case v1_13: System.out.println(new VersionCrack(version, 280, 29, 674, "111011111101101111111111101111101111110100111110111101111111110111111101111110011").run().toString()); break;
            //case v1_13: System.out.println(new VersionCrack(version, 693, 30, -74, "111011100101011111011011001111110110111011101111111011101111011011111110111111110").run().toString()); break;
            case v1_8: System.out.println(new VersionCrack(version, 544, 49, -229, "0110110110011001111111011111001110111011111111101", 774, 54, -129, "100110111101111011100101101110101101111111111100010111110111110101011100101111100").run().toString()); break;
            case vLegacy: System.out.println(new VersionCrack(version, -296, 19, 261, "010111111111011110001110011110120111111111101110011101111111011", -178, 14, 219, "010111110110110100101101100111101111111101111110111101111011110").run().toString()); break;
        }
    }*/
}
