package kinomora.gui.dungeondatatab;

import kaptainwutax.mcutils.version.MCVersion;
import kinomora.gui.dungeonseedtab.DungeonSeedTab;
import kinomora.gui.util.Dropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class VersionPanel extends JPanel implements ActionListener {
    public DungeonDataTab dataParent;
    public DungeonSeedTab seedParent;

    public MCVersion currentVersionSelected = MCVersion.latest();
    JRadioButton dungeonCount1Radio;
    JRadioButton dungeonCount2Radio;

    public VersionPanel(DungeonDataTab dataParent) {
        this.dataParent = dataParent;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(new GridBagLayout());

        this.populatePanel(true);
    }

    public VersionPanel(DungeonSeedTab parent) {
        this.seedParent = parent;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(new GridBagLayout());

        this.populatePanel(false);
    }

    private void populatePanel(boolean seedMode) {
        //Left Panel (dungeon data) objects
        JLabel versionLabel = new JLabel("Version");
        Dropdown<MCVersion> versionDropdown = new Dropdown<>(MCVersion.values());
        versionDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentVersionSelected = versionDropdown.getSelected();
                if (currentVersionSelected.isNewerOrEqualTo(MCVersion.v1_13)) {
                    dungeonCount1Radio.setSelected(true);
                    dungeonCount1Radio.setEnabled(true);
                    dataParent.versionChangedAbove112();
                } else {
                    dungeonCount2Radio.setSelected(true);
                    dungeonCount1Radio.setEnabled(false);
                    dataParent.versionChangedBelow113();
                }
            }
        });
        JLabel dungeonCountLabel = new JLabel("Dungeon Count");
        dungeonCount1Radio = new JRadioButton("1", true);
        dungeonCount1Radio.setFocusPainted(false);
        dungeonCount1Radio.setFocusable(false);
        dungeonCount1Radio.addActionListener(this);
        dungeonCount2Radio = new JRadioButton("2", false);
        dungeonCount2Radio.setFocusPainted(false);
        dungeonCount2Radio.setFocusable(false);
        dungeonCount2Radio.addActionListener(this);

        ButtonGroup dungeonRadioGroup = new ButtonGroup();
        dungeonRadioGroup.add(dungeonCount1Radio);
        dungeonRadioGroup.add(dungeonCount2Radio);

        this.add(versionLabel, setC(0, 0, 1, 1, 0, 0, 1, 0, GridBagConstraints.LINE_START, new Insets(0, 5, 0, 0)));
        this.add(versionDropdown, setC(1, 0, 2, 1, 0, 0, 1, 0, GridBagConstraints.LINE_START, new Insets(0, 0, 0, 0)));

        this.add(dungeonCountLabel, setC(0, 1, 1, 1, 0, 0, 1, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
        this.add(dungeonCount1Radio, setC(1, 1, 1, 1, 0, 0, 1, 0, GridBagConstraints.LINE_START, new Insets(5, 0, 0, 0)));
        this.add(dungeonCount2Radio, setC(2, 1, 1, 1, 0, 0, 1, 0, GridBagConstraints.LINE_START, new Insets(5, -40, 0, 0)));
        if (!seedMode) {
            //put DungeonSeedMode additional code here
        }

    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, int gridheight, int ipadx, int ipady, int weightx, int weighty, int anchor, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = weightx;
        c.weighty = weighty;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.ipadx = ipadx;
        c.ipady = ipady;
        c.anchor = anchor;
        c.insets = insets;
        return c;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton button = (JRadioButton) e.getSource();
        dataParent.radioButtonClicked(button);
    }
}
