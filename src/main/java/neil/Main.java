package neil;

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
        int spawnerCount = 0;
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
        System.out.print("Please provide the version number the world was created in as listed below:\n16:    Release 1.16.x\n15:    Release 1.15.x\n13:    Releases 1.13.x and 1.14.x\n8:     Releases 1.8.x through 1.12.x\n7:     Releases 1.7.x and earlier\n0:     I don't know\nType the corresponding number on the left: ");
        while(!validVersion) {
            String input = userInput.nextLine();
            if(validateUserVersionInput(input)==-1){
                System.out.println("Please provide a supported version number like \"16\". If you do not know the version, put 0");
                System.out.print("Version number: ");
            } else {
                validVersion = true;
                version = validateUserVersionInput(input);
            }
        }

        System.out.print("How many spawners do you have? Versions below 1.13 require a minimum of 2.\nSpawner count: ");
        while(!enoughSpaners){
            enoughSpaners = true;
            String input = userInput.nextLine();
            if(input.equals("1") && version >= 3){
                spawnerCount = 1;
            } else if(input.equals("2")){
                spawnerCount = 2;
            } else if(input.equals("3")){
                spawnerCount = 3;
            } else if(input.equals("4")){
                spawnerCount = 4;
            } else {
                if(version < 3){
                    System.out.println("Please input a valid number greater than 1.");
                } else {
                    System.out.println("Please input a valid number.");
                }
                System.out.print("Spawner count: ");
                enoughSpaners = false;
            }
        }



        switch(version) {
            case 0: System.out.println("Unknown Version.."); break;
            case 1: System.out.println("Running version for 1.7 and older.."); test(vLegacy); break;
            case 2: System.out.println("Running version for 1.8 through 1.12.."); test(v1_7); break;
            case 3: System.out.println("Running version for 1.13 and 1.14.."); test(v1_13); break;
            case 4: System.out.println("Running version for 1.15.."); test(v1_15); break;
            case 5: System.out.println("Running version for 1.16.."); test(v1_16); break;
            default: System.out.println("Something went wrong..");
        }
	}

	public static int validateUserVersionInput(String input){
	    switch(input){
            case "16": return 5;
            case "15": return 4;
            case "14":
            case "13": return 3;
            case "12":
            case "11":
            case "10":
            case "9":
            case "8": return 2;
            case "7": return 1;
            case "6":
            case "5":
            case "4":
            case "3":
            case "2":
            case "1":
            case "0": return 0;
            default: return -1;
        }
    }

    public static void test(MCVersion version) {
        // 1.16                       Dungeon Seed: [66991252199345]; Coords: [-6799 61 -1473]; World Seed: [-720350949281663006]
        // 1.15                       Dungeon Seed: [54954658892082]; Coords: [161 16 -716]; World Seed: [7298916735143357077]
        // 1.14, 1.13                 Dungeon Seed: [82836126371671]; Coords: [693 30 -74]; World Seed: [1724951870366438529]
        // 1.12, 1.11, 1.10, 1.9, 1.8 Dungeon Seed: [246636189820814,269259332384656]; Coords: [-296 19 26, -178 14 219];  World Seed: [-1700538326672817507]
        // 1.7 and below              Dungeon Seed: [41813458706666,190214760258714]; Coords: [544 49 -229, 774 54 -129]; World Seed: [-6812128122949736898]
        switch(version){
            case v1_16: System.out.println(new VersionCrack(version, -6799, 61, -1473, "011010011111011111011110011100111011110111011110001011110111011111000011111101011", Biomes.DESERT).run().toString()); break;
            case v1_15: System.out.println(new VersionCrack(version, 161, 16, -716, "1111111101101110111110111011101111111101101100101").run().toString()); break;
            case v1_13: System.out.println(new VersionCrack(version, 280, 29, 674, "111011111101101111111111101111101111110100111110111101111111110111111101111110011").run().toString()); break;
            case v1_7: System.out.println(new VersionCrack(version, 544, 49, -229, "0110110110011001111111011111001110111011111111101").run().toString()); break;
            //case v1_7: System.out.println(new VersionCrack(version, 774, 54, -129, "100110111101111011100101101110101101111111111100010111110111110101011100101111100").run().toString()); break;
            case vLegacy: System.out.println(new VersionCrack(version, -296, 19, 261, "010111111111011110001110011110120111111111101110011101111111011").run().toString()); break;
            //case vLegacy: System.out.println(new VersionCrack(version, -178, 14, 219, "010111110110110100101101100111101111111101111110111101111011110").run().toString()); break;
        }
    }
}
