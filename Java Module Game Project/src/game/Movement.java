package game;

import city.cs.engine.*;
import javax.swing.Timer;

/**
 * The animation for the player.
 */
public class Movement {
    /**
     * The current player.
     */
    private Player player;
    /**
     * The flag to track the player animation.
     */
    private String flag = "idle";
    /**
     * to track the frame of the animation.
     */
    private int frame = 0;
    /**
     * The array to hold each of the idle frames.
     */
    private final BodyImage[] idleFrame;
    /**
     * The array to hold each of the crouch frames.
     */
    private final BodyImage[] crouchFrame;
    /**
     * The array to hold each of the run frames.
     */
    private final BodyImage[] runFrame;
    /**
     * The array to hold each of the jump frames.
     */
    private final BodyImage[] jumpFrame;
    /**
     * The array to hold each of the throw frames.
     */
    private final BodyImage[] throwFrame;

    /**
     * Initialises the animation of the player.
     * <p>
     * creates and loads the animations for the animations
     * updates the animation after short delays
     *
     * @param player the current player
     */
    public Movement(Player player) {
        this.player = player;
        idleFrame = new BodyImage[3];
        crouchFrame = new BodyImage[3];
        runFrame = new BodyImage[5];
        jumpFrame = new BodyImage[7];
        throwFrame = new BodyImage[3];
        loadIdleFrame();
        loadCrouchFrame();
        loadRunFrame();
        loadJumpFrame();
        loadThrowFrame();
        Timer timer = new Timer(125, _ -> updateSprite(flag));
        timer.start();
    }

    /**
     * updates the animation of the player.
     * <p>
     * if the state is idle, cycle through the idle animation
     * if the state is idle, cycle through the crouch animation
     * if the state is idle, cycle through the run animation
     * if the state is idle, cycle through the jump animation
     * if the state is throw, cycle through the shooting animation
     *
     * @param state the animation state.
     */
    public void updateSprite(String state) {
        if (!flag.equals(state)) {
            player.removeAllImages();
            flag = state;
            frame = 0;
        }
        if (flag.equals("idle")) {
            frame = (frame + 1) % idleFrame.length;
            if (player.getFacingRight()) {
                player.removeAllImages();
                player.addImage(idleFrame[frame]);
            } else {
                player.removeAllImages();
                AttachedImage flippedImage = player.addImage(idleFrame[frame]);
                flippedImage.flipHorizontal();
            }
        }

        if (flag.equals("crouch")) {
            if (frame < crouchFrame.length - 1) {
                frame = (frame + 1) % crouchFrame.length;
            }
            if (player.getFacingRight()) {
                player.removeAllImages();
                player.addImage(crouchFrame[frame]);
            } else {
                player.removeAllImages();
                AttachedImage flippedImage = player.addImage(crouchFrame[frame]);
                flippedImage.flipHorizontal();
            }
        }

        if (flag.equals("run")) {
            frame = (frame + 1) % runFrame.length;
            if (player.getFacingRight()) {
                player.removeAllImages();
                player.addImage(runFrame[frame]);
            } else {
                player.removeAllImages();
                AttachedImage flippedImage = player.addImage(runFrame[frame]);
                flippedImage.flipHorizontal();
            }
        }

        if (flag.equals("jump")) {
            frame = (frame + 1) % jumpFrame.length;
            if (player.getFacingRight()) {
                player.removeAllImages();
                player.addImage(jumpFrame[frame]);
            } else {
                player.removeAllImages();
                AttachedImage flippedImage = player.addImage(jumpFrame[frame]);
                flippedImage.flipHorizontal();
            }
        }

        if (flag.equals("throw")) {
            frame = (frame + 1) % throwFrame.length;
            if (player.getFacingRight()) {
                player.removeAllImages();
                player.addImage(throwFrame[frame]);
            } else {
                player.removeAllImages();
                AttachedImage flippedImage = player.addImage(throwFrame[frame]);
                flippedImage.flipHorizontal();
            }
        }
    }

    /**
     * loads the idle frames.
     * <p>
     * for each of the frames in the idle array, display the image
     */
    public void loadIdleFrame() {
        for (int i = 0; i < idleFrame.length; i++) {
            idleFrame[i] = new BodyImage("data/idle/idle" + i + ".png", 3);
        }
    }

    /**
     * loads the crouch frames.
     * <p>
     * for each of the frames in the crouch array, display the image
     */
    public void loadCrouchFrame() {
        for (int i = 0; i < idleFrame.length; i++) {
            crouchFrame[i] = new BodyImage("data/crouch/crouch" + i + ".png", 3);
        }
    }

    /**
     * loads the run frames.
     * <p>
     * for each of the frames in the run array, display the image
     */
    public void loadRunFrame() {
        for (int i = 0; i < runFrame.length; i++) {
            runFrame[i] = new BodyImage("data/run/run" + i + ".png", 3);
        }
    }

    /**
     * loads the jump frames.
     * <p>
     * for each of the frames in the jump array, display the image
     */
    public void loadJumpFrame() {
        for (int i = 0; i < jumpFrame.length; i++) {
            jumpFrame[i] = new BodyImage("data/jump/jump" + i + ".png", 3);
        }
    }

    /**
     * loads the throw frames.
     * <p>
     * for each of the frames in the throw array, display the image
     */
    public void loadThrowFrame() {
        for (int i = 0; i < throwFrame.length; i++) {
            throwFrame[i] = new BodyImage("data/throw/throw" + i + ".png", 3);
        }
    }

    /**
     * update the player.
     * <p>
     * This is used to reset the player when entering a new level.
     * It updates the movement of the player so that they
     * can move in the new level.
     *
     * @param player player to be tracked
     */
    public void updatePlayer (Player player) {
        this.player = player;
    }

    /**
     * Setter method for the flag state.
     * <p>
     * This is used to set the state for the updateSprite method.
     *
     * @param flag animation state
     */
    public void setFlagState(String flag) {
        this.flag = flag;
    }

    /**
     * Getter method for the flag state.
     * <p>
     * This is used to get the state for the updateSprite method.
     *
     * @return flag animation state
     */
    public String getFlagState() {
        return flag;
    }
}