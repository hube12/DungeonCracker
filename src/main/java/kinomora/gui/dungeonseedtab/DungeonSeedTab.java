package kinomora.gui.dungeonseedtab;

import kinomora.gui.dungeondatatab.SpawnerDataPanel;
import kinomora.gui.dungeondatatab.VersionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class DungeonSeedTab extends JPanel implements ActionListener, MouseListener {

    //Dungeon data
    Set<Long> dungeon1Seeds = new HashSet<>();
    int dungeon1x = 0;
    int dungeon1y = 0;
    int dungeon1z = 0;
    int dungeon1FloorSize = 81;
    int[] dungeon1FloorDimensions = {9, 9};
    Set<Long> dungeon2Seeds = new HashSet<>();
    int dungeon2x = 0;
    int dungeon2y = 0;
    int dungeon2z = 0;
    int dungeon2FloorSize = 81;
    int[] dungeon2FloorDimensions = {9, 9};
    Set<Long> worldSeeds;
    private boolean hasMouseExited;

    public DungeonSeedTab() {
        VersionPanel versionPanel = new VersionPanel(this);
        SpawnerDataPanel spawner1DataPanel = new SpawnerDataPanel(this);
        SpawnerDataPanel spawner2DataPanel = new SpawnerDataPanel(this);

        spawner2DataPanel.setDungeonXYZLabelText(2);

        /*
        dungeonFloorPanel.setDungeonFloorPanelTitle("Dungeon Floor 2  |  Top is in-game North");
        dungeonFloorPanel.setCurrentFloorSequence(buttonStateArrayDungeon2);
        dungeonFloorPanel.setDungeonFloorSize(dungeon2FloorSize, 2);
        dungeonFloorPanel.setDungeonFloorDimensions(dungeon2FloorDimensions, 2);
        spawnerDataPanel.setCurrentBiomeDropdownValue(2);
        spawnerDataPanel.setDungeonXYZLabelText(2);
        spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
        spawnerDataPanel.setTextFieldValue(dungeon2x, dungeon2y, dungeon2z);
        spawnerDataPanel.dungeonSeedField.setText(dungeon2Seeds.toString());
         */

        //Set the layout for the window to be two halves
        this.setLayout(new GridBagLayout());

        //Create panels for each half
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        //Create subpanels for each section of the Dungeon Floor side
        JPanel dungeonSubButtonPanel = new JPanel(new FlowLayout());

        this.add(leftPanel, setC(0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 0, 0)));
        this.add(rightPanel, setC(1, 0, 1, 1, 0, 0, GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));

        leftPanel.add(spawner1DataPanel, setC(0, 0, 1, 1, 5, 15, GridBagConstraints.FIRST_LINE_START, new Insets(5, 5, 5, 0)));
        leftPanel.add(spawner2DataPanel, setC(0, 1, 1, 1, 5, 15, GridBagConstraints.PAGE_START, new Insets(5, 5, 5, 0)));

        rightPanel.add(versionPanel, setC(0, 0, 3, 1, 54, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, -95, 0, 0)));


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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
