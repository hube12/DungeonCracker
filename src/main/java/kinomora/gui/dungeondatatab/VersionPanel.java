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
        GridBagConstraints c = new GridBagConstraints();

        this.populatePanel(c);
    }

    private void populatePanel(GridBagConstraints c) {
        //Left Panel (dungeon data) objects
        JLabel versionLabel = new JLabel("Version");
        JComboBox<String> versionDropdown = new JComboBox<>(MCVersion.getVersionListAsArray());
        JLabel dungeonCountLabel = new JLabel("Dungeon Count");
        //JLabel dungeonCountLabel1 = new JLabel("1");
        JRadioButton dungeonCountRadio1 = new JRadioButton("1",true);
        //JLabel dungeonCountLabel2 = new JLabel("2");
        JRadioButton dungeonCountRadio2 = new JRadioButton("2",false);

        this.add(versionLabel, setC(0,0,1,new Insets(5,-30,0,0)));
        this.add(versionDropdown, setC(1,0, 2,new Insets(5,0,0,0)));

        this.add(dungeonCountLabel, setC(0,1,1,new Insets(0,-30,0,0)));
        this.add(dungeonCountRadio1, setC(1,1,1,new Insets(0,0,0,0)));
        this.add(dungeonCountRadio2, setC(2,1,1,new Insets(0,0,0,0)));
    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.insets = insets;
        c.anchor = GridBagConstraints.LINE_START;
        return c;
    }
}