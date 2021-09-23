package kinomora.gui.dungeondatatab;

import kinomora.gui.util.FloorButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DungeonFloorPanel extends JPanel {
    public final DungeonDataTab parent;
    public static final Map<Integer, FloorButton> buttonIDLookup = new ConcurrentHashMap<>();
    public static int[][] buttonStateArrayCurrent = new int[9][9];
    public static int[][] buttonStateArrayRotated = new int[9][9];
    public static int[][] sequenceHack = new int[9][9];

    //Yeah I realize there's probably a better way to do this but I don't care >:C
    public static boolean[] sevenbyseven = new boolean[] {false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, false, false, true, true,
                                                          true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true,
                                                          true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false,
                                                          false};
    public static boolean[] sevenbynine = new boolean[] {false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true
        , true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true,
                                                         false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false};
    public static boolean[] ninebyseven = new boolean[] {false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true,
                                                         true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
                                                         true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false};
    public static boolean[] ninebynine = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
        , true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
                                                        true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};

    public int currentFloorSize = 81;
    public int dungeon1FloorSize = 81;
    public int dungeon2FloorSize = 81;
    public int[] currentFloorDimension = {9, 9};
    public int[] dungeon1FloorDimension = {9, 9};
    public int[] dungeon2FloorDimension = {9, 9};
    public String currentDungeonFloorSequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";

    //Compat mode variable
    public boolean twoButtonMouseCompatMode;

    public DungeonFloorPanel(DungeonDataTab parent) {
        this.parent = parent;
        this.twoButtonMouseCompatMode = parent.twoButtonMouseCompatMode;

        this.setLayout(new GridLayout(9, 9, 0, 0));
        this.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1, true),
            "Dungeon 1",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION
        ));
        this.setSize(250, 250);
        this.createButtonMap();
        this.populatePanel();
    }

    /**
     * Adds all the item icons inside this panel
     */
    private void populatePanel() {
        for (int i = 0; i < buttonIDLookup.size(); i++) {
            this.add(buttonIDLookup.get(i));
        }
    }

    public void setCompatMode(boolean mode) {
        this.twoButtonMouseCompatMode = mode;
    }

    private void createButtonMap() {
        for (int ID = 0; ID < 81; ID++) {
            buttonIDLookup.put(ID, new FloorButton(ID, this));
        }
    }

    public void setDungeonFloorPanelTitle(String title) {
        this.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1, true),
            title,
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION
        ));
    }

    public int getCurrentFloorSize() {
        return this.currentFloorSize;
    }

    public void setCurrentFloorSize(int size) {
        this.currentFloorSize = size;
        int ID = 0;
        FloorButton button;
        if (size == 81) {
            for (boolean visibility : ninebynine) {
                button = buttonIDLookup.get(ID);
                button.setVisible(visibility);
                ID++;
            }
            currentFloorSize = 81;
        } else if (size == 63) {
            for (boolean visibility : sevenbynine) {
                button = buttonIDLookup.get(ID);
                button.setVisible(visibility);
                ID++;
            }
            currentFloorSize = 63;
        } else if (size == 49) {
            for (boolean visibility : sevenbyseven) {
                button = buttonIDLookup.get(ID);
                button.setVisible(visibility);
                ID++;
            }
            currentFloorSize = 49;
        } else {
            System.out.println("Critical error in setting current floor size..");
            System.exit(1);
        }
    }

    public int[] getCurrentFloorDimension() {
        return this.currentFloorDimension;
    }

    public void setCurrentFloorDimension(int[] dimension) {
        this.currentFloorDimension = dimension;
        int ID = 0;
        FloorButton button;

        if (dimension[0] == 7) {
            if (dimension[1] == 7) {        //7x7
                for (boolean visibility : sevenbyseven) {
                    button = buttonIDLookup.get(ID);
                    button.setVisible(visibility);
                    ID++;
                }
                currentFloorSize = 49;
            } else if (dimension[1] == 9) { //7x9
                for (boolean visibility : sevenbynine) {
                    button = buttonIDLookup.get(ID);
                    button.setVisible(visibility);
                    ID++;
                }
                currentFloorSize = 63;
            }
        } else if (dimension[0] == 9) {
            if (dimension[1] == 7) {        //9x7
                for (boolean visibility : ninebyseven) {
                    button = buttonIDLookup.get(ID);
                    button.setVisible(visibility);
                    ID++;
                }
                currentFloorSize = 63;
            } else if (dimension[1] == 9) { //9x9
                for (boolean visibility : ninebynine) {
                    button = buttonIDLookup.get(ID);
                    button.setVisible(visibility);
                    ID++;
                }
                currentFloorSize = 81;
            }

        } else {
            System.out.println("Critical error in setting current floor dimension..");
            System.out.println(Arrays.toString(dimension));
            System.exit(1);
        }
    }

    public void setDungeonFloorSize(int floorSize, int dungeon) {
        if (dungeon == 1) {
            dungeon1FloorSize = floorSize;
        } else {
            dungeon2FloorSize = floorSize;
        }
        setCurrentFloorSize(floorSize);
    }

    public void setDungeonFloorDimensions(int[] dimensions, int dungeon) {
        if (dungeon == 1) {
            dungeon1FloorDimension[0] = dimensions[0];
            dungeon1FloorDimension[1] = dimensions[1];
        } else {
            dungeon2FloorDimension[0] = dimensions[0];
            dungeon2FloorDimension[1] = dimensions[1];
        }
        setCurrentFloorDimension(dimensions);
    }

    public String getCurrentFloorSequence() {
        StringBuilder sequence = new StringBuilder();
        FloorButton button;

        //Fill in the sequence array so it can be iterated through
        for (int ID = 0; ID < 81; ID++) {
            button = buttonIDLookup.get(ID);
            if (button.isVisible()) {
                sequenceHack[ID / 9][ID % 9] = button.getButtonSequenceDigit();
            } else {
                sequenceHack[ID / 9][ID % 9] = -1;
            }
        }

        //Parse the array top-to-bottom, left-to-right
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (sequenceHack[j][i] >= 0) {
                    sequence.append(sequenceHack[j][i]);
                }
            }
        }

        this.currentDungeonFloorSequence = sequence.toString();
        return currentDungeonFloorSequence;
    }

    public void setCurrentFloorPattern(int[][] newSequence) {
        FloorButton button;
        for (int i = 0; i < 81; i++) {
            button = buttonIDLookup.get(i);
            button.setButtonStateByDigit(newSequence[i / 9][i % 9]);
        }
    }

    public int[][] getCurrentFloorStateArray() {
        FloorButton button;

        for (int ID = 0; ID < 81; ID++) {
            button = buttonIDLookup.get(ID);
            buttonStateArrayCurrent[ID / 9][ID % 9] = button.getButtonSequenceDigit();
        }

        return buttonStateArrayCurrent;
    }

    public void resetDungeonData(int reset) {
        currentFloorSize = 81;
        currentFloorDimension[0] = 9;
        currentFloorDimension[1] = 9;
        currentDungeonFloorSequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";

        if (reset == 1) {
            dungeon1FloorSize = 81;
            dungeon1FloorDimension[0] = 9;
            dungeon1FloorDimension[1] = 9;
        }
        if (reset == 2) {
            dungeon2FloorSize = 81;
            dungeon2FloorDimension[0] = 9;
            dungeon2FloorDimension[1] = 9;
        }
        if (reset == 3) {
            dungeon1FloorSize = 81;
            dungeon1FloorDimension[0] = 9;
            dungeon1FloorDimension[1] = 9;
            dungeon2FloorSize = 81;
            dungeon2FloorDimension[0] = 9;
            dungeon2FloorDimension[1] = 9;
        }
    }

    public void rotateFloorClockwise() {
        //Import the current state into the array
        importButtonsDigitsToArray();

        //Rotate the array
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                buttonStateArrayRotated[i][j] = buttonStateArrayCurrent[8 - j][i];
            }
        }

        //Export the rotated array state to the button map
        exportArrayToButtons();
    }

    public void rotateFloorCounterclockwise() {
        //Import the current state into the array
        importButtonsDigitsToArray();

        //Rotate the array
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                buttonStateArrayRotated[i][j] = buttonStateArrayCurrent[j][8 - i];
            }
        }

        //Export the rotated array state to the button map
        exportArrayToButtons();
    }

    //Yes I am just as mad about this method name as you are
    private void importButtonsDigitsToArray() {
        FloorButton button;

        //Import the current state into the array
        for (int ID = 0; ID < 81; ID++) {
            button = buttonIDLookup.get(ID);
            buttonStateArrayCurrent[ID / 9][ID % 9] = button.getButtonSequenceDigit();
        }
    }

    private void exportArrayToButtons() {
        FloorButton button;

        //Export the rotated array state to the button map
        for (int ID2 = 0; ID2 < 81; ID2++) {
            button = buttonIDLookup.get(ID2);
            button.setButtonStateByDigit(buttonStateArrayRotated[ID2 / 9][ID2 % 9]);
        }
        parent.floorButtonPressed(getCurrentFloorSequence(), getCurrentFloorStateArray(), getCurrentFloorSize(), getCurrentFloorDimension());
    }

    public void floorButtonPressed() {
        parent.floorButtonPressed(getCurrentFloorSequence(), getCurrentFloorStateArray(), getCurrentFloorSize(), getCurrentFloorDimension());
        parent.resetDungeonSeeds(parent.currentDungeonFloor);
        //System.out.println("Button ID pressed: " + button.getID() + "; State: " + button.getButtonSequenceDigit());
    }
}
