package kinomora;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.mcutils.version.MCVersion;
import kinomora.dungeon.DecoratorSeedProcessor;
import kinomora.dungeon.DungeonDataProcessor;
import kinomora.dungeon.StructureSeedProcessor;
import kinomora.gui.DungeonCrackerGUI;

import javax.swing.*;
import java.util.*;


public class Main {
    // starting from 1.7.2 all dungeon use 256 height
    public static final MCVersion LEGACY_VERSION=MCVersion.v1_7_2;

    public static void main(String[] args) throws Exception {
        //Meta data
        DungeonCrackerGUI GUI = new DungeonCrackerGUI();

        //Program data
        Scanner userInput = new Scanner(System.in);
        String input;
        boolean validInput = false;
        boolean dungeonSeedMode = false;
        boolean doubleSpawnerMode = false;
        MCVersion version = null;

        //Dungeon data
        long dungeon1Seed = 0L;
        int dungeon1x = 0;
        int dungeon1y = 0;
        int dungeon1z = 0;
        int dungeon1fsx = 0;
        int dungeon1fsz = 0;
        String dungeon1Sequence = "";
        Biome dungeon1Biome = Biomes.THE_VOID;

        long dungeon2Seed = 0L;
        int dungeon2x = 0;
        int dungeon2y = 0;
        int dungeon2z = 0;
        int dungeon2fsx = 0;
        int dungeon2fsz = 0;
        String dungeon2Sequence = "";
        Biome dungeon2Biome = Biomes.THE_VOID;


        //Check for command line args
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));


        //Intro
        System.out.println("===================================================================================");
        System.out.println("Minecraft Dungeon Cracker " + getAppVersion() + " | By Kinomora");
        System.out.println("Available for free at github.com/Kinomora/DungeonCracker");
        System.out.println("===================================================================================");

        //Testing data flag
        if (argsList.isEmpty()) {

            //GUI.mainGUI();
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            //Set up the GUI window
            GUI.pack();
            GUI.setSize(700, 475);
            GUI.setResizable(false);
            GUI.setVisible(true);
            GUI.setLocationRelativeTo(null);

            JMenuBar menuBar = new JMenuBar();

        } else {
            if (argsList.get(0).contains("nogui")) {
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
                        if (version==null){
                            continue;
                        }
                        validInput = true;
                    }
                }
                validInput = false;
                System.out.println("===================================================================================");

                //Ask the user if they are using 1 or 2 dungeons
                System.out.print("Do you have 1 dungeon or 2: ");
                while (!validInput) {
                    input = userInput.nextLine();
                    if (getIntFromInputString(input) == 1) {
                        //System.out.println("Using 1 dungeon..\n");
                        //doubleSpawnerMode = false;
                        validInput = true;
                    } else if (getIntFromInputString(input) == 2) {
                        //System.out.println("Using 2 dungeons..\n");
                        doubleSpawnerMode = true;
                        validInput = true;
                    } else {
                        System.out.println("Please enter a valid system mode by typing 1 or 2.\nApplication mode: ");
                    }
                }
                validInput = false;
                System.out.println("===================================================================================");

                if (version.isOlderThan(MCVersion.v1_13) && !doubleSpawnerMode) {
                    //Ask the user if they want to input dungeon data or seeds
                    System.out.println("Since you only have 1 dungeon and have selected a version older than 1.13, Dungeon Data Mode has automatically been selected.");
                    //System.out.println("Entering Dungeon Data Mode..\n");
                    dungeonSeedMode = false;
                } else {
                    //Ask the user if they want to input dungeon data or seeds
                    System.out.print("Do you have dungeon data or do you already have a dungeon seed?\n1     Dungeon Data Mode (Co-ords and Floor Pattern; Typical)\n2     Dungeon Seed Mode (Structure seeds; Uncommon)\nType the corresponding number on the left: ");
                    while (!validInput) {
                        input = userInput.nextLine();
                        if (getIntFromInputString(input) == 1) {
                            //System.out.println("Entering Dungeon Data Mode..\n");
                            //dungeonSeedMode = false;
                            validInput = true;
                        } else if (getIntFromInputString(input) == 2) {
                            //System.out.println("Entering Dungeon Seed Mode..\n");
                            dungeonSeedMode = true;
                            validInput = true;
                        } else {
                            System.out.println("Please enter a valid system mode by typing 1 or 2.\nApplication mode: ");
                        }
                    }
                }
                validInput = false;
                System.out.println("===================================================================================");

                //If we are running in Dungeon Data mode we will need to gather more input data
                if (!dungeonSeedMode) {
                    if (!doubleSpawnerMode) {
                        System.out.println("Please input your dungeon data:");
                        dungeon1x = getDungeonX(0);
                        dungeon1y = getDungeonY(0);
                        dungeon1z = getDungeonZ(0);
                        dungeon1Sequence = getSequence();
                        dungeon1fsx=getDungeonFloorSizeX(0);
                        dungeon1fsz=getDungeonFloorSizeZ(0);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                    } else {
                        System.out.println("Please provide data for the first dungeon:");
                        dungeon1x = getDungeonX(1);
                        dungeon1y = getDungeonY(1);
                        dungeon1z = getDungeonZ(1);
                        dungeon1fsx=getDungeonFloorSizeX(1);
                        dungeon1fsz=getDungeonFloorSizeZ(1);
                        dungeon1Sequence = getSequence();
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                        System.out.println("Now enter the data for the second dungeon:");
                        dungeon2x = getDungeonX(2);
                        dungeon2y = getDungeonY(2);
                        dungeon2z = getDungeonZ(2);
                        dungeon2fsx=getDungeonFloorSizeX(2);
                        dungeon2fsz=getDungeonFloorSizeZ(2);
                        dungeon2Sequence = getSequence();
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon2Biome = getDungeonBiome();
                        }
                    }
                } else {
                    if (!doubleSpawnerMode) {
                        System.out.println("Please input your dungeon info:");
                        dungeon1x = getDungeonX(0);
                        dungeon1z = getDungeonZ(0);
                        dungeon1Seed = getDungeonSeed(0);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                    } else {
                        System.out.println("Please provide info for the first dungeon:");
                        dungeon1x = getDungeonX(1);
                        dungeon1z = getDungeonZ(1);
                        dungeon1Seed = getDungeonSeed(1);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                        System.out.println("Now enter the info for the second dungeon:");
                        dungeon2x = getDungeonX(2);
                        dungeon2z = getDungeonZ(2);
                        dungeon2Seed = getDungeonSeed(2);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon2Biome = getDungeonBiome();
                        }
                    }
                }
                System.out.println("===================================================================================");
                System.out.println("Process data... Version: " + version + " | Multi-Dungeon: " + doubleSpawnerMode + " | Dungeon Seed Mode: " + dungeonSeedMode);
                System.out.println("===================================================================================");

                if (!dungeonSeedMode) {
                    if (!doubleSpawnerMode) {
                        //Dungeon Data mode with 1 dungeon
                        if (version.isBetween(LEGACY_VERSION, MCVersion.latest())) {
                            crackDungeonDataSingle(version, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence,dungeon1fsx,dungeon1fsz, dungeon1Biome);
                        } else {
                            System.out.println("Unknown supported version error..");
                            System.exit(0);
                        }
                    } else {
                        //Dungeon Data mode with 2 dungeons
                        if (version.isBetween(LEGACY_VERSION, MCVersion.latest())) {
                            crackDungeonDataDouble(version, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence,
                                    dungeon1fsx,dungeon1fsz, dungeon1Biome, dungeon2x, dungeon2y, dungeon2z,
                                    dungeon2Sequence,dungeon2fsx,dungeon2fsz, dungeon2Biome);
                        } else {
                            System.out.println("Unknown supported version error..");
                            System.exit(0);
                        }
                    }
                } else {
                    //Dungeon Seed mode with 1 dungeon
                    if (!doubleSpawnerMode) {
                        if (version.isNewerOrEqualTo(MCVersion.v1_13)) {
                            crackDungeonSeedSingle(version, dungeon1x, dungeon1z, dungeon1Biome, dungeon1Seed);
                        } else {
                            System.out.println("You can't run Dungeon Seed mode with only 1 dungeon on versions below 1.13!");
                            System.exit(0);
                        }
                    } else {
                        //Dungeon Seed mode with 2 dungeons
                        if (version.isBetween(LEGACY_VERSION, MCVersion.latest())) {
                            crackDungeonSeedDouble(version, dungeon1x, dungeon1z, dungeon1Biome, dungeon1Seed, dungeon2x, dungeon2z, dungeon2Biome, dungeon2Seed);
                        } else {
                            System.out.println("Unknown supported version error..");
                            System.exit(0);
                        }

                    }
                }

            }
        }
    }

    private static void crackDungeonDataSingle(MCVersion v, int x, int y, int z, String seq,int fsx,int fsz, Biome b) {
        Set<Long> DungeonSeeds = new DungeonDataProcessor(v, x, y, z, seq,fsx,fsz).dungeonDataToDecoratorSeed();
        System.out.print("\nYour dungeon seed is:\n" + DungeonSeeds + "\n");
        if (v.isNewerOrEqualTo(MCVersion.v1_13)) {
            Set<Long> WorldSeeds = new StructureSeedProcessor(new DecoratorSeedProcessor(v, x, z, b, DungeonSeeds).decoratorSeedsToStructureSeeds()).getWorldSeedsFromStructureSeeds();
            if (WorldSeeds.isEmpty()) {
                System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no dungeon seed found.");
            } else {
                System.out.println("If the data you entered was valid, your world will be one of these seeds. To narrow this down, use 2 dungeons:\n" + WorldSeeds + "\n");
            }
        }
    }

    public static Set<Long> getDungeonSeedsForGUI(MCVersion v, int x, int y, int z, String seq,int fsx,int fsz) {
        return new DungeonDataProcessor(v, x, y, z, seq,fsx,fsz).dungeonDataToDecoratorSeed();
    }

    public static Set<Long> getWorldSeedsForGUISingleDungeon(MCVersion v, int x, int y, int z, Biome b, Set<Long> dungeonSeeds) {
        return new StructureSeedProcessor(new DecoratorSeedProcessor(v, x, z, b, dungeonSeeds).decoratorSeedsToStructureSeeds()).getWorldSeedsFromStructureSeeds();
    }

    public static Set<Long> getWorldSeedsForGUIDoubleDungeon(MCVersion v, int x1, int y1, int z1, Biome b1, Set<Long> dungeon1Seeds, int x2, int y2, int z2, Biome b2, Set<Long> dungeon2Seeds) {
        Set<Long> StructureSeeds1 = new DecoratorSeedProcessor(v, x1, z1, b1, dungeon1Seeds).decoratorSeedsToStructureSeeds();
        Set<Long> StructureSeeds2 = new DecoratorSeedProcessor(v, x2, z2, b2, dungeon2Seeds).decoratorSeedsToStructureSeeds();

        StructureSeeds1.retainAll(StructureSeeds2);
        return new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
    }

    private static void crackDungeonDataDouble(MCVersion v, int x1, int y1, int z1, String seq1,int fsx1,int fsz1, Biome b1, int x2, int y2, int z2, String seq2,int fsx2,int fsz2, Biome b2) {
        Set<Long> DungeonSeeds1 = new DungeonDataProcessor(v, x1, y1, z1, seq1,fsx1,fsz1).dungeonDataToDecoratorSeed();
        Set<Long> DungeonSeeds2 = new DungeonDataProcessor(v, x2, y2, z2, seq2,fsx2,fsz2).dungeonDataToDecoratorSeed();
        Set<Long> StructureSeeds1 = new DecoratorSeedProcessor(v, x1, z1, b1, DungeonSeeds1).decoratorSeedsToStructureSeeds();
        Set<Long> StructureSeeds2 = new DecoratorSeedProcessor(v, x2, z2, b2, DungeonSeeds2).decoratorSeedsToStructureSeeds();

        StructureSeeds1.retainAll(StructureSeeds2);
        System.out.println("Your dungeon seeds: 1" + DungeonSeeds1 + ", 2" + DungeonSeeds2 + "\n");
        if (StructureSeeds1.isEmpty()) {
            System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no world seeds were found.");
        } else {
            System.out.println("If the data you entered was valid, your world seed is: \n" + new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds());
        }
    }

    public static Set<Long> crackDungeonDataDoubleReturn(MCVersion v, int x1, int y1, int z1, String seq1,int fsx1,int fsz1, Biome b1, int x2, int y2, int z2, String seq2,int fsx2,int fsz2, Biome b2) {
        Set<Long> DungeonSeeds1 = new DungeonDataProcessor(v, x1, y1, z1, seq1,fsx1,fsz1).dungeonDataToDecoratorSeed();
        Set<Long> DungeonSeeds2 = new DungeonDataProcessor(v, x2, y2, z2, seq2,fsx2,fsz2).dungeonDataToDecoratorSeed();
        Set<Long> StructureSeeds1 = new DecoratorSeedProcessor(v, x1, z1, b1, DungeonSeeds1).decoratorSeedsToStructureSeeds();
        Set<Long> StructureSeeds2 = new DecoratorSeedProcessor(v, x2, z2, b2, DungeonSeeds2).decoratorSeedsToStructureSeeds();

        StructureSeeds1.retainAll(StructureSeeds2);
        System.out.println("Your dungeon seeds: 1" + DungeonSeeds1 + ", 2" + DungeonSeeds2 + "\n");

        return new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
    }

    private static void crackDungeonSeedSingle(MCVersion v, int x, int z, Biome b, long dSeed) {
        Set<Long> WorldSeeds = new StructureSeedProcessor(new DecoratorSeedProcessor(v, x, z, b, Collections.singleton(dSeed)).decoratorSeedsToStructureSeeds()).getWorldSeedsFromStructureSeeds();

        if (WorldSeeds.isEmpty()) {
            System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no dungeon seed found.");
        } else {
            System.out.println("If the data you entered was valid, your world will be one of these seeds. To narrow this down, use 2 dungeons:\n" + WorldSeeds + "\n");
        }
    }

    private static void crackDungeonSeedDouble(MCVersion v, int x1, int z1, Biome b1, long dSeed1, int x2, int z2, Biome b2, long dSeed2) {
        Set<Long> StructureSeeds1 = new DecoratorSeedProcessor(v, x1, z1, b1, Collections.singleton(dSeed1)).decoratorSeedsToStructureSeeds();
        Set<Long> StructureSeeds2 = new DecoratorSeedProcessor(v, x2, z2, b2, Collections.singleton(dSeed2)).decoratorSeedsToStructureSeeds();

        StructureSeeds1.retainAll(StructureSeeds2);
        if (StructureSeeds1.isEmpty()) {
            System.out.println("Either the data you entered was invalid, or the dungeon was modified (either by a player or by internal RNG) as unfortunately no world seeds were found.");
        } else {
            System.out.println("If the data you entered was valid, your world seed is: \n" + new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds());
        }

    }


    public static MCVersion getMCVersionFromInt(int input) {
        switch (input) {
            case 17: return MCVersion.v1_17;
            case 16: return MCVersion.v1_16;
            case 15: return MCVersion.v1_15;
            case 14: return MCVersion.v1_14;
            case 13: return MCVersion.v1_13;
            case 12: return MCVersion.v1_12;
            case 11: return MCVersion.v1_11;
            case 10: return MCVersion.v1_10;
            case 9: return MCVersion.v1_9;
            case 8: return MCVersion.v1_8;
            case 7: return MCVersion.v1_7_2;
            case 6:return MCVersion.v1_6_1;
            case 5:return MCVersion.v1_5_1;
            case 4:return MCVersion.v1_4_2;
            case 3:return MCVersion.v1_3_1;
            case 2:return MCVersion.v1_2_1;
            case 1:return MCVersion.v1_1;
            case 0: return MCVersion.v1_0;
            default: return null;
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

    public static int getDungeonFloorSizeX(int type) {
        if (type == 0) {
            System.out.print("Spawner floor size X: ");
        } else if (type == 1) {
            System.out.print("Spawner 1 floor size X: ");
        } else {
            System.out.print("Spawner 2 floor size X: ");
        }
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        while (isNotInteger(input)) {
            if (type == 0) {
                System.out.print("Spawner floor size X: ");
            } else if (type == 1) {
                System.out.print("Spawner 1 floor size X: ");
            } else {
                System.out.print("Spawner 2 floor size X: ");
            }
            input = userInput.nextLine();
        }
        return getIntFromString(input);
    }


    public static int getDungeonFloorSizeZ(int type) {
        if (type == 0) {
            System.out.print("Spawner floor size Z: ");
        } else if (type == 1) {
            System.out.print("Spawner 1 floor size Z: ");
        } else {
            System.out.print("Spawner 2 floor size Z: ");
        }
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        while (isNotInteger(input)) {
            if (type == 0) {
                System.out.print("Spawner floor size Z: ");
            } else if (type == 1) {
                System.out.print("Spawner 1 floor size Z: ");
            } else {
                System.out.print("Spawner 2 floor size Z: ");
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
        System.out.print("Is the EXACT name of the biome that the spawner block is in called SWAMP, SWAMP_HILLS, or DESERT? Type \"yes\" or \"no\": ");
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
    private static void testVersioned(MCVersion v) {
        if (v==null){
            System.out.println("Please input a proper version");
            System.exit(1);
        }
        Set<Long> DungeonData;
        Set<Long> StructureSeeds;
        Set<Long> DungeonData1;
        Set<Long> StructureSeeds1;
        Set<Long> DungeonData2;
        Set<Long> StructureSeeds2;
        Set<Long> WorldSeeds;
        switch (v) {
            case v1_16: System.out.println("Running 1.16 test data..");
                DungeonData = new DungeonDataProcessor(MCVersion.v1_16, 25, 54, 88, "0111010110011110110100010101110110101110111111111",7,7).dungeonDataToDecoratorSeed();
                StructureSeeds = new DecoratorSeedProcessor(MCVersion.v1_16, 25, 88, Biomes.PLAINS, DungeonData).decoratorSeedsToStructureSeeds();
                WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
                System.out.println("Here is the 1.16 dungeon data: " + DungeonData + " | Expected data: [137229083672372]");
                System.out.println("Here is the 1.16 structure data: " + StructureSeeds + " | Expected data: [258737905361860]");
                System.out.println("Here are the 1.16 world seeds: " + WorldSeeds + " | Expected data: [1488979889728021444]");
                break;
            case v1_15: System.out.println("Running 1.15 test data..");
                DungeonData = new DungeonDataProcessor(MCVersion.v1_15, 161, 16, -716, "1111111101101110111110111011101111111101101100101",7,7).dungeonDataToDecoratorSeed();
                StructureSeeds = new DecoratorSeedProcessor(MCVersion.v1_15, 161, -716, Biomes.PLAINS, DungeonData).decoratorSeedsToStructureSeeds();
                WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
                System.out.println("1.15 dungeon data: " + DungeonData + " | Expected data: [54954658892082]");
                System.out.println("1.15 structure data: " + StructureSeeds + " | Expected data: [?]");
                System.out.println("1.15 world seeds: " + WorldSeeds + " | Expected data: [7298916735143357077]");
                break;
            case v1_13: System.out.println("Running 1.13 test data..");
                DungeonData1 = new DungeonDataProcessor(MCVersion.v1_13, 693, 30, -74, "111011100101011111011011001111110110111011101111111011101111011011111110111111110",9,9).dungeonDataToDecoratorSeed();
                StructureSeeds1 = new DecoratorSeedProcessor(MCVersion.v1_13, 693, -74, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
                DungeonData2 = new DungeonDataProcessor(MCVersion.v1_13, 280, 29, 674, "111011111101101111111111101111101111110100111110111101111111110111111101111110011",9,9).dungeonDataToDecoratorSeed();
                StructureSeeds2 = new DecoratorSeedProcessor(MCVersion.v1_13, 280, 674, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
                StructureSeeds1.retainAll(StructureSeeds2);
                WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
                System.out.println("1.13 dungeon1 seed: " + DungeonData1 + " | Expected data: [82836126371671]");
                System.out.println("1.13 dungeon2 seed: " + DungeonData2 + " | Expected data: [19957636759997]");
                System.out.println("1.13 world seeds: " + WorldSeeds + " | Expected data: [1724951870366438529]");
                break;
            case v1_8: System.out.println("Running 1.8 test data..");
                DungeonData1 = new DungeonDataProcessor(MCVersion.v1_8, 137, 27, -147, "111110101111111110110110111110011111111111111111111111101111011",9,9).dungeonDataToDecoratorSeed();
                ;
                StructureSeeds1 = new DecoratorSeedProcessor(MCVersion.v1_8, 137, -147, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
                DungeonData2 = new DungeonDataProcessor(MCVersion.v1_8, 61, 59, 668, "111111110111111111001101101110111111110111111001111111001111111",9,9).dungeonDataToDecoratorSeed();
                StructureSeeds2 = new DecoratorSeedProcessor(MCVersion.v1_8, 61, 668, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
                StructureSeeds1.retainAll(StructureSeeds2);
                WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
                System.out.println("1.8 dungeon1 seed: " + DungeonData1 + " | Expected data: [14581818956973]");
                System.out.println("1.8 dungeon2 seed: " + DungeonData2 + " | Expected data: [226023998267313]");
                System.out.println("1.8 world seeds: " + WorldSeeds + " | Expected data: [-1700538326672817507]");
                break;
            default: System.out.println("Running legacy test data..");

                DungeonData1 = new DungeonDataProcessor(v, 140, 81, -35, "001011101111111111001111111011011101110111111110000110110111111",9,9).dungeonDataToDecoratorSeed();
                StructureSeeds1 = new DecoratorSeedProcessor(v, 140, -35, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
                DungeonData2 = new DungeonDataProcessor(v, 171, 13, -34, "101111111111110111011101111100011111111100101011111011001111010",9,9).dungeonDataToDecoratorSeed();
                StructureSeeds2 = new DecoratorSeedProcessor(v, 171, -34, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
                StructureSeeds1.retainAll(StructureSeeds2);
                WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
                System.out.println("Legacy dungeon1 seed: " + DungeonData1 + " | Expected data: [240428811966007]");
                System.out.println("Legacy dungeon2 seed: " + DungeonData2 + " | Expected data: [82449899703950]");
                System.out.println("Legacy world seeds: " + WorldSeeds + " | Expected data: [3257840388504953787]");
                break;
        }
    }

    public static String getAppVersion() {
        return "v2.0.0_pre-release";
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
