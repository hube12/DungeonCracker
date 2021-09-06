package kinomora.gui.dungeondatatab;

import kaptainwutax.mcutils.version.MCVersion;
import kinomora.gui.util.Dropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class VersionPanel extends JPanel implements ActionListener {
    public final DungeonDataTab parent;
    public MCVersion currentVersionSelected = MCVersion.latest();
    JRadioButton dungeonCountRadio1;
    JRadioButton dungeonCountRadio2;

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
        Dropdown<MCVersion> versionDropdown = new Dropdown<>(MCVersion.values());
        versionDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentVersionSelected = versionDropdown.getSelected();
                if (currentVersionSelected.isNewerOrEqualTo(MCVersion.v1_13)) {
                    dungeonCountRadio1.setSelected(true);
                    dungeonCountRadio1.setEnabled(true);
                    parent.versionChangedAbove112();
                } else {
                    dungeonCountRadio2.setSelected(true);
                    dungeonCountRadio1.setEnabled(false);
                    parent.versionChangedBelow113();
                }
            }
        });
        JLabel dungeonCountLabel = new JLabel("Dungeon Count");
        dungeonCountRadio1 = new JRadioButton("1", true);
        dungeonCountRadio1.setFocusPainted(false);
        dungeonCountRadio1.setFocusable(false);
        dungeonCountRadio1.addActionListener(this);
        dungeonCountRadio2 = new JRadioButton("2", false);
        dungeonCountRadio2.setFocusPainted(false);
        dungeonCountRadio2.setFocusable(false);
        dungeonCountRadio2.addActionListener(this);

        ButtonGroup dungeonRadioGroup = new ButtonGroup();
        dungeonRadioGroup.add(dungeonCountRadio1);
        dungeonRadioGroup.add(dungeonCountRadio2);

        this.add(versionLabel, setC(0, 0, 1, new Insets(5, -21, 0, 0)));
        this.add(versionDropdown, setC(1, 0, 2, new Insets(5, 10, 0, 0)));

        this.add(dungeonCountLabel, setC(0, 1, 1, new Insets(-1, -21, 0, 0)));
        this.add(dungeonCountRadio1, setC(1, 1, 1, new Insets(0, 6, 0, 0)));
        this.add(dungeonCountRadio2, setC(2, 1, 1, new Insets(0, 0, 0, 0)));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton button = (JRadioButton) e.getSource();
        parent.radioButtonClicked(button);
    }
}
