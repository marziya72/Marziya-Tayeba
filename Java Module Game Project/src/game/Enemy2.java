package game;

import city.cs.engine.*;
import city.cs.engine.Shape;

/**
 * The Shooting Enemy.
 */
public class Enemy2 extends Walker {
    /**
     * The shape of the enemy.
     */
    private static final Shape enemyShape = new BoxShape(1,1.2f);
    /**
     * the value to track how many hits received by the enemy.
     */
    private int hitCount = 0;
    /**
     * The value to track if the enemy has shot a rock.
     */
    private boolean shoot = false;

    /**
     * Initialise a new shooter enemy.
     * <p>
     * Sets up the enemy shape into the world
     *
     * @param world world in which the game is in
     */
    public Enemy2(World world) {
        super(world, enemyShape);
    }

    /**
     * tracks how many hits the enemy has taken.
     * <p>
     * if the method is called, the hit count increases
     * and if it is/exceeds 3, the enemy is destroyed.
     */
    public void hit() {
        hitCount++;
        if (hitCount >= 3) {
            this.destroy();
        }
    }

    /**
     * Setter method for the enemy's shot.
     * <p>
     * the boolean value is set to true if the enemy
     * has taken a shot and false if not
     *
     * @param shoot The boolean value to track if the enemy has shot.
     */
    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    /**
     * Getter method for the enemy's shot.
     * <p>
     * retrieves whether the enemy has taken a shot or not
     *
     * @return the shoot boolean value.
     */
    public boolean getShoot() {
        return shoot;
    }
}
