package game;

/**
 * handles sorting the high score and times for the high score panel.
 */
public class Sorting implements Comparable<Sorting> {
    /**
     * The string name of the player.
     */
    private String name;
    /**
     * The int score of the player.
     */
    private int score;
    /**
     * The int time the player took.
     */
    private int seconds;

    /**
     * Initialises a new sorting method.
     *
     * @param name the name of the player
     * @param score the score of the player
     * @param seconds the time it took the player to complete the whole game
     */
    public Sorting(String name, int score, int seconds) {
        this.name = name;
        this.score = score;
        this.seconds = seconds;
    }

    /**
     * Compares this Sorting object to another for sorting purposes.
     * Players with higher scores rank higher. If scores are equal,
     * players with lower time (faster completion) rank higher.
     *
     * @param other the other Sorting object to compare to
     * @return a negative integer, zero, or a positive integer if this object
     *         should come before, is equal to, or comes after the specified object
     */
    public int compareTo(Sorting other) {
        if (this.score != other.score) {
            return Integer.compare(other.score, this.score);
        } else {
            return Integer.compare(this.seconds, other.seconds);
        }
    }

    /**
     * Returns a formatted display string for this high score entry.
     *
     * @return a string for the high score panel
     */
    public String toDisplayString() {
        return name + ": " + score + " orbs in " + String.format("%02d:%02d", seconds / 60, seconds % 60);
    }
}
