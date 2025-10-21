package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.swing.Timer;

/**
 * The animation for the boss enemy.
 */
public class bossMovement implements CollisionListener {
    /**
     * The current player.
     */
    private final Player player;
    /**
     * The boss enemy.
     */
    private final Boss boss;
    /**
     * to track the frame of the animation.
     */
    private int frame = 0;
    /**
     * The array to hold each of the attack frames.
     */
    private final BodyImage[] attackFrame;
    /**
     * The array to hold each of the death frames.
     */
    private final BodyImage[] deathFrame;
    /**
     * to track if the player has taken damage.
     */
    private boolean damage = true;
    /**
     * The flag to track the enemy animation.
     */
    private String flag = "attack";
    /**
     * to track the frame of the death animation.
     */
    private int deathFrameCounter;


    /**
     * Initialises the animation of the boss enemy.
     * <p>
     * creates and loads the animations for the attack and death animation
     * updates the animation after short delays.
     *
     * @param boss the boss enemy
     * @param player the current player
     */
    public bossMovement(Boss boss, Player player) {
        this.boss = boss;
        this.player = player;
        boss.addCollisionListener(this);
        attackFrame = new BodyImage[6];
        deathFrame = new BodyImage[4];

        loadAttackFrame();
        loadDeathFrame();

        Timer timer = new Timer(125, _ -> {
            if (!"death".equals(boss.getFlag())) {
                move();
                updateSprite("attack"); // keep attacking if alive
            } else {
                updateSprite("death");  // switch to death animation when dying
            }
        });
        timer.start();
    }

    /**
     * updates the animation of the boss enemy.
     * <p>
     * if the state is attack, cycle through the attack animation
     * if the state is death, cycle through the death animation
     *
     * @param state the animation state.
     */
    private void updateSprite(String state) {
        if (!flag.equals(state)) {
            boss.removeAllImages();
            flag = state;
            frame = 0;
            deathFrameCounter = 0;
        }
        if (flag.equals("attack")) {
            boss.removeAllImages();
            frame = (frame + 1) % attackFrame.length;
            AttachedImage flippedImage = boss.addImage(attackFrame[frame]);
            flippedImage.flipHorizontal();
        }
        if (flag.equals("death")) {
            boss.removeAllImages();
            deathFrameCounter++;
            if (deathFrameCounter >= 4) {
                frame = (frame + 1) % deathFrame.length;
                deathFrameCounter = 0; // reset the counter
            }
            AttachedImage flippedImage = boss.addImage(deathFrame[frame]);
            flippedImage.flipHorizontal();
            if (frame == deathFrame.length - 1) {
                boss.destroy(); // only destroy after last death frame
            }
        }
    }

    /**
     * loads the attack frames.
     * <p>
     * for each of the frames in the attack array, display the image
     */
    public void loadAttackFrame() {
        for (int i = 0; i < attackFrame.length; i++) {
            attackFrame[i] = new BodyImage("data/boss/attack" + i + ".png", 8);
        }
    }

    /**
     * loads the death frames.
     * <p>
     * for each of the frames in the death array, display the image
     */
    public void loadDeathFrame() {
        for (int i = 0; i < deathFrame.length; i++) {
            deathFrame[i] = new BodyImage("data/boss/death" + i + ".png", 8);
        }
    }

    /**
     * boss enemy walking.
     * <p>
     * if the boss enemy position is not dead, walk towards the right
     * else stop walking.
     */
    public void move() {
        if (!"death".equals(boss.getFlag())) {
            boss.startWalking(2); // only move if not dying
        } else {
            boss.stopWalking(); // stop moving if dying
        }
    }

    /**
     * collision method.
     * <p>
     * if the boss enemy comes in contact with the player and the damage is true,
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
}
