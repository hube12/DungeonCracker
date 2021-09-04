package kinomora.gui.util;

public class VersionComparison {

    public static boolean isVersionNewerOrEqualTo(String version1, String version2) {
        return convertVersionToInt(version1) >= convertVersionToInt(version2);
    }

    public static boolean isVersionOlderOrEqualTo(String version1, String version2){
        return convertVersionToInt(version1) <= convertVersionToInt(version2);
    }

    public static boolean isVersionNewerThan(String version1, String version2) {
        return convertVersionToInt(version1) > convertVersionToInt(version2);
    }

    public static boolean isVersionOlderThan(String version1, String version2){
        return convertVersionToInt(version1) < convertVersionToInt(version2);
    }

    private static int convertVersionToInt(String version) {
        switch (version) {
            case "1.17": return 17;
            case "1.16": return 16;
            case "1.14": return 15;
            case "1.15": return 14;
            case "1.13": return 13;
            case "1.12": return 12;
            case "1.11": return 11;
            case "1.10": return 10;
            case "1.9": return 9;
            case "1.8": return 8;
            case "1.7": return 7;
            case "Legacy": return 0;
            default: System.out.println("Critical error in converting Version to Int.."); System.exit(1); return -1;
        }
    }
}
