import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.World;

public class Misc {
    public static final int worldWidth = 600;
    public static final int worldHeight = 400;
    private static GameSelection gameSelection;
    private static World currentWorld;

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

    public static boolean mouseClicked() {
        if (Greenfoot.getMouseInfo() == null) return false;
        return Greenfoot.getMouseInfo().getClickCount() != 0;
    }

    public static Vector2 mousePosition() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        return new Vector2(mouseInfo.getX(), mouseInfo.getY());
    }

}
