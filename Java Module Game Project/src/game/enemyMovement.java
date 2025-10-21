package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.swing.Timer;

/**
 * The animation for the short-ranged enemy.
 */
public class enemyMovement implements CollisionListener {
    /**
     * The current player.
     */
    private final Player player;
    /**
     * The short-ranged enemy.
     */
    private final Enemy enemy;
    /**
     * the left bound of enemy movement.
     */
    private float left;
    /**
     * The flag to track which way the enemy is facing.
     */
    private boolean flag2 = false;
    /**
     * to track the frame of the animation.
     */
    private int frame = 0;
    /**
     * The array to hold each of the walk frames.
     */
    private final BodyImage[] walkFrame;
    /**
     * to track if the player has taken damage.
     */
    private boolean damage = true;
    /**
     * to track the starting position.
     */
    private final Vec2 pos2;
    /**
     * to track if file has been saved.
     */
    private boolean saved;

    /**
     * Initialises the animation of the walker enemy.
     * <p>
     * creates and loads the animations for the walk animation
     * updates the animation after short delays.
     * Sets left and right bounds of movement.
     *
     * @param enemy the shooter enemy
     * @param player the current player
     * @param isLoaded tracks if level was saved and loaded
     */
    public enemyMovement(Enemy enemy, Player player, boolean isLoaded) {
        this.enemy = enemy;
        this.player = player;
        enemy.addCollisionListener(this);
        walkFrame = new BodyImage[5];
        pos2 = enemy.getPosition(); // get start position
        if (!isLoaded) { // sets left/right bounds for initial start to level
            float left = pos2.x;
            enemy.setLeft(left);
            enemy.setRight(left + 9);
        }

        loadWalkFrame();
        Timer timer = new Timer(125, _ -> {
            move();
            updateSprite();
        });
        timer.start();

        startMovingAfterLoad();
    }

    /**
     * updates the animation of the enemy.
     * <p>
     * when called, cycle through the walk animation.
     * if facing left, flip the images.
     */
    private void updateSprite() {
        enemy.removeAllImages();
        frame = (frame + 1) % walkFrame.length;
        if (flag2) {
            enemy.removeAllImages();
            enemy.addImage(walkFrame[frame]);
        } else {
            enemy.removeAllImages();
            AttachedImage flippedImage = enemy.addImage(walkFrame[frame]);
            flippedImage.flipHorizontal();
        }
    }

    /**
     * loads the walk frames.
     * <p>
     * for each of the frames in the walk array, display the image
     */
    public void loadWalkFrame() {
        for (int i = 0; i < walkFrame.length; i++) {
            walkFrame[i] = new BodyImage("data/owl/walk/walk" + i + ".png", 3);
        }
    }

    /**
     * enemy walking.
     * <p>
     * if the enemy position is less that left then walk right,
     * else walk left.
     */
    public void move() {
        Vec2 pos = enemy.getPosition(); // get live x position
        if (pos.x <= enemy.getLeft()) {
            enemy.startWalking(5); // walk right
            flag2 = true;
        }
        else if (pos.x >= enemy.getRight()) {
            flag2 = false;
            enemy.startWalking(-5); // walk left
        }
    }

    /**
     * collision method.
     * <p>
     * if the enemy comes in contact with the player and the damage is true,
     * the player loses a life.
     *
     * @param e The {@link CollisionEvent} contains details about the collision.
     */
    public void collide(CollisionEvent e){
        if (e.getOtherBody() instanceof Player && damage) {
            int lives = player.getLives();
            player.setLives(lives - 1);
            damage = false;
            Timer timer2 = new Timer(1000, _ -> damage = true);
            timer2.start();

        }
    }

    /**
     * Sets the save action.
     *
     * @param saved the save action as a {@link Boolean}
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * Gets the save action.
     *
     * @return the save action as a {@link Boolean}
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Sets the enemy to start moving straight after being loaded,
     * otherwise it gets stuck between the two bound conditions.
     */
    private void startMovingAfterLoad() {
        Vec2 pos = enemy.getPosition();
        if (Math.abs(pos.x - enemy.getLeft()) < Math.abs(pos.x - enemy.getRight())) {
            enemy.startWalking(5); // closer to left, move right
        } else {
            enemy.startWalking(-5); // closer to right, move left
        }
    }
}
