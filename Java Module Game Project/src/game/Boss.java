package game;

import city.cs.engine.*;

/**
 * The Boss Enemy.
 */
public class Boss extends Walker {
    /**
     * The shape of the boss.
     */
    private static final Shape bossShape = new BoxShape(2,4f);
    /**
     * the value to track how many hits received by the boss.
     */
    private int hitCount = 0;
    /**
     * The flag to track the boss animation.
     */
    private String flag;

    /**
     * Initialise a new boss enemy.
     * <p>
     * Sets up the boss shape into the world
     *
     * @param world world in which the game is in
     */
    public Boss(World world) {
        super(world, bossShape);
    }

    /**
     * tracks how many hits the boss has taken.
     * <p>
     * if the method is called, the hit count increases
     * and if it is/exceeds 15, the boss is destroyed.
     */
    public void hit() {
        hitCount++;
        System.out.println(hitCount);
        if (hitCount >= 15) {
            setFlag("death");
        }
    }

    /**
     * Getter method for the hitCount.
     * <p>
     * returns how many times the boss is hit.
     *
     * @return the integer hitCount value.
     */
    public int getHitCount() {
        return hitCount;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * Getter method for the state flag.
     * <p>
     * returns the flag for the boss animation.
     *
     * @return the string flag state.
     */
    public String getFlag() {
        return flag;
    }
}

