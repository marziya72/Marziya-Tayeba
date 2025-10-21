package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * The power-up orb class.
 */
public class Orb extends DynamicBody implements CollisionListener {
    /**
     * The current game.
     */
    private Game game;
    /**
     * The body of the orb.
     */
    private DynamicBody orb;
    /**
     * The coordinates for the orb in the game.
     */
    private int x, y;
    /**
     * To track if object has been collected.
     */
    private boolean collected;

    /**
     * Initialise a new power-up orb.
     * <p>
     * loads in the orb image, sets its position and
     * adds a collision listener.
     *
     * @param world world in which the game is in
     * @param game game in which the player is in
     * @param x x coordinate of the orb position
     * @param y y coordinate of the orb position
     */
    public Orb(World world, Game game, int x, int y) {
        super(world, new CircleShape(0.5f));
        this.x = x;
        this.y = y;
        this.game = game;

        addImage(new BodyImage("data/orbP.png", 1));
        setPosition(new Vec2(x, y));
        addCollisionListener(this);
    }

    /**
     * collision method.
     * <p>
     * if the orb comes in contact with the player, the player
     * increases their speed to seven. the sounds are also played
     * and powerUp is set to true
     *
     * @param DynamicBody The {@link CollisionEvent} contains details about the collision.
     */
    public void collide(CollisionEvent DynamicBody) {
        if (DynamicBody.getOtherBody() instanceof Player player) {
            this.destroy();
            this.setCollected(true); // for saving
            player.setSpeed(7); // increase speed
            player.setPowerUp(true);
            game.getSoundManager().getPowerupSound().play();
        }
    }

    /**
     * Getter method for the collection of the orb.
     * <p>
     * This is used when saving if the orb has been collected or not
     *
     * @return collected to track if orb collected
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Setter method for the collection of the orb.
     * <p>
     * This is used when saving if the orb has been collected or not
     *
     * @param collected to track if orb collected
     */
    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
