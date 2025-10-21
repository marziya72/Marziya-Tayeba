package game;
import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import javax.swing.*;

/**
 * A platform that disappears after a while,
 * when made contact with.
 */
public class DPlatform extends StaticBody implements CollisionListener {
    /**
     * The shape of the platform.
     */
    private static Shape platformShape = new BoxShape(3, 0.5f);
    /**
     * The timer for how long before the platform disappears.
     */
    private Timer timer;
    /**
     * The image for the platform.
     */
    private BodyImage smallP;

    /**
     * Constructor to create the platform.
     *
     * @param world The world the platform exists in.
     * @param x x coordinate of the platform.
     * @param y y coordinate of the platform.
     */
    public DPlatform(World world, int x, int y) {
        super(world, platformShape);
        this.setPosition(new Vec2(x, y));

        smallP = new BodyImage("data/dPlatform.png", 4);
        new AttachedImage(this, smallP, 2, 0, new Vec2(1, -2.3f));

        this.addCollisionListener(this); // to detect collision of player with platform
    }

    /**
     * collision method.
     * <p>
     * if the player makes contact with the platform, the
     * platform will disappear after a delay
     *
     * @param e The {@link CollisionEvent} contains details about the collision.
     */
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player player) {
            // timer for platform, once runs out, platform is destroyed
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            timer = new Timer(2500, _ -> {
                this.destroy();
            });
            timer.start();
        }
    }
}
