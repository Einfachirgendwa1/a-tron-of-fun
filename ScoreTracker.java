import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public static int readHighScore() {
        try {
            return Integer.parseInt(Files.readString(highScoreFile));
        } catch (IOException e) {
            return 0;
        }
    }

    public static void saveHighScore() {
        if (score <= readHighScore()) return;

        try {
            Files.writeString(highScoreFile, Integer.toString(score));
        } catch (IOException e) {
            System.out.println("Failed to write highscore: " + e);
        }
    }
}
