package game;

import city.cs.engine.*;

import javax.swing.Timer;

/**
 * The animation for the shooter enemy.
 */
public class enemy2Movement implements CollisionListener {
    /**
     * The current player.
     */
    private final Player player;
    /**
     * The shooter enemy.
     */
    private final Enemy2 enemy;
    /**
     * The flag to track the enemy animation.
     */
    private String flag = "idle";
    /**
     * to track the frame of the animation.
     */
    private int frame = 0;
    /**
     * The array to hold each of the idle frames.
     */
    private BodyImage[] idleFrame;
    /**
     * The array to hold each of the throw frames.
     */
    private BodyImage[] throwFrame;
    /**
     * to track if the player has taken damage.
     */
    private boolean damage = true;

    /**
     * Initialises the animation of the shooter enemy.
     * <p>
     * creates and loads the animations for the throw and idle animation
     * updates the animation after short delays
     * but updates the shooting after a longer delay to ease player game difficulty.
     *
     * @param enemy the shooter enemy
     * @param player the current player
     */
    public enemy2Movement(Enemy2 enemy, Player player) {
        this.enemy = enemy;
        this.player = player;
        enemy.addCollisionListener(this);
        idleFrame = new BodyImage[3];
        throwFrame = new BodyImage[3];
        loadIdleFrame();
        loadThrowFrame();
        Timer timer = new Timer(125, _ -> {
            updateSprite(flag);
        });
        timer.start();
        Timer timer2 = new Timer(600, _ -> {
            checkSameLvl();
        });
        timer2.start();
    }

    /**
     * updates the animation of the enemy.
     * <p>
     * if the state is idle, cycle through the idle animation
     * if the state is throw, cycle through the shooting animation
     *
     * @param state the animation state.
     */
    private void updateSprite(String state) {
        if (!flag.equals(state)) {
            enemy.removeAllImages();
            flag = state;
            frame = 0;
        }
        if (flag.equals("idle")) {
            frame = (frame + 1) % idleFrame.length;
            enemy.removeAllImages();
            AttachedImage flippedImage = enemy.addImage(idleFrame[frame]);
            flippedImage.flipHorizontal();
        }
        if (flag.equals("throw")) {
            frame = (frame + 1) % throwFrame.length;
            enemy.removeAllImages();
            AttachedImage flippedImage = enemy.addImage(throwFrame[frame]);
            flippedImage.flipHorizontal();
        }
    }

    /**
     * loads the idle frames.
     * <p>
     * for each of the frames in the idle array, display the image
     */
    public void loadIdleFrame() {
        for (int i = 0; i < idleFrame.length; i++) {
            idleFrame[i] = new BodyImage("data/dude/idle/idle" + i + ".png", 3);
        }
    }

    /**
     * loads the throw frames.
     * <p>
     * for each of the frames in the throw array, display the image
     */
    public void loadThrowFrame() {
        for (int i = 0; i < throwFrame.length; i++) {
            throwFrame[i] = new BodyImage("data/dude/throw/throw" + i + ".png", 3);
        }
    }

    /**
     * checks if the enemy is on the same y level as the player.
     * <p>
     * if the player is on the same y level as the enemy,
     * update the animation to the throw animation and start shooting
     * else be idle
     */
    public void checkSameLvl() {
        float playerY = player.getPosition().y;
        float enemyY = enemy.getPosition().y;

        if (Math.abs(playerY - enemyY) < 1.0f && !enemy.getShoot()) {
            flag = "throw";
            EnemyShooting shoot = new EnemyShooting(enemy.getWorld(), enemy, this);
            shoot.shoot();
        } else {
            flag = "idle";
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
}
