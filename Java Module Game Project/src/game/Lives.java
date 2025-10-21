package game;

import city.cs.engine.*;

import javax.swing.*;
import java.awt.*;

/**
 * The health bar.
 */
public class Lives extends UserView {
    /**
     * The current player.
     */
    private final Player player;
    /**
     * Handles the sounds like the bgm or items sounds.
     */
    private SoundManager sound = new SoundManager();

    /**
     * Initialise a new health bar for the player.
     * <p>
     * This constructor sets up the health bar
     *
     * @param world world in which the game is in
     * @param width width of the world
     * @param height height of the world
     * @param player player of the health bar
     */
    public Lives(World world, int width, int height, Player player) {
        super(world, width, height);
        this.player = player;
    }

    /**
     * paints the foreground.
     * <p>
     * The health bar is created here where each hit taken by the player
     * results in a segment of the health bar being taken off.
     * If the player has a shield, the health bar is extended,
     * and they essentially have an extra life.
     * The game over text is also created here,
     * with the addition of a game over sound, and the system exits after a delay
     *
     * @param g The {@link Graphics2D} object used for drawing on the screen.
     */
    protected void paintForeground(Graphics2D g) {
        super.paintForeground(g);
        int baseX = 23;
        int baseY = 23;
        int widthFull = 176;
        int widthEach = 60;
        int healthHeight = 18;

        if (player.getLives() == 4) {
            g.setColor(Color.orange);
            g.fillRect(baseX, baseY, widthFull + widthEach + 5, healthHeight);
            g.setColor(Color.green);
            g.fillRect(baseX, baseY, widthFull, healthHeight);
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(4.9f));
            g.drawRect(20, 20, widthFull + widthEach + 10, 22);
        }
        else if (player.getLives() > 0) {
            int greenWidth = player.getLives() * widthEach;
            g.setColor(Color.green);
            g.fillRect(baseX, baseY, greenWidth, healthHeight);
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(4.9f));
            g.drawRect(20, 20, widthFull + 10, 22);
        }
        else {
            super.paintForeground(g);
            g.setColor(Color.white);
            g.setFont(new Font("ComicSans", Font.BOLD, 65));
            g.drawString("GAME OVER", 200, 300);
            sound.getGameOverSound().play();
            getWorld().stop();
            Timer timer = new Timer(900, _ -> {
                System.exit(0);
            });
            timer.start();
        }
    }
}