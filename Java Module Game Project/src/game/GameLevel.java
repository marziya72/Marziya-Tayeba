package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.awt.*;

/**
 * The base class for all the game levels.
 * <p>
 * Each level extends this class and provides its own implementation
 * for level completion, background image, and level name.
 */
public abstract class GameLevel extends World {
    /**
     * The current player.
     */
    private Player player;
    /**
     * The game in which the player is in.
     */
    private Game game;

    /**
     * Constructs a new game level.
     * <p>
     * Initializes the world and adds the player to it with a default
     * position and gravity scale.
     *
     * @param game the main game instance
     */
    public GameLevel(Game game) {
        super();
        this.game = game;
        player = new Player(this);
        player.setPosition(new Vec2(4,6));
        player.setGravityScale(1.2f); // set low so easier for player to jump
    }

    /**
     * Returns the player instance in this level.
     *
     * @return the {@link Player} object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Determines whether the level has been completed.
     *
     * @return true if the level is complete, otherwise false
     */
    public abstract Boolean isComplete();

    /**
     * Returns the background image for this level.
     *
     * @return the background {@link Image}
     */
    public abstract Image getBackground();

    /**
     * Returns the name of the current level.
     *
     * @return the level name as a {@link String}
     */
    public abstract String getLevelName();
    /**
     * populates the level with interactive features.
     * <p>
     * When called, it populates the level with interactive features,
     * that have their data stored in a save file for a mid-game,
     * save/load feature.
     */
    public void populate1() {
        Enemy enemy = new Enemy(this);
        enemy.setPosition(new Vec2(-17f, 14));
        new enemyMovement(enemy,this.getPlayer(), false);
        new OrbCollectible(this, game,0,5);
        new OrbCollectible(this, game,-4,5);
        new OrbCollectible(this, game,-8,5);
        new OrbCollectible(this, game,12,21);
    }
    /**
     * populates the level with interactive features.
     * <p>
     * When called, it populates the level with interactive features,
     * that have their data stored in a save file for a mid-game,
     * save/load feature.
     */
    public void populate2() {
        Enemy2 enemy2 = new Enemy2(this);
        enemy2.setPosition(new Vec2(12f, 24));
        new enemy2Movement(enemy2,this.getPlayer());
        new OrbCollectible(this, game,-1,12);
        new OrbCollectible(this, game,-10,14);
        new OrbCollectible(this, game,-8,25);
        new OrbCollectible(this, game,13,33);
        new Shield(this,game, 8 , 5);
    }
    /**
     * populates the level with interactive features.
     * <p>
     * When called, it populates the level with interactive features,
     * that have their data stored in a save file for a mid-game,
     * save/load feature.
     */
    public void populate3() {
        Enemy enemy = new Enemy(this);
        enemy.setPosition(new Vec2(-14, 52));
        new enemyMovement(enemy, this.getPlayer(), false);
        Enemy2 enemy2 = new Enemy2(this);
        enemy2.setPosition(new Vec2(14f, 35.5f));
        new enemy2Movement(enemy2, this.getPlayer());
        new ButtonOrb(this, game, -10, 3);
        new Shield(this, game, -8, 14);
        new OrbCollectible(this, game, 8, 19);
        new OrbCollectible(this, game, 0, 27);
        new OrbCollectible(this, game, -12, 32);
        new OrbCollectible(this, game, -4, 40);
        new OrbCollectible(this, game, -16, 52);
    }
}
