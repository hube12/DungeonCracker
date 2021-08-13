package kinomora.gui.dungeondatatab;

import other.util.MCVersion;

import javax.swing.*;
import java.awt.*;

public class DungeonDataTab extends JPanel {
    public final DungeonFloorPanel dungeonFloorPanel;
    public final VersionPanel versionPanel;
    public final SpawnerDataPanelBig spawnerDataPanelBig;

    /**
     * Creates and populates the Items(Overview) tab which contains the Inventory, Item Descriptions, Recommended Champions, and Inventory Item Crafting Calculator
     */
    public DungeonDataTab() {
        dungeonFloorPanel = new DungeonFloorPanel(this);
        versionPanel = new VersionPanel(this);
        spawnerDataPanelBig = new SpawnerDataPanelBig(this);

        //dungeonFloorPanel.setSize(320, 320);
        //versionPanel.setSize(250, 70);
        spawnerDataPanelBig.setSize(250, 280);

        //Right Panel (dungeon floor) objects
        JButton rotateCounterClockwise = new JButton("CC");
        JButton size7x7 = new JButton("7x7");
        JButton size7x9 = new JButton(" 7x9");
        JButton size9x9 = new JButton("9x9");
        JButton rotateClockwise = new JButton("CW");

        //Set the layout for the window to be two halves
        this.setLayout(new GridLayout(1, 2,5,5));

        //Create panels for each half
        JPanel leftPanel = new JPanel(new FlowLayout());
        leftPanel.setSize(350,380);
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setSize(250,70);

        //Create subpanels for each section of the Dungeon Floor side
        JPanel dungeonSubButtonPanel = new JPanel(new GridLayout(1, 5));
        dungeonSubButtonPanel.setSize(350, 50);

        //Fill in the dungeon subpanels
        dungeonSubButtonPanel.add(rotateCounterClockwise);
        dungeonSubButtonPanel.add(size7x7);
        dungeonSubButtonPanel.add(size7x9);
        dungeonSubButtonPanel.add(size9x9);
        dungeonSubButtonPanel.add(rotateClockwise);

        //Add the dungeon subpanels to the right
        leftPanel.add(dungeonFloorPanel);
        leftPanel.add(dungeonSubButtonPanel);

        //Add the Dungeon Data subpanels
        rightPanel.add(versionPanel);
        rightPanel.add(spawnerDataPanelBig);

        this.add(leftPanel);
        //this.add(rightPanel);
    }
}
