package kinomora.gui.dungeondatatab;

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
        this.dungeonFloorPanel = new DungeonFloorPanel(this);
        this.versionPanel = new VersionPanel(this);
        this.spawnerDataPanelBig = new SpawnerDataPanelBig(this);

        //Set the layout for the window
        this.setLayout(new BorderLayout());

        //Top half of the Items(overview) tab, contains the inventory and item(desc, etc) windows
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(versionPanel, BorderLayout.NORTH);
        rightPanel.add(spawnerDataPanelBig, BorderLayout.SOUTH);

        //Add the top panel and the bottom pane to the main Items(Overview) window
        this.add(dungeonFloorPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }
}
