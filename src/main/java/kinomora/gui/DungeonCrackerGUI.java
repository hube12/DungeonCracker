package kinomora.gui;

import kinomora.Main;
import kinomora.gui.abouttab.AboutTab;
import kinomora.gui.dungeondatatab.DungeonDataTab;

import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;

public class DungeonCrackerGUI extends JFrame {

    public DungeonCrackerGUI() throws HeadlessException, URISyntaxException {
        super("Universal Dungeon Cracker - " + Main.getAppVersion());

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //The main window that holds the overview tabs
        JTabbedPane tabs = new JTabbedPane();

        //Creating the main overview tabs
        DungeonDataTab dungeonDataTab = new DungeonDataTab();
        //DungeonSeedTab dungeonSeedTab = new DungeonSeedTab();
        AboutTab aboutTab = new AboutTab();

        //Add the tabs to the main window
        tabs.addTab("Dungeon Data Mode", dungeonDataTab);
        //tabs.addTab("Dungeon Seed Mode", dungeonSeedTab);
        tabs.addTab("About", aboutTab);

        this.setContentPane(tabs);
    }
}
