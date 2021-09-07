package kinomora;

import com.google.gson.Gson;
import kaptainwutax.mcutils.util.data.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static kinomora.Main.LOGGER;

public class Update {
    public static void updateApp(HashMap<String, Pair<Pair<String, String>, String>> updateInfo, boolean shouldAsk) {
        Pair<Pair<String, String>, String> release = updateInfo.get("jar");
        if (release == null) {
            LOGGER.severe("Missing jar Entry");
            return;
        }
        String OS = System.getProperty("os.name").toLowerCase();
        boolean isWindows = (OS.contains("win"));
        boolean isMax = (OS.contains("mac"));
        boolean isUnix = (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
        boolean isSolaris = (OS.contains("sunos"));
        boolean shouldUseVersion = false;
        Pair<Pair<String, String>, String> exeRelease = updateInfo.get("exe");
        if (exeRelease != null && isWindows) {
            release = exeRelease;
            shouldUseVersion = true;
        }
        // TODO add more build options here
        if (shouldAsk) {
            int dialogResult = JOptionPane.showConfirmDialog(
                null,
                String.format("Would you like to update to the version %s of Universal Dungeon Cracker?", release.getSecond()),
                "Update available for Universal Dungeon Cracker " + Main.APP_VERSION,
                JOptionPane.YES_NO_OPTION
            );
            if (dialogResult != 0) {
                return;
            }
        }
        JDialog downloadPopup = new ModalPopup(null, "Downloading new Universal Dungeon Cracker version");
        downloadPopup.setSize(new Dimension(300, 50));
        downloadPopup.setShape(new RoundRectangle2D.Double(0, 0, 300, 50, 50, 50));
        SwingWorker<String, Void> downloadWorker = getDownloadWorker(downloadPopup, release.getFirst());
        downloadWorker.execute();
        downloadPopup.setVisible(true);
        String newVersion = null;
        try {
            newVersion = downloadWorker.get(); // blocking wait (intended)
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed to use the download worker, error %s", e));
        }
        downloadPopup.setVisible(false);
        downloadPopup.dispose();
        if (newVersion != null) {
            Process ps;
            try {
                if (!shouldUseVersion) {
                    ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", newVersion, "--no-update"});
                } else {
                    ps = Runtime.getRuntime().exec(new String[] {"./" + newVersion, "--no-update"});
                }

                LOGGER.info(String.format("Process exited with %s", ps.waitFor()));
            } catch (Exception e) {
                LOGGER.severe(String.format("Failed to spawn the new process, error %s", e));
                return;
            }
            int exitVal = ps.exitValue();
            if (exitVal != 0) {
                LOGGER.severe("Failed to execute jar, " + Arrays.toString(new BufferedReader(new InputStreamReader(ps.getErrorStream())).lines().toArray()));
            } else {
                LOGGER.warning(String.format("UPDATING TO %s", newVersion));
                System.exit(0);
            }
        }
    }

    public static class ModalPopup extends JDialog {
        public ModalPopup(JFrame parent, String waitMsg) {
            JPanel p1 = new JPanel(new GridBagLayout());
            p1.add(new JLabel("<html><div style='text-align: center;'>" + waitMsg + "<br>Please wait...</div></html>"));
            this.setUndecorated(true);
            this.getContentPane().add(p1);
            this.pack();
            this.setLocationRelativeTo(parent);
            this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            this.setModal(true);
        }
    }

    private static SwingWorker<String, Void> getDownloadWorker(JDialog parent, Pair<String, String> newVersion) {
        return new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return downloadLatest(newVersion.getFirst(), newVersion.getSecond());
            }

            @Override
            protected void done() {
                super.done();
                parent.dispose();
            }
        };
    }

    public static String downloadLatest(String url, String filename) {
        if (download(url, new File(filename), null)) {
            return filename;
        }
        LOGGER.warning(String.format("Failed to download jar from url %s with filename %s", url, filename));
        return null;
    }

    private static boolean download(String url, File out, String sha1) {
        LOGGER.info(String.format("Downloading %s for file %s", url, out.getName()));
        ReadableByteChannel rbc;
        try {
            rbc = Channels.newChannel(new URL(url).openStream());
        } catch (IOException e) {
            LOGGER.severe(String.format("Could not open channel to url %s, error: %s", url, e));
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            fileOutputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fileOutputStream.close();
        } catch (IOException e) {
            LOGGER.severe(String.format("Could not download from channel to url %s for file %s, error: %s", url, out.getAbsolutePath(), e));
            return false;
        }
        return sha1 == null || compareSha1(out, sha1);
    }

    private static boolean compareSha1(File file, String sha1) {
        if (sha1 != null && file != null) {
            try {
                return getFileChecksum(MessageDigest.getInstance("SHA-1"), file).equals(sha1);
            } catch (NoSuchAlgorithmException e) {
                LOGGER.severe("Could not compute sha1 since algorithm does not exists");
            }
        }
        return false;
    }

    private static String getFileChecksum(MessageDigest digest, File file) {
        try {
            FileInputStream fis = new FileInputStream(file);

            byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            fis.close();
        } catch (IOException e) {
            LOGGER.severe(String.format("Failed to read file for checksum, error : %s", e));
            return "";
        }
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static String getDataRestAPI(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                LOGGER.severe(String.format("Failed to fetch URL %s, errorcode : %s", apiUrl, responseCode));
            } else {

                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();
                return inline.toString();
            }
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed to fetch URL %s, error : %s", apiUrl, e));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Pair<Pair<String, String>, String>> shouldUpdate() {
        String data = getDataRestAPI("https://api.github.com/repos/Kinomora/DungeonCracker/releases/latest");
        if (data == null) {
            return null;
        }
        Map<String, Object> map = new Gson().fromJson(data, Map.class);
        if (map.containsKey("tag_name")) {
            String tagName = (String) map.get("tag_name");
            if (!tagName.equals(Main.APP_VERSION)) {
                if (map.containsKey("assets")) {
                    ArrayList<Map<String, Object>> assets = (ArrayList<Map<String, Object>>) map.get("assets");
                    HashMap<String, Pair<Pair<String, String>, String>> versionToDownload = new LinkedHashMap<>();
                    for (Map<String, Object> asset : assets) {
                        if (asset.containsKey("browser_download_url") && asset.containsKey("name") && ((String) asset.get("name")).startsWith("UniversalDungeonCracker")) {
                            String url = (String) asset.get("browser_download_url");
                            String filename = (String) asset.get("name");
                            String[] split = filename.split("\\.");
                            if (split.length > 1) {
                                versionToDownload.put(split[split.length - 1], new Pair<>(new Pair<>(url, filename), tagName));
                            }
                        }
                    }
                    if (versionToDownload.isEmpty() || !versionToDownload.containsKey("jar")) {
                        LOGGER.warning("Github release does not contain a correct release."+versionToDownload.keySet());
                    } else {
                        return versionToDownload;
                    }
                } else {
                    LOGGER.warning("Github release does not contain a assets key.");
                }
            } else {
                LOGGER.info(String.format("Version match so we are not updating current :%s, github :%s", Main.APP_VERSION, tagName));
            }
        } else {
            LOGGER.warning("Github release does not contain a tag_name key.");
        }
        return null;
    }

}
