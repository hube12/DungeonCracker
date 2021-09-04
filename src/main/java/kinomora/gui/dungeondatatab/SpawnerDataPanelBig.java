package kinomora.gui.dungeondatatab;

import kinomora.gui.util.VersionComparison;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpawnerDataPanelBig extends JPanel implements ActionListener, MouseListener {
    //meta
    public final DungeonDataTab parent;
    private boolean hasMouseExited;

    JLabel spawnerXLabel = new JLabel("Spawner 1 X");
    JTextField spawnerXField = new JTextField("0", 6);
    JLabel spawnerYLabel = new JLabel("Spawner 1 Y");
    JTextField spawnerYField = new JTextField("0", 6);
    JLabel spawnerZLabel = new JLabel("Spawner 1 Z");
    JTextField spawnerZField = new JTextField("0", 6);
    JLabel biomeLabel = new JLabel("Biome");
    JComboBox<String> biomeDropdown = new JComboBox<>(new String[]{"OTHER", "DESERT", "SWAMP", "SWAMP_HILLS"});
    JLabel dungeonSequenceLabel = new JLabel("Dungeon Sequence");
    JLabel dungeonSeedLabel = new JLabel("Dungeon Seed");
    JTextField dungeonSequenceField = new JTextField("", 18);
    JTextField dungeonSeedField = new JTextField("", 18);

    JPanel buttonSubPanel = new JPanel(new GridBagLayout());
    JButton crackSeedButton = new JButton("Crack Seed");
    JButton swapDungeonButton = new JButton("Swap Floor");

    GridBagLayout layout;

    public String dungeon1Biome = "OTHER";
    public String dungeon2Biome = "OTHER";

    public SpawnerDataPanelBig(DungeonDataTab parent) {
        this.parent = parent;
        this.layout = new GridBagLayout();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();


        this.populatePanel(c);
    }

    private void populatePanel(GridBagConstraints c) {
        //Spawner data objects
        biomeDropdown.setEnabled(true);
        dungeonSequenceField.setEditable(false);
        dungeonSeedField.setEditable(false);

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
        buttonSubPanel.add(crackSeedButton, setC(0, 0, 2, 1, 2, 0, 0, new Insets(5, 7, 0, 0)));
        buttonSubPanel.add(swapDungeonButton, setC(1, 0, 2, 1, 2, 3, 0, new Insets(5, 109, 0, 0)));
        swapDungeonButton.setVisible(false);

        crackSeedButton.addMouseListener(this);
        crackSeedButton.addActionListener(this);
        swapDungeonButton.addMouseListener(this);
        swapDungeonButton.addActionListener(this);

        biomeDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (getCurrentDungeonNumber() == 1) {
                        dungeon1Biome = biomeDropdown.getSelectedItem().toString();
                    } else {
                        dungeon2Biome = biomeDropdown.getSelectedItem().toString();
                    }
                }
            }
        });
    }

    public void setCurrentBiomeDropdownValue(int dungeon) {
        if (dungeon == 1) {
            biomeDropdown.setSelectedItem(dungeon1Biome);
        } else {
            biomeDropdown.setSelectedItem(dungeon2Biome);
        }
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

    public void hideSwapDungeonButton() {
        swapDungeonButton.setVisible(false);
    }

    public void showSwapDungeonButton() {
        swapDungeonButton.setVisible(true);
    }

    public void setDungeonSequenceTextField(String sequence) {
        dungeonSequenceField.setText(sequence);
        dungeonSequenceField.getCaret().setDot(0);
    }

    public void setTextFieldValue(int valueX, int valueY, int valueZ) {
        spawnerXField.setText(String.valueOf(valueX));
        spawnerYField.setText(String.valueOf(valueY));
        spawnerZField.setText(String.valueOf(valueZ));

    }

    public void setDungeonXYZLabelText(int dungeon) {
        if (dungeon == 1) {
            spawnerXLabel.setText("Spawner 1 X");
            spawnerYLabel.setText("Spawner 1 Y");
            spawnerZLabel.setText("Spawner 1 Z");
        } else {
            spawnerXLabel.setText("Spawner 2 X");
            spawnerYLabel.setText("Spawner 2 Y");
            spawnerZLabel.setText("Spawner 2 Z");
        }
    }

    private int getCurrentDungeonNumber() {
        return parent.currentDungeonFloor;
    }

    //Action Listener events
    @Override
    public void actionPerformed(ActionEvent e) {
        this.hasMouseExited = false;
    }

    //Mouse listener events
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.hasMouseExited = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        //If mouse is inside the button..
        if (!hasMouseExited) {
            //Left mouse button pressed, released, and not exited button..
            if (e.getButton() == MouseEvent.BUTTON1) {
                //save the current dungeon coords
                parent.saveCurrentDungeonCoords(spawnerXField.getText(), spawnerYField.getText(), spawnerZField.getText());
                //swap dungeons
                if (button == swapDungeonButton) {
                    swapDungeon();
                } else {
                    //crack seed
                    parent.crackSeed();
                }
            }
        }
        //parent.spawnerDataPanelBigButtonPressed(this);
    }

    private void swapDungeon() {
        parent.swapDungeon();

    }
}
