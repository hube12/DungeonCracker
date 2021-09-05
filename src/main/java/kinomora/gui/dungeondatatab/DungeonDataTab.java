package kinomora.gui.dungeondatatab;

import kaptainwutax.mcutils.version.MCVersion;
import kinomora.Main;
import kinomora.gui.util.BiomeNameToBiome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class DungeonDataTab extends JPanel implements ActionListener, MouseListener {
    public final DungeonFloorPanel dungeonFloorPanel;
    public final VersionPanel versionPanel;
    public final SpawnerDataPanelBig spawnerDataPanelBig;
    private boolean hasMouseExited;
    public int currentDungeonFloor = 1;

    private static final ImageIcon CLOCKWISE_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/rotateIcons/CW.png")));
    private static final ImageIcon COUNTERCLOCKWISE_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/rotateIcons/CCW.png")));

    JButton rotateCounterClockwise = new JButton(COUNTERCLOCKWISE_ICON);
    JButton size7x7 = new JButton("7x7");
    JButton size7x9 = new JButton("7x9");
    JButton size9x9 = new JButton("9x9");
    JButton rotateClockwise = new JButton(CLOCKWISE_ICON);

    //---Application data---
    //Program data
    boolean validInput;
    boolean doubleSpawnerMode = false;

    //Dungeon data
    Set<Long> dungeon1Seeds = new HashSet<>();
    int dungeon1x = 0;
    int dungeon1y = 0;
    int dungeon1z = 0;
    int dungeon1fsx = 0;
    int dungeon1fsz = 0;
    int dungeon1FloorSize = 81;
    String dungeon1Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
    public static int[][] buttonStateArrayDungeon1 = new int[9][9];

    Set<Long> dungeon2Seeds = new HashSet<>();
    int dungeon2x = 0;
    int dungeon2y = 0;
    int dungeon2z = 0;
    int dungeon2fsx = 0;
    int dungeon2fsz = 0;
    int dungeon2FloorSize = 81;
    String dungeon2Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
    public static int[][] buttonStateArrayDungeon2 = new int[9][9];

    Set<Long> worldSeeds;

    /**
     * Creates and populates the Items(Overview) tab which contains the Inventory, Item Descriptions, Recommended Champions, and Inventory Item Crafting Calculator
     */
    public DungeonDataTab() {
        dungeonFloorPanel = new DungeonFloorPanel(this);
        versionPanel = new VersionPanel(this);
        spawnerDataPanelBig = new SpawnerDataPanelBig(this);

        //Set the layout for the window to be two halves
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 0));

        //Create panels for each half
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        //Create subpanels for each section of the Dungeon Floor side
        JPanel dungeonSubButtonPanel = new JPanel(new FlowLayout());

        // Right Panel (dungeon floor) objects
        dungeonFloorSubButtonMess(dungeonSubButtonPanel);


        // Adding both panels to the main window
        // Adding the left panel (dungeon floor + buttons) to the main window
        this.add(leftPanel, setC(0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 0, 0)));

        // Adding the right panel (version and coords) to the main window
        this.add(rightPanel, setC(1, 0, 1, 1, 0, 0, GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));


        // Adding the Dungeon Floor panel and the Dungeon Floor Buttons to the LEFT Panel
        // Add the Dungeon Floor
        leftPanel.add(dungeonFloorPanel, setC(0, 0, 1, 1, 0, 0, GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));

        //Adding the Floor Buttons
        leftPanel.add(dungeonSubButtonPanel, setC(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0)));


        // Adding the Version Panel and the Dungeon Data Big panel to the RIGHT Panel
        // Add the Version panel
        rightPanel.add(versionPanel, setC(0, 0, 1, 1, 54, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 10, 0)));

        // Adding the Dungeon Data panel
        rightPanel.add(spawnerDataPanelBig, setC(0, 1, 1, 3, 10, 38, GridBagConstraints.LINE_START, new Insets(0, 0, 30, 0)));

        //Set all the buttonStateArrays to 2 by default
        for (int i = 0; i < 9; i++) {
            Arrays.fill(buttonStateArrayDungeon1[i], 2);
            Arrays.fill(buttonStateArrayDungeon2[i], 2);
        }
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

    private void dungeonFloorSubButtonMess(JPanel dungeonSubButtonPanel) {
        //Create the buttons
        rotateCounterClockwise.setFocusPainted(false);
        rotateCounterClockwise.setFocusable(false);
        rotateCounterClockwise.addMouseListener(this);
        rotateCounterClockwise.addActionListener(this);

        size7x7.setFocusPainted(false);
        size7x7.setFocusable(false);
        size7x7.addMouseListener(this);
        size7x7.addActionListener(this);

        size7x9.setFocusPainted(false);
        size7x9.setFocusable(false);
        size7x9.addMouseListener(this);
        size7x9.addActionListener(this);

        size9x9.setFocusPainted(false);
        size9x9.setFocusable(false);
        size9x9.addMouseListener(this);
        size9x9.addActionListener(this);

        rotateClockwise.setFocusPainted(false);
        rotateClockwise.setFocusable(false);
        rotateClockwise.addMouseListener(this);
        rotateClockwise.addActionListener(this);

        //Fill in the dungeon subpanels
        dungeonSubButtonPanel.add(rotateCounterClockwise);
        dungeonSubButtonPanel.add(size7x7);
        dungeonSubButtonPanel.add(size7x9);
        dungeonSubButtonPanel.add(size9x9);
        dungeonSubButtonPanel.add(rotateClockwise);
    }

    private int safeIntParse(String text) {
        int number;
        try {
            number = parseInt(text);
        } catch (NumberFormatException e) {
            number = 0;
            validInput = false;
            System.out.println("Input was not a number.. Setting to 0");
        }
        return number;
    }

    private void swapToFloor1() {
        currentDungeonFloor = 1;
        dungeonFloorPanel.setDungeonFloorPanelTitle("Dungeon Floor 1  |  Top is in-game North");
        dungeonFloorPanel.setCurrentFloorSequence(buttonStateArrayDungeon1);
        dungeonFloorPanel.setDungeonFloorSize(dungeon1FloorSize, 1);
        spawnerDataPanelBig.setCurrentBiomeDropdownValue(1);
        spawnerDataPanelBig.setDungeonXYZLabelText(1);
        spawnerDataPanelBig.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
        spawnerDataPanelBig.setTextFieldValue(dungeon1x, dungeon1y, dungeon1z);
        spawnerDataPanelBig.dungeonSeedField.setText(dungeon1Seeds.toString());
    }

    private void swapToFloor2() {
        currentDungeonFloor = 2;
        dungeonFloorPanel.setDungeonFloorPanelTitle("Dungeon Floor 2  |  Top is in-game North");
        dungeonFloorPanel.setCurrentFloorSequence(buttonStateArrayDungeon2);
        dungeonFloorPanel.setDungeonFloorSize(dungeon2FloorSize, 2);
        spawnerDataPanelBig.setCurrentBiomeDropdownValue(2);
        spawnerDataPanelBig.setDungeonXYZLabelText(2);
        spawnerDataPanelBig.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
        spawnerDataPanelBig.setTextFieldValue(dungeon2x, dungeon2y, dungeon2z);
        spawnerDataPanelBig.dungeonSeedField.setText(dungeon2Seeds.toString());
    }

    //Methods with data sent from other classes
    public void floorButtonPressed(String floorSequence, int[][] buttonStateArrayCurrent, int currentFloorSize) {
        if (currentDungeonFloor == 2) {
            buttonStateArrayDungeon2 = Arrays.stream(buttonStateArrayCurrent).map(int[]::clone).toArray(int[][]::new);
            dungeon2Sequence = dungeonFloorPanel.getCurrentFloorSequence();
            dungeon2FloorSize = currentFloorSize;
        } else {
            buttonStateArrayDungeon1 = Arrays.stream(buttonStateArrayCurrent).map(int[]::clone).toArray(int[][]::new);
            dungeon1Sequence = dungeonFloorPanel.getCurrentFloorSequence();
            dungeon1FloorSize = currentFloorSize;
        }
        spawnerDataPanelBig.setDungeonSequenceTextField(floorSequence);
    }

    public void swapDungeon() {
        if (doubleSpawnerMode) {
            if (currentDungeonFloor == 1) {
                swapToFloor2();
            } else {
                swapToFloor1();
            }
        }
    }

    public void radioButtonClicked(JRadioButton button) {
        if (button == versionPanel.dungeonCountRadio1) {
            currentDungeonFloor = 1;
            doubleSpawnerMode = false;
            spawnerDataPanelBig.hideSwapDungeonButton();
            swapToFloor1();
        } else {
            doubleSpawnerMode = true;
            spawnerDataPanelBig.showSwapDungeonButton();
        }
    }

    public void saveCurrentDungeonCoords(String dungeonX, String dungeonY, String dungeonZ) {
        if (currentDungeonFloor == 1) {
            dungeon1x = safeIntParse(dungeonX);
            dungeon1y = safeIntParse(dungeonY);
            dungeon1z = safeIntParse(dungeonZ);
        } else {
            dungeon2x = safeIntParse(dungeonX);
            dungeon2y = safeIntParse(dungeonY);
            dungeon2z = safeIntParse(dungeonZ);
        }
    }

    private void getCurrentDungeonCoords() {
        saveCurrentDungeonCoords(spawnerDataPanelBig.spawnerXField.getText(), spawnerDataPanelBig.spawnerYField.getText(), spawnerDataPanelBig.spawnerZField.getText());
    }

    public void versionChangedAbove112() {
        getCurrentDungeonCoords();
        currentDungeonFloor = 1;
        doubleSpawnerMode = false;
        spawnerDataPanelBig.hideSwapDungeonButton();
        swapToFloor1();

    }

    public void versionChangedBelow113() {
        getCurrentDungeonCoords();
        doubleSpawnerMode = true;
        spawnerDataPanelBig.showSwapDungeonButton();
    }

    public static int getDungeonFloorSizeX(String sequence) {
        if (sequence.length() == 81) {
            return 9;
        } else if (sequence.length() == 63) {
            return 7;
        } else {
            return 7;
        }
    }

    public static int getDungeonFloorSizeZ(String sequence) {
        if (sequence.length() == 81) {
            return 9;
        } else if (sequence.length() == 63) {
            return 9;
        } else {
            return 7;
        }
    }

    public void crackSeed() {
        System.out.println("\nDungeon 1: " + dungeon1x + " " + dungeon1y + " " + dungeon1z + " " + dungeon1Sequence + " " + spawnerDataPanelBig.dungeon1Biome +  "\nDungeon 2: "  + dungeon2x + " " + dungeon2y + " " + dungeon2z + " " + dungeon2Sequence + " " + spawnerDataPanelBig.dungeon2Biome);

        JTextArea worldSeedsTextArea = new JTextArea();
        worldSeedsTextArea.setEditable(false);
        dungeon1fsx = getDungeonFloorSizeX(dungeon1Sequence);
        dungeon1fsz = getDungeonFloorSizeZ(dungeon1Sequence);
        dungeon2fsx = getDungeonFloorSizeX(dungeon2Sequence);
        dungeon2fsz = getDungeonFloorSizeZ(dungeon2Sequence);

        //Probably should add some kind of check where if there are more than 1 dungeon seeds in the dungeon seed set<long> then we don't try to find all matching world seeds because the calculation time might be incredibly long

        if (doubleSpawnerMode) {
            //2 spawners
            dungeon1Seeds = Main.getDungeonSeedsForGUI(versionPanel.currentVersionSelected, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence,dungeon1fsx,dungeon1fsz);
            dungeon2Seeds = Main.getDungeonSeedsForGUI(versionPanel.currentVersionSelected, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence,dungeon2fsx,dungeon2fsz);
            if(currentDungeonFloor == 1){
                spawnerDataPanelBig.dungeonSeedField.setText(dungeon1Seeds.toString());
            } else {
                spawnerDataPanelBig.dungeonSeedField.setText(dungeon2Seeds.toString());
            }
            worldSeeds = Main.getWorldSeedsForGUIDoubleDungeon(versionPanel.currentVersionSelected, dungeon1x, dungeon1z, BiomeNameToBiome.getBiomeFromString(spawnerDataPanelBig.dungeon1Biome), dungeon1Seeds, dungeon2x, dungeon2z, BiomeNameToBiome.getBiomeFromString(spawnerDataPanelBig.dungeon2Biome), dungeon2Seeds);
        } else {
            //1 spawner
            dungeon1Seeds = Main.getDungeonSeedsForGUI(versionPanel.currentVersionSelected, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence,dungeon1fsx,dungeon1fsz);
            spawnerDataPanelBig.dungeonSeedField.setText(dungeon1Seeds.toString());
            worldSeeds = Main.getWorldSeedsForGUISingleDungeon(versionPanel.currentVersionSelected, dungeon1x, dungeon1z, BiomeNameToBiome.getBiomeFromString(spawnerDataPanelBig.dungeon2Biome), dungeon1Seeds);
        }
        populateWorldSeedsInTextArea(worldSeedsTextArea, worldSeeds);
        JOptionPane.showMessageDialog(this, worldSeedsTextArea, "Potential World Seeds", JOptionPane.PLAIN_MESSAGE);

    }

    private void populateWorldSeedsInTextArea(JTextArea textArea, Set<Long> worldSeeds){
        StringBuilder textAreaSeeds = new StringBuilder();
        for(Long seed : worldSeeds){
            textAreaSeeds.append(seed).append("\n");
        }
        textArea.setText(String.valueOf(textAreaSeeds));
    }

    //Listeners
    //Action Listener events
    @Override
    public void actionPerformed(ActionEvent e) {
        this.hasMouseExited = false;
    }

    //Mouse listener events
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.hasMouseExited = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        //If mouse is inside the button..
        if (!hasMouseExited) {
            //Left mouse button pressed, released, and not exited button..
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (size7x7.equals(button)) {
                    dungeonFloorPanel.setCurrentFloorSize(49);
                    spawnerDataPanelBig.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                    if (currentDungeonFloor == 2) {
                        dungeon2FloorSize = 49;
                    } else {
                        dungeon1FloorSize = 49;
                    }
                } else if (size7x9.equals(button)) {
                    dungeonFloorPanel.setCurrentFloorSize(63);
                    spawnerDataPanelBig.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                    if (currentDungeonFloor == 2) {
                        dungeon2FloorSize = 63;
                    } else {
                        dungeon1FloorSize = 63;
                    }
                } else if (size9x9.equals(button)) {
                    dungeonFloorPanel.setCurrentFloorSize(81);
                    spawnerDataPanelBig.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                    if (currentDungeonFloor == 2) {
                        dungeon2FloorSize = 81;
                    } else {
                        dungeon1FloorSize = 81;
                    }
                } else if (rotateCounterClockwise.equals(button)) {
                    dungeonFloorPanel.rotateFloorCounterclockwise();
                    spawnerDataPanelBig.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                } else if (rotateClockwise.equals(button)) {
                    dungeonFloorPanel.rotateFloorClockwise();
                    spawnerDataPanelBig.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                }
            }
        }
    }
}
