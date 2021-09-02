package kinomora.gui.dungeondatatab;

import kinomora.gui.util.FloorButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SpawnerDataPanelBig extends JPanel {
    //meta
    public final DungeonDataTab parent;
    boolean isSingleButtonMode = true;

    JLabel spawnerXLabel = new JLabel("Spawner X");
    JTextField spawnerXField = new JTextField("", 6);
    JLabel spawnerYLabel = new JLabel("Spawner Y");
    JTextField spawnerYField = new JTextField("", 6);
    JLabel spawnerZLabel = new JLabel("Spawner Z");
    JTextField spawnerZField = new JTextField("", 6);
    JLabel biomeLabel = new JLabel("Biome");
    JComboBox<String> biomeDropdown = new JComboBox<>(new String[]{"OTHER", "DESERT", "SWAMP", "SWAMP_HILLS"});
    JLabel dungeonSequenceLabel = new JLabel("Dungeon Sequence");
    JLabel dungeonSeedLabel = new JLabel("Dungeon Seed");
    JTextField dungeonSequenceField = new JTextField("", 18);
    JTextField dungeonSeedField = new JTextField("", 18);

    JPanel buttonSubPanel = new JPanel(new GridBagLayout());
    JButton crackSeedButton = new JButton("Crack Seed");
    JButton swapDungeonButton = new JButton("Swap Floor");

    public SpawnerDataPanelBig(DungeonDataTab parent) {
        this.parent = parent;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        this.populatePanel(c);
    }

    private void populatePanel(GridBagConstraints c) {
        //Spawner data objects
        biomeDropdown.setEnabled(true);
        dungeonSequenceField.setEditable(false);
        dungeonSeedField.setEditable(false);
        //swapDungeonButton.setVisible(false);

        //disable the dumb highlight and text selection
        crackSeedButton.setFocusPainted(false);
        crackSeedButton.setFocusable(false);
        swapDungeonButton.setFocusPainted(false);
        swapDungeonButton.setFocusable(false);


        this.add(spawnerXLabel, setC(0, 0, 1, 1, 0, 0, 0, new Insets(-5, 5, 0, 0)));
        this.add(spawnerXField, setC(1, 0, 1, 1, 1, 0, 0, new Insets(-5, 10, 0, 0)));

        this.add(spawnerYLabel, setC(0, 1, 1, 1, 0, 0, 0, new Insets(5, 5, 0, 0)));
        this.add(spawnerYField, setC(1, 1, 1, 1, 0, 0, 0, new Insets(5, 10, 0, 0)));

        this.add(spawnerZLabel, setC(0, 2, 1, 1, 0, 0, 0, new Insets(5, 5, 0, 0)));
        this.add(spawnerZField, setC(1, 2, 1, 1, 0, 0, 0, new Insets(5, 10, 0, 0)));

        this.add(biomeLabel, setC(0, 3, 1, 1, 0, 0, 0, new Insets(10, 6, 0, 0)));
        this.add(biomeDropdown, setC(1, 3, 1, 1, 0, 0, 0, new Insets(10, 10, 0, 0)));

        this.add(dungeonSequenceLabel, setC(0, 4, 2, 1, 0, 0, 0, new Insets(10, 6, 0, 0)));
        this.add(dungeonSequenceField, setC(0, 5, 2, 1, 2, 0, 0, new Insets(5, 7, 0, 0)));

        this.add(dungeonSeedLabel, setC(0, 6, 2, 1, 0, 0, 0, new Insets(10, 6, 0, 0)));
        this.add(dungeonSeedField, setC(0, 7, 2, 1, 2, 0, 0, new Insets(5, 7, 0, 0)));

        this.add(buttonSubPanel, setC(0, 8, 2, 2, 0, 0, 0, new Insets(15, 0, 0, 0)));
        //buttonSubPanel.add(crackSeedButton, setC(0, 8, 2, 1, 0, 0, 0, new Insets(5, 59, 0, 0)));
        //isSingleButtonMode = !isSingleButtonMode;
        swapButtonMode();
    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, int gridheight, int weightx, int ipadx, int ipady, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.insets = insets;
        c.ipadx = ipadx;
        c.ipady = ipady;
        c.anchor = GridBagConstraints.LINE_START;
        return c;
    }

    public void swapButtonMode() {
        if (isSingleButtonMode) {
            this.remove(crackSeedButton);
            this.remove(swapDungeonButton);

            buttonSubPanel.add(crackSeedButton, setC(0, 8, 2, 1, 0, 0, 0, new Insets(5, 59, 0, 0)));

        } else {
            this.remove(crackSeedButton);
            this.remove(swapDungeonButton);

            buttonSubPanel.add(crackSeedButton, setC(0, 0, 2, 1, 2, 0 ,0, new Insets(5, 7, 0, 0)));
            buttonSubPanel.add(swapDungeonButton, setC(1, 0, 2, 1, 2, 3 ,0, new Insets(5, 109, 0, 0)));
        }
        isSingleButtonMode = !isSingleButtonMode;
    }

    public void setDungeonSequence(String sequence) {
        dungeonSequenceField.setText(sequence);
        dungeonSequenceField.getCaret().setDot(0);
    }

    /*
    this.currentFloorSize = size;
        int ID = 0;
        FloorButton button;
        if (size == 81) {
            for(boolean visibility : ninebynine){
                button = buttonIDLookup.get(ID);
                button.setVisible(visibility);
                ID++;
            }
        }
     */
}
