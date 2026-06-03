import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class LevelLoader {
    public static Path path(Class<? extends BaseWorld> clazz, String tag) {
        return Path.of(clazz.getName() + "." + tag + "Data");
    }

    public static ArrayList<Line> getLevelData(Class<? extends BaseWorld> level) {
        return getLevelData(path(level, "level"));
    }

    public static ArrayList<Line> getLevelData(Path path) {
        ArrayList<Line> levelData = new ArrayList<>();

        try {
            for (String line : Files.readAllLines(path)) {
                if (line.isBlank()) continue;

                Object[] data = Arrays.stream(line.split(",")).map(Integer::parseInt).toArray();
                Point2D a = new Point2D((Integer) data[0], (Integer) data[1]);
                Point2D b = new Point2D((Integer) data[2], (Integer) data[3]);

                levelData.add(new Line(a, b));
            }
        } catch (IOException e) {
            Misc.debugPrint("No level data found for " + path);
        }

        return levelData;
    }
}
