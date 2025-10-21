package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the restart button to restart the game level
 */
public class RestartButton {
    /**
     * the button for the restart feature.
     */
    private JButton button;
    /**
     * The current game.
     */
    private Game game;

    /**
     * Initialise a new restart button.
     * <p>
     * Sets up the button and its image.
     * if pressed, restart the game level
     *
     * @param game game in which the player is in.
     */
    public RestartButton(Game game) {
        this.game = game;

        button = new JButton("");
        ImageIcon icon = new ImageIcon("data/restart.png");
        button.setIcon(icon);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restart();
                game.getView().requestFocusInWindow(); // focus back on the game when clicked
                game.setRestart(true); // used for restarting the timer
            }
        });
    }

    /**
     * Getter method for the button.
     * <p>
     * returns the button to be used.
     *
     * @return the {@link JButton} instance.
     */
    public JButton getButton() {
        return button;
    }
}
