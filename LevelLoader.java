import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class LevelLoader {
    public static Path path(Class<? extends BaseWorld> clazz) {
        return Path.of(clazz.getName() + ".levelData");
    }

    public static ArrayList<Line> getLevelData(Class<? extends BaseWorld> level) {
        ArrayList<Line> levelData = new ArrayList<>();

        try {
            for (String line : Files.readAllLines(path(level))) {
                if (line.isBlank()) continue;

                Object[] data = Arrays.stream(line.split(",")).map(Integer::parseInt).toArray();
                Point a = new Point((Integer) data[0], (Integer) data[1]);
                Point b = new Point((Integer) data[2], (Integer) data[3]);

                levelData.add(new Line(a, b));
            }
        } catch (IOException e) {
            Misc.debugPrint("No level data found for " + level.getSimpleName());
        }

        return levelData;
    }
}
