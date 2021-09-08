package kinomora;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.mcutils.util.data.Pair;
import kaptainwutax.mcutils.version.MCVersion;
import kinomora.dungeon.DecoratorSeedProcessor;
import kinomora.dungeon.DungeonDataProcessor;
import kinomora.dungeon.StructureSeedProcessor;
import kinomora.gui.DungeonCrackerGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;


public class Main {
    public static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Main.class.getName());

    // starting from 1.7.2 all dungeon use 256 height
    public static final String APP_VERSION = "@VERSION@"; //using SemVer

    private static void registerLogger() throws IOException {
        Files.createDirectories(Paths.get("logs"));
        FileHandler ch = new FileHandler("logs" + File.separatorChar + "error%u%g.log", 1000000, 10);
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(ch);
        ch.setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                    new Date(lr.getMillis()),
                    lr.getLevel().getLocalizedName(),
                    lr.getMessage()
                );
            }
        });
        LOGGER.info(String.format("Dungeon Cracker started on %s",
            new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").
                format(new Date(System.currentTimeMillis()))));
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

        Map<String, String> systemProperties = runtimeBean.getSystemProperties();
        Set<String> keys = systemProperties.keySet();
        String username = "";
        if (keys.contains("user.name")) {
            username = systemProperties.get("user.name");
        }

        for (String key : keys) {
            LOGGER.info(String.format("[%s] = %s.", key, systemProperties.get(key).
                replace(username, "XANONX").
                replaceAll("Users\\\\.*?\\\\", "Users\\\\ANONYM\\\\").
                replace("\r\n", "CRLF").
                replace("\n", "LF")
            ));
        }
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(ch);
    }

    public enum LookType {
        DARK("Dark", FlatDarkLaf::new),
        LIGHT("Light", FlatLightLaf::new),
        INTELLIJ("Intellij", FlatIntelliJLaf::new),
        DARCULA("Darcula", FlatDarculaLaf::new);

        private final String name;
        private final Supplier<FlatLaf> supplier;

        LookType(String name, Supplier<FlatLaf> supplier) {
            this.name = name;
            this.supplier = supplier;
        }

        public void setLookAndFeel() throws UnsupportedLookAndFeelException {
            UIManager.setLookAndFeel(supplier.get());
            for (Window window : JFrame.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        }

        public String getName() {
            return name;
        }

        public boolean isDark() {
            return supplier.get().isDark();
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) throws Exception {
        // needs to be the first call !!
        registerLogger();

        if (Main.APP_VERSION.startsWith("@VER") && Main.APP_VERSION.endsWith("SION@")) {
            throw new UnsupportedOperationException("The version was not replaced manually or by gradle");
        }
        HashMap<String, Pair<Pair<String, String>, String>> updateInfo = Update.shouldUpdate();
        boolean noUpdate = Arrays.asList(args).contains("--no-update");
        boolean update = Arrays.asList(args).contains("--update");

        if (updateInfo != null && !noUpdate) {
            Update.updateApp(updateInfo, !update);
        }

        //Meta data
        DungeonCrackerGUI GUI = new DungeonCrackerGUI();

        //Program data
        Scanner userInput = new Scanner(System.in);
        String input;
        boolean dungeonSeedMode = false;
        boolean doubleSpawnerMode = false;
        MCVersion version;

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


        //If args list is empty we just go straight to the GUI version of the app
        if (!Arrays.asList(args).contains("nogui") && !Arrays.asList(args).contains("test")) {
            LookType.DARCULA.setLookAndFeel();
            GUI.pack();
            GUI.setSize(800, 550);
            GUI.setResizable(false);
            GUI.setVisible(true);
            GUI.setLocationRelativeTo(null);
        } else { //Test if the first argument contains the words nogui, is so we can enter text-only mode
            if ( Arrays.asList(args).contains("nogui")) {
                //Getting the version the dungeon was generated in
                System.out.print("Please provide the version number that the dungeon was created in. You can type things like \"1.16\" and \"a1.0.4\": ");
                version = MCVersion.fromString(userInput.nextLine());
                while (version == null) {
                    System.out.println("Please provide a valid version number like \"1.12.2\" or \"b1.0\"");
                    System.out.print("Version number: ");
                    version = MCVersion.fromString(userInput.nextLine());
                }
                System.out.println("===================================================================================");

                //Ask the user if they are using 1 or 2 dungeons
                System.out.print("Do you have 1 dungeon or 2: ");
                do {
                    input = userInput.nextLine().trim();
                    if (input.equals("2")) {
                        doubleSpawnerMode = true;
                    } else if (!input.equals("1")) {
                        input = null;
                        System.out.println("Please enter a valid dungeon mode by typing 1 or 2.\nApplication mode: ");
                    }
                } while (input == null);
                System.out.println("===================================================================================");

                //Decide if the user will be entering dungeon data mode (coords, biome, and sequence) or dungeon seed more (coords and dungeon seed)
                if (version.isOlderThan(MCVersion.v1_13) && !doubleSpawnerMode) {
                    System.out.println("Since you only have 1 dungeon and have selected a version older than 1.13, Dungeon Data Mode has automatically been selected.");
                    dungeonSeedMode = false;
                } else {
                    System.out.print("Do you have dungeon data or do you already have a dungeon seed?\n1     Dungeon Data Mode (Co-ords and Floor Pattern; Typical)\n2     Dungeon Seed Mode (Structure seeds; Uncommon)\nType the corresponding number on the left: ");
                    do {
                        input = userInput.nextLine();
                        if (input.equals("2")) {
                            dungeonSeedMode = true;
                        } else if (!input.equals("1")) {
                            input = null;
                            System.out.println("Please enter a valid cracking mode by typing 1 or 2.\nApplication mode: ");
                        }
                    } while (input == null);
                }
                System.out.println("===================================================================================");

                //If we are running in Dungeon Data mode we will need to gather more input data
                if (!dungeonSeedMode) {
                    if (!doubleSpawnerMode) {
                        System.out.println("Please input your dungeon data:");
                        dungeon1x = getDungeonInputGeneric("X", 0);
                        dungeon1y = getDungeonInputGeneric("Y", 0);
                        dungeon1z = getDungeonInputGeneric("Z", 0);
                        dungeon1Sequence = getSequence();
                        dungeon1fsx = getDungeonInputGeneric("Floor Size X", 0);
                        dungeon1fsz = getDungeonInputGeneric("Floor Size Z", 0);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                    } else {
                        System.out.println("Please provide data for the first dungeon:");
                        dungeon1x = getDungeonInputGeneric("X", 1);
                        dungeon1y = getDungeonInputGeneric("Y", 1);
                        dungeon1z = getDungeonInputGeneric("Z", 1);
                        dungeon1Sequence = getSequence();
                        dungeon1fsx = getDungeonInputGeneric("Floor Size X", 1);
                        dungeon1fsz = getDungeonInputGeneric("Floor Size Z", 1);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                        System.out.println("Now enter the data for the second dungeon:");
                        dungeon2x = getDungeonInputGeneric("X", 2);
                        dungeon2y = getDungeonInputGeneric("Y", 2);
                        dungeon2z = getDungeonInputGeneric("Z", 2);
                        dungeon2Sequence = getSequence();
                        dungeon2fsx = getDungeonInputGeneric("Floor Size X", 2);
                        dungeon2fsz = getDungeonInputGeneric("Floor Size Z", 2);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon2Biome = getDungeonBiome();
                        }
                    }
                } else {
                    if (!doubleSpawnerMode) {
                        System.out.println("Please input your dungeon info:");
                        dungeon1x = getDungeonInputGeneric("X", 0);
                        dungeon1z = getDungeonInputGeneric("Z", 0);
                        dungeon1Seed = getDungeonSeed(0);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                    } else {
                        System.out.println("Please provide data for the first dungeon:");
                        dungeon1x = getDungeonInputGeneric("X", 1);
                        dungeon1z = getDungeonInputGeneric("Z", 1);
                        dungeon1Seed = getDungeonSeed(1);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon1Biome = getDungeonBiome();
                        }
                        System.out.println("Now enter the data for the second dungeon:");
                        dungeon2x = getDungeonInputGeneric("X", 2);
                        dungeon2z = getDungeonInputGeneric("Z", 2);
                        dungeon2Seed = getDungeonSeed(2);
                        if (version.isNewerOrEqualTo(MCVersion.v1_16)) {
                            dungeon2Biome = getDungeonBiome();
                        }
                    }
                }
                System.out.println("===================================================================================");
                System.out.println("Processing data... Version: " + version + " | Multi-Dungeon: " + doubleSpawnerMode + " | Dungeon Seed Mode: " + dungeonSeedMode);
                System.out.println("===================================================================================");

                if (!dungeonSeedMode) {
                    if (!doubleSpawnerMode) {
                        //Dungeon Data mode with 1 dungeon
                        crackDungeonDataSingle(version, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon1fsx, dungeon1fsz, dungeon1Biome);
                    } else {
                        //Dungeon Data mode with 2 dungeons
                        crackDungeonDataDouble(version, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence,
                            dungeon1fsx, dungeon1fsz, dungeon1Biome, dungeon2x, dungeon2y, dungeon2z,
                            dungeon2Sequence, dungeon2fsx, dungeon2fsz, dungeon2Biome);
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
                        crackDungeonSeedDouble(version, dungeon1x, dungeon1z, dungeon1Biome, dungeon1Seed, dungeon2x, dungeon2z, dungeon2Biome, dungeon2Seed);
                    }
                }

                System.out.println("Dumping current values:\nDungeon 1:\n" + dungeon1x + "\n" + dungeon1y + "\n" + dungeon1z + "\n" + dungeon1fsx + "\n" + dungeon1fsz + "\n" + dungeon1Sequence + "\n" + dungeon1Biome + "\nDungeon 2:\n" + dungeon2x + "\n" + dungeon2y + "\n" + dungeon2z + "\n" + dungeon2fsx + "\n" + dungeon2fsz + "\n" + dungeon2Sequence + "\n" + dungeon2Biome);

            } else if (Arrays.asList(args).contains("test")) {
                //Test changes to the cracking code with this method. Supported versions are all major versions between v1.17 and v1.8, plus v1.0 for "Legacy" code.
                testVersioned(MCVersion.fromString(argsList.get(2)));
            } else {
                System.out.println("Invalid arguments entered, please use either no arguments for GUI mode or --nogui for text-only mode.");
                System.exit(1);
            }
        }
    }

    private static void crackDungeonDataSingle(MCVersion v, int x, int y, int z, String seq, int fsx, int fsz, Biome b) {
        Set<Long> DungeonSeeds = new DungeonDataProcessor(v, x, y, z, seq, fsx, fsz).dungeonDataToDecoratorSeed();
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

    private static void crackDungeonDataDouble(MCVersion v, int x1, int y1, int z1, String seq1, int fsx1, int fsz1, Biome b1, int x2, int y2, int z2, String seq2, int fsx2, int fsz2, Biome b2) {
        Set<Long> DungeonSeeds1 = new DungeonDataProcessor(v, x1, y1, z1, seq1, fsx1, fsz1).dungeonDataToDecoratorSeed();
        Set<Long> DungeonSeeds2 = new DungeonDataProcessor(v, x2, y2, z2, seq2, fsx2, fsz2).dungeonDataToDecoratorSeed();
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

    public static Set<Long> getDungeonSeedsForGUI(MCVersion v, int x, int y, int z, String seq, int fsx, int fsz) {
        return new DungeonDataProcessor(v, x, y, z, seq, fsx, fsz).dungeonDataToDecoratorSeed();
    }

    public static Set<Long> getWorldSeedsForGUISingleDungeon(MCVersion v, int x, int z, Biome b, Set<Long> dungeonSeeds) {
        return new StructureSeedProcessor(new DecoratorSeedProcessor(v, x, z, b, dungeonSeeds).decoratorSeedsToStructureSeeds()).getWorldSeedsFromStructureSeeds();
    }

    public static Set<Long> getWorldSeedsForGUIDoubleDungeon(MCVersion v, int x1, int z1, Biome b1, Set<Long> dungeon1Seeds, int x2, int z2, Biome b2, Set<Long> dungeon2Seeds) {
        Set<Long> StructureSeeds1 = new DecoratorSeedProcessor(v, x1, z1, b1, dungeon1Seeds).decoratorSeedsToStructureSeeds();
        Set<Long> StructureSeeds2 = new DecoratorSeedProcessor(v, x2, z2, b2, dungeon2Seeds).decoratorSeedsToStructureSeeds();

        StructureSeeds1.retainAll(StructureSeeds2);
        return new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
    }

    public static boolean isNotInteger(String input) {
        try {
            Integer.parseInt(input.trim());
        } catch (NumberFormatException | NullPointerException e) {
            System.out.print("Please input a number.\n");
            return true;
        }
        return false;
    }

    public static boolean isNotLong(String input) {
        try {
            Long.parseLong(input.trim());
        } catch (NumberFormatException | NullPointerException e) {
            System.out.print("Please input a dungeon seed.\n");
            return true;
        }
        return false;
    }

    public static int getDungeonInputGeneric(String name, int type) {
        String input;
        do {
            switch (type) {
                case 0:
                    System.out.print("Spawner " + name + ": ");
                    break;
                case 1:
                    System.out.print("Spawner 1 " + name + ": ");
                    break;
                default:
                    System.out.print("Spawner 2 " + name + ": ");
            }
            Scanner userInput = new Scanner(System.in);
            input = userInput.nextLine();
        } while (isNotInteger(input));
        return Integer.parseInt(input.trim());
    }

    public static Long getDungeonSeed(int type) {
        String input;
        do {
            switch (type) {
                case 0:
                    System.out.print("Dungeon Seed: ");
                    break;
                case 1:
                    System.out.print("Dungeon 1 Seed: ");
                    break;
                default:
                    System.out.print("Dungeon 2 Seed: ");
            }
            Scanner userInput = new Scanner(System.in);
            input = userInput.nextLine();
        } while (isNotLong(input));
        return Long.parseLong(input.trim());
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
                case "ys":
                case "yes":
                case "t":
                case "true":
                    biome = Biomes.DESERT;
                    break;
                case "0":
                case "n":
                case "no":
                case "f":
                case "false":
                    biome = Biomes.THE_VOID;
                    break;
                default:
                    System.out.print("Please type yes or no: ");
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
        if (v == null) {
            System.out.println("Please pass in a proper version to the test data.");
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
            case v1_17:
                System.out.println("Running 1.17 test data..");
                DungeonData = new DungeonDataProcessor(MCVersion.v1_17, 25, 54, 88, "0111010110011110110100010101110110101110111111111", 7, 7).dungeonDataToDecoratorSeed();
                StructureSeeds = new DecoratorSeedProcessor(MCVersion.v1_17, 25, 88, Biomes.PLAINS, DungeonData).decoratorSeedsToStructureSeeds();
                WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
                System.out.println("Here is the 1.16 dungeon data: " + DungeonData + " | Expected data: [137229083672372]");
                System.out.println("Here is the 1.16 structure data: " + StructureSeeds + " | Expected data: [258737905361860]");
                System.out.println("Here are the 1.16 world seeds: " + WorldSeeds + " | Expected data: [1488979889728021444]");
                break;
            case v1_16:
                System.out.println("Running 1.16 test data..");
                DungeonData = new DungeonDataProcessor(MCVersion.v1_16, -6799, 61, -1473, "011010011111011111011110011100111011110111011110001011110111011111000011111101011", 9, 9).dungeonDataToDecoratorSeed();
                StructureSeeds = new DecoratorSeedProcessor(MCVersion.v1_16, -6799 , -1473 , Biomes.DESERT, DungeonData).decoratorSeedsToStructureSeeds();
                WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
                System.out.println("Here is the 1.16 dungeon data: " + DungeonData + " | Expected data: [66991252199345]");
                System.out.println("Here is the 1.16 structure data: " + StructureSeeds + " | Expected data: [?]");
                System.out.println("Here are the 1.16 world seeds: " + WorldSeeds + " | Expected data: [-720350949281663006]");
                break;
            case v1_15:
                System.out.println("Running 1.15 test data..");
                DungeonData = new DungeonDataProcessor(MCVersion.v1_15, 161, 16, -716, "1111111101101110111110111011101111111101101100101", 7, 7).dungeonDataToDecoratorSeed();
                StructureSeeds = new DecoratorSeedProcessor(MCVersion.v1_15, 161, -716, Biomes.PLAINS, DungeonData).decoratorSeedsToStructureSeeds();
                WorldSeeds = new StructureSeedProcessor(StructureSeeds).getWorldSeedsFromStructureSeeds();
                System.out.println("1.15 dungeon data: " + DungeonData + " | Expected data: [54954658892082]");
                System.out.println("1.15 structure data: " + StructureSeeds + " | Expected data: [?]");
                System.out.println("1.15 world seeds: " + WorldSeeds + " | Expected data: [7298916735143357077]");
                break;
            case v1_14:
            case v1_13:
                System.out.println("Running 1.14 and 1.13 test data..");
                DungeonData1 = new DungeonDataProcessor(MCVersion.v1_13, 693, 30, -74, "111011100101011111011011001111110110111011101111111011101111011011111110111111110", 9, 9).dungeonDataToDecoratorSeed();
                StructureSeeds1 = new DecoratorSeedProcessor(MCVersion.v1_13, 693, -74, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
                DungeonData2 = new DungeonDataProcessor(MCVersion.v1_13, 280, 29, 674, "111011111101101111111111101111101111110100111110111101111111110111111101111110011", 9, 9).dungeonDataToDecoratorSeed();
                StructureSeeds2 = new DecoratorSeedProcessor(MCVersion.v1_13, 280, 674, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
                StructureSeeds1.retainAll(StructureSeeds2);
                WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
                System.out.println("1.13 dungeon1 seed: " + DungeonData1 + " | Expected data: [82836126371671]");
                System.out.println("1.13 dungeon2 seed: " + DungeonData2 + " | Expected data: [19957636759997]");
                System.out.println("1.13 world seeds: " + WorldSeeds + " | Expected data: [1724951870366438529]");
                break;
            case v1_12:
            case v1_11:
            case v1_10:
            case v1_9:
            case v1_8:
                System.out.println("Running 1.12-1.8 test data..");
                DungeonData1 = new DungeonDataProcessor(MCVersion.v1_8, 137, 27, -147, "111110101111111110110110111110011111111111111111111111101111011", 7, 9).dungeonDataToDecoratorSeed();
                StructureSeeds1 = new DecoratorSeedProcessor(MCVersion.v1_8, 137, -147, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
                DungeonData2 = new DungeonDataProcessor(MCVersion.v1_8, 61, 59, 668, "111111110111111111001101101110111111110111111001111111001111111", 9, 7).dungeonDataToDecoratorSeed();
                StructureSeeds2 = new DecoratorSeedProcessor(MCVersion.v1_8, 61, 668, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
                StructureSeeds1.retainAll(StructureSeeds2);
                WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
                System.out.println("1.8 dungeon1 seed: " + DungeonData1 + " | Expected data: [14581818956973]");
                System.out.println("1.8 dungeon2 seed: " + DungeonData2 + " | Expected data: [226023998267313]");
                System.out.println("1.8 world seeds: " + WorldSeeds + " | Expected data: [-1700538326672817507]");
                break;
            case v1_0: //a1.2.2a dungeons (pack.png btw)
                System.out.println("Running pre-1.7 and legacy test data..");
                DungeonData1 = new DungeonDataProcessor(v, 140, 81, -35, "001011101111111111001111111011011101110111111110000110110111111", 7, 9).dungeonDataToDecoratorSeed();
                StructureSeeds1 = new DecoratorSeedProcessor(v, 140, -35, Biomes.PLAINS, DungeonData1).decoratorSeedsToStructureSeeds();
                DungeonData2 = new DungeonDataProcessor(v, 171, 13, -34, "101111111111110111011101111100011111111100101011111011001111010", 7, 9).dungeonDataToDecoratorSeed();
                StructureSeeds2 = new DecoratorSeedProcessor(v, 171, -34, Biomes.PLAINS, DungeonData2).decoratorSeedsToStructureSeeds();
                StructureSeeds1.retainAll(StructureSeeds2);
                WorldSeeds = new StructureSeedProcessor(StructureSeeds1).getWorldSeedsFromStructureSeeds();
                System.out.println("Legacy dungeon1 seed: " + DungeonData1 + " | Expected data: [240428811966007]");
                System.out.println("Legacy dungeon2 seed: " + DungeonData2 + " | Expected data: [82449899703950]");
                System.out.println("Legacy world seeds: " + WorldSeeds + " | Expected data: [3257840388504953787]");
                break;
            default:
                System.out.println("Unsupported version. Please enter only major versions between 1.17 and 1.8, or 1.0 for Legacy versions.");
        }
    }

    public static String getAppVersion() {
        return Main.APP_VERSION;
    }

    /* Cobble = 0; Moss = 1; Unknown = 2
    // 1.17                       Dungeon Seed: [137229083672372]; Coords: [25 54 88];       Sequence: [0111010110011110110100010101110110101110111111111];                                 World Seed: [1488979889728021444]; Biome: Giant_Tree_Taiga  Spawner: [7x7]
    // 1.16                       Dungeon Seed: [66991252199345];  Coords: [-6799 61 -1473]; Sequence: [011010011111011111011110011100111011110111011110001011110111011111000011111101011]; World Seed: [-720350949281663006]; Biome: Desert            Spawner: [9x9]
    // 1.15                       Dungeon Seed: [54954658892082];  Coords: [161 16 -716];    Sequence: [1111111101101110111110111011101111111101101100101];                                 World Seed: [7298916735143357077]                           Spawner: [7x7]
    // 1.14, 1.13                 Dungeon Seed: [82836126371671];  Coords: [693 30 -74];     Sequence: [111011100101011111011011001111110110111011101111111011101111011011111110111111110]; World Seed: [1724951870366438529]                           Spawner: [9x9]
                                  Dungeon Seed: [19957636759997];  Coords: [280 29 674];     Sequence: [111011111101101111111111101111101111110100111110111101111111110111111101111110011]; World Seed: [1724951870366438529]                           Spawner: [9x9]
    // 1.12, 1.11, 1.10, 1.9, 1.8 Dungeon Seed: [14581818956973];  Coords: [137 27 -147];    Sequence: [111110101111111110110110111110011111111111111111111111101111011];                   World Seed: [-1700538326672817507]; Version: [1.10.0]       Spawner: [7x9]
                                  Dungeon Seed: [226023998267313]; Coords: [61 59 668];      Sequence: [111111110111111111001101101110111111110111111001111111001111111];                   World Seed: [-1700538326672817507]; Version: [1.10.0]       Spawner: [9x7]
    // 1.7 and below              Dungeon Seed: [215824296572061]; Coords: [256 28 129];     Sequence: [111110110111111111111111111111110111111111101111100101110111011];                   World Seed: [4549957071420637180];  Version: [1.4.7]        Spawner: [9x7]
                                  Dungeon Seed: [185122040393267]; Coords: [240 25 170];     Sequence: [001101111011110110111111100011101111111101000111110010101101011];                   World Seed: [4549957071420637180];  Version: [1.4.7]        Spawner: [9x7]
    // alpha testing              Dungeon Seed: [240428811966007]; Coords: [140 81 -35];     Sequence: [001011101111111111001111111011011101110111111110000110110111111];                   World Seed: [3257840388504953787];  Version: [a1.2.2]       Spawner: [7x9]
    //                            Dungeon Seed: [82449899703950];  Coords: [171 13 -34];     Sequence: [101111111111110111011101111100011111111100101011111011001111010];                   World Seed: [3257840388504953787];  Version: [a1.2.2]       Spawner: [7x9]
    */
}
