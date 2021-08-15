package kinomora.gui.dungeondatatab;

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
    JTextField dungeonSequenceField = new JTextField("1011100010101100", 18);
    JTextField dungeonSeedField = new JTextField("245244786781278", 18);
    JButton crackSeedButton = new JButton("Crack Seed");
    JButton swapDungeonButton = new JButton("Dungeon 2");

    public SpawnerDataPanelBig(DungeonDataTab parent) {
        this.parent = parent;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        ;

        this.populatePanel(c);
    }

    private void populatePanel(GridBagConstraints c) {
        //Spawner data objects
        biomeDropdown.setEnabled(true);
        dungeonSequenceField.setEditable(false);
        dungeonSeedField.setEditable(false);
        swapDungeonButton.setVisible(false);


        this.add(spawnerXLabel, setC(0, 0, 1, 1, 0, new Insets(-5, 5, 0, 0)));
        this.add(spawnerXField, setC(1, 0, 1, 1, 1, new Insets(-5, -90, 0, 0)));

        this.add(spawnerYLabel, setC(0, 1, 1, 1, 0, new Insets(0, 5, 0, 0)));
        this.add(spawnerYField, setC(1, 1, 1, 1, 0, new Insets(0, -90, 0, 0)));

        this.add(spawnerZLabel, setC(0, 2, 1, 1, 0, new Insets(0, 5, 0, 0)));
        this.add(spawnerZField, setC(1, 2, 1, 1, 0, new Insets(0, -90, 0, 0)));

        this.add(biomeLabel, setC(0, 3, 1, 1, 0, new Insets(0, -18, 0, 0)));
        this.add(biomeDropdown, setC(1, 3, 1, 1, 0, new Insets(5, -46, 0, 0)));

        this.add(dungeonSequenceLabel, setC(0, 4, 2, 1, 0, new Insets(5, -92, 0, 0)));
        this.add(dungeonSequenceField, setC(0, 5, 2, 1, 2, new Insets(5, 2, 0, 0)));

        this.add(dungeonSeedLabel, setC(0, 6, 2, 1, 0, new Insets(5, -120, 0, 0)));
        this.add(dungeonSeedField, setC(0, 7, 2, 1, 2, new Insets(5, 2, 0, 0)));

        this.add(crackSeedButton, setC(0, 8, 2, 1, 0, new Insets(5, 0, 0, 0)));
    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, int gridheight, int weightx, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.insets = insets;
        c.anchor = GridBagConstraints.LINE_START;
        return c;
    }

    public void swapButtonMode(){
        if(isSingleButtonMode) {
            this.remove(crackSeedButton);
            this.remove(swapDungeonButton);

            this.add(crackSeedButton, setC(0, 8, 1, 1, 0, new Insets(5, 0, 0, 0)));

        } else {
            this.remove(crackSeedButton);
            this.remove(swapDungeonButton);

            this.add(crackSeedButton, setC(0, 8, 1, 1, 0, new Insets(5, 0, 0, 0)));
            this.add(swapDungeonButton, setC(1, 8, 1, 1, 0, new Insets(5, 0, 0, 0)));
        }
        isSingleButtonMode = !isSingleButtonMode;
    }
}
