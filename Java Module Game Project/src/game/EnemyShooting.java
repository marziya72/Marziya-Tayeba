package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.swing.Timer;

/**
 * The Shooting logic for the shooter enemy.
 */
public class EnemyShooting {
    /**
     * The world in which the game is in.
     */
    private World world;
    /**
     * The shooter enemy.
     */
    private Enemy2 enemy;
    /**
     * The animation for the enemy.
     */
    private enemy2Movement move;

    /**
     * Initialises the shooting for the shooter enemy.
     *
     * @param world the current world
     * @param enemy the shooter enemy
     * @param move the animation of the enemy
     */
    public EnemyShooting(World world, Enemy2 enemy, enemy2Movement move) {
        this.world = world;
        this.enemy = enemy;
        this.move = move;
    }

    /**
     * Method for enemy shooting.
     * <p>
     * creates the rock to be thrown and sets the position it shoots from, next to the enemy.
     * the velocity is set as well.
     * after a delay, the rock is destroyed to avoid too many rock bodies in the level.
     * if the rock hits a player, the player loses a life.
     */
    public void shoot() {
        CircleShape circle = new CircleShape(0.3f);
        DynamicBody rock = new DynamicBody(world,circle);
        BodyImage img = new BodyImage("data/Rock1.png", 4);
        AttachedImage rockImage = new AttachedImage(rock,img,0.15f,0,new Vec2());

        Vec2 enemyPos = enemy.getPosition();

        rock.setPosition(new Vec2(enemyPos.x-1,enemyPos.y));
        rock.setLinearVelocity(new Vec2(-20, 0));

        Timer timer = new Timer(2000, e -> {
            rock.destroy();
        });
        timer.setRepeats(false); // only run once
        timer.start();

        rock.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                if (e.getOtherBody() instanceof Player) {
                    ((Player) e.getOtherBody()).setLives(((Player) e.getOtherBody()).getLives()-1);
                }
            }
        });
    }

}
