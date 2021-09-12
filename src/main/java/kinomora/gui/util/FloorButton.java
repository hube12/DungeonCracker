package kinomora.gui.util;

import kinomora.gui.dungeondatatab.DungeonFloorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FloorButton extends JButton implements ActionListener, MouseListener {

    private static final ImageIcon COBBLE_TILE = new ImageIcon(FloorButton.class.getResource("/dungeonFloorIcons/cobble.png"));
    private static final ImageIcon MOSSY_TILE = new ImageIcon(FloorButton.class.getResource("/dungeonFloorIcons/mossy.png"));
    private static final ImageIcon UNKNOWN_TILE = new ImageIcon(FloorButton.class.getResource("/dungeonFloorIcons/unknown.png"));
    private final int ID;
    DungeonFloorPanel parent;
    private boolean hasMouseExited;

    public FloorButton(int ID, DungeonFloorPanel parent) {
        //Listeners
        this.addMouseListener(this);
        this.addActionListener(this);

        //object configuration
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setIcon(UNKNOWN_TILE);
        this.setEnabled(true);
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.ID = ID;
        this.parent = parent;
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
        //If mouse is inside the button..
        if (!parent.twoButtonMouseCompatMode) {
            // Three-mouse button mode just sets the button values based on the mouse clicked
            if (!hasMouseExited) {
                if (e.getButton() == MouseEvent.BUTTON1) {        //Left Click
                    this.setIcon(COBBLE_TILE);
                } else if (e.getButton() == MouseEvent.BUTTON3) { //Right Click
                    this.setIcon(MOSSY_TILE);
                } else if (e.getButton() == MouseEvent.BUTTON2) { //Middle Mouse Button
                    this.setIcon(UNKNOWN_TILE);
                }
            }
        } else {
            // Two-button only mouse compat mode cycles blocks
            // Left click pressed, cycle from unknown -> mossy -> cobble ->
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (this.getIcon().equals(UNKNOWN_TILE)) {
                    this.setIcon(COBBLE_TILE);
                } else if (this.getIcon().equals(COBBLE_TILE)) {
                    this.setIcon(MOSSY_TILE);
                } else if (this.getIcon().equals(MOSSY_TILE)) {
                    this.setIcon(UNKNOWN_TILE);
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                //Right click pressed, cycle from unknown -> mossy -> cobble ->
                if (this.getIcon().equals(UNKNOWN_TILE)) {
                    this.setIcon(MOSSY_TILE);
                } else if (this.getIcon().equals(MOSSY_TILE)) {
                    this.setIcon(COBBLE_TILE);
                } else if (this.getIcon().equals(COBBLE_TILE)) {
                    this.setIcon(UNKNOWN_TILE);
                }
            }
        }
        parent.floorButtonPressed();
    }

    public int getButtonSequenceDigit() {
        if (this.getIcon().equals(UNKNOWN_TILE)) {
            return 2;
        } else if (this.getIcon().equals(MOSSY_TILE)) {
            return 1;
        } else if (this.getIcon().equals(COBBLE_TILE)) {
            return 0;
        } else {
            return -1;
        }
    }

    public void setButtonStateByDigit(int digit) {
        if (digit == 0) {
            this.setIcon(COBBLE_TILE);
        } else if (digit == 1) {
            this.setIcon(MOSSY_TILE);
        } else if (digit == 2) {
            this.setIcon(UNKNOWN_TILE);
        } else {
            System.out.println("Critical error in setting a button state..");
            System.exit(1);
        }
    }
}
