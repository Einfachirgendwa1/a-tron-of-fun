import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import greenfoot.World;

import java.util.Optional;

public class Misc {
    public static final int worldWidth = 600;
    public static final int worldHeight = 400;
    public static final GreenfootImage blank = new GreenfootImage("images/blank.png");
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private static final boolean debug = false;
    private static GameSelection gameSelection;
    private static World currentWorld = null;

    public static void setWorld(World world) {
        currentWorld = world;

        if (world instanceof GameSelection) {
            gameSelection = (GameSelection) world;
        }
    }

    public static void loadWorld(World world) {
        Greenfoot.setWorld(world);
        setWorld(world);
    }

    public static boolean flag(int flags, int flag) {
        return (flags & flag) != 0;
    }

    public static boolean horizontal(int flags) {
        return flag(HORIZONTAL, flags);
    }

    public static boolean vertical(int flags) {
        return flag(VERTICAL, flags);
    }

    public static void applyRotationFlags(GreenfootImage image, int flags) {
        if (horizontal(flags)) image.mirrorHorizontally();
        if (vertical(flags)) image.mirrorVertically();
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

    public static World getCurrentWorld() {
        return currentWorld;
    }

    public static Optional<Vector2> mousePosition() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return Optional.empty();
        return Optional.of(new Vector2(mouseInfo.getX(), mouseInfo.getY()));
    }

    public static void debugPrint(String message) {
        if (debug) System.out.println(message);
    }
}
