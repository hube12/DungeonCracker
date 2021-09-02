package kinomora.gui.dungeondatatab;

import other.util.MCVersion;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DungeonDataTab extends JPanel implements ActionListener, MouseListener {
    public final DungeonFloorPanel dungeonFloorPanel;
    public final VersionPanel versionPanel;
    public final SpawnerDataPanelBig spawnerDataPanelBig;
    private boolean hasMouseExited;

    /**
     * Creates and populates the Items(Overview) tab which contains the Inventory, Item Descriptions, Recommended Champions, and Inventory Item Crafting Calculator
     */
    public DungeonDataTab() {
        dungeonFloorPanel = new DungeonFloorPanel(this);
        versionPanel = new VersionPanel(this);
        spawnerDataPanelBig = new SpawnerDataPanelBig(this);

        //Set the layout for the window to be two halves
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 0));

        //Create panels for each half
        JPanel leftPanel = new JPanel(new GridBagLayout());
        //leftPanel.setSize(350,380);
        JPanel rightPanel = new JPanel(new GridBagLayout());
        //rightPanel.setSize(250,300);
        //Create subpanels for each section of the Dungeon Floor side
        JPanel dungeonSubButtonPanel = new JPanel(new FlowLayout());
        //Right Panel (dungeon floor) objects
        dungeonFloorSubButtonMess(dungeonSubButtonPanel);


        /***Adding both panels to the main window***/
        //Adding the left panel (dungeon floor + buttons) to the main window
        this.add(leftPanel, setC(0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 0, 0)));

        //Adding the right panel (version and coords) to the main window
        this.add(rightPanel, setC(1, 0, 1, 1, 0, 0, GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));


        /***Adding the Dungeon Floor panel and the Dungeon Floor Buttons to the LEFT Panel***/
        //Add the Dungeon Floor
        leftPanel.add(dungeonFloorPanel, setC(0, 0, 1, 1, 0, 0, GridBagConstraints.PAGE_START, new Insets(0, 0, 0, 0)));

        //Adding the Floor Buttons
        leftPanel.add(dungeonSubButtonPanel, setC(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0)));


        /***Adding the Version Panel and the Dungeon Data Big panel to the RIGHT Panel***/
        //Add the Version panel
        rightPanel.add(versionPanel, setC(0, 0, 1, 1, 94, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 10, 0)));

        //Adding the Dungeon Data panel
        //rightPanel.add(spawnerDataPanelBig, setC(0, 1, 1, 3, 10, 42, GridBagConstraints.LINE_START, new Insets(0, 0, 30, 0)));
        rightPanel.add(spawnerDataPanelBig, setC(0, 1, 1, 3, 10, 38, GridBagConstraints.LINE_START, new Insets(0, 0, 30, 0)));

    }

    private GridBagConstraints setC(int gridx, int gridy, int gridwidth, int gridheight, int ipadx, int ipady, int anchor, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.ipadx = ipadx;
        c.ipady = ipady;
        c.anchor = anchor;
        c.insets = insets;
        return c;
    }

    public void buttonPressed(String floorSequence) {
        spawnerDataPanelBig.setDungeonSequence(floorSequence);
    }

    private void dungeonFloorSubButtonMess(JPanel dungeonSubButtonPanel) {
        //Create the buttons
        JButton rotateCounterClockwise = new JButton("CC");
        rotateCounterClockwise.setFocusPainted(false);
        rotateCounterClockwise.setFocusable(false);
        rotateCounterClockwise.addMouseListener(this);
        rotateCounterClockwise.addActionListener(this);

        JButton size7x7 = new JButton("7x7");
        size7x7.setFocusPainted(false);
        size7x7.setFocusable(false);
        size7x7.addMouseListener(this);
        size7x7.addActionListener(this);

        JButton size7x9 = new JButton("7x9");
        size7x9.setFocusPainted(false);
        size7x9.setFocusable(false);
        size7x9.addMouseListener(this);
        size7x9.addActionListener(this);

        JButton size9x9 = new JButton("9x9");
        size9x9.setFocusPainted(false);
        size9x9.setFocusable(false);
        size9x9.addMouseListener(this);
        size9x9.addActionListener(this);

        JButton rotateClockwise = new JButton("CW");
        rotateClockwise.setFocusPainted(false);
        rotateClockwise.setFocusable(false);
        rotateClockwise.addMouseListener(this);
        rotateClockwise.addActionListener(this);

        //Fill in the dungeon subpanels
        dungeonSubButtonPanel.add(rotateCounterClockwise);
        dungeonSubButtonPanel.add(size7x7);
        dungeonSubButtonPanel.add(size7x9);
        dungeonSubButtonPanel.add(size9x9);
        dungeonSubButtonPanel.add(rotateClockwise);
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
            //Left click pressed,
            if (e.getButton() == MouseEvent.BUTTON1) {
                switch (button.getText()) {
                    case "7x7":
                        dungeonFloorPanel.setCurrentFloorSize(49);
                        spawnerDataPanelBig.setDungeonSequence(dungeonFloorPanel.getFloorSequence());
                        break;
                    case "7x9":
                        dungeonFloorPanel.setCurrentFloorSize(63);
                        spawnerDataPanelBig.setDungeonSequence(dungeonFloorPanel.getFloorSequence());
                        break;
                    case "9x9":
                        dungeonFloorPanel.setCurrentFloorSize(81);
                        spawnerDataPanelBig.setDungeonSequence(dungeonFloorPanel.getFloorSequence());
                        break;
                    case "CC":
                        dungeonFloorPanel.rotateFloorCounterclockwise();
                        spawnerDataPanelBig.setDungeonSequence(dungeonFloorPanel.getFloorSequence());
                        break;
                    case "CW":
                        dungeonFloorPanel.rotateFloorClockwise();
                        spawnerDataPanelBig.setDungeonSequence(dungeonFloorPanel.getFloorSequence());
                        break;
                    default:
                        System.out.println("You pressed: " + button.getText());
                        break;
                }
            }
        }
    }
}
