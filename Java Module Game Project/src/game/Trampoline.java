package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;

/**
 * The trampoline platform.
 */
public class Trampoline extends StaticBody implements CollisionListener {
    /**
     * The shape of the trampoline.
     */
    private static final Shape trampolineShape = new BoxShape(1.5f, 0.1f);
    /**
     * The image for the stationary trampoline.
     */
    private final BodyImage img5;
    /**
     * The image for the extended trampoline.
     */
    private final BodyImage img6;
    /**
     * The timer for the extension of the trampoline.
     */
    private Timer timer;

    /**
     * Initialise a new trampoline platform.
     * <p>
     * loads in the trampoline images and
     * adds a collision listener to it.
     *
     * @param world world in which the game is in
     * @param x x coordinate of the trampoline position
     * @param y y coordinate of the trampoline position
     */
    public Trampoline(World world, int x, int y) {
        super(world, trampolineShape);
        this.setPosition(new Vec2(x, y));

        img5 = new BodyImage("data/trampoline.png", 4);
        img6 = new BodyImage("data/trampoline2.png", 4);

        new AttachedImage(this, img5, 0.5f, 0, new Vec2(0, -0.2f));

        this.addCollisionListener(this);
    }

    /**
     * collision method.
     * <p>
     * if the player makes contact with the trampoline,
     * increase the players linear velocity (in the y-axis),
     * and change the trampoline image. after a small delay,
     * revert image back to its original image.
     *
     * @param e The {@link CollisionEvent} contains details about the collision.
     */
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player player) {
            player.setLinearVelocity(new Vec2(player.getLinearVelocity().x, 15));
            this.removeAllImages();
            new AttachedImage(this, img6, 0.5f, 0, new Vec2(0.5f, -0.2f));
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            timer = new Timer(1000, _ -> {
                this.removeAllImages();
                new AttachedImage(this, img5, 0.5f, 0, new Vec2(0.2f, -0.2f));
            });
            timer.start();
        }
    }
}
