package kinomora.gui.dungeondatatab;

import kinomora.gui.util.FloorButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DungeonFloorPanel extends JPanel {

    public final DungeonDataTab parent;

    public DungeonFloorPanel(DungeonDataTab parent) {
        this.parent = parent;

        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                "Dungeon Floor",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION
        ));

        this.populateFloor();
    }

    /**
     * Adds all the item icons inside this panel
     */
    private void populateFloor() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                FloorButton button = new FloorButton();
                this.add(button);
            }
        }
    }
}