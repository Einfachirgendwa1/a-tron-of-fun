import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Handelt den aktuellen Score und den Highscore.
 * Der Highscore wird in high_score.txt gespeichert.
 *
 * @author Faris
 * @see Score
 */
public abstract class ScoreTracker {
    private static final Path highScoreFile = Path.of("high_score.txt");
    private static int score;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        ScoreTracker.score = score;
    }

    public static void addScore(int score) {
        ScoreTracker.score += score;
    }

    /**
     * Liest den gespeicherten Highscore.
     *
     * @return Der aktuell gespeicherte Highscore oder 0, wenn noch keiner existiert.
     */
    public static int readHighScore() {
        try {
            return Integer.parseInt(Files.readString(highScoreFile));
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * Speichert den aktuellen Score als neuen Highscore.
     * Macht nichts, wenn der aktuelle Score kleiner ist als der Highscore.
     */
    public static void saveHighScore() {
        if (score <= readHighScore()) return;

        try {
            Files.writeString(highScoreFile, Integer.toString(score));
        } catch (IOException e) {
            System.out.println("Failed to write highscore: " + e);
        }
    }
}
