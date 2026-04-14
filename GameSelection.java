import greenfoot.Greenfoot;
import greenfoot.World;

public class GameSelection extends World {
    public static GameSelection instance;

    private final World[] miniGames = new World[]{new LightCyclesWorld(), new ConeShooterWorld(), new GridBugsWorld(), new TankLabyrinthWorld()};
    private final GameSelectionPlayer player;

    public GameSelection() {
        super(600, 400, 1);

        instance = this;

        player = new GameSelectionPlayer(this);
        addObject(player, 300, 200);
    }

    public void enterMinigame(int minigameIndex) {
        Greenfoot.setWorld(miniGames[minigameIndex]);
    }

    public void exitMinigame() {
        Greenfoot.setWorld(this);
        player.setLocation(300, 200);
    }
}
