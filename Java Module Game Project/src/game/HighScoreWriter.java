package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * handles writing and reading high scores from a file.
 */
public class HighScoreWriter {
    /**
     * The file name to read/write high scores from.
     */
    private String fileName;

    /**
     * Constructs a new HighScoreWriter with the given file.
     *
     * @param fileName the name of the file used for storing high scores
     */
    public HighScoreWriter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Writes a single high score entry to the file.
     *
     * @param name the name of the player
     * @param score the player's score
     * @param timerText the time taken
     * @throws IOException if writing to the file fails
     */
    public void writeHighScore(String name, int score, String timerText) throws IOException {
        boolean append = true;
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, append);
            writer.write(name + "," + score + "," + timerText + "\n"); // write to the file
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * For testing the high score writing.
     *
     * @param args command-line arguments
     * @throws IOException if writing to the file fails
     */
    public static void main(String[] args) throws IOException {
        HighScoreWriter hsWriter = new HighScoreWriter("sample.hs"); // sample/test file
        for (int i = 0; i < args.length; i += 2) {
            String name = args[i];
            int score = Integer.parseInt(args[i + 1]);
            String timerText = String.join(args[i + 2]);
            hsWriter.writeHighScore(name, score, timerText);
        }
    }

    /**
     * Reads all high score entries from the file
     * and converts them into {@link Sorting} objects.
     *
     * @return a list of score entries
     * @throws IOException if reading from the file fails
     */
    public ArrayList<Sorting> readScores() throws IOException {
        ArrayList<Sorting> entries = new ArrayList<>(); // array list for sorted items
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 3) { // so only entries with three parts are submitted
                    String name = tokens[0];
                    int score = Integer.parseInt(tokens[1]);
                    String[] timeParts = tokens[2].split(":");
                    int seconds = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
                    entries.add(new Sorting(name, score, seconds));
                }
            }
        }
        return entries;
    }
}
