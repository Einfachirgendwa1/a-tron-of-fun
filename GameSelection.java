import greenfoot.Greenfoot;
import greenfoot.World;

public class GameSelection extends World {
    private final MiniGame[] miniGames = new MiniGame[]{new LightCycles()};
    private final GameSelectionPlayer player;

    public GameSelection() {
        super(600, 400, 1);

        player = new GameSelectionPlayer(this);
        addObject(player, 300, 200);
    }

    public void enterMinigame(int minigameIndex) {
        MiniGame miniGame = miniGames[minigameIndex];
        if (miniGame == null) return;

        World world = miniGame.createWorld();
        if (world == null) {
            System.err.println("Error: minigame did not create a world!");
            return;
        }

        Greenfoot.setWorld(world);

    }
}
