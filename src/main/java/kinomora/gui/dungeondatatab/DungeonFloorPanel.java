package kinomora.gui.dungeondatatab;

import javafx.util.Pair;
import kinomora.gui.util.FloorButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class DungeonFloorPanel extends JPanel {
    public final DungeonDataTab parent;
    public static final Map<Integer, FloorButton> buttonIDLookup = new HashMap<>();
    public static boolean[] sevenbyseven = new boolean[]{
            false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false
    };
    public static boolean[] sevenbynine = new boolean[]{false, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false
    };
    public static boolean[] ninebynine = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
    };
    public int floorSize = 81;

    public DungeonFloorPanel(DungeonDataTab parent) {
        this.parent = parent;

        this.setLayout(new GridLayout(9, 9, 0, 0));
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1, true),
                "Dungeon Floor",
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
            buttonIDLookup.put(ID, new FloorButton(ID));
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

    public void setFloorSize(int size) {
        this.floorSize = size;

        if (size == 81) {

        } else if (size == 63) {

        } else if (size == 49) {

        } else {
            System.exit(1);
        }
    }

    public String get9x9FloorSequence() {

        return "";
    }
}