import greenfoot.Actor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Die zugehörige Welt für das TankLabyrinth Minispiel.
 *
 * @author Faris
 * @see TankLabyrinthEnemy
 * @see TankLabyrinthPlayer
 */
public class TankLabyrinthWorld extends BaseWorld {
    public static final Line2D[] pathData = LevelLoader.getLevelData(LevelLoader.filePath(
        TankLabyrinthWorld.class,
        "path"
    )).toArray(Line2D[]::new);

    public static final Hashtable<Point2D, ArrayList<Point2D>> paths = new Hashtable<>();
    static final List<Point2D> nodes;
    private final ArrayList<TankLabyrinthEnemy> enemies = new ArrayList<>();
    private final TankLabyrinthPlayer player;

    static {
        for (Line2D line : pathData) {
            //noinspection unused
            paths.computeIfAbsent(line.start(), k -> new ArrayList<>());

            //noinspection unused
            paths.computeIfAbsent(line.end(), k -> new ArrayList<>());

            paths.get(line.start()).add(line.end());
            paths.get(line.end()).add(line.start());
        }

        nodes = new ArrayList<>(paths.keySet());
    }

    public TankLabyrinthWorld() {
        super();

        player = Misc.addObject(new TankLabyrinthPlayer(), new Point2D(351, 97));

        addEnemy(new Point2D(472, 216));
        addEnemy(new Point2D(206, 332));
        addEnemy(new Point2D(247, 55));
    }

    private void addEnemy(Point2D pos) {
        enemies.add(Misc.addObject(new TankLabyrinthEnemy(new BreadthFirstSearch(), player), pos));
    }

    @Override
    public void removeObject(Actor object) {
        if (object instanceof TankLabyrinthEnemy) {
            enemies.remove(object);
        }

        super.removeObject(object);
    }

    @Override
    public void act() {
        super.act();
        if (enemies.isEmpty()) {
            won();
        }
    }
}
