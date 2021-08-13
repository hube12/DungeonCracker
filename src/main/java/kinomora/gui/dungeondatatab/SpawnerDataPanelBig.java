package kinomora.gui.dungeondatatab;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SpawnerDataPanelBig extends JPanel {
    public final DungeonDataTab parent;

    public SpawnerDataPanelBig(DungeonDataTab parent) {
        this.parent = parent;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.populatePanel(c);
    }

    private void populatePanel(GridBagConstraints c) {
        //Spawner data objects
        JLabel spawnerXLabel = new JLabel("Spawner X: ");
        JTextField spawnerXField = new JTextField();
        JLabel spawnerYLabel = new JLabel("Spawner Y: ");
        JTextField spawnerYField = new JTextField();
        JLabel spawnerZLabel = new JLabel("Spawner Z: ");
        JTextField spawnerZField = new JTextField();
        JLabel biomeLabel = new JLabel("Biome: ");
        JComboBox<String> biomeDropdown = new JComboBox<>(new String[]{"OTHER", "DESERT", "SWAMP", "SWAMP_HILLS"});
        JLabel dungeonSequenceLabel = new JLabel("Dungeon Sequence");
        JLabel dungeonSeedLabel = new JLabel("Dungeon Seed");
        JTextField dungeonSequenceField = new JTextField();
        JTextField dungeonSeedField = new JTextField();
        JButton crackSeedButton = new JButton("Crack Seed");
        JButton swapDungeonButton = new JButton("Dungeon 2");

        spawnerXField.setSize(50,20);
        spawnerYLabel.setSize(50,20);
        spawnerZField.setSize(50,20);

        this.add(spawnerXLabel, c.gridx);
        this.add(spawnerXField);
        this.add(spawnerYLabel);
        this.add(spawnerYField);
        this.add(spawnerZLabel);
        this.add(spawnerZField);
        this.add(biomeLabel);
        this.add(biomeDropdown);
        this.add(dungeonSequenceLabel);
        this.add(dungeonSeedLabel);
        this.add(dungeonSequenceField);
        this.add(dungeonSeedField);
        this.add(crackSeedButton);
        this.add(swapDungeonButton);
    }
}
