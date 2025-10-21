package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

/**
 * Finish line button
 */
public class FinishLine extends UserView implements SensorListener {
    /**
     * The current game.
     */
    private Game game;
    /**
     * The image for the un-pressed button.
     */
    private final BodyImage button = new BodyImage("data/button.png");
    /**
     * The image for the pressed button.
     */
    private final BodyImage button2 = new BodyImage("data/button2.png");
    /**
     * The body of the button.
     */
    private final StaticBody shape1;

    /**
     * Constructor to create the finish line button.
     *
     * @param world The world the button exists in.
     * @param game The game instance to trigger sounds.
     * @param width the width of the button
     * @param height the height of the button
     * @param x x coordinate of the button.
     * @param y y coordinate of the button.
     */
    public FinishLine(World world, Game game, int width, int height, int x, int y) {
        super(world, width, height);
        this.game = game;

        // body, shape and image of the finish line button
        Shape shape = new BoxShape(0.5f, 0.5f);
        shape1 = new StaticBody(world, shape);
        shape1.setPosition(new Vec2(x,y));
        new AttachedImage(shape1, button, 7f, 0, new Vec2(-18, 2f));

        // adding a sensor to the button to check collisions
        Sensor sensor = new Sensor(shape1, shape);
        sensor.addSensorListener(this);
    }

    /**
     * Called when the player first touches the button.
     * <p>
     * If a {@link Player} presses the button, the image changes,
     * and the sound is played.
     *
     * @param e The sensor event
     */
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player){
            game.getSoundManager().getButtonSound().play();
            shape1.removeAllImages();
            new AttachedImage(shape1, button2, 7f, 0, new Vec2(-18, 2f));
        }
    }

    /**
     * Called when the player first leaves the button.
     * <p>
     * If a {@link Player} leaves the button, change its image
     * and go to the next level.
     *
     * @param e The sensor event
     */
    public void endContact(SensorEvent e) {
        shape1.removeAllImages();
        new AttachedImage(shape1, button, 7f, 0, new Vec2(-18, 2f));
        game.goToNextLevel(); // calls the method to go and set the next level
    }
}
