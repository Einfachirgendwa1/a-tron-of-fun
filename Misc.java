import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.Optional;
import java.util.function.Function;

public class Misc {
    public static final int worldWidth = 600;
    public static final int worldHeight = 400;
    public static final GreenfootImage blank = new GreenfootImage("images/blank.png");
    public static final int wallThickness = 3;
    private static final boolean debug = false;
    private static GameSelection gameSelection;
    private static BaseWorld currentWorld = null;

    public static void reloadGameSelection() {
        gameSelection = new GameSelection();
        loadWorld(gameSelection);
    }

    public static void setWorld(BaseWorld world) {
        currentWorld = world;

        if (world instanceof GameSelection) {
            gameSelection = (GameSelection) world;
        }
    }

    public static void loadWorld(BaseWorld world) {
        Greenfoot.setWorld(world);
        setWorld(world);
    }

    public static void exitMinigame() {
        if (gameSelection == null) {
            gameSelection = new GameSelection();
            Misc.loadWorld(gameSelection);
        } else {
            gameSelection.exitMinigame();
        }
    }

    public static BaseWorld getCurrentWorld() {
        return currentWorld;
    }

    public static Optional<Point2D> mousePosition() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return Optional.empty();
        return Optional.of(new Point2D(mouseInfo.getX(), mouseInfo.getY()));
    }

    public static void debugPrint(String message) {
        if (debug) System.out.println(message);
    }

    public static <T extends Actor> T addObject(T actor, int x, int y) {
        currentWorld.addObject(actor, x, y);
        return actor;
    }

    public static <T extends Actor> T addObject(T actor, Position2D vector) {
        Point2D position = new Point2D(vector.vec());
        return addObject(actor, position.x(), position.y());
    }

    public static Optional<Double> angleToMouse(Position2D start) {
        return mousePosition().map(point -> start.vec().angle(point));
    }

    public static Function<Vector2D, Position2D> centeredAround(Vector2D center) {
        return dimensions -> center.minus(dimensions.multiply(.5f));
    }
}
