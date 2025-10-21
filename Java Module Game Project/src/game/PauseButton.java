package game;

import city.cs.engine.SoundClip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the pause button to pause the game and sound of the current bgm
 */
public class PauseButton {
    /**
     * the button for the pause feature.
     */
    private JButton button;
    /**
     * Handles the sounds.
     */
    private SoundManager sound;
    /**
     * tracks if the button is pressed.
     */
    private boolean pressed = false;
    /**
     * The current game.
     */
    private Game game;

    /**
     * Initialise a new pause button.
     * <p>
     * Sets up the button and its image.
     * if pressed, stops the current bgm, and pauses the game and its timer.
     * if pressed again and pressed is true, it starts the game up again from
     * the point it left from
     *
     * @param game game in which the player is in.
     * @param sound the {@link SoundManager} instance.
     */
    public PauseButton(Game game, SoundManager sound) {
        this.game = game;
        this.sound = sound;

        button = new JButton("");
        ImageIcon icon = new ImageIcon("data/pause.png");
        button.setIcon(icon);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pressed) {
                    sound.muteCurrentBgm();
                    game.pause();
                    ((Camera)game.getView()).pauseTimer(); // pause the timer in the camera class
                    button.setIcon(new ImageIcon("data/start.png"));
                    pressed = true; // track that button was pressed
                } else {
                    // resume everything
                    SoundClip current = sound.getCurrentBgm();
                    if (current != null) {
                        sound.playBgm(current);
                        game.resume();
                        ((Camera)game.getView()).resumeTimer();
                        button.setIcon(new ImageIcon("data/pause.png"));
                        pressed = false; // reset
                    } else {
                        System.out.println("Warning: currentBgm is null");
                    }
                }
                game.getView().requestFocusInWindow(); // focus back on the game when clicked
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
