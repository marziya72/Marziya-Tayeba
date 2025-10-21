package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.awt.*;

/**
 * The boss's health bar.
 */
public class BossLives extends UserView {
    /**
     * The enemy boss.
     */
    private final Boss boss;
    /**
     * The current game.
     */
    private Game game;

    /**
     * Initialise a new health bar for the boss.
     * <p>
     * This constructor sets up the health bar
     *
     * @param world world in which the game is in
     * @param width width of the world
     * @param height height of the world
     * @param boss boss of the health bar
     * @param game game in which the boss is in
     */
    public BossLives(World world, int width, int height, Boss boss, Game game) {
        super(world, width, height);
        this.boss = boss;
        this.game = game;
    }

    /**
     * paints the foreground.
     * <p>
     * The health bar is created here where each hit taken by the boss
     * results in a segment of the health bar being taken off.
     * The health bar follows the boss as it moves.
     *
     * @param g The {@link Graphics2D} object used for drawing on the screen.
     */
    protected void paintForeground(Graphics2D g) {
        super.paintForeground(g);
        int widthFull = 180;
        int widthEach = 12;
        int healthHeight = 18;

        if (boss.getHitCount() >= 0 && boss.getHitCount() < 15) {
            Vec2 bossPos = boss.getPosition();
            int x = (int) (bossPos.x + 100);
            int y = (int) (bossPos.y + 180);
            int greenWidth = widthFull - (boss.getHitCount() * widthEach) + 6;
            g.setColor(Color.red);
            g.fillRect(x + 2, y + 2, greenWidth, healthHeight);
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(4.9f));
            g.drawRect(x, y, widthFull + 10, 22);
        } else if (boss.getHitCount() == 15) {
            boss.setFlag("death");
            game.goToNextLevel();
        }
    }
}