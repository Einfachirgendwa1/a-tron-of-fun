import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.Optional;

public class Misc {
    public static final int worldWidth = 600;
    public static final int worldHeight = 400;
    public static final GreenfootImage blank = new GreenfootImage("images/blank.png");
    private static final boolean debug = false;
    private static GameSelection gameSelection;
    private static BaseWorld currentWorld = null;

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
        } else {
            gameSelection.exitMinigame();
        }
    }

    public static void enterMinigame(int id) {
        gameSelection.enterMinigame(id);
    }

    public static BaseWorld getCurrentWorld() {
        return currentWorld;
    }

    public static Optional<Point> mousePosition() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return Optional.empty();
        return Optional.of(new Point(mouseInfo.getX(), mouseInfo.getY()));
    }

    public static void debugPrint(String message) {
        if (debug) System.out.println(message);
    }

    public static <T extends Actor> T addObject(T actor, int x, int y) {
        currentWorld.addObject(actor, x, y);
        System.out.println("Creating " + actor.getClass().getSimpleName() + " onto " + currentWorld.getClass().getSimpleName());
        return actor;
    }

    public static <T extends Actor> T addObject(T actor, IGetVector2 vector) {
        Vector2 position = vector.position();
        return addObject(actor, Math.round(position.x()), Math.round(position.y()));
    }

    public static Optional<Double> angleToMouse(IGetVector2 start) {
        return mousePosition().map(point -> start.position().angle(point));

    }

}
