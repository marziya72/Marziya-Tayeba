package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;

/**
 * The structure of Level 4.
 */
public class Level4 extends GameLevel {
    /**
     * The background image.
     */
    private Image background;

    /**
     * Initialise a new level 4 Game.
     * <p>
     * Sets up the game, adding in all the platforms, bodies and images.
     *
     * @param game game in which the player is in
     */
    public Level4(Game game) {
        super(game);
        background = new ImageIcon("data/sky4.png").getImage();
        game.getSoundManager().muteCurrentBgm();
        game.getSoundManager().setCurrentBgm(game.getSoundManager().getBgmSound4());
        game.getSoundManager().playBgm(game.getSoundManager().getBgmSound4());
        game.getSoundManager().getBgmSound4().setVolume(0.2);

        // ground platform
        Shape shape = new BoxShape(20, 0.5f);
        StaticBody ground = new StaticBody(this, shape);
        ground.setPosition(new Vec2(0f, 3));

        // platform images
        BodyImage img1 = new BodyImage("data/platform.png", 4);
        BodyImage img2 = new BodyImage("data/platform2.png", 4);
        BodyImage img3 = new BodyImage("data/platform3.png", 4);
        BodyImage img4 = new BodyImage("data/platform4.png", 4);
        BodyImage img5 = new BodyImage("data/platform5.png", 4);
        BodyImage img6 = new BodyImage("data/vertical.png", 4);
        BodyImage instructionImg = new BodyImage("data/instruction1.png", 4);

        // platform shapes and positions
        Shape sideShapes = new BoxShape(0.2f, 15);

        // sides of the game so player does not fall off
        StaticBody side1 = new StaticBody(this, sideShapes);
        side1.setPosition(new Vec2(-20.3f, 15f));
        StaticBody side2 = new StaticBody(this, sideShapes);
        side2.setPosition(new Vec2(20.3f, 15f));
        Sensor leftS = new Sensor(side1, sideShapes);
        leftS.addSensorListener(new SensorListener() {
            public void beginContact(SensorEvent e) {
                if (e.getContactBody() instanceof Player player) {
                    player.setTouch(true);
                }
            }

            public void endContact(SensorEvent e) {
                if (e.getContactBody() instanceof Player player) {
                    player.setTouch(false);
                }
            }
        });
        Sensor rightS = new Sensor(side1, sideShapes);
        rightS.addSensorListener(new SensorListener() {
            public void beginContact(SensorEvent e) {
                if (e.getContactBody() instanceof Player player) {
                    player.setTouch(true);
                }
            }

            public void endContact(SensorEvent e) {
                if (e.getContactBody() instanceof Player player) {
                    player.setTouch(false);
                }
            }
        });

        // attaching images to platforms
        new AttachedImage(ground, img4, 1, 0, new Vec2(0f, 0f));

        // interactive objects/bodies
        new FinishLine(this,game, 800,600,18, 22);
    }

    /**
     * Method for returning if level is complete.
     * <p>
     * returns whether the level is complete.
     *
     * @return a boolean value if complete.
     */
    @Override
    public Boolean isComplete() {
        return null;
    }

    /**
     * Getter method for the background.
     * <p>
     * returns the background image for the level.
     *
     * @return the background {@link Image} instance.
     */
    @Override
    public Image getBackground(){
        return background;
    }

    /**
     * Getter method for the level name.
     * <p>
     * returns the current level name.
     *
     * @return the string value of the level.
     */
    @Override
    public String getLevelName() {
        return "Level4";
    }
}
