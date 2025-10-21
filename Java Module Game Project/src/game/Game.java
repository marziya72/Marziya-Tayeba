package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * My main game entry point
 */
public class Game {
    /**
     * Handles keyboard input for movement and game actions.
     */
    private KeyboardController keys;
    /**
     * The current level that's playing.
     */
    private GameLevel level;
    /**
     * To see into the world.
     */
    private UserView view;
    /**
     * To make the player move.
     */
    private Movement move;
    /**
     * So that the player can shoot.
     */
    private Shooting shooting;
    /**
     * Handles the sounds like the bgm or items sounds.
     */
    private SoundManager soundManager;
    /**
     * Handles the high score file.
     */
    private HighScoreWriter hsHandler;
    /**
     * Handles the quit, high score and next level buttons.
     */
    private ControlPanelForm cpanel;
    /**
     * Variable that tracks if the restart button is pressed.
     */
    private boolean restart;
    /**
     * Tracks the time the player starts at
     * level 2 and 3 so that the timer can reset
     * to this time rather than 0.
     */
    private int levelStartTime;
    /**
     * Tracks the score the player starts at
     * level 2 and 3 so that the score can reset
     * to this time rather than 0.
     */
    private int levelScore;
    private Boss boss;

    /**
     * Initialise a new Game.
     * <p>
     * Sets up the game level, sound manager, high score writer, camera,
     * moving and shooting, and GUI components
     */
    public Game() {
        soundManager = new SoundManager();
        hsHandler = new HighScoreWriter("Scores.txt");
        level = new Level1(this);

        this.view = new Camera(level, 800, 600, level.getPlayer(), this); // the view
        view.setFocusable(true);
        cpanel = new ControlPanelForm(this);

        final JFrame frame = new JFrame("City Game");
        frame.add(view);
        frame.add(cpanel.getMainPanel(), BorderLayout.SOUTH); // adds the three buttons at the bottom of the screen

        JLayeredPane layeredPane = new JLayeredPane(); // to add the three buttons on top of the screen
        layeredPane.setPreferredSize(new Dimension(800, 600));
        view.setBounds(0, 0, 800, 600);
        layeredPane.add(view, Integer.valueOf(0)); // game on layer 0

        MuteButton mute = new MuteButton(this, soundManager);
        JButton button = mute.getButton();
        button.setBounds(640, 21, 24, 24);
        layeredPane.add(button, Integer.valueOf(1)); // button on layer 1 (so on top of layer 0)

        PauseButton pause = new PauseButton(this, soundManager);
        JButton button2 = pause.getButton();
        button2.setBounds(610, 21, 24, 24);
        layeredPane.add(button2, Integer.valueOf(1)); // button on layer 1 (so on top of layer 0)
        frame.add(layeredPane);

        RestartButton restart = new RestartButton(this);
        JButton button3 = restart.getButton();
        button3.setBounds(580, 21, 24, 24);
        layeredPane.add(button3, Integer.valueOf(1)); // button on layer 1 (so on top of layer 0)
        frame.add(layeredPane);

        this.move = new Movement(level.getPlayer());
        this.keys = new KeyboardController(this, level.getPlayer(), move);
        view.addKeyListener(keys);
        this.shooting = new Shooting(level, level.getPlayer(), move);
        view.addMouseListener(shooting);
        level.populate1(); // level populated with the save-able interactive features

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(false);
        frame.setLocation(270, 60);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        //new DebugViewer(level, 470, 700);
        level.start();
    }

    /** Run the game. */
    public static void main(String[] args) {
        new Game();
    }

    /**
     * Handles the changing to a new level.
     * <p>
     * Once a level is completed, this method is called and loads in the next level.
     * Components like movement, shooting and camera are updated and score and time is carried over.
     * This method also handles the game completion after level 3.
     */
    public void goToNextLevel() {
        int ongoingScore = level.getPlayer().getScore();
        int currentTime = ((Camera) view).getSeconds();
        int ongoingLives = level.getPlayer().getLives();
        if (level instanceof Level1) {
            level.stop();
            level = new Level2(this);
            level.populate2(); // level populated with the save-able interactive features
        } else if (level instanceof Level2) {
            level.stop();
            level = new Level3(this);
            level.populate3(); // level populated with the save-able interactive features
        } else if (level instanceof Level3) {
            level.stop();
            level = new Level4(this);
            boss = new Boss(level);
            boss.setPosition(new Vec2(-15,6));
            new bossMovement(boss,level.getPlayer());
            ((Camera) view).setBoss(boss);
        }else if (level instanceof Level4) {
            System.out.println("Well done! Game complete");
            gameOver();
            System.exit(0);
        }
        levelStartTime = currentTime; // time to be carried over
        levelScore = ongoingScore; // score to be carried over
        view.setWorld(level);
        move.updatePlayer(level.getPlayer());
        keys.updatePlayer(level.getPlayer());
        shooting.updatePlayer(level, level.getPlayer());
        ((Camera)view).setPlayer(level.getPlayer());
        view.setCentre(level.getPlayer().getPosition());
        level.start();
        level.getPlayer().setScore(ongoingScore);
        level.getPlayer().setLives(ongoingLives);
    }

