package game;

import city.cs.engine.SoundClip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the mute button to mute the sound of the current bgm
 */
public class MuteButton {
    /**
     * the button for the mute feature.
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
     * Initialise a new mute button.
     * <p>
     * Sets up the button and its image.
     * if pressed, mutes the current bgm, else plays it again -
     * depending on the state of pressed
     *
     * @param game game in which the player is in.
     * @param sound the {@link SoundManager} instance.
     */
    public MuteButton(Game game, SoundManager sound) {
        this.game = game;
        this.sound = sound;

        button = new JButton("");
        ImageIcon icon = new ImageIcon("data/soundOn.png");
        button.setIcon(icon);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pressed) {
                    sound.muteCurrentBgm();
                    button.setIcon(new ImageIcon("data/soundOff.png"));
                    pressed = true; // tracking that the button was pressed
                } else {
                    SoundClip current = sound.getCurrentBgm(); // holds the current bgm to be played again
                    if (current != null) {
                        sound.playBgm(current);
                        button.setIcon(new ImageIcon("data/soundOn.png"));
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
