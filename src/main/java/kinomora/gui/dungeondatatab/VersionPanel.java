package kinomora.gui.dungeondatatab;

import other.util.MCVersion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class VersionPanel extends JPanel {
    public final DungeonDataTab parent;

    public VersionPanel(DungeonDataTab parent) {
        this.parent = parent;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(new GridBagLayout());
        this.setSize(250,70);
        GridBagConstraints c = new GridBagConstraints();

        this.populatePanel(c);
    }

    private void populatePanel(GridBagConstraints c) {
        //Left Panel (dungeon data) objects
        JLabel versionLabel = new JLabel("Version: ");
        JComboBox<String> versionDropdown = new JComboBox<>(MCVersion.getVersionListAsArray());
        JLabel dungeonCountLabel = new JLabel("Dungeon Count: ");
        JLabel dungeonCountLabel1 = new JLabel("1");
        JRadioButton dungeonCountRadio1 = new JRadioButton();
        JLabel dungeonCountLabel2 = new JLabel("2");
        JRadioButton dungeonCountRadio2 = new JRadioButton();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(10,0,0,0);
        this.add(versionLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        this.add(versionDropdown, c);
        c.gridx = 0;
        c.gridy = 1;
        this.add(dungeonCountLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        this.add(dungeonCountLabel1,c);
        c.gridx = 2;
        c.gridy = 1;
        this.add(dungeonCountRadio1, c);
        c.gridx = 3;
        c.gridy = 1;
        this.add(dungeonCountLabel2,c);
        c.gridx = 4;
        c.gridy = 1;
        this.add(dungeonCountRadio2, c);
    }
}