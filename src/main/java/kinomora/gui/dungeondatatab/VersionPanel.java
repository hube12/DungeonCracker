package kinomora.gui.dungeondatatab;

import kaptainwutax.mcutils.version.MCVersion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class VersionPanel extends JPanel implements ActionListener {
    public final DungeonDataTab parent;

    JRadioButton dungeonCountRadio1;
    JRadioButton dungeonCountRadio2;

    public String currentVersionSelected = "1.17";
    public String previousVersionSelected = "1.17";

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
        // Use DropDown from javautil or Minemap please
        MCVersion[] versions=MCVersion.values();
        String[] versionName=new String[versions.length];
        for (int i = 0; i < versions.length; i++) {
            versionName[i]=versions[i].toString();
        }
        JComboBox<String> versionDropdown = new JComboBox<>(versionName);
        versionDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                previousVersionSelected = currentVersionSelected;
                currentVersionSelected = Objects.requireNonNull(versionDropdown.getSelectedItem()).toString();
                MCVersion previous=MCVersion.fromString(previousVersionSelected);
                MCVersion current=MCVersion.fromString(currentVersionSelected);

                if(current.isOlderOrEqualTo(previous)){ //the current version is older than the previous one
                    if(current.isOlderThan(MCVersion.v1_13)){ //the current version is older than 1.13
                        //we need to  require 2 dungeons
                        dungeonCountRadio2.setSelected(true);
                        dungeonCountRadio1.setEnabled(false);
                        parent.versionChangedBelow113(currentVersionSelected);
                    }
                    //else-skipped: Since the current version is older than the previous one, but still newer than 1.12 we don't need to do anything
                } else {//the current version is newer than the previous one
                    if(previous.isNewerOrEqualTo(MCVersion.v1_13)){//the current version is newer than 1.12
                        dungeonCountRadio1.setSelected(true);
                        dungeonCountRadio1.setEnabled(true);
                        parent.versionChangedAbove112(currentVersionSelected);
                    }
                    //else-skipped: Since the current version is not newer than 1.13 but still newer than the old version, then we don't need to do anything
                    //TODO
                    //You might need to work on this, it seems that it might still not work 100% as intended, but at least all the data saves so that's the most important part
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

        this.add(versionLabel, setC(0, 0, 1, new Insets(5, -40, 0, 0)));
        this.add(versionDropdown, setC(1, 0, 2, new Insets(5, 10, 0, 0)));

        this.add(dungeonCountLabel, setC(0, 1, 1, new Insets(0, -40, 0, 0)));
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