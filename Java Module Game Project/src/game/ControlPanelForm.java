package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Where the JButtons are created and functionalities are added.
 */
public class ControlPanelForm {
    /**
     * The main panel for the buttons.
     */
    private JPanel mainPanel;
    /**
     * The quit button.
     */
    private JButton quit;
    /**
     * The high score button.
     */
    private JButton highScore;
    /**
     * The next level button.
     */
    private JButton nextLevel;
    /**
     * The current game.
     */
    private Game game;

    /**
     * Initialise the Control Panel.
     * <p>
     * Sets up the control panel to hold 3 buttons.
     * the quit button quits the game when clicked.
     * the high score button opens a new panel with sorted high scores.
     * the next level button loads in the next level when clicked.
     *
     * @param game game in which the player is in
     */
    public ControlPanelForm(Game game) {
        this.game = game;
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Sorting> entries = game.getHighScoreHandler().readScores();
                    entries.sort(null);

                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    for (Sorting entry : entries) {
                        listModel.addElement(entry.toDisplayString());
                    }

                    JList<String> scoreList = new JList<>(listModel);
                    JFrame frame = new JFrame("Top Scores");
                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    panel.add(new JScrollPane(scoreList), BorderLayout.CENTER);

                    frame.add(panel);
                    frame.setSize(175, 300);
                    frame.setLocation(270, 100);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                game.getView().requestFocusInWindow();
            }
        });
        nextLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.goToNextLevel();
                game.getView().requestFocusInWindow();
            }
        });
    }

    /**
     * Getter method for the main panel.
     * <p>
     * used when other classes and components require access to the panel.
     *
     * @return the {@link JPanel } instance.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
