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
        //spawnerDataPanelBig.setPreferredSize(new Dimension(220,360));
        //spawnerDataPanelBig.setSize(350,380);

        //Right Panel (dungeon floor) objects
        JButton rotateCounterClockwise = new JButton("CC");
        JButton size7x7 = new JButton("7x7");
        JButton size7x9 = new JButton(" 7x9");
        JButton size9x9 = new JButton("9x9");
        JButton rotateClockwise = new JButton("CW");

        //Set the layout for the window to be two halves
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 5,0));

        //Create panels for each half
        JPanel leftPanel = new JPanel(new GridBagLayout());
        //leftPanel.setSize(350,380);
        JPanel rightPanel = new JPanel(new GridBagLayout());
        //rightPanel.setSize(250,300);

        //Create subpanels for each section of the Dungeon Floor side
        JPanel dungeonSubButtonPanel = new JPanel(new FlowLayout());

        //Fill in the dungeon subpanels
        dungeonSubButtonPanel.add(rotateCounterClockwise);
        dungeonSubButtonPanel.add(size7x7);
        dungeonSubButtonPanel.add(size7x9);
        dungeonSubButtonPanel.add(size9x9);
        dungeonSubButtonPanel.add(rotateClockwise);

        /***Adding the Dungeon Floor panel and the Dungeon Floor Buttons to the LEFT Panel***/
        //Add the Dungeon Floor
        /*c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        //c.ipadx = 5;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets =new Insets(0, 0, 0, 0);
        //gridx,gridy,gridwidth,gridheight,weightx,weighty,ipadx,ipady,anchor,insets*/
        leftPanel.add(dungeonFloorPanel, setC(0,0,1,1,0,0,GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));

        //Adding the Floor Buttons
        /*c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        //c.ipadx = 5;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        //gridx,gridy,gridwidth,gridheight,weightx,weighty,ipadx,ipady,anchor,insets*/
        leftPanel.add(dungeonSubButtonPanel, setC(0,1,1,1,0,0,GridBagConstraints.CENTER,new Insets(0, 0, 0, 0)));


        /***Adding the Version Panel and the Dungeon Data Big panel to the RIGHT Panel***/
        //Add the Version panel
        /*c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 60;
        c.ipady = 14;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 5, 0, 0);
        //gridx,gridy,gridwidth,gridheight,weightx,weighty,ipadx,ipady,anchor,insets*/

        rightPanel.add(versionPanel, setC(0,0,1,1,0,0,GridBagConstraints.PAGE_START,new Insets(0, 0, 0, 0)));

        //Adding the Dungeon Data panel
        /*c.gridx = 0;
        c.gridy = 1;
        c.weightx = 2;
        c.ipadx = 70;
        c.ipady = 20;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 0, 0);
        //gridx,gridy,gridwidth,gridheight,weightx,weighty,ipadx,ipady,anchor,insets*/

        rightPanel.add(spawnerDataPanelBig, setC(0,1,1,1,0,0,GridBagConstraints.LINE_START,new Insets(0, 0, 0, 0)));


        /***Adding both panels to the main window***/
        //Adding the left panel (dungeon floor + buttons) to the main window
        /*c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 80;
        c.ipady = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(-40, 0, 0, 0);
        //gridx,gridy,gridwidth,gridheight,weightx,weighty,ipadx,ipady,anchor,insets*/

        this.add(leftPanel, setC(0,0,1,1,0,0,GridBagConstraints.FIRST_LINE_START,new Insets(0, 0, 0, 0)));

        //Adding the right panel (version and coords) to the main window
        /*c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 20;
        c.ipady = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(-28, -10, 0, 0);
        //gridx,gridy,gridwidth,gridheight,weightx,weighty,ipadx,ipady,anchor,insets*/

        this.add(rightPanel, setC(1,0,1,1,0,0,GridBagConstraints.FIRST_LINE_END,new Insets(0, -0, 0, 0)));
    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, int gridheight, int ipadx, int ipady, int anchor, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.ipadx = ipadx;
        c.ipady = ipady;
        c.anchor = anchor;
        c.insets = insets;
        return c;
    }
}
