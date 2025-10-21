package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;

/**
 * The structure of Level 3.
 */
public class Level3 extends GameLevel{
    /**
     * The background image.
     */
    private final Image background;

    /**
     * Initialise a new level 3 Game.
     * <p>
     * Sets up the game, adding in all the platforms, bodies and images.
     *
     * @param game game in which the player is in
     */
    public Level3(Game game) {
        super(game);
        background = new ImageIcon("data/sky.png").getImage();
        game.getSoundManager().muteCurrentBgm();
        game.getSoundManager().setCurrentBgm(game.getSoundManager().getBgmSound3());
        game.getSoundManager().playBgm(game.getSoundManager().getBgmSound3());
        game.getSoundManager().getBgmSound3().setVolume(0.2);


        // ground platform
        Shape shape = new BoxShape(20, 0.5f);
        StaticBody ground = new StaticBody(this, shape);
        ground.setPosition(new Vec2(0f, 3));

        // platform images
        BodyImage smallBushRImg = new BodyImage("data/platform.png", 4);
        BodyImage smallP = new BodyImage("data/platform2.png", 4);
        BodyImage longP = new BodyImage("data/platform3.png", 4);
        BodyImage groundImg = new BodyImage("data/platform4.png", 4);
        BodyImage longBushRImg = new BodyImage("data/platform5.png", 4);
        BodyImage verticalImg = new BodyImage("data/vertical.png", 4);
        BodyImage instructionImg = new BodyImage("data/instruction3.png", 4);

        // platform shapes and positions
        Shape platformShape = new BoxShape(3, 0.5f);
        Shape verticalShape = new BoxShape(0.1f, 2.8f);
        Shape platformShape2 = new BoxShape(6.5f, 0.5f);
        Shape sideShapes = new BoxShape(0.2f, 35);
        new DPlatform(this, -8, 12);
        StaticBody platform2 = new StaticBody(this, platformShape);
        platform2.setPosition(new Vec2(8, 17));
        StaticBody platform3 = new StaticBody(this, platformShape);
        platform3.setPosition(new Vec2(-13, 30));
        StaticBody platform4 = new StaticBody(this, platformShape2);
        platform4.setPosition(new Vec2(9f, 33));
        StaticBody platform5 = new StaticBody(this, platformShape);
        platform5.setPosition(new Vec2(-13, 37));
        StaticBody platform6 = new StaticBody(this, platformShape2);
        platform6.setPosition(new Vec2(-8f, 50));
        StaticBody vPlatform = new StaticBody(this, verticalShape);
        vPlatform.setPosition(new Vec2(5f, 8.2f));
        StaticBody instruction = new StaticBody(this, platformShape);
        instruction.setPosition(new Vec2(5f, -8.2f));

        // sides of the game so player does not fall off
        StaticBody side1 = new StaticBody(this, sideShapes);
        side1.setPosition(new Vec2(-20.3f, 35f));
        StaticBody side2 = new StaticBody(this, sideShapes);
        side2.setPosition(new Vec2(20.3f, 35f));
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
        new AttachedImage(ground, groundImg, 1, 0, new Vec2(0f, 0f));
        new AttachedImage(platform2, smallBushRImg, 2, 0, new Vec2(1f, -2.3f));
        AttachedImage bench3 = new AttachedImage(platform3, smallBushRImg, 2, 0, new Vec2(1, -2.3f));
        bench3.flipHorizontal();
        new AttachedImage(platform4, longP, 0.7f, 0, new Vec2(0.1f, 0.1f));
        new AttachedImage(platform5, smallP, 2, 0, new Vec2(1f, -2.3f));
        new AttachedImage(platform6, longBushRImg, 2f, 0, new Vec2(0.5f, -2.4f));
        new AttachedImage(vPlatform, verticalImg, 1.5f, 0, new Vec2(2.2f, 0.2f));
        new AttachedImage(instruction, instructionImg, 3f, 0, new Vec2(-4f, 4.5f));

        // interactive objects/bodies
        new Trampoline(this, 0, 22);
        new Trampoline(this, -16, 41);
        new Trampoline(this, 0, 52);
        new FinishLine(this,game, 800,600,18, 60);
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
        return "Level3";
    }
}
