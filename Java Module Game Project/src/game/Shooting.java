package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.event.*;
import javax.swing.Timer;

/**
 * The Shooting logic for the player.
 */
public class Shooting implements MouseListener {
    /**
     * The world in which the game is in.
     */
    private World world;
    /**
     * The current player.
     */
    private Player player;
    /**
     * The animation for the player.
     */
    private Movement move;

    /**
     * Initialises the shooting for the player.
     *
     * @param world the current world
     * @param player the current player
     * @param move the animation of the player
     */
    public Shooting(World world, Player player, Movement move) {
        this.world = world;
        this.player = player;
        this.move = move;
    }

    /**
     * Method for player shooting.
     * <p>
     * creates the rock to be thrown and sets the position it shoots from, next to the player.
     * the velocity is set as well.
     * after a delay, the rock is destroyed to avoid too many rock bodies in the level.
     * if the rock hits an enemy, the enemy's hit method is called.
     */
    public void shoot() {
            CircleShape circle = new CircleShape(0.3f);
            DynamicBody rock = new DynamicBody(world,circle);
            BodyImage img = new BodyImage("data/Rock1.png", 4);
            AttachedImage rockImage = new AttachedImage(rock,img,0.15f,0,new Vec2());

            Vec2 playerPos = player.getPosition();

            if (player.getFacingRight()) {
                rock.setPosition(player.getPosition());
                rock.setLinearVelocity(new Vec2(20, 0));
            }
            else {
                rock.setPosition(new Vec2(playerPos.x-1,playerPos.y));
                rock.setLinearVelocity(new Vec2(-20, 0));
            }

            Timer timer = new Timer(2000, e -> {
                rock.destroy();
            });
            timer.setRepeats(false); // only run once
            timer.start();

            rock.addCollisionListener(new CollisionListener() {
                @Override
                public void collide(CollisionEvent e) {
                    if (e.getOtherBody() instanceof Enemy) {
                        ((Enemy) e.getOtherBody()).hit();
                    }
                    if (e.getOtherBody() instanceof Enemy2) {
                        ((Enemy2) e.getOtherBody()).hit();
                        ((Enemy2) e.getOtherBody()).setShoot(true);
                    }
                    if (e.getOtherBody() instanceof Boss) {
                        ((Boss) e.getOtherBody()).hit();
                    }
                }
            });
    }

    /**
     * Setter method for the player in the game world.
     * <p>
     * This is used to update what is happening to the player.
     *
     * @param level the current level
     * @param player player to be tracked
     */
    public void updatePlayer(GameLevel level, Player player) {
        this.player = player;
        this.world = level;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * mouse press method.
     * <p>
     * if the player presses the mouse button 1,
     * the animation of the player updates to throw and shoots
     *
     * @param e The {@link MouseEvent} contains details about the mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            move.updateSprite("throw");
            shoot();
        }
    }

    /**
     * mouse release method.
     * <p>
     * if the player releases the mouse button 1,
     * the animation of the player updates to idle
     *
     * @param e The {@link MouseEvent} contains details about the mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        move.updateSprite("idle");
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
