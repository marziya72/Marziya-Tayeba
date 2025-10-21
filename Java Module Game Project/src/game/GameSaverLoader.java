package game;

import city.cs.engine.Body;
import org.jbox2d.common.Vec2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The game saver/loader class.
 */
public class GameSaverLoader {
    /**
     * the current player.
     */
    private static Player player;

    /**
     * saves to the file.
     * <p>
     * The name, score, lives and position of player is written to the file, separated by commas.
     * For each dynamic body that I want to save, their positions and other relevant data is saved.
     * The same is done for all the static bodies.
     *
     * @param level The current {@link GameLevel} instance.
     * @param fileName file that holds the name and score
     * @throws IOException if an I/O error occurs during writing.
     */
    public static void save(GameLevel level, String fileName) throws IOException {
        FileWriter writer = null;
        try {
            // open file + get name and score of players
            writer = new FileWriter(fileName, false);
            String levelName = level.getLevelName();
            int score = level.getPlayer().getScore();
            int lives = level.getPlayer().getLives();
            Vec2 posP = level.getPlayer().getPosition();

            // write data to file
            writer.write(levelName + "," + score + "," + lives + "," + posP.x + "," + posP.y + "\n");

            for (Body body : level.getDynamicBodies()) {
                if (body instanceof Enemy) {
                    Vec2 pos = body.getPosition();
                    Enemy enemy = (Enemy) body;
                    writer.write("Enemy," + pos.x + "," + pos.y + "," + enemy.getLeft() + "," + enemy.getRight() + "\n");
                } else if (body instanceof Enemy2) {
                    Vec2 pos = body.getPosition();
                    writer.write("Enemy2," + pos.x + "," + pos.y + "\n");
                } else if (body instanceof Orb) {
                    Vec2 pos = body.getPosition();
                    Orb orb = (Orb) body;
                    writer.write("Orb," + pos.x + "," + pos.y + "," + orb.isCollected() + "\n");
                }
            }
            for (Body body : level.getStaticBodies()) {
                if (body instanceof OrbCollectible) {
                    Vec2 pos = body.getPosition();
                    OrbCollectible orb = (OrbCollectible) body;
                    writer.write("OrbCollectible," + pos.x + "," + pos.y + "," + orb.isCollected() + "\n");
                } else if (body instanceof ButtonOrb) {
                    Vec2 pos = body.getPosition();
                    ButtonOrb button = (ButtonOrb) body;
                    writer.write("ButtonOrb," + pos.x + "," + pos.y + "," + button.isPressed() + "\n");
                }  else if (body instanceof Shield) {
                    Vec2 pos = body.getPosition();
                    Shield shield = (Shield) body;
                    writer.write("Shield," + pos.x + "," + pos.y + "," + shield.isCollected() + "\n");
                }
            }
        } finally {
            // close file
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Loads a saved game from a file.
     * <p>
     * Reads the level name,score, lives and player positions and reloads the correct level instance.
     *
     * @param fileName The name of the file to load from.
     * @param game The current {@link Game} instance .
     * @return A {@link GameLevel} with the loaded state, or null if the loading fails.
     * @throws IOException if an I/O error occurs during reading.
     */
    public static GameLevel load(String fileName, Game game) throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(fileName); // open the file for reading
            reader = new BufferedReader(fr);
            String line = reader.readLine(); // read the line

            if (line != null) {
                String[] tokens = line.split(","); // split the string using commas
                String levelName = tokens[0];
                int score = Integer.parseInt(tokens[1]);
                int lives = Integer.parseInt(tokens[2]);
                float playerX = Float.parseFloat(tokens[3]);
                float playerY = Float.parseFloat(tokens[4]);
                Vec2 playerPos = new Vec2(playerX, playerY);

                GameLevel level;

                // chooses level based on name
                if (levelName.equals("Level1")) {
                    level = new Level1(game);
                } else if (levelName.equals("Level2")) {
                    level = new Level2(game);
                } else if (levelName.equals("Level3")) {
                    level = new Level3(game);
                } else {
                    throw new IllegalArgumentException("Unknown level: " + levelName);
                }
                level.getPlayer().setScore(score); // set the players score
                level.getPlayer().setLives(lives); // set the players lives
                level.getPlayer().setPosition(playerPos); // set the players position

                while ((line = reader.readLine()) != null) {
                    tokens = line.split(",");
                    String type = tokens[0];
                    float x = Float.parseFloat(tokens[1]);
                    float y = Float.parseFloat(tokens[2]);
                    switch (type) {
                        case "Enemy":
                            float left = Float.parseFloat(tokens[3]);
                            float right = Float.parseFloat(tokens[4]);
                            Enemy enemy = new Enemy(level);
                            enemy.setPosition(new Vec2(x, y));
                            enemyMovement move = new enemyMovement(enemy, level.getPlayer(), true);
                            enemy.setLeft(left);
                            enemy.setRight(right);
                            break;
                        case "Enemy2":
                            Enemy2 enemy2 = new Enemy2(level);
                            enemy2.setPosition(new Vec2(x,y));
                            new enemy2Movement(enemy2, level.getPlayer());
                            break;
                        case "OrbCollectible":
                            boolean orbCollected = Boolean.parseBoolean(tokens[3]);
                            OrbCollectible orbCollectible = new OrbCollectible(level, game,(int) x,(int) y);
                            orbCollectible.setCollected(orbCollected);
                            break;
                        case "Orb":
                            boolean orbPCollected = Boolean.parseBoolean(tokens[3]);
                            Orb orb = new Orb(level, game,(int) x,(int) y);
                            orb.setCollected(orbPCollected);
                            break;
                        case "Shield":
                            boolean shieldCollected = Boolean.parseBoolean(tokens[3]);
                            Shield shield = new Shield(level, game,(int) x,(int) y);
                            shield.setCollected(shieldCollected);
                            break;
                        case "ButtonOrb":
                            boolean buttonPressed = Boolean.parseBoolean(tokens[3]);
                            ButtonOrb buttonOrb = new ButtonOrb(level, game, (int) x, (int) y);
                            buttonOrb.setPressed(buttonPressed);
                            break;
                    }
                }
                return level;
            }
        } finally {
            // close both the reader and file
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        return null;
    }
}
