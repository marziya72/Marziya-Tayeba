package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * The main controllable player.
 * It extends {@link Walker} to enable physics-based movement and handles
 * jumping, collisions, and state management.
 */
public class Player extends Walker implements CollisionListener {
    private static final Shape playerShape = new BoxShape(1,1.5f);
    private int jumpCount = 0;
    private final int maxJump = 2;
    private int lives = 3;
    private int speed = 5;
    private boolean powerUp = false;
    private boolean touch;
    private int score;
    private boolean flag;
    private String timerText;
    private int seconds;

    /**
     * Creates a new player in the given world with default settings.
     *
     * @param world the world that the player exists in
     */
    public Player(World world) {
        super(world, playerShape);
        this.addCollisionListener(this);
    }

    /**
     * Double jump.
     *
     * @param speed the vertical velocity to apply when jumping
     */
    public void jump(float speed) {
        if (jumpCount < maxJump && !getTouch()) {
            setLinearVelocity(new Vec2(getLinearVelocity().x, speed));
            jumpCount++;
        }
    }

    /**
     * Resets the jump count when colliding with a static body.
     *
     * @param e the collision event
     */
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof StaticBody) {
            jumpCount = 0;
        }
    }

    /**
     * @return the current number of jumps performed
     */
    public int getJumpCount() {
        return jumpCount;
    }

    /**
     * @return the maximum number of allowed jumps
     */
    public int getMaxJump() {
        return maxJump;
    }

    /**
     * @return the current number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the number of lives.
     *
     * @param lives the new number of lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * @return the movement speed of the player
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the movement speed.
     *
     * @param speed the new speed value
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Enables or disables the power-up state.
     *
     * @param powerUp true to activate power-up, false otherwise
     */
    public void setPowerUp(boolean powerUp) {
        this.powerUp = powerUp;
    }

    /**
     * @return true if the player has a power-up, false otherwise
     */
    public boolean getPowerUp() {
        return powerUp;
    }

    /**
     * Sets the "touch" state, for touching platform like the side platforms.
     *
     * @param touch true if touching a surface
     */
    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    /**
     * @return true if the player is touching a surface, false otherwise
     */
    public boolean getTouch() {
        return touch;
    }

    /**
     * Sets the player's score.
     *
     * @param score the score to set
     */
    public void setScore (int score) {
        this.score = score;
    }

    /**
     * @return the player's current score
     */
    public int getScore () {
        return score;
    }

    /**
     * Sets the player's facing direction.
     *
     * @param flag true if facing right, false if facing left
     */
    public void setFacingRight(boolean flag) {
        this.flag = flag;
    }

    /**
     * @return true if the player is facing right, false otherwise
     */
    public boolean getFacingRight() {
        return flag;
    }

    /**
     * Sets the timer text.
     *
     * @param timerText the formatted time string
     */
    public void setTimerText(String timerText) {
        this.timerText = timerText;
    }

    /**
     * @return the formatted timer string
     */
    public String getTimerText() {
        return timerText;
    }

    /**
     * Sets the number of elapsed seconds.
     *
     * @param seconds the number of seconds
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * @return the number of seconds elapsed
     */
    public int getSeconds() {
        return seconds;
    }
}