    /**
     * Getter method to retrieve current level.
     * <p>
     * This is used when other components or classes
     * require access to the current level
     *
     * @return the current {@link GameLevel} instance.
     */
    public GameLevel getLevel() {
        return level;
    }

    /**
     * Getter method for the sound manager.
     * <p>
     * Used when components and classes require access to the sound manager,
     * for sounds and bgm.
     *
     * @return the {@link SoundManager} instance.
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Getter method for the high score.
     * <p>
     * Handles writing to and retrieving scores and times,
     * from the file it is stored in.
     * It is also used within {@link ControlPanelForm} for the
     * high score panel.
     *
     * @return the {@link HighScoreWriter} instance.
     */
    public HighScoreWriter getHighScoreHandler() {
        return hsHandler;
    }

    /**
     * Handles the game over functions.
     * <p>
     * Once a level 3 is completed, this method is called and loads in an
     * input dialog to log the players name, score and final time.
     */
    public void gameOver() {
        String playerName = JOptionPane.showInputDialog("Enter your name");
        try {
            hsHandler.writeHighScore(playerName, getLevel().getPlayer().getScore(), getLevel().getPlayer().getTimerText());
            System.out.println(level.getPlayer().getScore());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getter method for the view.
     * <p>
     * This is to give access to components and classes
     * that require the view.
     *
     * @return the {@link UserView} instance.
     */
    public UserView getView() {
        return view;
    }

    /**
     * Handles setting the level.
     * <p>
     * This is used to load in a new level.
     * It updates all components for the new level.
     *
     * @param level level to be started
     */
    public void setLevel(GameLevel level) {
        this.level.stop();
        this.level = level;
        view.setWorld(level);
        move.updatePlayer(level.getPlayer());
        keys.updatePlayer(level.getPlayer());
        shooting.updatePlayer(level, level.getPlayer());
        ((Camera)view).setPlayer(level.getPlayer());
        view.setCentre(level.getPlayer().getPosition());
        this.level.start();
    }

    /**
     * Getter method for the control panel.
     * <p>
     * Retrieves the control panel for the classes
     * and components that require access to it.
     *
     * @return the {@link ControlPanelForm} instance.
     */
    public ControlPanelForm getControlPanel() {
        return cpanel;
    }

    /**
     * Handles pausing the level.
     * <p>
     * This is used when the pause button is pressed.
     * When called, it stops the current level.
     */
    public void pause() {
        level.stop();
    }

    /**
     * Handles resuming the level.
     * <p>
     * This is used when the resume button is pressed.
     * When called, it starts the current level again.
     */
    public void resume() {
        level.start();
    }

    /**
     * Handles restarting the level.
     * <p>
     * This is used mainly when the restart button is pressed
     * so that the current level is reloaded and started again.
     */
    public void restart() {
        if (level instanceof Level1) {
            Level1 newLevel = new Level1(this);
            newLevel.populate1();
            setLevel(newLevel);
        } else if (level instanceof Level2) {
            Level2 newLevel = new Level2(this);
            newLevel.populate2();
            setLevel(newLevel);
            level.getPlayer().setScore(getOngoingScore());
        } else if (level instanceof Level3) {
            Level3 newLevel = new Level3(this);
            newLevel.populate3();
            setLevel(newLevel);
            level.getPlayer().setScore(getOngoingScore());
        } else if (level instanceof Level4) {
            System.out.println("Unavailable");
        }
    }

    /**
     * Setter method for the restart variable.
     * <p>
     * When called, it sets the boolean flag and indicates a restart.
     *
     * @param restart boolean value used to indicate a restart
     */
    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    /**
     * Getter method for the restart variable.
     * <p>
     * Tracks whether the restart button is pressed (true) or not (false).
     * This is used mainly within {@link RestartButton}
     *
     * @return the boolean restart variable.
     */
    public boolean isRestart() {
        return restart;
    }

    /**
     * Getter method for accessing the time.
     * <p>
     * This tracks the time the player starts at level 2 and 3,
     * so that if they press the restart button at said levels,
     * they start from the time they entered that level and not 0.
     *
     * @return the levelStartTime variable.
     */
    public int getLevelStartTime() {
        return levelStartTime;
    }

    /**
     * Getter method for accessing the score.
     * <p>
     * This tracks the score the player starts at level 2 and 3,
     * so that if they press the restart button at said levels,
     * they start from the score they entered that level and not 0.
     *
     * @return the levelScore variable.
     */
    public int getOngoingScore() {
        return levelScore;
    }
}
