package neil;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import neil.dungeons.VersionCrack;

import java.util.Scanner;

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
            System.out.print("Using the following data - Coords: [" + dungeon1x + " " + dungeon1y + " " + dungeon1z + "; Sequence: [" + dungeon1Sequence + "]");
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
            System.out.print("Using the following data\nCoords 1: [" + dungeon1x + " " + dungeon1y + " " + dungeon1z + "; Sequence: [" + dungeon1Sequence + "]\nCoords 2: [" + dungeon2x + " " + dungeon2y + " " + dungeon2z + "; Sequence: [" + dungeon2Sequence + "]");
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
            case 7: new VersionCrack(v1_8, 61, 59, 668, "111111110111111111001101101110111111110111111001111111001111111", 137, 27, -147, "111110101111111110110110111110011111111111111111111111101111011").getSeedBranched();
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
        System.out.print("Please provide the biome the spawner block is in.\n1     SWAMP\n2     SWAMP_HILLS\n3     DESERT\n4     OTHER\nType the corresponding number on the left: ");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        Biome biome = Biomes.FOREST;
        while (biome == Biomes.FOREST) {
            switch (input) {
                case "1": biome = Biomes.SWAMP;
                    break;
                case "2": biome = Biomes.SWAMP_HILLS;
                    break;
                case "3": biome = Biomes.DESERT;
                    break;
                case "4": biome = Biomes.PLAINS;
                    break;
                default: System.out.print("Please input a number 1-4: ");
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
}
