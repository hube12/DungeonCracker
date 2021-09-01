package kinomora.gui.dungeondatatab;

import kinomora.gui.util.FloorButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DungeonFloorPanel extends JPanel {
    public final DungeonDataTab parent;
    public static final Map<Integer, FloorButton> buttonIDLookup = new HashMap<>();
    public static int[][] buttonStateArrayCurrent = new int[9][9];
    public static int[][] buttonStateArrayRotated = new int[9][9];
    public static int[][] sequenceHack = new int[9][9];

    //Yeah I realize there's probably a better way to do this but I don't care >:C
    public static boolean[] sevenbyseven = new boolean[]{false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false};
    public static boolean[] sevenbynine = new boolean[]{false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false};
    public static boolean[] ninebynine = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
    public int currentFloorSize = 81;
    public String floorSequence = "222222222222222222222222222222222222222222222222222222222222222222222222222222222";

    public DungeonFloorPanel(DungeonDataTab parent) {
        this.parent = parent;

        this.setLayout(new GridLayout(9, 9, 0, 0));
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1, true),
                "Dungeon Floor -  Top is in-game North",
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
        } else if (size == 63) {
            for (boolean visibility : sevenbynine) {
                button = buttonIDLookup.get(ID);
                button.setVisible(visibility);
                ID++;
            }
        } else if (size == 49) {
            for (boolean visibility : sevenbyseven) {
                button = buttonIDLookup.get(ID);
                button.setVisible(visibility);
                ID++;
            }
        } else {
            System.exit(1);
        }
    }

    public String getFloorSequence() {
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

        this.floorSequence = sequence.toString();
        return floorSequence;
    }

    public void rotateFloorClockwise() {
        //Import the current state into the array
        importt();

        //Rotate the array
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                buttonStateArrayRotated[i][j] = buttonStateArrayCurrent[8 - j][i];
            }
        }

        //Export the rotated array state to the button map
        export();
    }

    public void rotateFloorCounterclockwise() {
        //Import the current state into the array
        importt();

        //Rotate the array
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                buttonStateArrayRotated[i][j] = buttonStateArrayCurrent[j][8 - i];
            }
        }

        //Export the rotated array state to the button map
        export();
    }

    //Yes I am just as mad about this method name as you are
    private void importt() {
        FloorButton button;

        //Import the current state into the array
        for (int ID = 0; ID < 81; ID++) {
            button = buttonIDLookup.get(ID);
            buttonStateArrayCurrent[ID / 9][ID % 9] = button.getButtonSequenceDigit();
        }
    }

    private void export() {
        FloorButton button;

        //Export the rotated array state to the button map
        for (int ID2 = 0; ID2 < 81; ID2++) {
            button = buttonIDLookup.get(ID2);
            button.setButtonStateByDigit(buttonStateArrayRotated[ID2 / 9][ID2 % 9]);
        }

    }

    public void buttonPressed(FloorButton button) {
        parent.buttonPressed(getFloorSequence());
        //System.out.println("Button ID pressed: " + button.getID() + "; State: " + button.getButtonSequenceDigit());
    }
}