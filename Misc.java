import greenfoot.World;

public class Misc {
    private static GameSelection gameSelection;
    private static World currentWorld;

    public static void setWorld(World world) {
        currentWorld = world;

        if (world instanceof GameSelection) {
            gameSelection = (GameSelection) world;
        }
    }

    public static void exitMinigame() {
        gameSelection.exitMinigame();
    }

    public static World getCurrentWorld() {
        return currentWorld;
    }
}
