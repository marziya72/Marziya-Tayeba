package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * The collectible shield class
 */
public class Shield extends StaticBody implements CollisionListener{
    /**
     * The current game.
     */
    private Game game;
    /**
     * The coordinates for the shield in the game.
     */
    private int x, y;
    /**
     * To track if object has been collected.
     */
    private boolean collected;

    /**
     * Initialise a new shield collectible.
     * <p>
     * loads in the shield image, set its position and
     * calls adds a collision listener.
     *
     * @param world world in which the game is in
     * @param game game in which the player is in
     * @param x x coordinate of the shield position
     * @param y y coordinate of the shield position
     */
    public Shield(World world, Game game, int x, int y) {
        super(world, new BoxShape(0.5f, 0.5f));
        this.game = game;
        this.x = x;
        this.y = y;

        addImage(new BodyImage("data/shield.png", 1.5f));
        setPosition(new Vec2(x, y));
        addCollisionListener(this);
    }

    /**
     * collision method.
     * <p>
     * if the shield comes in contact with the player, the player
     * increases their lives by one. the sounds are also played
     *
     * @param StaticBody The {@link CollisionEvent} contains details about the collision.
     */
    @Override
    public void collide(CollisionEvent StaticBody) {
        if (StaticBody.getOtherBody() instanceof Player player) {
            this.destroy();
            this.setCollected(true);
            player.setLives(player.getLives()+1);
            game.getSoundManager().getShieldSound().play();
        }
    }

    /**
     * Getter method for the collection of the shield.
     * <p>
     * This is used when saving if the shield has been collected or not
     *
     * @return collected to track if shield collected
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Setter method for the collection of the shield.
     * <p>
     * This is used when saving if the shield has been collected or not
     *
     * @param collected to track if shield collected
     */
    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
