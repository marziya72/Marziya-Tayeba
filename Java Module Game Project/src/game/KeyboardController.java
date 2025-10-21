package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * The class for all keyboard inputs.
 */
public class KeyboardController implements KeyListener {
    /**
     * The current game.
     */
    private Game game;
    /**
     * The current player.
     */
    private Player player;
    /**
     * The players movement animation.
     */
    private Movement move;
    /**
     * The walker enemy's movement.
     */
    private enemyMovement move2;

    /**
     * Initialises keyboard management.
     *
     * @param game game in which the player is in
     * @param player the player the camera follows
     * @param move the players movement animation
     */
    public KeyboardController(Game game, Player player, Movement move) {
        this.game = game;
        this.player = player;
        this.move = move;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Responds to key presses for movement and actions.
     *
     * @param e the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // for running/walking towards the right
        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.startWalking(player.getSpeed());
            if (!move.getFlagState().equals("run")) {
                move.updateSprite("run");
            }
            player.setFacingRight(true);
        // for running/walking towards the left
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.startWalking(-player.getSpeed());
            if (!move.getFlagState().equals("run")) {
                move.updateSprite("run");
            }
            player.setFacingRight(false);
        // for jumping
        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (player.getJumpCount() < player.getMaxJump()) {
                player.jump(8);
                move.updateSprite("jump");
            }
        // for crouching
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            move.setFlagState("crouch");
        }
        // for saving the game file
        if (e.getKeyCode() == KeyEvent.VK_1) {
            try {
                GameSaverLoader.save(game.getLevel(), "savefile.txt");
                System.out.println("Game saved!");
                if (!game.getLevel().equals("Level1") && move2 != null) {  // Check if move2 is initialized before using it
                    move2.setSaved(true);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // for loading the game
        if (e.getKeyCode() == KeyEvent.VK_2) {
            try {
                GameLevel loadedLevel = GameSaverLoader.load("savefile.txt", game);
                game.setLevel(loadedLevel); // used for the enemy movement class
                System.out.println("Game loaded!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Responds to key releases to stop movement or reset states.
     *
     * @param e the key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
            player.stopWalking();
            move.updateSprite("idle");
        }
        if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
            move.updateSprite("idle");
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            move.updateSprite("idle");
        }
    }

    /**
     * Updates the player instance (used when the level changes).
     *
     * @param player the new player object
     */
    public void updatePlayer (Player player) {
        this.player = player;
    }
}
