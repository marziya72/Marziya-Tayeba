package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * The main visual functions of the game.
 */
public class Camera extends UserView {
    /**
     * The current player.
     */
    private Player player;
    /**
     * The boss of level 4.
     */
    private Boss boss;
    /**
     * The health bar.
     */
    private Lives lives;
    /**
     * The boss health bar.
     */
    private BossLives bossLives;
    /**
     * tracks that the power up is only consumed once.
     */
    private boolean running = false;
    /**
     * The current game.
     */
    private Game game;
    /**
     * tracks the seconds for the timer.
     */
    private int seconds = 0;
    /**
     * Used to display the timer text.
     */
    private String timerText;
    /**
     * The timer that tracks the game.
     */
    private Timer gameTimer;

    /**
     * Initialise a new Camera.
     * <p>
     * Sets up the game timer and the power up timer.
     * Displays the score, the timer, the power up message, the health bar,
     * and the save/load message
     *
     * @param world world in which the game is in
     * @param game game in which the player is in
     * @param height the height of the view
     * @param player the player the camera follows
     * @param width the width of the view
     */
    public Camera(GameLevel world, int width, int height, Player player, Game game) {
        super(world, width, height);
        this.player = player;
        this.lives = new Lives(world,width,height, (Player) player);
        this.game = game;

        gameTimer = new Timer(1000, e -> {
            if (game.isRestart()) {
                if (getWorld() instanceof Level1) { // if its level 1, can restart timer to 0
                    seconds = 0;
                } else if (getWorld() instanceof Level2) { // if level 2 or 3, restart timer to the time they started the corresponding level.
                    seconds = game.getLevelStartTime();
                } else if (getWorld() instanceof Level3) {
                    seconds = game.getLevelStartTime();
                }
                game.setRestart(false); // reset
            } else {
                seconds++;
            }
            repaint();
        });
        gameTimer.start();
    }

    /**
     * paints the background.
     * <p>
     * The background follows the player and is set so that
     * the view follows the camera up and down but not left and right
     *
     * @param g The {@link Graphics2D} object used for drawing on the screen.
     */
    protected void paintBackground(Graphics2D g) {
        g.drawImage(game.getLevel().getBackground(), -30, -190, this);
        this.setCentre(new Vec2(0, player.getPosition().y));
    }

    /**
     * paints the foreground.
     * <p>
     * The score, time, power up message and load/save message
     * is created and displayed from here.
     * the health bar of the player and boss is also displayed from here.
     *
     * @param g The {@link Graphics2D} object used for drawing on the screen.
     */
    protected void paintForeground(Graphics2D g) {
        lives.paintForeground(g);
        if (game.getLevel().getLevelName().equals("Level4")) {
            bossLives.paintForeground(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("ComicSans", Font.PLAIN, 20));
        timerText = String.format("%02d:%02d", seconds / 60, seconds % 60);
        g.drawString("Press \"1\" to Save, \"2\" to Load", 535, 580); // load/save msg
        g.drawString("Score: " + player.getScore(), 480, 39); // score msg
        int textWidth = g.getFontMetrics().stringWidth(timerText);
        g.drawString("Time: " + timerText, getWidth() - textWidth - 65, 38); // timer msg
        player.setTimerText(timerText);
        player.setSeconds(seconds);

        if (player.getPowerUp()) {
            g.setColor(Color.white);
            g.setFont(new Font("ComicSans", Font.BOLD, 10));
            Point2D.Float pos = worldToView(player.getPosition());
            g.drawString("+2 Speed", (int) pos.getX(), (int) pos.getY() - 20); // speed msg

            if (!running) {
                running = true;
                Timer timer = new Timer(4000, _ -> { // msg disappears after a delay
                    player.setPowerUp(false);
                    running = false;
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    /**
     * Setter method for the player.
     * <p>
     * This is used to reset the player when entering a new level.
     * It updates the lives of the player so that they
     * start the new level with a fresh health bar.
     *
     * @param player player to be tracked
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.lives = new Lives(getWorld(), getWidth(), getHeight(), player);
        repaint();
    }

    /**
     * Handles pausing the timer.
     * <p>
     * This is used to pause the timer.
     */
    public void pauseTimer() {
        if (gameTimer != null) gameTimer.stop();
    }

    /**
     * Handles resuming the timer.
     * <p>
     * This is used to resume the timer.
     */
    public void resumeTimer() {
        if (gameTimer != null) gameTimer.start();
    }

    /**
     * Getter method for the seconds.
     * <p>
     * returns the seconds used to track the game timer.
     *
     * @return the integer seconds value.
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Setter method for the boss.
     * <p>
     * This is used to set the boss when entering the level.
     * It updates the lives of the boss so that they
     * start the new level with a fresh health bar.
     *
     * @param boss boss to be tracked
     */
    public void setBoss(Boss boss) {
        this.boss = boss;
        this.bossLives = new BossLives(getWorld(), getWidth(), getHeight(), boss, game);
    }
}
