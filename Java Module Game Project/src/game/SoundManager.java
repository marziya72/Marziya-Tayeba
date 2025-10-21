package game;

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Where sounds are managed from.
 */
public class SoundManager {
    /**
     * The sound clips of all the sounds used.
     */
    private SoundClip currentBgm, orbSound, powerupSound, shieldSound, buttonSound, bgmSound1, bgmSound2, bgmSound3, bgmSound4, gameOverSound;

    /**
     * Initialise the Sound Manager.
     * <p>
     * Sets up all the sounds and catches errors with audio files.
     */
    public SoundManager() {
        currentBgm = orbSound = powerupSound = shieldSound= buttonSound = bgmSound1 = bgmSound2 = bgmSound3 = bgmSound4 = gameOverSound = null;
        try {
            orbSound = new SoundClip("data/collect.wav");
            powerupSound = new SoundClip("data/powerup.wav");
            shieldSound = new SoundClip("data/shield.wav");
            buttonSound = new SoundClip("data/click2.wav");
            bgmSound1 = new SoundClip("data/bgm1.wav");
            bgmSound2 = new SoundClip("data/bgm2.wav");
            bgmSound3 = new SoundClip("data/bgm3.wav");
            bgmSound4 = new SoundClip("data/bossMusic.wav");
            gameOverSound = new SoundClip("data/gameOver.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error" + e);
        }
    }

    /**
     * plays the bgm.
     * <p>
     * Stops the current bgm that's playing, if there's one playing.
     * resumes the bgm that was just stopped.
     */
    public void playBgm(SoundClip bgm) {
        if (currentBgm != null) {
            currentBgm.stop();
        }
        currentBgm = bgm;
        currentBgm.resume();
    }

    /**
     * mutes the bgm.
     * <p>
     * pauses current bgm that's playing, if there's one playing.
     */
    public void muteCurrentBgm() {
        if (currentBgm != null) {
            currentBgm.pause();
        }
    }

    /**
     * sets the bgm.
     * <p>
     * sets the current bgm to the one that's currently playing
     */
    public void setCurrentBgm(SoundClip bgm) {
        currentBgm = bgm;
    }

    /**
     * Getter method for the current bgm.
     * <p>
     * returns the sound clip for the current bgm.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getCurrentBgm() {
        return currentBgm;
    }
    /**
     * Getter method for the orb sound.
     * <p>
     * returns the sound clip for the orb sound.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getOrbSound() {
        return orbSound;
    }
    /**
     * Getter method for the power up sound.
     * <p>
     * returns the sound clip for the power up sound.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getPowerupSound() {
        return powerupSound;
    }
    /**
     * Getter method for the shield sound.
     * <p>
     * returns the sound clip for the shield sound.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getShieldSound() {
        return shieldSound;
    }
    /**
     * Getter method for the button sound.
     * <p>
     * returns the sound clip for the button sound.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getButtonSound() {
        return buttonSound;
    }
    /**
     * Getter method for bgm1.
     * <p>
     * returns the sound clip for bgm1.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getBgmSound1() {
        return bgmSound1;
    }
    /**
     * Getter method for bgm2.
     * <p>
     * returns the sound clip for bgm2.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getBgmSound2() {
        return bgmSound2;
    }
    /**
     * Getter method for bgm3.
     * <p>
     * returns the sound clip for bgm3.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getBgmSound3() {
        return bgmSound3;
    }
    /**
     * Getter method for bgm3.
     * <p>
     * returns the sound clip for bgm3.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getBgmSound4() {
        return bgmSound4;
    }
    /**
     * Getter method for the game over sound.
     * <p>
     * returns the sound clip for the game over sound.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundClip getGameOverSound() {
        return gameOverSound;
    }
}