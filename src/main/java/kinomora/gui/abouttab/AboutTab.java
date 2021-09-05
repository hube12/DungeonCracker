package kinomora.gui.abouttab;

import kinomora.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutTab extends JPanel implements ActionListener, MouseListener {

    final URI releasesURL = new URI("https://github.com/Kinomora/DungeonCracker/releases");
    final URI issuesURL = new URI("https://github.com/Kinomora/DungeonCracker/issues");
    final URI repoURL = new URI("https://github.com/Kinomora/DungeonCracker");
    JButton visitGithub;
    JButton visitIssueTracker;
    JButton visitRepository;
    private boolean hasMouseExited;

    public AboutTab() throws URISyntaxException {
        JPanel panel = new JPanel(new GridBagLayout());
        populatePanel(panel);

        this.setLayout(new GridBagLayout());
        this.add(panel, setC(0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(-100, -160, 0, 0)));
    }

    private void populatePanel(JPanel panel) {
        JLabel AppTitle = new JLabel("Universal Dungeon Cracker");
        JLabel versionNumber = new JLabel("Version: " + Main.getAppVersion());
        JLabel author = new JLabel("Author: Kinomora");
        JLabel credits = new JLabel("Special thanks: Neil, KaptainWutax, and Matt for the original cracking code");
        JLabel getLatestVersion = new JLabel("Download the latest version for free at GitHub");
        visitGithub = new JButton("Visit GitHub");
        visitGithub.addActionListener(this);
        visitGithub.addMouseListener(this);
        JLabel reportBugs = new JLabel("Report bugs on the issue tracker");
        visitIssueTracker = new JButton("Visit Issue Tracker");
        visitIssueTracker.addActionListener(this);
        visitIssueTracker.addMouseListener(this);
        JLabel findInstructions = new JLabel("Find detailed instructions on the main repository");
        visitRepository = new JButton("Visit Repository");
        visitRepository.addActionListener(this);
        visitRepository.addMouseListener(this);

        //disable the dumb highlight and text selection
        visitGithub.setFocusPainted(false);
        visitGithub.setFocusable(false);
        visitIssueTracker.setFocusPainted(false);
        visitIssueTracker.setFocusable(false);
        visitRepository.setFocusPainted(false);
        visitRepository.setFocusable(false);

        //change the font size
        AppTitle.setFont(new Font(AppTitle.getName(), Font.PLAIN, 16));

        panel.add(AppTitle, setC(0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(0, 0, 0, 0)));
        panel.add(versionNumber, setC(0, 1, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(15, 0, 0, 0)));
        panel.add(author, setC(0, 2, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 0, 0, 0)));
        panel.add(credits, setC(0, 3, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 0, 0, 0)));
        panel.add(getLatestVersion, setC(0, 4, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(20, 0, 0, 0)));
        panel.add(visitGithub, setC(0, 5, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 0, 0, 0)));
        panel.add(reportBugs, setC(0, 6, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(15, 0, 0, 0)));
        panel.add(visitIssueTracker, setC(0, 7, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 0, 0, 0)));
        panel.add(findInstructions, setC(0, 8, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(15, 0, 0, 0)));
        panel.add(visitRepository, setC(0, 9, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, new Insets(5, 0, 0, 0)));
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

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {
                System.out.println("Critical error attempting to open a URL..");
                System.exit(1);
            }
        }
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
                if (button.equals(visitGithub)) {
                    open(releasesURL);
                } else if (button.equals(visitIssueTracker)) {
                    open(issuesURL);
                } else if (button.equals(visitRepository)) {
                    open(repoURL);
                } else {
                    System.out.println("Critical issue with button press on About page..");
                    System.exit(1);
                }
            }
        }
    }
}
