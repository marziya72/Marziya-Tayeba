package game;

import city.cs.engine.*;

/**
 * The Sword Enemy.
 */
public class Enemy extends Walker {
    /**
     * The shape of the enemy.
     */
    private static final Shape enemyShape = new BoxShape(1,1.2f);
    /**
     * the value to track how many hits received by the enemy.
     */
    private int hitCount = 0;
    /**
     * the right bound of enemy movement.
     */
    private static float right;
    /**
     * the left bound of enemy movement.
     */
    private float left;

    /**
     * Initialise a new short-ranged enemy.
     * <p>
     * Sets up the enemy shape into the world
     *
     * @param world world in which the game is in
     */
    public Enemy(World world) {
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
     * Sets the left bound.
     *
     * @param left the left bound as a {@link Float}
     */
    public void setLeft(float left) {
        this.left = left;
    }

    /**
     * Sets the right bound.
     *
     * @param right the right bound as a {@link Float}
     */
    public void setRight(float right) {
        this.right = right;
    }

    /**
     * Returns the left bound.
     *
     * @return the left bound as a {@link Float}
     */
    public float getLeft() {
        return left;
    }

    /**
     * Returns the right bound.
     *
     * @return the right bound as a {@link Float}
     */
    public float getRight() {
        return right;
    }
}
