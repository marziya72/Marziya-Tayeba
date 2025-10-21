package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Button to spawn the power up orb
 */
public class ButtonOrb extends StaticBody implements SensorListener {
    /**
     * The current game.
     */
    private Game game;
    /**
     * The world in which the player is in.
     */
    private World world;
    /**
     * The image for the untouched button.
     */
    private final BodyImage button1;
    /**
     * The image for the pressed button.
     */
    private final BodyImage button2;
    /**
     * The image for the un-pressed button.
     */
    private final BodyImage button3;
    /**
     * The coordinates for the button in the game.
     */
    private int x, y;
    /**
     * Tracks how many times the button is pressed.
     */
    private int count = 0;
    /**
     * To track if object has been pressed.
     */
    private boolean pressed;

    /**
     * Constructor to create the button for the power up orb.
     *
     * @param world The world the button exists in.
     * @param game The game instance to trigger sounds.
     * @param x x coordinate of the button.
     * @param y y coordinate of the button.
     */
    public ButtonOrb(World world, Game game, int x, int y) {
        super(world, new BoxShape(0.5f, 0.5f));
        this.world = world;
        this.x = x;
        this.y = y;
        this.game = game;

        // creates position and image of untouched button
        setPosition(new Vec2(x, y));
        button1 = new BodyImage("data/button3.png", 1);
        new AttachedImage(this, button1, 2f, 0, new Vec2(0, 1));

        // button images
        button2 = new BodyImage("data/button4.png", 1);
        button3 = new BodyImage("data/button5.png", 1);

        if (this.isPressed()) {
            new AttachedImage(this, button3, 2f, 0, new Vec2(0, 1));
        }

        // adds sensors to the button to detect collisions
        Sensor sensor = new Sensor(this, new BoxShape(0.5f, 0.5f));
        sensor.addSensorListener(this);
    }

    /**
     * Called when the player first touches the button.
     * <p>
     * If a {@link Player} steps on the button, the image changes,
     * and the power up orb is triggered if it hasn’t already been pressed.
     *
     * @param e The sensor event
     */
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player && count < 1 && !this.isPressed()) {
            this.removeAllImages();
            new AttachedImage(this, button2, 2f, 0, new Vec2(0, 1));
            collided();
            this.setPressed(true);// to play the sound and activate the power up orb
            count++;
        }
    }

    /**
     * Called when the player first leaves the button.
     * <p>
     * If a {@link Player} leaves the button, change its image.
     *
     * @param e The sensor event
     */
    public void endContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) {
            this.removeAllImages();
            new AttachedImage(this, button3, 2f, 0, new Vec2(0, 1));
        }
    }

    /**
     * Called when the button is successfully pressed.
     * <p>
     * plays sound and spawns the orb.
     */
    public void collided() {
        if (count != 1) {
            game.getSoundManager().getButtonSound().play();
            this.setPressed(true);
            new Orb(world, game, -8, 10);
            count++; // prevent activating the button again
        }
    }

    /**
     * Getter method for the pressing of the button.
     * <p>
     * This is used when saving if the button has been pressed or not
     *
     * @return pressed to track if pressed
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Setter method for the pressing of the button.
     * <p>
     * This is used when saving if the button was pressed
     *
     * @param pressed to track if button pressed
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
