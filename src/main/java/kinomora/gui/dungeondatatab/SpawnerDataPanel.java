package kinomora.gui.dungeondatatab;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.version.MCVersion;
import kinomora.gui.dungeonseedtab.DungeonSeedTab;
import kinomora.gui.util.BoundsPopupMenuListener;
import kinomora.gui.util.Dropdown;
import kinomora.gui.util.Str;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SpawnerDataPanel extends JPanel implements ActionListener, MouseListener {
    public static final Biome UNKNOWN = new Biome(MCVersion.v1_0, Dimension.OVERWORLD, -1, "Unknown", Biome.Category.NONE, Biome.Precipitation.NONE, 0, 0, 0, null, null);
    // we made sure to have unknown before every other ones
    public static final java.util.List<Biome> ALL_BIOMES_WITH_UNKNOWN = new ArrayList<Biome>() {{
        add(UNKNOWN);
        addAll(Biomes.REGISTRY.values());
    }};
    //meta
    public DungeonDataTab dataParent;
    public DungeonSeedTab seedParent;
    public Biome dungeon1Biome = UNKNOWN;
    public Biome dungeon2Biome = UNKNOWN;
    JLabel spawnerXLabel = new JLabel("Spawner 1 X");
    JTextField spawnerXField = new JTextField("0", 6);
    JLabel spawnerYLabel = new JLabel("Spawner 1 Y");
    JTextField spawnerYField = new JTextField("0", 6);
    JLabel spawnerZLabel = new JLabel("Spawner 1 Z");
    JTextField spawnerZField = new JTextField("0", 6);
    JLabel biomeLabel = new JLabel("Biome");
    Dropdown<Biome> biomeDropdown = new Dropdown<>(b -> Str.prettifyDashed(b.getName()), ALL_BIOMES_WITH_UNKNOWN);
    JLabel dungeonSequenceLabel = new JLabel("Dungeon Sequence");
    JLabel dungeonSeedLabel = new JLabel("Dungeon Seed");
    JTextField dungeonSequenceField = new JTextField("", 19);
    JTextField dungeonSeedField = new JTextField("", 19);
    JPanel buttonSubPanel = new JPanel(new GridBagLayout());
    JButton crackDungeonSeed = new JButton("Dungeon Seed");
    JButton swapDungeonButton = new JButton("Swap Floors");
    GridBagLayout layout;
    private boolean hasMouseExited;

    public SpawnerDataPanel(DungeonDataTab dataParent) {
        this.dataParent = dataParent;
        this.layout = new GridBagLayout();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(layout);

        this.populatePanel(true);
    }

    public SpawnerDataPanel(DungeonSeedTab seedParent) {
        this.seedParent = seedParent;
        this.layout = new GridBagLayout();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.setLayout(layout);

        this.populatePanel(false);
    }

    private void populatePanel(boolean big) {
        if (big) {
            //Spawner data objects
            biomeDropdown.setEnabled(true);
            dungeonSequenceField.setEditable(false);
            dungeonSeedField.setEditable(false);

            //disable the dumb highlight and text selection
            crackDungeonSeed.setFocusPainted(false);
            crackDungeonSeed.setFocusable(false);
            swapDungeonButton.setFocusPainted(false);
            swapDungeonButton.setFocusable(false);


            this.add(spawnerXLabel, setC(0, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
            this.add(spawnerXField, setC(1, 0, 1, 1, 1, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 10, 0, 0)));

            this.add(spawnerYLabel, setC(0, 1, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
            this.add(spawnerYField, setC(1, 1, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 10, 0, 0)));

            this.add(spawnerZLabel, setC(0, 2, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));
            this.add(spawnerZField, setC(1, 2, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 10, 0, 0)));

            this.add(biomeLabel, setC(0, 3, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(10, 5, 0, 0)));

            this.add(biomeDropdown, setC(1, 3, 1, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(10, 10, 0, 0)));
            // fix the width to only be that much characters as well as number of element to display
            biomeDropdown.setMaximumRowCount(10);
            BoundsPopupMenuListener listener = new BoundsPopupMenuListener(true, false);
            biomeDropdown.addPopupMenuListener(listener);
            biomeDropdown.setPrototypeDisplayValue("XXXXXXXX");
            this.add(dungeonSequenceLabel, setC(0, 4, 2, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(10, 5, 0, 0)));
            this.add(dungeonSequenceField, setC(0, 5, 2, 1, 2, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));

            this.add(dungeonSeedLabel, setC(0, 6, 2, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(10, 5, 0, 0)));
            this.add(dungeonSeedField, setC(0, 7, 2, 1, 2, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 5, 0, 0)));

            this.add(buttonSubPanel, setC(0, 8, 2, 2, 0, 0, 0, 14, GridBagConstraints.FIRST_LINE_START, new Insets(2, 0, 0, 0)));
            buttonSubPanel.add(crackDungeonSeed, setC(0, 0, 2, 1, 1, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(0, 5, 0, 0)));
            buttonSubPanel.add(swapDungeonButton, setC(1, 0, 2, 1, 1, 0, 14, 0, GridBagConstraints.LINE_START, new Insets(0, 121, 0, 0)));
            swapDungeonButton.setVisible(false);

            crackDungeonSeed.addMouseListener(this);
            crackDungeonSeed.addActionListener(this);
            swapDungeonButton.addMouseListener(this);
            swapDungeonButton.addActionListener(this);

            biomeDropdown.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (getCurrentDungeonNumber() == 1) {
                        dungeon1Biome = biomeDropdown.getSelected();
                    } else {
                        dungeon2Biome = biomeDropdown.getSelected();
                    }
                }
            });
        } else {
            //Spawner data objects
            biomeDropdown.setEnabled(true);
            dungeonSeedField.setEditable(true);

            this.add(spawnerXLabel, setC(0, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 5, 0, 0)));
            this.add(spawnerXField, setC(1, 0, 4, 1, 1, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 10, 0, 0)));

            this.add(spawnerZLabel, setC(0, 2, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 5, 0, 0)));
            this.add(spawnerZField, setC(1, 2, 4, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 10, 0, 0)));

            this.add(biomeLabel, setC(0, 4, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(10, 6, 0, 0)));
            this.add(biomeDropdown, setC(1, 4, 4, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(10, 10, 0, 0)));

            this.add(dungeonSeedLabel, setC(0, 5, 4, 1, 0, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(10, 6, 0, 0)));
            this.add(dungeonSeedField, setC(0, 6, 4, 1, 2, 0, 0, 0, GridBagConstraints.LINE_START, new Insets(5, 7, 0, 0)));
        }
    }

    public void setCurrentBiomeDropdownValue(int dungeon) {
        if (dungeon == 1) {
            biomeDropdown.setSelectedItem(dungeon1Biome);
        } else {
            biomeDropdown.setSelectedItem(dungeon2Biome);
        }
    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int ipadx, int ipady, int anchor, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.weighty = weighty;
        c.ipadx = ipadx;
        c.ipady = ipady;
        c.anchor = anchor;
        c.insets = insets;
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
        spawnerXLabel.setText("Spawner " + dungeon + " X");
        spawnerYLabel.setText("Spawner " + dungeon + " Y");
        spawnerZLabel.setText("Spawner " + dungeon + " Z");
    }

    private int getCurrentDungeonNumber() {
        return dataParent.currentDungeonFloor;
    }

    //Action Listener events
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    //Mouse listener events
    @Override
    public void mouseEntered(MouseEvent e) {
        this.hasMouseExited = false;
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
                dataParent.saveCurrentDungeonCoords(spawnerXField.getText(), spawnerYField.getText(), spawnerZField.getText());
                //swap dungeons
                if (button == swapDungeonButton) {
                    swapDungeon();
                } else if (button == crackDungeonSeed) {
                    //crack dungeon seed
                    dataParent.crackDungeonSeed(getCurrentDungeonNumber());
                }
            }
        }
        //parent.spawnerDataPanelBigButtonPressed(this);
    }

    private void swapDungeon() {
        dataParent.swapDungeon();

    }
}
