package kinomora.gui.dungeondatatab;

import kinomora.Main;

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
    public final SpawnerDataPanel spawnerDataPanel;
    private static final ImageIcon CLOCKWISE_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/rotateIcons/CW.png")));
    private static final ImageIcon COUNTERCLOCKWISE_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/rotateIcons/CCW.png")));
    private static final ImageIcon SPAWNER_SHAPE_SMALL_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/spawnerIcons/small.png")));
    private static final ImageIcon SPAWNER_SHAPE_TALL_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/spawnerIcons/tall.png")));
    private static final ImageIcon SPAWNER_SHAPE_LONG_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/spawnerIcons/long.png")));
    private static final ImageIcon SPAWNER_SHAPE_BIG_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/spawnerIcons/big.png")));
    private static final ImageIcon TRASH_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/extraIcons/trash.png")));
    private static final ImageIcon TRASH_ALL_ICON = new ImageIcon(Objects.requireNonNull(DungeonDataTab.class.getResource("/extraIcons/trashAll.png")));
    public int currentDungeonFloor = 1;
    public static int[][] emptyFloorSequence = new int[9][9];
    public static int[][] buttonStateArrayDungeon1 = new int[9][9];
    public static int[][] buttonStateArrayDungeon2 = new int[9][9];
    public boolean twoButtonMouseCompatMode = false;
    JButton rotateCounterClockwise = new JButton(COUNTERCLOCKWISE_ICON);
    JButton size7x7 = new JButton(SPAWNER_SHAPE_SMALL_ICON);
    JButton size7x9 = new JButton(SPAWNER_SHAPE_TALL_ICON);
    JButton size9x7 = new JButton(SPAWNER_SHAPE_LONG_ICON);
    JButton size9x9 = new JButton(SPAWNER_SHAPE_BIG_ICON);
    JButton rotateClockwise = new JButton(CLOCKWISE_ICON);
    JButton clearFloorData = new JButton(TRASH_ICON);
    JButton clearProgramData = new JButton(TRASH_ALL_ICON);
    JButton crackSeedButton = new JButton("Crack Seed");
    //---Application data---
    //Program data
    boolean validInput;
    boolean doubleSpawnerMode = false;
    //Dungeon data
    Set<Long> dungeon1Seeds = new HashSet<>();
    int dungeon1x = 0;
    int dungeon1y = 0;
    int dungeon1z = 0;
    int dungeon1FloorSize = 81;
    int[] dungeon1FloorDimensions = {9, 9};
    String dungeon1Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
    Set<Long> dungeon2Seeds = new HashSet<>();
    int dungeon2x = 0;
    int dungeon2y = 0;
    int dungeon2z = 0;
    int dungeon2FloorSize = 81;
    int[] dungeon2FloorDimensions = {9, 9};
    String dungeon2Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
    Set<Long> worldSeeds;
    private boolean hasMouseExited;

    /**
     * Creates and populates the Items(Overview) tab which contains the Inventory, Item Descriptions, Recommended Champions, and Inventory Item Crafting Calculator
     */
    public DungeonDataTab() {
        dungeonFloorPanel = new DungeonFloorPanel(this);
        versionPanel = new VersionPanel(this);
        spawnerDataPanel = new SpawnerDataPanel(this);

        //Set the layout for the window to be two halves
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 0));

        //Create panels for each half
        JPanel leftPanel = new JPanel(new GridBagLayout());  //gridx = left and right
        JPanel rightPanel = new JPanel(new GridBagLayout()); //gridy = up and down

        //Create subpanels for each section of the Dungeon Floor side
        JPanel dungeonSubButtonPanel = new JPanel(new GridBagLayout());

        // Right Panel (dungeon floor) objects
        dungeonFloorSubButtonMess(dungeonSubButtonPanel);

        // Other Swing Elements
        JLabel dungeonNorthLabel = new JLabel("Floor relative to North");
        dungeonNorthLabel.setFont(new Font(dungeonNorthLabel.getName(), Font.PLAIN, 14));
        crackSeedButton.setFocusPainted(false);
        crackSeedButton.setFocusable(false);
        crackSeedButton.addMouseListener(this);
        crackSeedButton.addActionListener(this);


        this.add(leftPanel, setC(0, 0, 1, 1, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 0, 0)));
        this.add(rightPanel, setC(1, 0, 1, 1, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 0, 0)));


        // Adding the Dungeon Floor panel and the Dungeon Floor Buttons to the LEFT Panel
        leftPanel.add(dungeonNorthLabel, setC(0, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.CENTER, new Insets(10, 0, 0, 0)));
        leftPanel.add(dungeonFloorPanel, setC(0, 1, 1, 1, 0, 0, 0, 0, GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));
        leftPanel.add(dungeonSubButtonPanel, setC(0, 2, 1, 1, 0, 0, 0, 0, GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));

        // Adding the Version Panel and the Dungeon Data Big panel to the RIGHT Panel
        rightPanel.add(versionPanel, setC(0, 0, 1, 1, 55, 8, 1, 1, GridBagConstraints.FIRST_LINE_START, new Insets(37, 0, 0, 0)));
        rightPanel.add(spawnerDataPanel, setC(0, 1, 1, 1, 6, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, new Insets(7, 0, 0, 0)));
        rightPanel.add(crackSeedButton, setC(0, 2, 1, 1, 150, 0, 1, 1, GridBagConstraints.PAGE_START, new Insets(7, -2, 0, 0)));

        //Set all the buttonStateArrays to 2 by default
        for (int i = 0; i < 9; i++) {
            Arrays.fill(buttonStateArrayDungeon1[i], 2);
            Arrays.fill(buttonStateArrayDungeon2[i], 2);
            Arrays.fill(emptyFloorSequence[i], 2);
        }
    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, int gridheight, int ipadx, int ipady, int weightx, int weighty, int anchor, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = weightx;
        c.weighty = weighty;
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
        rotateCounterClockwise.setToolTipText("Rotate the entire floor Counter-Clockwise");

        size7x7.setFocusPainted(false);
        size7x7.setFocusable(false);
        size7x7.addMouseListener(this);
        size7x7.addActionListener(this);
        size7x7.setToolTipText("7x7");

        size7x9.setFocusPainted(false);
        size7x9.setFocusable(false);
        size7x9.addMouseListener(this);
        size7x9.addActionListener(this);
        size7x9.setToolTipText("7x9");

        size9x7.setFocusPainted(false);
        size9x7.setFocusable(false);
        size9x7.addMouseListener(this);
        size9x7.addActionListener(this);
        size9x7.setToolTipText("9x7");

        size9x9.setFocusPainted(false);
        size9x9.setFocusable(false);
        size9x9.addMouseListener(this);
        size9x9.addActionListener(this);
        size9x9.setToolTipText("9x9");

        rotateClockwise.setFocusPainted(false);
        rotateClockwise.setFocusable(false);
        rotateClockwise.addMouseListener(this);
        rotateClockwise.addActionListener(this);
        rotateClockwise.setToolTipText("Rotate the entire floor Clockwise");

        clearFloorData.setFocusPainted(false);
        clearFloorData.setFocusable(false);
        clearFloorData.addMouseListener(this);
        clearFloorData.addActionListener(this);
        clearFloorData.setToolTipText("Clear the current dungeon floor pattern");

        clearProgramData.setFocusPainted(false);
        clearProgramData.setFocusable(false);
        clearProgramData.addMouseListener(this);
        clearProgramData.addActionListener(this);
        clearProgramData.setToolTipText("Clear ALL dungeon floor patterns, coordinates, biomes, and sequence");

        //Fill in the dungeon subpanels
        dungeonSubButtonPanel.add(rotateCounterClockwise, setC(0, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 0, 0, 0)));
        dungeonSubButtonPanel.add(size7x7, setC(1, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
        dungeonSubButtonPanel.add(size7x9, setC(2, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
        dungeonSubButtonPanel.add(size9x7, setC(3, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
        dungeonSubButtonPanel.add(size9x9, setC(4, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
        dungeonSubButtonPanel.add(rotateClockwise, setC(5, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));

        JLabel separator = new JLabel("|");
        separator.setFont(new Font(separator.getName(), Font.PLAIN, 16));
        dungeonSubButtonPanel.add(separator, setC(6, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 15, 0, 15)));

        dungeonSubButtonPanel.add(clearFloorData, setC(7, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 0, 0, 0)));
        dungeonSubButtonPanel.add(clearProgramData, setC(8, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
    }

    public void setTwoButtonMouseCompatMode(boolean mode) {
        this.twoButtonMouseCompatMode = mode;
        dungeonFloorPanel.setCompatMode(mode);
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
        dungeonFloorPanel.setDungeonFloorPanelTitle("Dungeon 1");
        dungeonFloorPanel.setCurrentFloorPattern(buttonStateArrayDungeon1);
        dungeonFloorPanel.setDungeonFloorSize(dungeon1FloorSize, 1);
        dungeonFloorPanel.setDungeonFloorDimensions(dungeon1FloorDimensions, 1);
        spawnerDataPanel.setCurrentBiomeDropdownValue(1);
        spawnerDataPanel.setDungeonXYZLabelText(1);
        spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
        spawnerDataPanel.setTextFieldValue(dungeon1x, dungeon1y, dungeon1z);
        spawnerDataPanel.dungeonSeedField.setText(dungeon1Seeds.toString());
    }

    public void swapToFloor2() {
        currentDungeonFloor = 2;
        dungeonFloorPanel.setDungeonFloorPanelTitle("Dungeon 2");
        dungeonFloorPanel.setCurrentFloorPattern(buttonStateArrayDungeon2);
        dungeonFloorPanel.setDungeonFloorSize(dungeon2FloorSize, 2);
        dungeonFloorPanel.setDungeonFloorDimensions(dungeon2FloorDimensions, 2);
        spawnerDataPanel.setCurrentBiomeDropdownValue(2);
        spawnerDataPanel.setDungeonXYZLabelText(2);
        spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
        spawnerDataPanel.setTextFieldValue(dungeon2x, dungeon2y, dungeon2z);
        spawnerDataPanel.dungeonSeedField.setText(dungeon2Seeds.toString());
    }

    //Methods with data sent from other classes
    public void floorButtonPressed(String floorSequence, int[][] buttonStateArrayCurrent, int currentFloorSize, int[] currentFloorDimensions) {
        if (currentDungeonFloor == 2) {
            buttonStateArrayDungeon2 = Arrays.stream(buttonStateArrayCurrent).map(int[]::clone).toArray(int[][]::new);
            dungeon2Sequence = dungeonFloorPanel.getCurrentFloorSequence();
            dungeon2FloorSize = currentFloorSize;
            dungeon2FloorDimensions[0] = currentFloorDimensions[0];
            dungeon2FloorDimensions[1] = currentFloorDimensions[1];
        } else {
            buttonStateArrayDungeon1 = Arrays.stream(buttonStateArrayCurrent).map(int[]::clone).toArray(int[][]::new);
            dungeon1Sequence = dungeonFloorPanel.getCurrentFloorSequence();
            dungeon1FloorSize = currentFloorSize;
            dungeon1FloorDimensions[0] = currentFloorDimensions[0];
            dungeon1FloorDimensions[1] = currentFloorDimensions[1];
        }
        spawnerDataPanel.setDungeonSequenceTextField(floorSequence);
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
        if (button == versionPanel.dungeonCount1Radio) {
            currentDungeonFloor = 1;
            doubleSpawnerMode = false;
            spawnerDataPanel.hideSwapDungeonButton();
            swapToFloor1();
        } else {
            doubleSpawnerMode = true;
            spawnerDataPanel.showSwapDungeonButton();
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
        saveCurrentDungeonCoords(spawnerDataPanel.spawnerXField.getText(), spawnerDataPanel.spawnerYField.getText(), spawnerDataPanel.spawnerZField.getText());
    }

    public void versionChangedAbove112() {
        getCurrentDungeonCoords();
        currentDungeonFloor = 1;
        doubleSpawnerMode = false;
        spawnerDataPanel.hideSwapDungeonButton();
        swapToFloor1();

    }

    public void versionChangedBelow113() {
        getCurrentDungeonCoords();
        doubleSpawnerMode = true;
        spawnerDataPanel.showSwapDungeonButton();
    }

    public void crackDungeonSeed(int dungeon) {
        saveCurrentDungeonCoords(spawnerDataPanel.spawnerXField.getText(), spawnerDataPanel.spawnerYField.getText(), spawnerDataPanel.spawnerZField.getText());

        System.out.println("\nDungeon 1: " + dungeon1x + " " + dungeon1y + " " + dungeon1z + " " + dungeon1Sequence + " " + spawnerDataPanel.dungeon1Biome + "\nDungeon 2: " + dungeon2x + " " + dungeon2y + " " + dungeon2z + " " + dungeon2Sequence + " " + spawnerDataPanel.dungeon2Biome);
        if (dungeon == 1) {
            if (isSequenceAll2s(dungeon1Sequence)) {
                System.out.println("Your dungeon sequence is empty and continuing would cause the app to hang.\nEnter in your dungeon floor pattern before continuing.");
                JOptionPane.showMessageDialog(this, "Your dungeon sequence is empty and continuing would cause the app to hang.\nEnter in your dungeon floor pattern before continuing.", "Missing data!", JOptionPane.ERROR_MESSAGE);
            } else {
                dungeon1Seeds = Main.getDungeonSeedsForGUI(versionPanel.currentVersionSelected, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon1FloorDimensions[0],
                    dungeon1FloorDimensions[1]);
            }
            if (dungeon1Seeds.isEmpty()) {
                spawnerDataPanel.dungeonSeedField.setText("No results.");
            } else {
                spawnerDataPanel.dungeonSeedField.setText(dungeon1Seeds.toString());
            }
        }
        if (dungeon == 2) {
            if (isSequenceAll2s(dungeon2Sequence)) {
                System.out.println("Your dungeon sequence is empty and continuing would cause the app to hang.\nEnter in your dungeon floor pattern before continuing.");
                JOptionPane.showMessageDialog(this, "Your dungeon sequence is empty and continuing would cause the app to hang.\nEnter in your dungeon floor pattern before continuing.", "Missing data!", JOptionPane.ERROR_MESSAGE);
            } else {
                dungeon2Seeds = Main.getDungeonSeedsForGUI(versionPanel.currentVersionSelected, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence, dungeon2FloorDimensions[0],
                    dungeon2FloorDimensions[1]);
            }
            if (dungeon2Seeds.isEmpty()) {
                spawnerDataPanel.dungeonSeedField.setText("No results.");
            } else {
                spawnerDataPanel.dungeonSeedField.setText(dungeon2Seeds.toString());
            }
        }
        if (dungeon == 3) {
            if (isSequenceAll2s(dungeon1Sequence) || isSequenceAll2s(dungeon2Sequence)) {
                System.out.println("One of your dungeon sequences is empty and continuing would cause the app to hang.\nEnter in your other dungeon info or switch to 1 Dungeon mode.");
                JOptionPane.showMessageDialog(this, "One of your dungeon sequences is empty and continuing would cause the app to hang.\nEnter in your other dungeon info or switch to 1 Dungeon mode.", "Missing data!", JOptionPane.ERROR_MESSAGE);
            } else {
                dungeon1Seeds = Main.getDungeonSeedsForGUI(versionPanel.currentVersionSelected, dungeon1x, dungeon1y, dungeon1z, dungeon1Sequence, dungeon1FloorDimensions[0],
                    dungeon1FloorDimensions[1]);
                dungeon2Seeds = Main.getDungeonSeedsForGUI(versionPanel.currentVersionSelected, dungeon2x, dungeon2y, dungeon2z, dungeon2Sequence, dungeon2FloorDimensions[0],
                    dungeon2FloorDimensions[1]);
                if (currentDungeonFloor == 1) {
                    if (dungeon1Seeds.isEmpty()) {
                        spawnerDataPanel.dungeonSeedField.setText("No results.");
                    } else {
                        spawnerDataPanel.dungeonSeedField.setText(dungeon1Seeds.toString());
                    }
                } else {
                    if (dungeon2Seeds.isEmpty()) {
                        spawnerDataPanel.dungeonSeedField.setText("No results.");
                    } else {
                        spawnerDataPanel.dungeonSeedField.setText(dungeon2Seeds.toString());
                    }
                }
            }
        }
    }

    public void crackSeed() {
        JTextArea worldSeedsTextArea = new JTextArea();
        worldSeedsTextArea.setEditable(false);

        //Added checking code to stop the app from running if crack seed is pressed without any data put into the floor
        if (doubleSpawnerMode) {
            //2 spawners
            if (isSequenceAll2s(dungeon1Sequence) || isSequenceAll2s(dungeon2Sequence)) {
                System.out.println("One of your dungeon sequences is empty and continuing would cause the app to hang.\nEnter in your other dungeon info or switch to 1 Dungeon mode.");
                JOptionPane.showMessageDialog(this, "One of your dungeon sequences is empty and continuing would cause the app to hang.\nEnter in your other dungeon info or switch to 1 Dungeon mode.", "Missing data!", JOptionPane.ERROR_MESSAGE);
            } else {
                if (dungeon1Seeds.isEmpty() || dungeon2Seeds.isEmpty()) {
                    crackDungeonSeed(3);
                }
                if (currentDungeonFloor == 1) {
                    spawnerDataPanel.dungeonSeedField.setText(dungeon1Seeds.toString());
                } else {
                    spawnerDataPanel.dungeonSeedField.setText(dungeon2Seeds.toString());
                }
                worldSeeds = Main.getWorldSeedsForGUIDoubleDungeon(versionPanel.currentVersionSelected, dungeon1x, dungeon1z, spawnerDataPanel.dungeon1Biome,
                    dungeon1Seeds, dungeon2x, dungeon2z, spawnerDataPanel.dungeon2Biome, dungeon2Seeds);
                if (worldSeeds.isEmpty()) {
                    worldSeedsTextArea.setText("No valid results");
                } else {
                    populateWorldSeedsInTextArea(worldSeedsTextArea, worldSeeds);
                    JOptionPane.showMessageDialog(this, worldSeedsTextArea, "Potential World Seeds", JOptionPane.PLAIN_MESSAGE);
                }
            }

        } else {
            //1 spawner
            if (isSequenceAll2s(dungeon1Sequence)) {
                System.out.println("Your dungeon sequence is empty and continuing would cause the app to hang.\nEnter in your other dungeon info before pressing Crack Seed.");
                JOptionPane.showMessageDialog(this, "Your dungeon sequence is empty and continuing would cause the app to hang.\nEnter in your other dungeon info before pressing Crack Seed.", "Missing data!", JOptionPane.ERROR_MESSAGE);
            } else {
                if (dungeon1Seeds.isEmpty()) {
                    crackDungeonSeed(1);
                }
                spawnerDataPanel.dungeonSeedField.setText(dungeon1Seeds.toString());
                worldSeeds = Main.getWorldSeedsForGUISingleDungeon(versionPanel.currentVersionSelected, dungeon1x, dungeon1z, spawnerDataPanel.dungeon2Biome,
                    dungeon1Seeds);
                if (worldSeeds.isEmpty()) {
                    worldSeedsTextArea.setText("No valid results");
                } else {
                    populateWorldSeedsInTextArea(worldSeedsTextArea, worldSeeds);
                    JOptionPane.showMessageDialog(this, worldSeedsTextArea, "Potential World Seeds", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    private boolean isSequenceAll2s(String sequence) {
        for (char ch : sequence.toCharArray()) {
            if (!(ch == '2')) {
                return false;
            }
        }
        return true;
    }

    private void populateWorldSeedsInTextArea(JTextArea textArea, Set<Long> worldSeeds) {
        StringBuilder textAreaSeeds = new StringBuilder();
        for (Long seed : worldSeeds) {
            textAreaSeeds.append(seed).append("\n");
        }
        textArea.setText(String.valueOf(textAreaSeeds));
    }

    public void resetDungeonData(boolean resetAllData) {
        if (resetAllData) {
            for (int i = 0; i < 9; i++) {
                Arrays.fill(buttonStateArrayDungeon1[i], 2);
                Arrays.fill(buttonStateArrayDungeon2[i], 2);
            }
            dungeon1Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
            dungeon1FloorSize = 81;
            dungeon1FloorDimensions[0] = 9;
            dungeon1FloorDimensions[1] = 9;
            dungeon1x = 0;
            dungeon1y = 0;
            dungeon1z = 0;
            dungeon2Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
            dungeon2FloorSize = 81;
            dungeon2FloorDimensions[0] = 9;
            dungeon2FloorDimensions[1] = 9;
            dungeon2x = 0;
            dungeon2y = 0;
            dungeon2z = 0;
            resetDungeonSeeds(3);
            dungeonFloorPanel.resetDungeonData(3);

            //System.out.println("Both dungeon's floors, size, coords, and sequence have been reset");
        } else {
            if (currentDungeonFloor == 1) {
                for (int i = 0; i < 9; i++) {
                    Arrays.fill(buttonStateArrayDungeon1[i], 2);
                }
                dungeon1Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
                dungeon1FloorSize = 81;
                dungeon1FloorDimensions[0] = 9;
                dungeon1FloorDimensions[1] = 9;
                dungeon1x = 0;
                dungeon1y = 0;
                dungeon1z = 0;
                resetDungeonSeeds(1);
                dungeonFloorPanel.resetDungeonData(1);
            } else {
                for (int i = 0; i < 9; i++) {
                    Arrays.fill(buttonStateArrayDungeon2[i], 2);
                }
                dungeon2Sequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";
                dungeon2FloorSize = 81;
                dungeon2FloorDimensions[0] = 9;
                dungeon2FloorDimensions[1] = 9;
                dungeon2x = 0;
                dungeon2y = 0;
                dungeon2z = 0;
                resetDungeonSeeds(2);
                dungeonFloorPanel.resetDungeonData(2);
            }
        }
        dungeonFloorPanel.setCurrentFloorPattern(emptyFloorSequence);
        dungeonFloorPanel.setCurrentFloorSize(81);
        dungeonFloorPanel.setCurrentFloorDimension(new int[] {9, 9});
        spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
        spawnerDataPanel.setTextFieldValue(0, 0, 0);

        //System.out.println("Current dungeon floor data reset");
    }

    public void resetDungeonSeeds(int reset) {
        if (reset == 1) {
            dungeon1Seeds.clear();
        }
        if (reset == 2) {
            dungeon2Seeds.clear();
        }
        if (reset == 3) {
            dungeon1Seeds.clear();
            dungeon2Seeds.clear();
        }
        spawnerDataPanel.dungeonSeedField.setText("");
    }

    //Listeners
    //Action Listener events
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    //Mouse listener events
    @Override
    public void mouseEntered(MouseEvent e) {
        this.hasMouseExited = false;
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
                if (size7x7.equals(button)) { //small dungeon
                    spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                    if (currentDungeonFloor == 2) {
                        dungeon2FloorSize = 49;
                        dungeon2FloorDimensions[0] = 7;
                        dungeon2FloorDimensions[1] = 7;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon2FloorDimensions);
                    } else {
                        dungeon1FloorSize = 49;
                        dungeon1FloorDimensions[0] = 7;
                        dungeon1FloorDimensions[1] = 7;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon1FloorDimensions);
                    }
                } else if (size7x9.equals(button)) { //tall dungeon
                    spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                    if (currentDungeonFloor == 2) {
                        dungeon2FloorSize = 63;
                        dungeon2FloorDimensions[0] = 7;
                        dungeon2FloorDimensions[1] = 9;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon2FloorDimensions);
                    } else {
                        dungeon1FloorSize = 63;
                        dungeon1FloorDimensions[0] = 7;
                        dungeon1FloorDimensions[1] = 9;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon1FloorDimensions);
                    }
                } else if (size9x7.equals(button)) { //long dungeon
                    spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                    if (currentDungeonFloor == 2) {
                        dungeon2FloorSize = 63;
                        dungeon2FloorDimensions[0] = 9;
                        dungeon2FloorDimensions[1] = 7;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon2FloorDimensions);
                    } else {
                        dungeon1FloorSize = 63;
                        dungeon1FloorDimensions[0] = 9;
                        dungeon1FloorDimensions[1] = 7;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon1FloorDimensions);
                    }
                } else if (size9x9.equals(button)) { //big dungeon
                    spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                    if (currentDungeonFloor == 2) {
                        dungeon2FloorSize = 81;
                        dungeon2FloorDimensions[0] = 9;
                        dungeon2FloorDimensions[1] = 9;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon2FloorDimensions);
                    } else {
                        dungeon1FloorSize = 81;
                        dungeon1FloorDimensions[0] = 9;
                        dungeon1FloorDimensions[1] = 9;
                        dungeonFloorPanel.setCurrentFloorDimension(dungeon1FloorDimensions);
                    }
                } else if (rotateCounterClockwise.equals(button)) {
                    dungeonFloorPanel.rotateFloorCounterclockwise();
                    spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                } else if (rotateClockwise.equals(button)) {
                    dungeonFloorPanel.rotateFloorClockwise();
                    spawnerDataPanel.setDungeonSequenceTextField(dungeonFloorPanel.getCurrentFloorSequence());
                } else if (clearFloorData.equals(button)) {
                    resetDungeonData(false);
                } else if (clearProgramData.equals(button)) {
                    resetDungeonData(true);
                } else if (crackSeedButton.equals(button)) {
                    crackSeed();
                } else {
                    System.out.println("Boop.");
                }
            }
        }
    }
}
