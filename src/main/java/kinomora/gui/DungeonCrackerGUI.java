package kinomora.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import kinomora.Main;
import kinomora.gui.abouttab.AboutTab;
import kinomora.gui.dungeondatatab.DungeonDataTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URISyntaxException;
import java.util.function.Supplier;

public class DungeonCrackerGUI extends JFrame implements ActionListener, ItemListener {

    //Creating the main overview tabs
    DungeonDataTab dungeonDataTab;
    //DungeonSeedTab dungeonSeedTab;
    AboutTab aboutTab;


    //The main window that holds the overview tabs
    JTabbedPane tabs = new JTabbedPane();

    //Menu items
    JMenuBar menuBar = new JMenuBar();
    JMenu settingsMenu = new JMenu("Settings");
    JMenu infoMenu = new JMenu("Info");
    ButtonGroup group = new ButtonGroup();
    JRadioButtonMenuItem radioMenuDarkMode = new JRadioButtonMenuItem("Dark Mode");
    JRadioButtonMenuItem radioMenuLightMode = new JRadioButtonMenuItem("Light Mode");
    JCheckBoxMenuItem twoButtonCompatMode = new JCheckBoxMenuItem("Mouse Compatibility Mode");
    JMenuItem helpMenuItem = new JMenuItem("In-App Help and Information");
    JMenuItem aboutMenuItem = new JMenuItem("About and Credits");

    JLabel helpMenuText = new JLabel();

    public DungeonCrackerGUI() throws HeadlessException, URISyntaxException, UnsupportedLookAndFeelException {
        super("Universal Dungeon Cracker - " + Main.getAppVersion());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Menu stuff
        menuBar.add(settingsMenu);
        menuBar.add(infoMenu);

        settingsMenu.add(radioMenuDarkMode);
        settingsMenu.add(radioMenuLightMode);
        radioMenuDarkMode.setSelected(true);
        group.add(radioMenuDarkMode);
        group.add(radioMenuLightMode);

        settingsMenu.addSeparator();
        settingsMenu.add(twoButtonCompatMode);
        twoButtonCompatMode.setToolTipText("Changes the way clicking floor buttons work for users with only 1 or 2 mouse buttons");
        infoMenu.add(helpMenuItem);
        //infoMenu.add(aboutMenuItem); Eventually move everything in the About Tab to this menu item

        // Add listeners
        radioMenuDarkMode.addActionListener(this);
        radioMenuLightMode.addActionListener(this);
        twoButtonCompatMode.addActionListener(this);
        helpMenuItem.addItemListener(this);
        aboutMenuItem.addItemListener(this);


        //Creating the main overview tabs
        dungeonDataTab = new DungeonDataTab();
        //dungeonSeedTab = new DungeonSeedTab();
        aboutTab = new AboutTab();

        //Add the tabs to the main window
        tabs.addTab("Dungeon Cracker", dungeonDataTab);
        //tabs.addTab("Structure Seed Cracker", dungeonSeedTab);
        tabs.addTab("About", aboutTab);
        helpMenuText.setText(getHelpText());
        helpMenuText.setForeground(Color.WHITE);
        helpMenuText.setForeground(Color.WHITE);
        helpMenuItem.addActionListener(this);

        this.setJMenuBar(menuBar);
        this.setContentPane(tabs);

        LookType.DARCULA.setLookAndFeel();
    }

    public String getHelpText() {
        String text;

        text = "<html>" +
            "<body>" +
            "<p>" +
            "<h2>How to use the Dungeon Cracker Tab</h2>" +
            "<ol>" +
            "<li>Find an unaltered dungeon</li>" +
            "<li>Remove the walls and chests, if possible. Determine what direction is North</li>" +
            "<li>Select the correct dungeon shape at the bottom of the window</li>" +
            "<li>Begin entering in the dungeon floor pattern on the left side of the app</li>" +
            "<li>Select the minecraft version that the dungeon was generated in (older versions require 2 dungeons to get the world seed)</li>" +
            "<li>Select the biome that the spawner block is in (only necessary for dungeons generated in 1.16 and newer)</li>" +
            "<li>Press the Dungeon Seed button if you only want the structure seed, otherwise press the Crack Seed button</li>" +
            "<li>After some time you will get a pop-up telling you the valid world seeds for the data you entered, or none if the data was invalid</li>" +
            "</ol>" +
            "</p><p>" +
            "Terminology" +
            "<ul>" +
            "<li>World Seed: The seed you can put into the game and recreate any world" +
            "<li>Dungeon Seed: The internal seed the game uses to determine where dungeons are placed, this is NOT a World Seed</li>" +
            "<li>Structure Seed: Like a Dungeon Seed, but for other structures</li>" +
            "<li>Dungeon Sequence: The pattern of Cobble and Moss that make up a dungeon floor in a particular order</li>" +
            "</ul>" +
            "</p><p>" +
            "Dungeon Floor Patterns" +
            "<ul>" +
            "<li>The floor must be the correct size, counting the blocks under the walls</li>" +
            "<li>When looking at the floor in-game, orient yourself so that you are facing North</li>" +
            "<li>By default, left-click sets Cobble, right-click sets Mossy, and Middle-click sets Unknown</li>" +
            "<li>You must enter in the entire floor, including what is under the walls</li>" +
            "<li>If you do not what type blocks are because they are obscured or missing, leave them as [?]</li>" +
            "</ul>" +
            "</p><p>" +
            "Spawners Info" +
            "<ul>" +
            "<li>The spawner coords are the coords of the Spawner block itself</li>" +
            "<li>The Biome is the Biome that the Spawner block is in</li>" +
            "<li>Biomes are only required for 1.16+</li>" +
            "<li>Two dungeons are required for seed cracking in 1.12 and older</li>" +
            "<li>You can still get the dungeon seed for a single dungeon in any version</li>" +
            "</ul>" +
            "</p>" +
            "</body>" +
            "</html>";

        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();

        if (event.equals(radioMenuLightMode)) {
            try {
                LookType.INTELLIJ.setLookAndFeel();
                helpMenuText.setForeground(Color.BLACK);
            } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                unsupportedLookAndFeelException.printStackTrace();
            }
        } else if (event.equals(radioMenuDarkMode)) {
            try {
                LookType.DARCULA.setLookAndFeel();
                helpMenuText.setForeground(Color.WHITE);
            } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                unsupportedLookAndFeelException.printStackTrace();
            }
        } else if (event.equals(twoButtonCompatMode)) {
            dungeonDataTab.setTwoButtonMouseCompatMode(twoButtonCompatMode.isSelected());
        } else if (event.equals(helpMenuItem)) {
            JOptionPane.showMessageDialog(this, helpMenuText, "Help Menu", JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

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
}
